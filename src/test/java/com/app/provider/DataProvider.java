package com.app.provider;

import com.app.persistence.model.Customer;
import com.app.persistence.model.EGender;
import com.app.persistence.model.EStatus;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;

public class DataProvider {
    public static final Customer mockCustomer() {
        return Customer.builder()
                       .identification(123456789L)
                       .name("Be Human")
                       .gender(EGender.MALE)
                       .age(30)
                       .address("Calle 123 #45-67, Bogotá")
                       .phone("3001234567")
                       .password("secret1")
                       .status(EStatus.ACTIVE)
                       .build();
    }

    public static final SaveCustomerDTO mockSaveCustomerDTO() {
        return SaveCustomerDTO.builder()
                              .identification(123456789L)
                              .name("Be Human")
                              .gender(EGender.MALE)
                              .age(30)
                              .address("Calle 123 #45-67, Bogotá")
                              .phone("3001234567")
                              .password("secret1")
                              .status(EStatus.ACTIVE)
                              .build();
    }

    public static final UpdateCustomerDTO mockUpdateCustomerDTO() {
        return UpdateCustomerDTO.builder()
                                .name("Not Be Human")
                                .gender(EGender.FEMALE)
                                .age(25)
                                .address("Calle 0 #3-11")
                                .phone("3012863980")
                                .status(EStatus.INACTIVE)
                                .build();
    }
}
