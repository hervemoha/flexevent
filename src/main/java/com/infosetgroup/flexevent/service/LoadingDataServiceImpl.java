package com.infosetgroup.flexevent.service;

import com.infosetgroup.flexevent.entity.*;
import com.infosetgroup.flexevent.repository.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoadingDataServiceImpl implements LoadingDataService{

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
    private TagRepository tagRepository;

    @Autowired
    private ItemSpecificationRepository itemSpecificationRepository;

    @Autowired
    private  ItemSpecificationItemRepository itemSpecificationItemRepository;

    @Autowired
    private ItemPriceRepository itemPriceRepository;

    @Override
    public void load() {

        // Role
        Role roleAdmin = new Role();
        roleAdmin.setType(1);
        //roleAdmin.setCode(RandomStringUtils.randomAlphanumeric(10));
        roleAdmin.setCode("01");
        roleAdmin.setName("ADMIN");
        roleAdmin.setLibelle("Administrateur");
        this.roleRepository.save(roleAdmin);

        Role roleMerchant = new Role();
        roleMerchant.setType(2);
        //roleMerchant.setCode(RandomStringUtils.randomAlphanumeric(10));
        roleMerchant.setCode("02");
        roleMerchant.setName("MERCHANT");
        roleMerchant.setLibelle("Marchand");
        this.roleRepository.save(roleMerchant);

        Role roleCustomer = new Role();
        roleCustomer.setType(3);
        //roleCustomer.setCode(RandomStringUtils.randomAlphanumeric(10));
        roleCustomer.setCode("03");
        roleCustomer.setName("CUSTOMER");
        roleCustomer.setLibelle("Client");
        this.roleRepository.save(roleCustomer);

        // Confguration
        Configuration configuration = new Configuration();
        configuration.setActivated(true);
        configuration.setCode(RandomStringUtils.randomAlphanumeric(5));
        configuration.setHomeWording("Trouver la salle, voiture etc.  de vos rêves");
        configuration.setWebsiteUrl("http://41.243.7.46:3004/flexevent/public/uploads/");
        configuration.setWelcomeMessage("Votre inscription est complète. Pour plus d'infos, contacter le 243 99 60 200");
        configuration.setSmsName("FLEXEVENT");
        configuration.setSmsToken("GF482KMBQ23P98W");
        configuration.setSendSms(true);
        configuration.setSmsWelcomeMessage("Bienvenue sur FlexEvent. Pour plus infos, visitez http://flexevent.biz");
        this.configurationRepository.save(configuration);

        // Country
        Country rdc = new Country();
        //rdc.setCode(RandomStringUtils.randomAlphanumeric(10));
        rdc.setCode("h43dgvUKjB");
        rdc.setActivated(true);
        rdc.setCallingCode("243");
        rdc.setName("Congo RD");
        this.countryRepository.save(rdc);

        Country rc = new Country();
        //rc.setCode(RandomStringUtils.randomAlphanumeric(10));
        rc.setCode("KaS2f6ClVX");
        rc.setActivated(true);
        rc.setCallingCode("242");
        rc.setName("Congo Republique");
        this.countryRepository.save(rc);

        Country angola = new Country();
        //angola.setCode(RandomStringUtils.randomAlphanumeric(10));
        angola.setCode("L60g6y3c1m");
        angola.setActivated(true);
        angola.setCallingCode("244");
        angola.setName("Angola");
        this.countryRepository.save(angola);

        Country france = new Country();
        //france.setCode(RandomStringUtils.randomAlphanumeric(10));
        france.setCode("rZQxsH211r");
        france.setActivated(true);
        france.setCallingCode("33");
        france.setName("France");
        this.countryRepository.save(france);

        // City
        City kinshasa = new City();
        //kinshasa.setCode(RandomStringUtils.randomAlphanumeric(10));
        kinshasa.setCode("1YYQXhtFMD");
        kinshasa.setName("Kinshasa");
        kinshasa.setCountry(rdc);
        kinshasa.setActivated(true);
        this.cityRepository.save(kinshasa);

        City goma = new City();
        goma.setCode(RandomStringUtils.randomAlphanumeric(10));
        goma.setName("Goma");
        goma.setCountry(rdc);
        goma.setActivated(true);
        this.cityRepository.save(goma);

        City lubumbashi = new City();
        lubumbashi.setCode(RandomStringUtils.randomAlphanumeric(10));
        lubumbashi.setName("Lubumbashi");
        lubumbashi.setCountry(rdc);
        lubumbashi.setActivated(true);
        this.cityRepository.save(lubumbashi);

        City boma = new City();
        boma.setCode(RandomStringUtils.randomAlphanumeric(10));
        boma.setName("Boma");
        boma.setCountry(rdc);
        boma.setActivated(true);
        this.cityRepository.save(boma);

        City paris = new City();
        paris.setCode(RandomStringUtils.randomAlphanumeric(10));
        paris.setName("Paris");
        paris.setCountry(france);
        paris.setActivated(true);
        this.cityRepository.save(paris);

        City brazzaville = new City();
        brazzaville.setCode(RandomStringUtils.randomAlphanumeric(10));
        brazzaville.setName("Brazzaville");
        brazzaville.setCountry(rc);
        brazzaville.setActivated(true);
        this.cityRepository.save(brazzaville);

        City moanda = new City();
        moanda.setCode(RandomStringUtils.randomAlphanumeric(10));
        moanda.setName("Moanda");
        moanda.setCountry(angola);
        moanda.setActivated(true);
        this.cityRepository.save(moanda);

        // Category
        Category partyRoom = new Category();
        //partyRoom.setCode(RandomStringUtils.randomAlphanumeric(10));
        partyRoom.setCode("01");
        partyRoom.setWording("Salle de fête");
        partyRoom.setName("Location salle de fête");
        partyRoom.setActivated(true);
        partyRoom.setWeight(1);
        //partyRoom.setIcon(RandomStringUtils.randomAlphanumeric(10)+".png");
        partyRoom.setIcon("Dfs95qXiJ8.png");
        this.categoryRepository.save(partyRoom);

        Category vehicle = new Category();
        //vehicle.setCode(RandomStringUtils.randomAlphanumeric(10));
        vehicle.setCode("02");
        vehicle.setWording("Véhicule");
        vehicle.setName("Location véhicule");
        vehicle.setActivated(true);
        vehicle.setWeight(2);
        //vehicle.setIcon(RandomStringUtils.randomAlphanumeric(10)+".png");
        vehicle.setIcon("HirzgnMSoO.png");
        this.categoryRepository.save(vehicle);

        Category weddingDress = new Category();
        //weddingDress.setCode(RandomStringUtils.randomAlphanumeric(10));
        weddingDress.setCode("03");
        weddingDress.setWording("Robe de mariage");
        weddingDress.setName("Location robe de mariage");
        weddingDress.setActivated(true);
        weddingDress.setWeight(3);
        //weddingDress.setIcon(RandomStringUtils.randomAlphanumeric(10)+".png");
        weddingDress.setIcon("8GBjeknAWf.png");
        this.categoryRepository.save(weddingDress);

        Category musicInstrument = new Category();
        //musicInstrument.setCode(RandomStringUtils.randomAlphanumeric(10));
        musicInstrument.setCode("04");
        musicInstrument.setWording("Instruments de musique");
        musicInstrument.setName("Location Instru-musique");
        musicInstrument.setActivated(true);
        musicInstrument.setWeight(4);
        //musicInstrument.setIcon(RandomStringUtils.randomAlphanumeric(10)+".png");
        musicInstrument.setIcon("v4SEmRzcNN.png");
        this.categoryRepository.save(musicInstrument);

        /*
        CategoryDetail nbPlaces = new CategoryDetail();
        nbPlaces.setActivated(true);
        nbPlaces.setCode(RandomStringUtils.randomAlphanumeric(10));
        nbPlaces.setName("Places");
        this.categoryDetailRepository.save(nbPlaces);

        CategoryDetail nbPlaces = new CategoryDetail();
        nbPlaces.setActivated(true);
        nbPlaces.setCode(RandomStringUtils.randomAlphanumeric(10));
        nbPlaces.setName("Places");
        this.categoryDetailRepository.save(nbPlaces);
        */

        // Currency
        Currency cdf = new Currency();
        cdf.setCode(RandomStringUtils.randomAlphanumeric(10));
        cdf.setActivated(true);
        cdf.setName("CDF");
        this.currencyRepository.save(cdf);

        Currency usd = new Currency();
        usd.setCode(RandomStringUtils.randomAlphanumeric(10));
        usd.setActivated(true);
        usd.setName("USD");
        this.currencyRepository.save(usd);

        // Tags
        Tag kinGombe = new Tag();
        kinGombe.setCode(RandomStringUtils.randomAlphanumeric(10));
        kinGombe.setName("Kinshasa-Gombe");
        this.tagRepository.save(kinGombe);

        Tag kinLemba = new Tag();
        kinLemba.setCode(RandomStringUtils.randomAlphanumeric(10));
        kinLemba.setName("Kinshasa-Lemba");
        this.tagRepository.save(kinLemba);

        Tag kinKalamu = new Tag();
        kinKalamu.setCode(RandomStringUtils.randomAlphanumeric(10));
        kinKalamu.setName("Kinshasa-Kalamu");
        this.tagRepository.save(kinKalamu);

        // Item specification
        ItemSpecification nbPlace = new ItemSpecification();
        nbPlace.setCode(RandomStringUtils.randomAlphanumeric(10));
        nbPlace.setName("Places");
        this.itemSpecificationRepository.save(nbPlace);

        // Item
        Item maranatha = new Item();
        //maranatha.setCode(RandomStringUtils.randomAlphanumeric(10));
        maranatha.setCode("iT0001");
        maranatha.setActivated(true);
        maranatha.setCountry(rdc);
        maranatha.setCity(kinshasa);
        maranatha.setName("Maranatha");
        maranatha.setQuantity(1);
        maranatha.setStorable(false);
        //maranatha.setThumbnail(RandomStringUtils.randomAlphanumeric(10));
        maranatha.setThumbnail("mara001");
        maranatha.setDescription("Une salle au standard international");
        maranatha.setAdress("Kinshasa, Gombe, av. Shaumba 548");
        maranatha.getTags().add(kinGombe);
        maranatha.setCategory(partyRoom);
        this.itemRepository.save(maranatha);

        Item beatitude = new Item();
        //beatitude.setCode(RandomStringUtils.randomAlphanumeric(10));
        beatitude.setCode("iT0002");
        beatitude.setActivated(true);
        beatitude.setCountry(rdc);
        beatitude.setCity(kinshasa);
        beatitude.setName("Beatitude");
        beatitude.setQuantity(1);
        beatitude.setStorable(false);
        //beatitude.setThumbnail(RandomStringUtils.randomAlphanumeric(10));
        beatitude.setThumbnail("bea001");
        beatitude.setDescription("Une salle au standard international");
        beatitude.setAdress("Kinshasa, Lemba, av. Mofu 2412");
        beatitude.getTags().add(kinLemba);
        beatitude.setCategory(partyRoom);
        this.itemRepository.save(beatitude);

        // Specification item
        ItemSpecificationItem specificationItem1 = new ItemSpecificationItem();
        specificationItem1.setCode(RandomStringUtils.randomAlphanumeric(10));
        specificationItem1.setItemSpecification(nbPlace);
        specificationItem1.setItem(maranatha);
        specificationItem1.setValue2(500);
        specificationItem1.setValue1("");
        this.itemSpecificationItemRepository.save(specificationItem1);

        ItemSpecificationItem specificationItem2 = new ItemSpecificationItem();
        specificationItem2.setCode(RandomStringUtils.randomAlphanumeric(10));
        specificationItem2.setItemSpecification(nbPlace);
        specificationItem2.setItem(beatitude);
        specificationItem2.setValue2(300);
        specificationItem2.setValue1("");
        this.itemSpecificationItemRepository.save(specificationItem2);

        // Price
        ItemPrice priceMaranatha = new ItemPrice();
        priceMaranatha.setItem(maranatha);
        priceMaranatha.setAmount(2000);
        priceMaranatha.setCurrency(usd);
        priceMaranatha.setActivated(true);
        this.itemPriceRepository.save(priceMaranatha);

        ItemPrice priceBeatitude = new ItemPrice();
        priceBeatitude.setItem(beatitude);
        priceBeatitude.setAmount(1000);
        priceBeatitude.setCurrency(usd);
        this.itemPriceRepository.save(priceBeatitude);


        // Picture
        //String cdPictMara1 = RandomStringUtils.randomAlphanumeric(10);
        String cdPictMara1 = "pict0001";
        ItemPicture mara1 = new ItemPicture();
        mara1.setActivated(true);
        mara1.setCode(cdPictMara1);
        mara1.setItem(maranatha);
        mara1.setName(cdPictMara1);
        this.itemPictureRepository.save(mara1);

        //String cdPictMara2 = RandomStringUtils.randomAlphanumeric(10);
        String cdPictMara2 = "pict0002";
        ItemPicture mara2 = new ItemPicture();
        mara2.setActivated(true);
        mara2.setCode(cdPictMara2);
        mara2.setItem(maranatha);
        mara2.setName(cdPictMara2);
        this.itemPictureRepository.save(mara2);

        //String cdPictMara3 = RandomStringUtils.randomAlphanumeric(10);
        String cdPictMara3 = "pict0003";
        ItemPicture mara3 = new ItemPicture();
        mara3.setActivated(true);
        mara3.setCode(cdPictMara3);
        mara3.setItem(maranatha);
        mara3.setName(cdPictMara3);
        this.itemPictureRepository.save(mara3);

        //String cdPictBea1 = RandomStringUtils.randomAlphanumeric(10);
        String cdPictBea1 = "pict0004";
        ItemPicture beat1 = new ItemPicture();
        beat1.setActivated(true);
        beat1.setCode(cdPictBea1);
        beat1.setItem(beatitude);
        beat1.setName(cdPictBea1);
        this.itemPictureRepository.save(beat1);

        //String cdPictBea2 = RandomStringUtils.randomAlphanumeric(10);
        String cdPictBea2 = "pict0005";
        ItemPicture beat2 = new ItemPicture();
        beat2.setActivated(true);
        beat2.setCode(cdPictBea2);
        beat2.setItem(beatitude);
        beat2.setName(cdPictBea2);
        this.itemPictureRepository.save(beat2);

        //String cdPictBea3 = RandomStringUtils.randomAlphanumeric(10);
        String cdPictBea3 = "pict0006";
        ItemPicture beat3 = new ItemPicture();
        beat3.setActivated(true);
        beat3.setCode(cdPictBea3);
        beat3.setItem(beatitude);
        beat3.setName(cdPictBea3);
        this.itemPictureRepository.save(beat3);

        AppAccount admin = new AppAccount();
        admin.setPassword(passwordEncoder.encode("12345"));
        admin.setUsername("admin");
        admin.setActivated(true);
        admin.setCode(RandomStringUtils.randomAlphanumeric(10));
        admin.addRole(roleAdmin);
        this.appAccountRepository.save(admin);

    }

    @Override
    public void display() {

    }
}
