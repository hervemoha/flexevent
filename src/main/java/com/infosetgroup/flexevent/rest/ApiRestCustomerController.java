package com.infosetgroup.flexevent.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosetgroup.flexevent.dto.*;
import com.infosetgroup.flexevent.entity.*;
import com.infosetgroup.flexevent.repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/rest/sec")
public class ApiRestCustomerController {

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

    @Autowired
    private ProviderFormatRepository providerFormatRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @GetMapping(value = "/v1/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAccount(Principal principal) {
        GetAccountDTO response = new GetAccountDTO();
        try {
            Configuration configuration = this.configurationRepository.findByActivated(true);
            System.out.println("Principal user : " + principal.getName());
            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setStatus(true);
            response.setFirstname(account.getInscription().getFirstname());
            response.setLastname(account.getInscription().getLastname());
            response.setUsername(account.getUsername());
            response.setPhone(account.getInscription().getPhone());
            response.setEmail(account.getInscription().getEmail());
            response.setCityCode(account.getInscription().getCity().getCode());
            response.setPhoneCountry(account.getInscription().getPhoneCountry().getCode());
            response.setMessage("Un utilisateur a été trouvé");

            List<Favorite> favorites = this.favoriteRepository.findAllByAppAccount(account);
            favorites.forEach(
                    favorite -> {
                        ItemDTO dto = new ItemDTO();
                        dto.setFavorite(favorite.getCode());
                        dto.setName(favorite.getItem().getName());
                        dto.setCode(favorite.getItem().getCode());
                        dto.setDescription(favorite.getItem().getDescription());
                        dto.setAdress(favorite.getItem().getAdress());
                        dto.setCity(favorite.getItem().getCity().getName());
                        dto.setCountry(favorite.getItem().getCountry().getName());
                        dto.setDate(favorite.getItem().createdDateToString());
                        dto.setIcon(favorite.getItem().getThumbnail());

                        favorite.getItem().getPictures().forEach(
                                itemPicture -> {
                                    dto.getPictures().add(configuration.getWebsiteUrl()+favorite.getItem().getCode()+"/"+ itemPicture.getCode()+".png");
                                }
                        );

                        favorite.getItem().getSpecifications().forEach(
                                specificationItem -> {
                                    String v = specificationItem.getValue1().equalsIgnoreCase("") ? specificationItem.getValue2() +"" :"";
                                    dto.getSpecifications().add(specificationItem.getItemSpecification().getName() + " - " + v);
                                }
                        );

                        favorite.getItem().getTags().forEach(
                                tag -> {
                                    dto.getTags().add(tag.getCode());
                                }
                        );

                        favorite.getItem().getPrices().forEach(
                                price -> {
                                    if (price.isActivated())
                                        dto.setPrice(price.getAmount()+"");
                                    dto.setCurrency(price.getCurrency().getName()+"");
                                }
                        );

                        response.getFavorites().add(dto);
                    }
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception exception) {
            response.setStatus(false);
            response.setMessage("Aucun utilisateur trouvé");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/v1/password/modify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> changePassword(Principal principal, @RequestBody ChangePasswordRequest passwordRequest) {
        ChangePasswordResponse response = new ChangePasswordResponse();
        try {
            System.out.println("Principal user : " + principal.getName());
            if (!passwordRequest.getNewPassword().equalsIgnoreCase(passwordRequest.getConfirmPassword())) {
                response.setStatus(false);
                response.setMessage("Le nouveau mot de passe ne correspond pas");
                response.setField("newPassword");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (!passwordEncoder.matches(passwordRequest.getOldPassword(), account.getPassword())) {
                response.setStatus(false);
                response.setMessage("L'ancien mot de passe ne correspond pas");
                response.setField("oldPassword");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            account.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            this.appAccountRepository.save(account);

            response.setStatus(true);
            response.setMessage("Le mot de passe a été changé avec succès");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception exception) {
            response.setStatus(false);
            response.setMessage("Aucun utilisateur trouvé");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/v1/favorite/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFavorite(Principal principal, @RequestBody FavoriteAddRequest favoriteAddRequest) {
        ChangePasswordResponse response = new ChangePasswordResponse();
        try {
            System.out.println("Principal user : " + principal.getName());
            System.out.println("Get user : " + favoriteAddRequest.getCodeCustomer());
            if (favoriteAddRequest.getCodeItem().equalsIgnoreCase("")) {
                response.setStatus(false);
                response.setMessage("Le code item est non spécifié");
                response.setField("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Item item = this.itemRepository.findByCode(favoriteAddRequest.getCodeItem());
            if (item == null) {
                response.setStatus(false);
                response.setMessage("Aucun item trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Favorite favorite = new Favorite();
            favorite.setActivated(true);
            favorite.setCode(RandomStringUtils.randomAlphanumeric(10));
            favorite.setItem(item);
            favorite.setAppAccount(account);
            this.favoriteRepository.save(favorite);

            response.setStatus(true);
            response.setMessage("L'item a été rajouté avec succès");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(false);
            response.setMessage("Une exception lors du traitement de la requête");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/favorite/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> myFavorites(Principal principal) {
        Configuration configuration = this.configurationRepository.findByActivated(true);
        GetFavoriteResponse response = new GetFavoriteResponse();
        try {
            System.out.println("Principal user : " + principal.getName());

            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            List<Favorite> favorites = this.favoriteRepository.findAllByAppAccount(account);
            if (favorites.isEmpty()) {
                response.setStatus(false);
                response.setMessage("Pas d'item en favori pour le moment");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            favorites.forEach(
                    favorite -> {
                        ItemDTO dto = new ItemDTO();
                        dto.setFavorite(favorite.getCode());
                        dto.setName(favorite.getItem().getName());
                        dto.setCode(favorite.getItem().getCode());
                        dto.setDescription(favorite.getItem().getDescription());
                        dto.setAdress(favorite.getItem().getAdress());
                        dto.setCity(favorite.getItem().getCity().getName());
                        dto.setCountry(favorite.getItem().getCountry().getName());
                        dto.setDate(favorite.getItem().createdDateToString());
                        dto.setIcon(favorite.getItem().getThumbnail());

                        favorite.getItem().getPictures().forEach(
                                itemPicture -> {
                                    dto.getPictures().add(configuration.getWebsiteUrl()+favorite.getItem().getCode()+"/"+ itemPicture.getCode()+".png");
                                }
                        );

                        favorite.getItem().getSpecifications().forEach(
                                specificationItem -> {
                                    String v = specificationItem.getValue1().equalsIgnoreCase("") ? specificationItem.getValue2() +"" :"";
                                    dto.getSpecifications().add(specificationItem.getItemSpecification().getName() + " - " + v);
                                }
                        );

                        favorite.getItem().getTags().forEach(
                                tag -> {
                                    dto.getTags().add(tag.getCode());
                                }
                        );

                        favorite.getItem().getPrices().forEach(
                                price -> {
                                    if (price.isActivated())
                                        dto.setPrice(price.getAmount()+"");
                                    dto.setCurrency(price.getCurrency().getName()+"");
                                }
                        );

                        response.getItems().add(dto);
                    }
            );

            response.setStatus(true);
            response.setMessage("L'item a été rajouté avec succès");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(false);
            response.setMessage("Une exception lors du traitement de la requête");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/v1/booking/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> verifyDisponibilty(Principal principal, @RequestBody BookingVerify bookingVerify) {
        ChangePasswordResponse response = new ChangePasswordResponse();
        try {
            System.out.println("Principal user : " + principal.getName());
            System.out.println("Get product code : " + bookingVerify.getCodeItem());
            if (bookingVerify.getCodeItem().equalsIgnoreCase("")) {
                response.setStatus(false);
                response.setMessage("Le code item est non spécifié");
                response.setField("");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Item item = this.itemRepository.findByCode(bookingVerify.getCodeItem());

            //String str = "2016-03-04 11:30";
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(bookingVerify.getDate() + " 00:00", formatter);

            Order orderExist = this.orderRepository.findByItemAndPaidAndBookingDateEquals(item, true, dateTime);
            if (orderExist != null) {
                response.setStatus(true);
                response.setMessage("Une réservation existe à cette date");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                response.setStatus(false);
                response.setMessage("Aucune réservation trouvée pour cette date");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(false);
            response.setMessage("Une exception lors du traitement de la requête");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/v1/booking/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> bookingBook(Principal principal, @RequestBody BookingDto bookingDto) {
        BookBookingResponse response = new BookBookingResponse();
        try {
            System.out.println("Principal user : " + principal.getName());
            System.out.println("Get product code : " + bookingDto.getCodeItem());
            if (bookingDto.getCodeItem().equalsIgnoreCase("")) {
                response.setStatus(false);
                response.setMessage("Le code item est non spécifié");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Item item = this.itemRepository.findByCode(bookingDto.getCodeItem());

            //String str = "2016-03-04 11:30";
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(bookingDto.getDate() + " 00:00", formatter);

            Order orderExist = this.orderRepository.findByItemAndPaidAndBookingDateEquals(item, true, dateTime);
            if (orderExist != null) {
                response.setStatus(true);
                response.setMessage("Une réservation existe à cette date");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                Currency currency = this.currencyRepository.findByName(bookingDto.getCurrency());
                if (currency == null) {
                    response.setStatus(false);
                    response.setMessage("La devise n'a pas été retrouvée");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                double amount = Double.parseDouble(bookingDto.getAmount());
                Order order = new Order();
                String cd = RandomStringUtils.randomAlphanumeric(20);
                order.setCode(cd);
                order.setAmount(amount);
                order.setBookingDate(dateTime);
                order.setCurrency(currency);
                order.setCustomer(account);
                order.setClosed(false);
                order.setPaid(false);
                order.setStep(1);
                order.setItem(item);
                if (item.getMerchant() != null)
                    order.setMerchant(item.getMerchant());
                this.orderRepository.save(order);

                response.setStatus(true);
                response.setMessage("Une réservation a été ajoutée");
                response.setBookingCode(cd);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(false);
            response.setMessage("Une exception lors du traitement de la requête");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/v1/booking/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> bookingPay(Principal principal, @RequestBody BookingPay bookingPay) {
        BookBookingResponse response = new BookBookingResponse();
        try {
            System.out.println("Principal user : " + principal.getName());
            System.out.println("Get booking code : " + bookingPay.getCodeBooking());
            if (bookingPay.getCodeBooking().equalsIgnoreCase("")) {
                response.setStatus(false);
                response.setMessage("Le code booking est non spécifié");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Order order = this.orderRepository.findByCode(bookingPay.getCodeBooking());
            if (order == null) {
                response.setStatus(false);
                response.setMessage("Aucune réservation n'a été trouvée");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                // Send payment
                if (bookingPay.getType() == 1) {
                    Configuration configuration = this.configurationRepository.findByActivated(true);
                    String header = bookingPay.getPhone().substring(3, 5);
                    ProviderFormat providerFormat = this.providerFormatRepository.findByHeader(header);
                    if (providerFormat == null) {
                        System.out.println("Le format du numéro de téléphone ne correspond pas");
                        response.setStatus(false);
                        response.setMessage("Le format du numéro de téléphone ne correspond pas");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }

                    try {
                        String merchant = "";
                        if (order.getMerchant() != null) {
                            if (order.getMerchant().getMerchantShortCode() != null)
                                merchant = order.getMerchant().getMerchantShortCode();
                        }else {
                            merchant = configuration.getShortCode();
                        }
                        String type = "1";
                        String callback = configuration.getCallbackUrl();

                        String jsonInputString = "{\"merchant\": \""+merchant+"\", \"type\": \""+type+"\", \"phone\": \""+ bookingPay.getPhone()+"\", \"reference\": \""+order.getCode()+"\", \"amount\": \""+order.getAmount()+"\", \"currency\": \""+order.getCurrency().getName()+"\", \"callbackUrl\": \""+callback+"\"}";
                        System.out.println(jsonInputString);
                        String url = configuration.getMobileUrl();
                        System.out.println("Mobile url : " + url);
                        StringEntity params = new StringEntity(jsonInputString);
                        HttpClient httpClient = HttpClientBuilder
                                .create()
                                .build();
                        HttpPost request = new HttpPost(url);
                        request.addHeader("content-type", "application/json");
                        request.setEntity(params);
                        HttpResponse httpResponse = httpClient.execute(request);
                        int responseCode = httpResponse.getStatusLine().getStatusCode();
                        System.out.println("Response code : " + responseCode);

                        if (responseCode == 200 || responseCode == 201) {
                            String resp = EntityUtils.toString(httpResponse.getEntity());
                            System.out.println(resp);
                            FxResponse body = new ObjectMapper().readValue(resp, FxResponse.class);
                            if (body != null) {
                                if (body.getCode().equalsIgnoreCase("0")) {
                                    response.setStatus(true);
                                    response.setMessage("Le paiement a été envoyé. Veuillez valider le push message");
                                    return new ResponseEntity<>(response, HttpStatus.OK);
                                }else {
                                    response.setStatus(false);
                                    response.setMessage("Une erreur lors de l'envoi de votre paiement, veuillez réessayer");
                                    return new ResponseEntity<>(response, HttpStatus.OK);
                                }
                            }else {
                                System.out.println("Is null response");
                                response.setStatus(false);
                                response.setMessage("Une erreur de traitement lors de l'envoi de votre paiement, veuillez réessayer");
                                return new ResponseEntity<>(response, HttpStatus.OK);
                            }
                        }else {
                            response.setStatus(false);
                            response.setMessage("Une erreur de connexion lors de l'envoi de votre paiement, veuillez réessayer");
                            return new ResponseEntity<>(response, HttpStatus.OK);
                        }
                    }catch (Exception exception) {
                        response.setStatus(false);
                        response.setMessage("Une erreur exception lors de l'envoi de votre paiement, veuillez réessayer");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                }else {
                    response.setStatus(false);
                    response.setMessage("le paiement par carte n'est pas pris en charge");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        }catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(false);
            response.setMessage("Une exception lors du traitement de la requête");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/booking/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> myBookings(Principal principal) {
        Configuration configuration = this.configurationRepository.findByActivated(true);
        GetBookingResponse response = new GetBookingResponse();
        try {
            System.out.println("Principal user : " + principal.getName());

            AppAccount account = this.appAccountRepository.findByUsername(principal.getName());
            if (account == null) {
                response.setStatus(false);
                response.setMessage("Aucun utilisateur trouvé");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            List<Order> orders = this.orderRepository.findAllByCustomer(account);
            if (orders.isEmpty()) {
                response.setStatus(false);
                response.setMessage("Pas de reservations");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            orders.forEach(
                    favorite -> {

                        ItemBooking itemBooking = new ItemBooking();
                        itemBooking.setAdress(favorite.getItem().getAdress());
                        itemBooking.setCity(favorite.getItem().getCity().getName());
                        itemBooking.setCode(favorite.getItem().getCode());
                        itemBooking.setDescription(favorite.getItem().getDescription());
                        itemBooking.setName(favorite.getItem().getName());
                        itemBooking.setIcon(configuration.getWebsiteUrl()+favorite.getItem().getCode()+"/"+ favorite.getItem().getThumbnail() + ".png");
                        itemBooking.setCurrency(favorite.getCurrency().getName());

                        favorite.getItem().getPrices().forEach(
                                price -> {
                                    if (price.isActivated())
                                        itemBooking.setPrice(price.getAmount()+"");
                                }
                        );

                        BookingItem dto = new BookingItem();
                        dto.setCode(favorite.getCode());
                        dto.setAmount(favorite.getAmount()+"");
                        dto.setCurrency(favorite.getCurrency().getName());
                        dto.setPaid(favorite.isPaid());
                        dto.setClosed(favorite.isClosed());
                        dto.setDate(favorite.createdDateToString());
                        dto.setItem(itemBooking);
                        response.getItems().add(dto);
                    }
            );

            response.setStatus(true);
            response.setMessage("Une ou plusieurs reservations trouvées");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(false);
            response.setMessage("Une exception lors du traitement de la requête");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
