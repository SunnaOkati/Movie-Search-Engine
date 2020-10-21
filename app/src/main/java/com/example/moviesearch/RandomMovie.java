package com.example.moviesearch;

public class RandomMovie {
    private String name;
    private int year;
    private String genre;
    private int id;

    public RandomMovie() {
    }

    public RandomMovie(String name, int year, String genre, int id) {
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }
}
