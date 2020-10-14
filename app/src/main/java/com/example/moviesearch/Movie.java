package com.example.moviesearch;

public class Movie {
    String country, director, genre, name, rating, star, writer;
    int runtime, year, price;
    double score;
    public Movie(){

    }

    public Movie(String country, String director, String genre, String name, String rating, int runtime, double score, String star, String writer, int year, int price) {
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.name = name;
        this.rating = rating;
        this.runtime = runtime;
        this.score = score;
        this.star = star;
        this.writer = writer;
        this.year = year;
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
