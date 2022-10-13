package com.infosetgroup.flexevent.entity;

import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "TB_COUNTRIES")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 50, nullable = false)
    private String code;
    private String name;
    private String callingCode;
    private boolean activated;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @ManyToOne
    private AppAccount createdBy;

    public Country() {
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

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
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
