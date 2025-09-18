package com.app.presentation.controller;

import com.app.presentation.dto.CustomerDTO;
import com.app.presentation.dto.ResponseDTO;
import com.app.presentation.dto.SaveCustomerDTO;
import com.app.presentation.dto.UpdateCustomerDTO;
import com.app.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping
    public ResponseEntity<ResponseDTO> findAllRegisters() {
        List<CustomerDTO> registers = this.customerService.findAll();

        ResponseDTO response = ResponseDTO.builder()
                                          .status(HttpStatus.OK.value())
                                          .message("Se cargaron todos los clientes")
                                          .data(registers)
                                          .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> saveRegister(@Valid @RequestBody SaveCustomerDTO request) {
        CustomerDTO register = this.customerService.save(request);

        ResponseDTO response = ResponseDTO.builder()
                                          .status(HttpStatus.OK.value())
                                          .message("Cliente guardado correctamente")
                                          .data(register)
                                          .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "{identification}")
    public ResponseEntity<ResponseDTO> updateRegister(@PathVariable Long identification, @Valid @RequestBody UpdateCustomerDTO request) {
        Optional<CustomerDTO> register = this.customerService.update(identification, request);

        return register.map(value -> ResponseEntity.ok(ResponseDTO.builder()
                                                                               .status(HttpStatus.OK.value())
                                                                               .message("Cliente actualizado correctamente")
                                                                               .data(value)
                                                                               .build()
                                                                   )
                        )
                        .orElseGet(() -> ResponseEntity.badRequest().body(ResponseDTO.builder()
                                                                                     .status(HttpStatus.BAD_REQUEST.value())
                                                                                     .message(String.format("La identificación %s no esta registrada", identification))
                                                                                     .data(null)
                                                                                     .build()
                        ));
    }

    @DeleteMapping(path = "{identification}")
    public ResponseEntity<String> deleteRegister(@PathVariable Long identification) {
        boolean wasDeleted = this.customerService.delete(identification);

        if (!wasDeleted) return ResponseEntity.badRequest().body(String.format("La identificación %s no esta registrada", identification));

        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}
