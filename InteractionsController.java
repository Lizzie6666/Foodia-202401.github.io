package com.upc.foodiabackend.controller;

import com.upc.foodiabackend.dtos.InteractionsDTO;
import com.upc.foodiabackend.entities.Interactions;
import com.upc.foodiabackend.servicesimplements.InteractionsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/interactions")
public class InteractionsController {
    @Autowired
    private InteractionsServiceImpl interactionsService;


    @GetMapping("/list")
    public ResponseEntity<List<InteractionsDTO>> list(){
        ModelMapper modelMapper=new ModelMapper();
        List<InteractionsDTO>interac= Arrays.asList(
                modelMapper.map(interactionsService.list(),
                        InteractionsDTO[].class)
        );
        return new ResponseEntity<>(interac, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<InteractionsDTO> save(@RequestBody InteractionsDTO interactionsDTO){
        ModelMapper modelMapper=new ModelMapper();
        Interactions interactions=modelMapper.map(interactionsDTO,Interactions.class);
        interactions=interactionsService.save(interactions);
        interactionsDTO=modelMapper.map(interactions,InteractionsDTO.class);
        return new ResponseEntity<>(interactionsDTO,HttpStatus.OK);
    }
}
