package com.upc.foodiabackend.controller;

import com.upc.foodiabackend.entities.User;
import com.upc.foodiabackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Secured({"ADMIN"})
@RequestMapping("/users")
public class UserController {
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private IUserService uService;

    @PostMapping("/save")
    public ResponseEntity<Integer> saveUser(@RequestBody User user) {
        if (uService.buscarUser(user.getUsername()) == 0) {
            String bcryptPassword = bcrypt.encode(user.getPassword());
            user.setPassword(bcryptPassword);
            uService.insertUser(user);
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        }
        return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/save/{user_id}/{rol_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Integer> saveUseRol(@PathVariable("user_id") Long user_id,
                                              @PathVariable("rol_id") Long rol_id){
        return new ResponseEntity<Integer>(uService.insertUserRol(user_id, rol_id),HttpStatus.OK);
        //return new ResponseEntity<Integer>(uService.insertUserRol2(user_id, rol_id),HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(uService.list(),HttpStatus.OK);
    }


}
