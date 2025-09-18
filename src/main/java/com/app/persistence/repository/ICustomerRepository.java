package com.app.persistence.repository;

import com.app.persistence.model.Customer;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends ListCrudRepository<Customer, Long> { }
