package com.example.moviesearch;

import java.util.List;

// performs a search comparing a quantity to a given
// input, ie. (> amount, = amount, < amount)

public class SearchQuant extends SearchTerm {

    private String searchCategory;
    private String quantifier;
    private int quantity;

    public SearchQuant(String searchCategory, String quantifier, int quantity) {
        this.searchCategory = searchCategory;
        this.quantifier = quantifier;
        this.quantity = quantity;
    }

    @Override
    public List<Movie> search(List<Movie> movieList) {
        return movieList;
    }

    @Override
    public String debugShow() {
        return "CATEGORY: " + this.searchCategory + " QUANTIFIER: " + this.quantifier + " QUANTITY: " + Integer.toString(this.quantity);
    }
}
