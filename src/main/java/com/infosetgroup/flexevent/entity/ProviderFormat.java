package com.infosetgroup.flexevent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_PROVIDERS_FORMATS")
public class ProviderFormat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Provider provider;
    private int length;

    @Column(length = 100)
    private String header;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
