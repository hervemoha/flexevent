package com.infosetgroup.flexevent.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PROVIDERS")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 30)
    private String code;

    @Column(length = 20)
    private String name;
    private int type;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime modifiedAt;

    public Provider(){

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @PrePersist
    void onCreate() {
        this.setCreatedAt(LocalDateTime.now());
        this.setModifiedAt(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setModifiedAt(LocalDateTime.now());
    };
}
