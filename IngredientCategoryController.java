package com.upc.foodiabackend.controller;

import com.upc.foodiabackend.dtos.IngredientCategoryDTO;
import com.upc.foodiabackend.entities.IngredientCategory;
import com.upc.foodiabackend.services.IIngredientCategoryService;
import com.upc.foodiabackend.servicesimplements.IngredientCategoryServiceImpl;
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
@RequestMapping("/ingredientCategory")
public class IngredientCategoryController {
    @Autowired
    private IIngredientCategoryService ingredientCategoryService;


    @GetMapping("/list")
    public ResponseEntity<List<IngredientCategoryDTO>> list(){
        ModelMapper modelMapper=new ModelMapper();
        List<IngredientCategoryDTO>ing= Arrays.asList(
                modelMapper.map(ingredientCategoryService.list(),
                        IngredientCategoryDTO[].class)
        );
        return new ResponseEntity<>(ing, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<IngredientCategoryDTO>save(@RequestBody IngredientCategoryDTO ingredientCategoryDTO)
    {
        ModelMapper modelMapper=new ModelMapper();
        IngredientCategory ingredientCategory=modelMapper.map(ingredientCategoryDTO,IngredientCategory.class);
        ingredientCategory=ingredientCategoryService.save(ingredientCategory);
        ingredientCategoryDTO=modelMapper.map(ingredientCategory,IngredientCategoryDTO.class);
        return new ResponseEntity<>(ingredientCategoryDTO,HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<IngredientCategoryDTO> update(@RequestBody IngredientCategoryDTO ingredientCategoryDto) {

        IngredientCategory ingredientCategory;
        try {
            ModelMapper modelMapper = new ModelMapper();
            ingredientCategory = modelMapper.map(ingredientCategoryDto, IngredientCategory.class);
            ingredientCategory = ingredientCategoryService.update(ingredientCategory);
            ingredientCategoryDto = modelMapper.map(ingredientCategory, IngredientCategoryDTO.class);
            return new ResponseEntity<>(ingredientCategoryDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update");
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        ingredientCategoryService.delete(id);
    }
}
