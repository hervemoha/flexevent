package com.infosetgroup.flexevent.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_ITEMS")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 50, nullable = false)
    private String code;
    @Column(length = 100)
    private String name;
    private String description;
    private String adress;
    private boolean activated;
    private boolean storable;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @ManyToOne
    private AppAccount createdBy;
    private int quantity;
    private String thumbnail;
    @ManyToOne
    private Country country;
    @ManyToOne
    private City city;
    @OneToMany(mappedBy = "item")
    private List<ItemPicture> pictures = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<ItemSpecificationItem> specifications = new ArrayList<>();

    @ManyToMany(mappedBy = "items")
    private List<Tag> tags = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<ItemPrice> prices = new ArrayList<>();
    @ManyToOne
    private Category category;

    @ManyToOne
    private AppAccount merchant;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public AppAccount getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppAccount createdBy) {
        this.createdBy = createdBy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<ItemPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<ItemPicture> pictures) {
        this.pictures = pictures;
    }

    public boolean isStorable() {
        return storable;
    }

    public void setStorable(boolean storable) {
        this.storable = storable;
    }

    public List<ItemSpecificationItem> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<ItemSpecificationItem> specifications) {
        this.specifications = specifications;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<ItemPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<ItemPrice> prices) {
        this.prices = prices;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AppAccount getMerchant() {
        return merchant;
    }

    public void setMerchant(AppAccount merchant) {
        this.merchant = merchant;
    }

    @PrePersist
    void onCreate() {
        this.setCreatedAt(LocalDateTime.now());
        this.setModifiedAt(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setModifiedAt(LocalDateTime.now());
    }

    public String createdDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.createdAt.format(formatter);
    }
}
