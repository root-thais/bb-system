package com.barber.systembarber.controller;

import com.barber.systembarber.dto.ClienteDTO;
import com.barber.systembarber.model.Cliente;
import com.barber.systembarber.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/newclient")
    public Cliente newClient(@RequestBody Cliente new_client) {
        return clienteService.registerClient(new_client);
    }

    @GetMapping("/allclients")
    public List<Cliente> findAllClients() {
        return clienteService.getClients();
    }

    @GetMapping()
    public Cliente ClientById(@RequestParam Long id) {
        return clienteService.getClientById(id);
    }

    @PatchMapping("/updaterecords")
    public Cliente updateClient(@RequestBody Cliente update_records, @RequestParam Long id) {
        return clienteService.updateClientRecords(id, update_records);
    }
}