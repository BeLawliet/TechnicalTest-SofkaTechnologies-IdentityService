package com.app.presentation.dto;

import lombok.*;

@Getter
@Setter
@Builder
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
