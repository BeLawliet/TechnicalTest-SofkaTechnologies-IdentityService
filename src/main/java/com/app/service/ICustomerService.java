package com.app.service;

import com.app.presentation.dto.CustomerDTO;
import com.app.presentation.dto.ResponseDTO;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import java.util.List;

public interface ICustomerService {
    ResponseDTO<List<CustomerDTO>> findAll();
    ResponseDTO<CustomerDTO> save(SaveCustomerDTO request);
    ResponseDTO<CustomerDTO> update(Long identification, UpdateCustomerDTO request);
    ResponseDTO<String> delete(Long identification);
}
