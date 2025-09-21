package com.app.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends Person {
    @Column(name = "customer_id", unique = true)
    private UUID customerId;

    private String password;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @PrePersist
    public void init() {
        this.customerId = UUID.randomUUID();
    }
}
