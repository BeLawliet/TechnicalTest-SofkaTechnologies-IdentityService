package com.app.presentation.controller;

import com.app.presentation.dto.CustomerDTO;
import com.app.presentation.dto.ResponseDTO;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import com.app.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CustomerDTO>>> findAllRegisters() {
        ResponseDTO<List<CustomerDTO>> response = this.customerService.findAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<CustomerDTO>> saveRegister(@Valid @RequestBody SaveCustomerDTO request) {
        ResponseDTO<CustomerDTO> response = this.customerService.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "{identification}")
    public ResponseEntity<ResponseDTO<CustomerDTO>> updateRegister(@PathVariable Long identification, @Valid @RequestBody UpdateCustomerDTO request) {
        ResponseDTO<CustomerDTO> response = this.customerService.update(identification, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "{identification}")
    public ResponseEntity<ResponseDTO<String>> deleteRegister(@PathVariable Long identification) {
        return ResponseEntity.ok(this.customerService.delete(identification));
    }
}
