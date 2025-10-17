package com.app.service.impl;

import com.app.persistence.model.Customer;
import com.app.persistence.model.EStatus;
import com.app.presentation.dto.ETypeEventDTO;
import com.app.persistence.repository.ICustomerRepository;
import com.app.presentation.dto.CustomerDTO;
import com.app.presentation.dto.CustomerEventDTO;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import com.app.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    @Value("${kafka-topic}")
    private String KAFKA_TOPIC;

    private final ICustomerRepository customerRepository;
    private final KafkaTemplate<String, CustomerEventDTO> kafkaTemplate;
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

        this.publishKafkaEvent(ETypeEventDTO.CUSTOMER_CREATED, request.accountType(), customerSaved);

        return this.modelMapper.map(customerSaved, CustomerDTO.class);
    }

    @Override
    public Optional<CustomerDTO> update(Long identification, UpdateCustomerDTO request) {
        return this.customerRepository.findById(identification)
                                      .map(customer -> {
                                          this.modelMapper.map(request, customer);

                                          Customer updatedCustomer = this.customerRepository.save(customer);

                                          this.publishKafkaEvent(ETypeEventDTO.CUSTOMER_UPDATED, null, updatedCustomer);

                                          return this.modelMapper.map(updatedCustomer, CustomerDTO.class);
                                      });
    }

    @Override
    public boolean delete(Long identification) {
        return this.customerRepository.findById(identification)
                                      .map(customer -> {
                                            customer.setStatus(EStatus.INACTIVE);
                                            Customer updatedCustomer = this.customerRepository.save(customer);

                                            this.publishKafkaEvent(ETypeEventDTO.CUSTOMER_UPDATED, null, updatedCustomer);

                                            return true;
                                      })
                                      .orElse(false);
    }

    private void publishKafkaEvent(ETypeEventDTO typeEvent, String accountType, Customer model) {
        CustomerEventDTO customerEventDTO = CustomerEventDTO.builder()
                                                            .typeEvent(typeEvent)
                                                            .customerId(model.getCustomerId())
                                                            .accountType(accountType)
                                                            .status(model.getStatus())
                                                            .build();

        this.kafkaTemplate.send(KAFKA_TOPIC, customerEventDTO);
    }
}
