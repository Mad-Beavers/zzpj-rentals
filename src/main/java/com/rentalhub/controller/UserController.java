package com.rentalhub.controller;

import com.rentalhub.dto.ClientDto;
import com.rentalhub.dto.ClientInformationDto;
import com.rentalhub.mappers.ClientMapper;
import com.rentalhub.model.Client;
import com.rentalhub.model.User;
import com.rentalhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {


    private UserService userService;
    private ClientMapper clientMapper;

    @Autowired
    public UserController(UserService userService, ClientMapper clientMapper) {
        this.userService = userService;
        this.clientMapper = clientMapper;
    }

    @PutMapping("/editClient")
    public ResponseEntity editClient(@RequestBody ClientDto clientDto) {
        userService.editClient(clientMapper.toEditClient(clientDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/blockClient")
    public ResponseEntity block(@RequestBody String login) {
        userService.changeActivity(login, false);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/unblockClient")
    public ResponseEntity unblock(@RequestBody String login) {
        userService.changeActivity(login, false);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUser")
    public ResponseEntity<ClientInformationDto> getClient(@RequestBody String login) {
        return ResponseEntity.ok().body(clientMapper.toEditClientInformation(userService.getClient(login)));
    }


}
