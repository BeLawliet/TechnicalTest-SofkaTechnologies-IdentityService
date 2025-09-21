package com.app.service.impl;

import com.app.persistence.model.Customer;
import com.app.persistence.repository.ICustomerRepository;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static com.app.provider.DataProvider.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private ICustomerRepository customerRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        this.customerService = new CustomerServiceImpl(customerRepository, modelMapper);
    }

    @Test
    void should_return_customers_when_repository_has_data() {
        // Arrange
        Customer customer = mockCustomer();
        when(this.customerRepository.findAll()).thenReturn(List.of(customer));

        // Act
        var result = this.customerService.findAll();

        // Assert
        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(1, result.size()),
            () -> assertEquals(123456789L, result.get(0).getIdentification()),
            () -> assertEquals("3001234567", result.get(0).getPhone())
        );
    }

    @Test
    void should_save_customer_when_parameters_correct() {
        // Arrange
        Customer customer = mockCustomer();
        SaveCustomerDTO saveCustomerDTO = mockSaveCustomerDTO();

        when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        var result = this.customerService.save(saveCustomerDTO);

        // Assert
        verify(this.customerRepository, times(1)).save(any(Customer.class));

        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(123456789L, result.getIdentification()),
            () -> assertEquals("Be Human", result.getName()),
            () -> assertEquals("MALE", result.getGender())
        );
    }

    @Test
    void should_update_customer_when_parameters_correct() {
        // Arrange
        Long identification = 123456789L;
        Customer customer = mockCustomer();
        UpdateCustomerDTO updateCustomerDTO = mockUpdateCustomerDTO();

        when(this.customerRepository.findById(identification)).thenReturn(Optional.of(customer));
        when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        var result = this.customerService.update(identification, updateCustomerDTO);

        // Assert
        verify(this.customerRepository, times(1)).save(any(Customer.class));

        assertAll(
            () -> assertFalse(result.isEmpty()),
            () -> assertEquals(123456789L, result.get().getIdentification()),
            () -> assertEquals(30, result.get().getAge()),
            () -> assertEquals("Calle 123 #45-67, Bogotá", result.get().getAddress())
        );
    }

    @Test
    void should_do_not_update_customer_when_id_incorrect() {
        // Arrange
        Long identification = 0L;
        when(this.customerRepository.findById(identification)).thenReturn(Optional.empty());

        // Act
        var result = this.customerService.update(identification, null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void should_delete_customer_when_id_correct() {
        // Arrange
        Long identification = 123456789L;
        Customer customer = mockCustomer();

        when(this.customerRepository.findById(identification)).thenReturn(Optional.of(customer));

        // Act
        var result = this.customerService.delete(identification);

        // Assert
        verify(this.customerRepository).delete(customer);
        assertTrue(result);
    }

    @Test
    void should_do_not_delete_customer_when_id_incorrect() {
        // Arrange
        Long identification = 0L;
        when(this.customerRepository.findById(identification)).thenReturn(Optional.empty());

        // Act
        var result = this.customerService.delete(identification);

        // Assert
        assertFalse(result);
    }
}
