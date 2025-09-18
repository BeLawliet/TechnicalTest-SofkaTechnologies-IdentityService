package com.app.service;

import com.app.presentation.dto.CustomerDTO;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    List<CustomerDTO> findAll();
    CustomerDTO save(SaveCustomerDTO request);
    Optional<CustomerDTO> update(Long identification, UpdateCustomerDTO request);
    boolean delete(Long identification);
}
