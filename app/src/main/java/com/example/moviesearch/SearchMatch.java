package com.example.moviesearch;

import java.util.List;

// performs a search that a certain attribute matches the
// search query

public class SearchMatch extends SearchTerm {
    private String searchCategory;
    private String searchQuery;

    @Override
    public List<Movie> search(List<Movie> movieList) {
        return movieList;
    }

    public SearchMatch(String searchCategory, String searchQuery) {
        this.searchCategory = searchCategory;
        this.searchQuery = searchQuery;
    }


    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public String debugShow() {
        return "CATEGORY: " + this.searchCategory + " QUERY: " + this.searchQuery;
    }
}
