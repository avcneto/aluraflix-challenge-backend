package com.aluraflix.aluraflixchallengebackend.controller;

import com.aluraflix.aluraflixchallengebackend.dao.CategoryDAO;
import com.aluraflix.aluraflixchallengebackend.model.Category;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryDAO dao;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAll() {
        ArrayList<Category> listCategory = (ArrayList<Category>) dao.findAll();
        if (listCategory.size() == 0) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(listCategory);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategories(@PathVariable int id) {
        Category category = dao.findById(id).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PostMapping("/newCategory")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        try {
            if (Category.validCategory(category)) {
                dao.save(category);
                return ResponseEntity.ok(category);
            }
            return new ResponseEntity("The field is mandatory", HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/category/{id}/update")
    public Category replaceCategory(@PathVariable int id, @RequestBody Category categoryBody) {
        return dao.findById(id)
                .map(category -> {
                    category.setTitle(categoryBody.getTitle());
                    category.setColor(categoryBody.getColor());
                    return dao.save(category);
                })
                .orElseGet(() -> {
                    categoryBody.setId(id);
                    return dao.save(categoryBody);
                });
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        Category category = dao.findById(id).orElse(null);
        if (category != null) {
            dao.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("id number: " + id + " deleted successfully");
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("It was not possible to delete because id: " + id + " not found");

    }

}
