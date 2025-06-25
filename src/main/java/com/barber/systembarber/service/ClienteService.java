package com.barber.systembarber.service;


import com.barber.systembarber.exception.notification.NotificationException;
import com.barber.systembarber.exception.severity.Severity;
import com.barber.systembarber.model.Cliente;
import com.barber.systembarber.repository.ClienteRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository client_repository;

	public Cliente registerClient(Cliente new_cliente) {
		validateExistsBy(new_cliente);
		return client_repository.save(new_cliente);
	}

   public Cliente getClientById(Long id) {
       try {
           return client_repository.findById(id).orElseThrow(() ->
                   new NotificationException("Cliente não foi encontrado em nosso sistema!", Severity.INFO));
       } catch (Exception e) {
    	   throw new NotificationException("Erro ao registrar cliente", Severity.ERROR);
       }
   }

	public List<Cliente> getClients() {
		List<Cliente> clientes = client_repository.findAll();
		if (clientes.isEmpty()) {
			throw new NotificationException("Não foi encontrado nenhum cliente em nosso sistema!", Severity.WARNING);
		}
		return clientes;
	}

   public Cliente updateClientRecords(Long id, Cliente changed_cliente) {
	    validateNotBlank(changed_cliente);

	    Cliente existing_client = client_repository.findById(id)
	            .orElseThrow(() -> new NotificationException("Cliente com ID " + id + " não encontrado", Severity.INFO));

	    BeanUtils.copyProperties(changed_cliente, existing_client, "id");

	    try {
	        return client_repository.save(existing_client);
	    } catch (Exception e) {
	        throw new NotificationException("Erro ao atualizar os dados do cliente", Severity.ERROR);
	    }
   }
   
   
	private void validateNotBlank(Cliente cliente) {
		if (!StringUtils.hasText(cliente.getName())) {
			throw new NotificationException("Nome é obrigatório", Severity.WARNING);
		}
		if (!StringUtils.hasText(cliente.getEmail())) {
			throw new NotificationException("E-mail é obrigatório", Severity.WARNING);
		}
		if (!StringUtils.hasText(cliente.getPhone())) {
			throw new NotificationException("Telefone é obrigatório", Severity.WARNING);
		}
	}
	
	
	private void validateExistsBy(Cliente cliente) {
		validateNotBlank(cliente);
		if (client_repository.existsClientByEmail(cliente.getEmail())) {
			throw new NotificationException("E-mail já registrado no sistema!", Severity.INFO);
		}
	}
   
}