package com.upc.foodiabackend.controller;

import com.upc.foodiabackend.dtos.RecipeCategoryDTO;
import com.upc.foodiabackend.entities.RecipeCategory;
import com.upc.foodiabackend.services.IRecipeCategoryService;
import com.upc.foodiabackend.servicesimplements.RecipeCategoryServiceImpl;
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
@RequestMapping("recipeCategory")
public class RecipeCategoryController {
    @Autowired
    private IRecipeCategoryService recipeCategoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<RecipeCategoryDTO> save(@RequestBody RecipeCategoryDTO recipeCategoryDTO){
        ModelMapper modelMapper=new ModelMapper();
        RecipeCategory recipeCategory=modelMapper.map(recipeCategoryDTO,RecipeCategory.class);
        recipeCategory=recipeCategoryService.save(recipeCategory);
        recipeCategoryDTO=modelMapper.map(recipeCategory,RecipeCategoryDTO.class);
        return new ResponseEntity<>(recipeCategoryDTO, HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<RecipeCategoryDTO>> list(){
        ModelMapper modelMapper=new ModelMapper();
        List<RecipeCategoryDTO>rec= Arrays.asList(
                modelMapper.map(recipeCategoryService.list(),
                        RecipeCategoryDTO[].class)
        );
        return new ResponseEntity<>(rec,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<RecipeCategoryDTO> update(@RequestBody RecipeCategoryDTO recipeCategoryDTO) {
        RecipeCategory recipeCategory;
        try {
            ModelMapper modelMapper=new ModelMapper();
            recipeCategory=modelMapper.map(recipeCategoryDTO,RecipeCategory.class);
            recipeCategory=recipeCategoryService.update(recipeCategory);
            recipeCategoryDTO=modelMapper.map(recipeCategory,RecipeCategoryDTO.class);
            return new ResponseEntity<>(recipeCategoryDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        recipeCategoryService.delete(id);
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<List<RecipeCategoryDTO>>search(@PathVariable("name") String name){
        try{
            ModelMapper modelMapper=new ModelMapper();
            List<RecipeCategoryDTO>rec= Arrays.asList(
                    modelMapper.map(recipeCategoryService.search(name),
                            RecipeCategoryDTO[].class)
            );
            return new ResponseEntity<>(rec,HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe category not found");
        }
    }
}
