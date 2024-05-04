package com.upc.foodiabackend.controller;

import com.upc.foodiabackend.dtos.IngredientDTO;
import com.upc.foodiabackend.entities.Ingredient;
import com.upc.foodiabackend.services.IIngredientService;
import com.upc.foodiabackend.servicesimplements.IngredientServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IIngredientService ingredientService;

    @GetMapping("/list")
    public ResponseEntity<List<IngredientDTO>> list(){
        ModelMapper modelMapper=new ModelMapper();
        List<IngredientDTO> ingredient= Arrays.asList(
                modelMapper.map(ingredientService.list(),
                        IngredientDTO[].class)
        );
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<IngredientDTO> save(@RequestBody IngredientDTO ingredientDto){
        ModelMapper modelMapper=new ModelMapper();
        Ingredient ingredient=modelMapper.map(ingredientDto,Ingredient.class);
        ingredient=ingredientService.save(ingredient);
        ingredientDto=modelMapper.map(ingredient,IngredientDTO.class);
        return new ResponseEntity<>(ingredientDto,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<IngredientDTO> update(@RequestBody IngredientDTO ingredientDto) {
        Ingredient ingredient;
        try{
            ModelMapper modelMapper=new ModelMapper();
            ingredient=modelMapper.map(ingredientDto,Ingredient.class);
            ingredient=ingredientService.update(ingredient);
            ingredientDto=modelMapper.map(ingredient, IngredientDTO.class);
            return new ResponseEntity<>(ingredientDto,HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        ingredientService.delete(id);
    }

}
