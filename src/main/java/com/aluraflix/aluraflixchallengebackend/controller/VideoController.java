package com.aluraflix.aluraflixchallengebackend.controller;

import com.aluraflix.aluraflixchallengebackend.dao.CategoryDAO;
import com.aluraflix.aluraflixchallengebackend.dao.VideoDAO;
import com.aluraflix.aluraflixchallengebackend.model.Category;
import com.aluraflix.aluraflixchallengebackend.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class VideoController {

    @Autowired
    private VideoDAO videoDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    @GetMapping("/videos")
    public ResponseEntity<List<Video>> getAll() {
        List<Video> listVideos = (List<Video>) videoDAO.findAll();
        if (listVideos.size() == 0) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(listVideos);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable int id) {
        Video video = videoDAO.findById(id).orElse(null);
        if (video != null) {
            return ResponseEntity.ok(video);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping(value = "/categories/{id}/videos/")
    public ResponseEntity<List<Video>> getVideosByCategory(@PathVariable int id) {
        List<Video> listVideo = (List<Video>) videoDAO.findByCategoryId(id);

        if (listVideo.size() == 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.ok(listVideo);
    }

    @GetMapping(value = "/videos/")
    public ResponseEntity<List<Video>> getVideosByTitle(@RequestParam(value = "search", required = true) String title) {
        List<Video> listVideo = videoDAO.findByTitleContainingIgnoreCase(title);

        if (listVideo.size() == 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.ok(listVideo);
    }

    @PostMapping("newVideo")
    public ResponseEntity<Video> add(@RequestBody Video video) {
        try {
            if (video.getCategory() == null) {
                Category category = categoryDAO.findById(1).orElse(null);
                if (category != null) {
                    video.setCategory(category);
                    videoDAO.save(video);
                    return ResponseEntity.ok(video);
                }

                return new ResponseEntity("Invalid category ", HttpStatus.BAD_REQUEST);
            }

            Category category = categoryDAO.findById(video.getCategory().getId()).orElse(null);
            if (category == null) {
                return new ResponseEntity("Invalid category ", HttpStatus.BAD_REQUEST);
            }
            video.setCategory(category);
            videoDAO.save(video);
            return ResponseEntity.ok(video);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(403).build();

        }
    }

    @PutMapping("/videos/{id}/update")
    public Video replaceVideo(@PathVariable int id, @RequestBody Video videoBody) {
        return videoDAO.findById(id)
                .map(video -> {
                    video.setTitle(videoBody.getTitle());
                    video.setDescription(videoBody.getDescription());
                    video.setUrl(videoBody.getUrl());
                    return videoDAO.save(video);
                })
                .orElseGet(() -> {
                    videoBody.setId(id);
                    return videoDAO.save(videoBody);
                });
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable int id) {
        Video video = videoDAO.findById(id).orElse(null);
        if (video != null) {
            videoDAO.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("id number: " + id + " deleted successfully");
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("It was not possible to delete because id: " + id + " not found");
    }

}










