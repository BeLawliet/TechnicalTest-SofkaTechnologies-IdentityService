package com.app.service.impl;

import com.app.persistence.model.Customer;
import com.app.persistence.repository.ICustomerRepository;
import com.app.presentation.dto.CustomerDTO;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import com.app.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customers = this.customerRepository.findAll();

        return customers.stream()
                        .map(customer -> this.modelMapper.map(customer, CustomerDTO.class))
                        .toList();
    }

    @Override
    public CustomerDTO save(SaveCustomerDTO request) {
        Customer newCustomer = this.modelMapper.map(request, Customer.class);

        Customer customerSaved = this.customerRepository.save(newCustomer);

        return this.modelMapper.map(customerSaved, CustomerDTO.class);
    }

    @Override
    public Optional<CustomerDTO> update(Long identification, UpdateCustomerDTO request) {
        return this.customerRepository.findById(identification)
                                      .map(customer -> {
                                          this.modelMapper.map(request, customer);

                                          Customer updatedCustomer = this.customerRepository.save(customer);

                                          return this.modelMapper.map(updatedCustomer, CustomerDTO.class);
                                      });
    }

    @Override
    public boolean delete(Long identification) {
        return this.customerRepository.findById(identification)
                                      .map(customer -> {
                                            this.customerRepository.delete(customer);
                                            return true;
                                      })
                                      .orElse(false);
    }
}
