package com.rentalhub.controller;

import com.rentalhub.dto.ClientDto;
import com.rentalhub.dto.ClientInformationDto;
import com.rentalhub.mappers.ClientMapper;
import com.rentalhub.model.Client;
import com.rentalhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final ClientMapper clientMapper;

    @Autowired
    public UserController(UserService userService, ClientMapper clientMapper) {
        this.userService = userService;
        this.clientMapper = clientMapper;
    }

    @PutMapping("/edit-client")
    public ResponseEntity<Client> editClient(@RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(userService.editClient(clientMapper.toClient(clientDto)));
    }

    @PutMapping("/change-state/{login}/{newState}")
    public ResponseEntity<Client> changeState(@PathVariable String login, @PathVariable boolean newState) {
        return ResponseEntity.ok(userService.changeClientActivity(login, newState));
    }

    @GetMapping("/{login}")
    public ResponseEntity<ClientInformationDto> getClientByLogin(@RequestBody String login) {
        Optional<Client> clientOptional = userService.getClient(login);
        return clientOptional.map(client -> ResponseEntity.ok(clientMapper.toClientInformationDto(client)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
