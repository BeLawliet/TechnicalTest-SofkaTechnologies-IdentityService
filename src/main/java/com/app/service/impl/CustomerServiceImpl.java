package com.app.service.impl;

import com.app.persistence.model.Customer;
import com.app.persistence.repository.ICustomerRepository;
import com.app.presentation.dto.CustomerDTO;
import com.app.presentation.dto.ResponseDTO;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import com.app.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseDTO<List<CustomerDTO>> findAll() {
        List<Customer> customers = this.customerRepository.findAll();

        List<CustomerDTO> customersDTO = customers.stream()
                                                  .map(element -> this.modelMapper.map(element, CustomerDTO.class))
                                                  .toList();

        return new ResponseDTO<>(HttpStatus.OK.value(), "Se cargaron todos los clientes", customersDTO);
    }

    @Override
    public ResponseDTO<CustomerDTO> save(SaveCustomerDTO request) {
        Customer newCustomer = this.modelMapper.map(request, Customer.class);
        newCustomer.setCustomerId(UUID.randomUUID());

        Customer customerSaved = this.customerRepository.save(newCustomer);

        CustomerDTO dto = this.modelMapper.map(customerSaved, CustomerDTO.class);
        return new ResponseDTO<>(HttpStatus.OK.value(),"Cliente guardado correctamente", dto);
    }

    @Override
    public ResponseDTO<CustomerDTO> update(Long identification, UpdateCustomerDTO request) {
        return this.customerRepository.findById(identification)
                                      .map(existingCustomer -> {
                                          this.modelMapper.map(request, existingCustomer);

                                          Customer updatedCustomer = this.customerRepository.save(existingCustomer);

                                          CustomerDTO dto = this.modelMapper.map(updatedCustomer, CustomerDTO.class);

                                          return new ResponseDTO<>(HttpStatus.OK.value(), "Cliente actualizado correctamente", dto);
                                      })
                                      .orElseGet(() -> new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), String.format("ID: %s no conseguido", identification), null));
    }

    @Override
    public ResponseDTO<String> delete(Long identification) {
        return this.customerRepository.findById(identification)
                                      .map(existingCustomer -> {
                                            this.customerRepository.delete(existingCustomer);
                                            return new ResponseDTO<>(HttpStatus.OK.value(), "Registro eliminado correctamente", "OK");
                                      })
                                      .orElseGet(() -> new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), String.format("ID: %s no conseguido", identification), null));
    }
}
