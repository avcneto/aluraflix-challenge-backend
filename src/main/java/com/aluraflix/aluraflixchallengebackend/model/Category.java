package com.aluraflix.aluraflixchallengebackend.model;


import javax.persistence.*;

@Entity
@Table(name = "TB_CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", length = 45)
    private String title;

    @Column(name = "color", length = 45)
    private String color;

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public Category(int id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    public void setAll(int id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (!color.isEmpty()) {
            this.color = color;
        }

    }

    public static boolean validCategory(Category category) {
        if (category.getColor() == null) {
            return false;
        }

        if (category.getTitle() == null) {
            return false;
        }

        return true;
    }

}
