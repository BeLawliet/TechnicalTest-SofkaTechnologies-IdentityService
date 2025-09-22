package com.app.presentation.controller;

import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import com.app.service.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static com.app.provider.DataProvider.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ICustomerService customerService;

    @Test
    void should_return_ok_with_customers_when_service_has_data() throws Exception {
        when(this.customerService.findAll()).thenReturn(List.of(mockCustomerDTO()));

        mockMvc.perform(get("/api/v1/customers"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
               .andExpect(jsonPath("$.message").value("Se cargaron todos los clientes"))
               .andExpect(jsonPath("$.data.length()").value(1))
               .andExpect(jsonPath("$.data[0].identification").value(123456789L))
               .andExpect(jsonPath("$.data[0].name").value("Be Human"));
    }

    @Test
    void should_return_ok_when_save_customer() throws Exception {
        SaveCustomerDTO saveCustomerDTO = mockSaveCustomerDTO();
        String jsonRequest = this.objectMapper.writeValueAsString(saveCustomerDTO);

        when(this.customerService.save(saveCustomerDTO)).thenReturn(mockCustomerDTO());

        mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON)
                                                            .content(jsonRequest))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
               .andExpect(jsonPath("$.message").value("Cliente guardado correctamente"))
               .andExpect(jsonPath("$.data.identification").value(123456789L))
               .andExpect(jsonPath("$.data.name").value("Be Human"));
    }

    @Test
    void should_return_error_when_update_parameters_incorrect() throws Exception {
        SaveCustomerDTO saveCustomerDTO = SaveCustomerDTO.builder()
                                                         .identification(null)
                                                         .name("")
                                                         .build();
        String jsonRequest = this.objectMapper.writeValueAsString(saveCustomerDTO);

        mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON)
                                                            .content(jsonRequest))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
               .andExpect(jsonPath("$.message").value("Error de validación"))
               .andExpect(jsonPath("$.data.identification").value("La identificación es obligatoria"))
               .andExpect(jsonPath("$.data.name").value("El nombre es obligatorio"));
    }

    @Test
    void should_return_ok_when_update_customer() throws Exception {
        Long identification = 123456789L;
        UpdateCustomerDTO updateCustomerDTO = mockUpdateCustomerDTO();
        String jsonRequest = this.objectMapper.writeValueAsString(updateCustomerDTO);

        when(this.customerService.update(identification, updateCustomerDTO)).thenReturn(Optional.of(mockCustomerDTO()));

        mockMvc.perform(put("/api/v1/customers/{identification}", identification).contentType(MediaType.APPLICATION_JSON)
                                                                                             .content(jsonRequest))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
               .andExpect(jsonPath("$.message").value("Cliente actualizado correctamente"))
               .andExpect(jsonPath("$.data.identification").value(123456789L))
               .andExpect(jsonPath("$.data.name").value("Be Human"));
    }

    @Test
    void should_return_error_when_update_paramaters_incorrect() throws Exception {
        UpdateCustomerDTO updateCustomerDTO = UpdateCustomerDTO.builder()
                                                               .name("")
                                                               .status(null)
                                                               .build();

        String jsonRequest = this.objectMapper.writeValueAsString(updateCustomerDTO);

        mockMvc.perform(put("/api/v1/customers/{identification}", 123456789L).contentType(MediaType.APPLICATION_JSON)
                                                                                                      .content(jsonRequest))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
               .andExpect(jsonPath("$.message").value("Error de validación"))
               .andExpect(jsonPath("$.data.name").value("El nombre es obligatorio"))
               .andExpect(jsonPath("$.data.status").value("El estado es obligatorio"));
    }

    @Test
    void should_return_error_when_update_id_incorrect() throws Exception {
        Long identification = 123456789L;
        String jsonRequest = this.objectMapper.writeValueAsString(mockUpdateCustomerDTO());

        when(this.customerService.update(eq(identification), any(UpdateCustomerDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/customers/{identification}", identification).contentType(MediaType.APPLICATION_JSON)
                                                                                             .content(jsonRequest))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
               .andExpect(jsonPath("$.message").value("La identificación 123456789 no esta registrada"));
    }

    @Test
    void should_return_ok_when_delete_customer() throws Exception {
        String expectedMessage = "Cliente eliminado correctamente";

        when(this.customerService.delete(123456789L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/customers/{identification}", 123456789L))
               .andExpect(status().isOk())
               .andExpect(content().string(expectedMessage));
    }

    @Test
    void should_return_error_when_id_customer_incorrect() throws Exception {
        String expectedMessage = "La identificación 123456789 no esta registrada";

        when(this.customerService.delete(123456789L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/customers/{identification}", 123456789L))
               .andExpect(status().isBadRequest())
               .andExpect(content().string(expectedMessage));
    }

    @Test
    void should_internal_server_error() throws Exception {
        when(customerService.findAll()).thenThrow(new RuntimeException("DB down"));

        mockMvc.perform(get("/api/v1/customers"))
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
               .andExpect(jsonPath("$.message").value("Ocurrió un error inesperado, contacte al administrador"));
    }
}
