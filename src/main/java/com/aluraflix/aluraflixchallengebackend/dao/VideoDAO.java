package com.aluraflix.aluraflixchallengebackend.dao;


import com.aluraflix.aluraflixchallengebackend.model.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface VideoDAO extends CrudRepository<Video, Integer> {
    public List<Video> findByTitleContainingIgnoreCase(String title);

    public List<Video> findByCategoryId(int id);


}
