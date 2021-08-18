package com.aluraflix.aluraflixchallengebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "TB_VIDEOS")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title", length = 45)
    private String title;
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "url", length = 45)
    private String url;

    @OneToOne
    @JsonIgnoreProperties("video")
    private Category category;

    public Video() {
    }

    public Video(int id, String title, String description, String url, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public void setAll(int id, String title, String description, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }


}
