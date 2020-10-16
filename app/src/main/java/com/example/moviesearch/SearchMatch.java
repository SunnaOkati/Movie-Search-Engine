package com.example.moviesearch;

import java.util.List;

// performs a search that a certain attribute matches the
// search query

public class SearchMatch extends SearchTerm {

    private String searchCategory;
    private String searchQuery;

    public SearchMatch(String searchCategory, String searchQuery) {
        this.searchCategory = searchCategory;
        this.searchQuery = searchQuery;
    }

    @Override
    public List<Movie> search(List<Movie> movieList) {
        return movieList;
    }

    @Override
    public String debugShow() {
        return "CATEGORY: " + this.searchCategory + " QUERY: " + this.searchQuery;
    }
}
