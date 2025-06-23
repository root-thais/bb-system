package com.barber.systembarber.service;

import ch.qos.logback.core.net.server.Client;
import com.barber.systembarber.dto.ClienteDTO;
import com.barber.systembarber.model.Cliente;
import com.barber.systembarber.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository client_repository;

   public Cliente registerClient(Cliente new_cliente) {
       try {
           return client_repository.save(new_cliente);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

   public Cliente getClientById(Long id) {
       try {
           return client_repository.findById(id).orElseThrow(() ->
                   new EntityNotFoundException("Client not found"));
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

   public List<Cliente> getClients() {
       try {
           return client_repository.findAll();
       }  catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

   public Cliente updateClientRecords(Long id, Cliente changed_cliente) {
        try {
            Cliente verify_client = client_repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + id + " não encontrado."));

                verify_client.setName(changed_cliente.getName());
                verify_client.setPhone(changed_cliente.getPhone());
                verify_client.setEmail(changed_cliente.getEmail());

             return client_repository.save(verify_client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
   }
}