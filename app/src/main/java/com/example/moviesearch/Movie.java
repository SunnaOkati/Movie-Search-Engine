package com.example.moviesearch;

public class Movie {
    private String name;
    private int year;
    private String genre;
    private int id;

    public Movie() {
    }

    public Movie(String name, int year, String genre, int id) {
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

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", id=" + id +
                '}';
    }
}
