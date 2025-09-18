package com.app.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long identification;
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private String customerId;
    private String status;
}
