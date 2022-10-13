package com.infosetgroup.flexevent.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "TB_CONFIGURATIONS")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 50, nullable = false)
    private String code;
    @Column(length = 100)
    private String homeWording;
    private String websiteUrl;
    private boolean activated;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @ManyToOne
    private AppAccount createdBy;
    private String welcomeMessage;
    @Column(length = 100)
    private String smsToken;
    @Column(length = 50)
    private String smsName;
    private String smsWelcomeMessage;
    private boolean sendSms;

    private String shortCode;
    private String callbackUrl;
    private String mobileUrl;

    public Configuration() {
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

    public String getHomeWording() {
        return homeWording;
    }

    public void setHomeWording(String homeWording) {
        this.homeWording = homeWording;
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getSmsToken() {
        return smsToken;
    }

    public void setSmsToken(String smsToken) {
        this.smsToken = smsToken;
    }

    public String getSmsName() {
        return smsName;
    }

    public void setSmsName(String smsName) {
        this.smsName = smsName;
    }

    public String getSmsWelcomeMessage() {
        return smsWelcomeMessage;
    }

    public void setSmsWelcomeMessage(String smsWelcomeMessage) {
        this.smsWelcomeMessage = smsWelcomeMessage;
    }

    public boolean isSendSms() {
        return sendSms;
    }

    public void setSendSms(boolean sendSms) {
        this.sendSms = sendSms;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
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
