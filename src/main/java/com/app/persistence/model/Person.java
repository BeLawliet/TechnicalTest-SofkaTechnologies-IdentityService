package com.app.persistence.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
    @Id
    private Long identification;

    private String name;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    private int age;
    private String address;
    private String phone;
}
