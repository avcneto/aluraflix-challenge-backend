package com.aluraflix.aluraflixchallengebackend.dao;

import com.aluraflix.aluraflixchallengebackend.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, Integer> {

}
