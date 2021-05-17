package com.rentalhub.controller;

import com.rentalhub.dto.ClientDto;
import com.rentalhub.mappers.ClientMapper;
import com.rentalhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping("/editClient")
    public ResponseEntity editClient(ClientDto clientDto) {
        userService.editClient(clientMapper.toEditClient(clientDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/blockClient")
    public ResponseEntity block(String login) {
        userService.changeActivity(login, false);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/unblockClient")
    public ResponseEntity unblock(String login) {
        userService.changeActivity(login, false);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getUser")
    public ResponseEntity getUser(String login) {
        return ResponseEntity.ok().body(userService.getUser(login));
    }


}
