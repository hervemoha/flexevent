package com.infosetgroup.flexevent.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "TB_CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 50, nullable = false)
    private String code;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String wording;
    private int weight;
    private boolean activated;
    @Column(length = 50)
    private String icon;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @ManyToOne
    private AppAccount createdBy;

    public Category() {
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

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
