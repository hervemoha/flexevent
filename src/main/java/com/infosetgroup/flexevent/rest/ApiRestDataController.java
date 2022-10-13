package com.infosetgroup.flexevent.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosetgroup.flexevent.dto.*;
import com.infosetgroup.flexevent.entity.*;
import com.infosetgroup.flexevent.repository.*;
import com.infosetgroup.flexevent.security.SecurityParams;
import com.infosetgroup.flexevent.util.FlexEventUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/rest/public")
public class ApiRestDataController {

    @Autowired
    private AppAccountRepository appAccountRepository;

    @Autowired
    private CategoryDetailCategoryRepository categoryDetailCategoryRepository;

    @Autowired
    private CategoryDetailRepository categoryDetailRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private ItemPictureRepository itemPictureRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PaymentWayRepository paymentWayRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(value = "/v1/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseHomeDTO> getHomeCategories() {
        Configuration configuration = this.configurationRepository.findByActivated(true);
        ResponseHomeDTO response = new ResponseHomeDTO();
        response.setMessage(configuration.getHomeWording());

        List<Category> categories = this.categoryRepository.findByActivatedOrderByWeightAsc(true);
        categories.forEach(
                category -> {
                    response.getCategories().add(new CategoryDTO(category.getCode(), category.getName(), category.getWording(), configuration.getWebsiteUrl()+category.getCode()+"/"+category.getIcon()));
                }
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/category/items/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getItemsByCategory(@PathVariable String code) {
        Configuration configuration = this.configurationRepository.findByActivated(true);
        GetFavoriteResponse response = new GetFavoriteResponse();

        Category category = this.categoryRepository.findByCode(code);
        if (category == null) {
            response.setStatus(false);
            response.setMessage("La catégorie n'a pas été retrouvée");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<Item> items = this.itemRepository.findAllByCategory(category);
        items.forEach(
                item -> {
                    ItemDTO dto = new ItemDTO();
                    dto.setCode(item.getCode());
                    dto.setDescription(item.getDescription());
                    dto.setAdress(item.getAdress());
                    dto.setCity(item.getCity().getName());
                    dto.setCountry(item.getCountry().getName());
                    dto.setDate(item.createdDateToString());
                    dto.setIcon(configuration.getWebsiteUrl()+item.getCode()+"/"+ item.getThumbnail() + ".png");
                    dto.setName(item.getName());

                    item.getPictures().forEach(
                            itemPicture -> {
                                dto.getPictures().add(configuration.getWebsiteUrl()+item.getCode()+"/"+ itemPicture.getCode()+".png");
                            }
                    );

                    item.getSpecifications().forEach(
                            specificationItem -> {
                                String v = specificationItem.getValue1().equalsIgnoreCase("") ? specificationItem.getValue2() +"" :"";
                                dto.getSpecifications().add(specificationItem.getItemSpecification().getName() + "-" + v);
                            }
                    );

                    item.getTags().forEach(
                            tag -> {
                                dto.getTags().add(tag.getCode());
                            }
                    );

                    item.getPrices().forEach(
                            price -> {
                                if (price.isActivated()) {
                                    dto.setPrice(price.getAmount()+"");
                                    dto.setPartialPrice(price.getPartial()+"");
                                }
                                dto.setCurrency(price.getCurrency().getName()+"");
                            }
                    );

                    response.getItems().add(dto);
                }
        );

        response.setStatus(true);
        response.setMessage("Les items");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getItems(@RequestParam(value = "page", defaultValue = "1") int page) {
        Configuration configuration = this.configurationRepository.findByActivated(true);
        GetFavoriteResponse response = new GetFavoriteResponse();

        page = page -1;

        Page<Item> items = this.itemRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
        items.forEach(
                item -> {
                    ItemDTO dto = new ItemDTO();
                    dto.setCode(item.getCode());
                    dto.setDescription(item.getDescription());
                    dto.setAdress(item.getAdress());
                    dto.setCity(item.getCity().getName());
                    dto.setCountry(item.getCountry().getName());
                    dto.setDate(item.createdDateToString());
                    dto.setIcon(configuration.getWebsiteUrl()+item.getCode()+"/"+ item.getThumbnail() + ".png");
                    dto.setName(item.getName());

                    item.getPictures().forEach(
                            itemPicture -> {
                                dto.getPictures().add(configuration.getWebsiteUrl()+item.getCode()+"/"+ itemPicture.getCode()+".png");
                            }
                    );

                    item.getSpecifications().forEach(
                            specificationItem -> {
                                String v = specificationItem.getValue1().equalsIgnoreCase("") ? specificationItem.getValue2() +"" :"";
                                dto.getSpecifications().add(specificationItem.getItemSpecification().getName() + "-" + v);
                            }
                    );

                    item.getTags().forEach(
                            tag -> {
                                dto.getTags().add(tag.getCode());
                            }
                    );

                    item.getPrices().forEach(
                            price -> {
                                if (price.isActivated()) {
                                    dto.setPrice(price.getAmount()+"");
                                    dto.setPartialPrice(price.getPartial()+"");
                                }
                                dto.setCurrency(price.getCurrency().getName()+"");
                            }
                    );

                    response.getItems().add(dto);
                }
        );

        response.setStatus(true);
        response.setMessage("Les items");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/item/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getItemByCode(@PathVariable String code) {
        Configuration configuration = this.configurationRepository.findByActivated(true);
        GetItemResponse response = new GetItemResponse();

        Item item = this.itemRepository.findByCode(code);
        if (item == null) {
            response.setStatus(false);
            response.setMessage("L'article n'a pas été retrouvé");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            ItemDTO dto = new ItemDTO();
            dto.setCode(item.getCode());
            dto.setDescription(item.getDescription());
            dto.setAdress(item.getAdress());
            dto.setCity(item.getCity().getName());
            dto.setCountry(item.getCountry().getName());
            dto.setDate(item.createdDateToString());
            dto.setIcon(configuration.getWebsiteUrl()+item.getCode()+"/"+ item.getThumbnail() + ".png");
            dto.setName(item.getName());

            item.getPictures().forEach(
                    itemPicture -> {
                        dto.getPictures().add(configuration.getWebsiteUrl()+item.getCode()+"/"+ itemPicture.getCode()+".png");
                    }
            );

            item.getSpecifications().forEach(
                    specificationItem -> {
                        String v = specificationItem.getValue1().equalsIgnoreCase("") ? specificationItem.getValue2() +"" :"";
                        dto.getSpecifications().add(specificationItem.getItemSpecification().getName() + "-" + v);
                    }
            );

            item.getTags().forEach(
                    tag -> {
                        dto.getTags().add(tag.getCode());
                    }
            );

            item.getPrices().forEach(
                    price -> {
                        if (price.isActivated()) {
                            dto.setPrice(price.getAmount()+"");
                            dto.setPartialPrice(price.getPartial()+"");
                        }
                        dto.setCurrency(price.getCurrency().getName()+"");
                    }
            );

            response.setStatus(true);
            response.setItem(dto);
            response.setMessage("Un article a été retrouvé");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryDTO>> getCountries() {
        List<CountryDTO> response = new ArrayList<>();
        this.countryRepository.findByActivatedOrderByNameAsc(true).forEach(
                country -> {
                    response.add(new CountryDTO(country.getCode(), country.getCallingCode(), country.getName()));
                }
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/cities/{country}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCities(@PathVariable("country") String countryCode) {
        if (countryCode.equalsIgnoreCase("")) {
            GlobalResponseDTO responseDTO = new GlobalResponseDTO(false, "Country code is empty");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        Country country = this.countryRepository.findByCode(countryCode);
        if (country == null) {
            GlobalResponseDTO responseDTO = new GlobalResponseDTO(false, "The country was not found");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        List<CityDTO> response = new ArrayList<>();
        this.cityRepository.findAllByCountryAndActivatedOrderByNameAsc(country, true).forEach(
                city -> {
                    response.add(new CityDTO(city.getCode(), city.getName()));
                }
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/inscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> inscription(@RequestBody InscriptionRequestDTO requestDTO, HttpServletRequest request) {
        try {
            if (requestDTO.getFirstname().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Firstname is empty", "firstname");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (requestDTO.getLastname().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Lastname is empty", "lastname");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (requestDTO.getPhone().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Phone number is empty", "phone");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (requestDTO.getPhoneCountry().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Phone country code is empty", "phoneCountry");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (requestDTO.getUsername().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Username is empty", "username");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (requestDTO.getPassword1().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Password is empty", "password1");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (requestDTO.getPassword2().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Verification password is empty", "password2");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (requestDTO.getCityCode().equalsIgnoreCase("")) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "City code is empty", "cityCode");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            if (!requestDTO.getPassword1().equalsIgnoreCase(requestDTO.getPassword2())) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Password and verification password is not equals", "password1");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            Country country = this.countryRepository.findByCode(requestDTO.getPhoneCountry());
            if (country == null) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "The country of the phone was not found", "phoneCountry");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            City city = this.cityRepository.findByCode(requestDTO.getCityCode());
            if (city == null) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "The city was not found", "cityCode");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            AppAccount account = this.appAccountRepository.findByUsername(requestDTO.getUsername());
            if (account != null) {
                GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Username is already taken", "username");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            // Configuration
            Configuration configuration = this.configurationRepository.findByActivated(true);

            // Role
            Role roleCustomer = this.roleRepository.findByType(3);

            //Account
            String accountCode = RandomStringUtils.randomAlphanumeric(20);
            AppAccount appAccount = new AppAccount();
            appAccount.setActivated(true);
            appAccount.setCode(accountCode);
            appAccount.setUsername(requestDTO.getUsername());
            appAccount.setPassword(passwordEncoder.encode(requestDTO.getPassword1()));
            appAccount.addRole(roleCustomer);
            this.appAccountRepository.save(appAccount);

            // Inscription
            Inscription inscription = new Inscription();
            inscription.setCode(RandomStringUtils.randomAlphanumeric(20));
            inscription.setCity(city);
            inscription.setPhone(requestDTO.getPhone());
            inscription.setCountry(city.getCountry());
            inscription.setFirstname(requestDTO.getFirstname());
            inscription.setLastname(requestDTO.getLastname());
            inscription.setEmail(requestDTO.getEmail());
            inscription.setPhoneCountry(country);
            inscription.setAppAccount(appAccount);
            this.inscriptionRepository.save(inscription);

            // Send SMS
            if (configuration.isSendSms()) {
                String token = configuration.getSmsToken();
                String from = configuration.getSmsName();
                String to = country.getCallingCode()+requestDTO.getPhone();
                String text = configuration.getSmsWelcomeMessage();
                String status = FlexEventUtils.sendSms(token, from, to, text);
                System.out.println("Status :*"+status+"*");
            }

            // Token
            List<String> roles = new ArrayList<>();
            roles.add(roleCustomer.getName());
            String jwt = JWT.create()
                    .withIssuer(request.getRequestURI())
                    .withSubject(requestDTO.getUsername())
                    .withArrayClaim("roles", roles.toArray(new String[0]))
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityParams.EXPIRATION))
                    .sign(Algorithm.HMAC256(SecurityParams.SECRET));

            GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(true, configuration.getWelcomeMessage(), "");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Autorization", jwt);
            headers.set("customer", accountCode);
            headers.set("username", requestDTO.getUsername());
            headers.set("name", requestDTO.getLastname() + " " + requestDTO.getFirstname());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(responseDTO);
        }catch (Exception exception) {
            exception.printStackTrace();
            GlobalResponseFieldDTO responseDTO = new GlobalResponseFieldDTO(false, "Une erreur lors de l'inscription", "");
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }

    @PostMapping("/v1/callback/notification")
    public ResponseEntity<Object> callbackNotification(HttpServletRequest request) throws IOException {
        try {
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            System.out.println("/v1/callback/notification : " + formatter.format(now));

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String data = buffer.toString();
            System.out.println(data);

            CallbackDto body = new ObjectMapper().readValue(data, CallbackDto.class);
            Order order = this.orderRepository.findByCode(body.getReference());
            if (order != null) {
                if (!order.isClosed() && !order.isPaid()) {
                    if (body.getCode().equalsIgnoreCase("0")) {
                        order.setPaid(true);
                        order.setClosed(true);
                        order.setStep(5);
                        this.orderRepository.save(order);
                    }else {
                        order.setPaid(false);
                        order.setClosed(true);
                        order.setStep(4);
                        this.orderRepository.save(order);
                    }
                }
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
