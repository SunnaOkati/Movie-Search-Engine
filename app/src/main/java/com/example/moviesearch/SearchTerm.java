package com.example.moviesearch;

import java.util.List;

// search terms for the current search
public abstract class SearchTerm {
    private String searchCategory;

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public abstract List<Movie> search(List<Movie> movieList);
    public abstract String debugShow();
}
