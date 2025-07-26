package com.barber.systembarber.repository;

import com.barber.systembarber.model.Cliente;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	@Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.email = :email")
	boolean existsClientByEmail(String email);

}