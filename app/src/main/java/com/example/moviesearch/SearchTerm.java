package com.example.moviesearch;

import java.util.List;

// search terms for the current search
public abstract class SearchTerm {
    public abstract List<Movie> search(List<Movie> movieList);
    public abstract String debugShow();
}
