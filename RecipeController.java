package com.upc.foodiabackend.controller;

import com.upc.foodiabackend.dtos.RecipeDTO;
import com.upc.foodiabackend.entities.Recipe;
import com.upc.foodiabackend.services.IRecipeService;
import com.upc.foodiabackend.servicesimplements.RecipeServiceImpl;
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
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    @GetMapping("/list")
    public ResponseEntity<List<RecipeDTO>> recipeList() {
        ModelMapper modelMapper=new ModelMapper();
        List<RecipeDTO>rec= Arrays.asList(
                modelMapper.map(recipeService.list(),
                        RecipeDTO[].class)
        );
        return new ResponseEntity<>(rec, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<RecipeDTO> save(@RequestBody RecipeDTO recipeDto){
        ModelMapper modelMapper=new ModelMapper();
        Recipe recipe=modelMapper.map(recipeDto,Recipe.class);
        recipe=recipeService.save(recipe);
        recipeDto=modelMapper.map(recipe,RecipeDTO.class);
        return new ResponseEntity<>(recipeDto,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<RecipeDTO> update(@RequestBody RecipeDTO recipeDto) {
        Recipe recipe;
        try{
            ModelMapper modelMapper=new ModelMapper();
            recipe=modelMapper.map(recipeDto,Recipe.class);
            recipe=recipeService.update(recipe);
            recipeDto=modelMapper.map(recipe,RecipeDTO.class);
            return new ResponseEntity<>(recipeDto,HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update");
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        recipeService.delete(id);
    }

    @GetMapping("/filterByType/{type}")
    public ResponseEntity<List<RecipeDTO>>filterByType(@PathVariable("type")String type){
        ModelMapper modelMapper=new ModelMapper();
        List<RecipeDTO>rec= Arrays.asList(
                modelMapper.map(recipeService.filterByType(type),
                        RecipeDTO[].class)
        );
        return new ResponseEntity<>(rec,HttpStatus.OK);
    }
    @GetMapping("/search/{prefix}")
    public ResponseEntity<List<RecipeDTO>>search(@PathVariable("prefix")String prefix){
        try{
            ModelMapper modelMapper=new ModelMapper();
            List<RecipeDTO>rec= Arrays.asList(
                    modelMapper.map(recipeService.search(prefix),
                            RecipeDTO[].class)
            );
            return new ResponseEntity<>(rec,HttpStatus.OK);
        }catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
    }
}
