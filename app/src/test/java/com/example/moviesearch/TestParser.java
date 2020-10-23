package com.example.moviesearch;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestParser {
    private static Tokenizer tokenizer;
    private static Parser parser;

    @Test(timeout=1000)
    public void testSingleToken() {
        tokenizer = new Tokenizer("\"movie\"");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        // checks whether the first search term is null
        assertNull(
                "Invalid result for search term. Expected null.",
                searchTerms.get(0)
        );
    }

    @Test(timeout=1000)
    public void testIncompleteQuery() {
        tokenizer = new Tokenizer("\"movie\" : ");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        // checks whether the first search term is null
        assertNull(
                "Invalid result for search term. Expected null.",
                searchTerms.get(0)
        );
    }

    @Test(timeout=1000)
    public void testFullQueryMatch() {
        tokenizer = new Tokenizer("\"movie\" : happy sunrise");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        // checks whether the first search term is a valid search term
        SearchMatch movieMatch = new SearchMatch(
                "\"movie\"",
                "happy sunrise"
        );

        assertEquals(
                "Invalid result for search term. Expected a match term.",
                movieMatch.debugShow(),
                searchTerms.get(0).debugShow()
        );
    }

    @Test(timeout=1000)
    public void testFullQueryQuant() {
        tokenizer = new Tokenizer("\"year\" >= 1964");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        // checks whether the first search term is a valid search term
        SearchQuant yearQuant = new SearchQuant(
                "\"year\"",
                ">=",
                1964
        );

        assertEquals(
                "Invalid result for search term. Expected a match term.",
                yearQuant.debugShow(),
                searchTerms.get(0).debugShow()
        );
    }

    @Test(timeout=1000)
    public void testIncompleteBeforeMatch() {
        tokenizer = new Tokenizer("\"movie\" : \"movie\" : happy sunrise");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        System.out.println(searchTerms.get(0).debugShow());

        // checks the validity of the search term
        SearchMatch movieMatch = new SearchMatch(
                "\"movie\"",
                "\"movie\" : happy sunrise"
        );

        assertEquals(
                "Invalid result for search term. Expected a match term.",
                movieMatch.debugShow(),
                searchTerms.get(0).debugShow()
        );
    }

    @Test(timeout=1000)
    public void testIncompleteBeforeQuant() {
        tokenizer = new Tokenizer("\"year\" < \"movie\" : lucky");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        // checks whether the second search term is a valid search term
        SearchQuant yearQuant = new SearchQuant(
                "\"year\"",
                "<",
                0
        );

        assertEquals(
                "Invalid result for search term. Expected a quant term.",
                yearQuant.debugShow(),
                searchTerms.get(0).debugShow()
        );
    }

    @Test(timeout=1000)
    public void testIncompleteAfterMatch() {
        tokenizer = new Tokenizer("\"movie\" : lucky, \"movie\" : ");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        // checks whether the first search term is a valid search term
        SearchMatch movieMatch = new SearchMatch(
                "\"movie\"",
                "lucky"
        );

        assertEquals(
                "Invalid result for search term. Expected a match term.",
                movieMatch.debugShow(),
                searchTerms.get(0).debugShow()
        );

        // checks whether the first search term is null
        assertNull(
                "Invalid result for search term. Expected null.",
                searchTerms.get(1)
        );
    }

    @Test(timeout=1000)
    public void testIncompleteAfterQuant() {
        tokenizer = new Tokenizer("\"movie\" : unhappy sunset ,\"year\" <=");
        parser = new Parser(tokenizer);

        // the list of search terms returned from getSearchTerms
        List<SearchTerm> searchTerms = parser.getSearchTerms();

        // checks whether the first search term is a valid search term
        SearchMatch movieMatch = new SearchMatch(
                "\"movie\"",
                "unhappy sunset"
        );

        assertEquals(
                "Invalid result for search term. Expected a match term.",
                movieMatch.debugShow(),
                searchTerms.get(0).debugShow()
        );

        // checks whether the first search term is null
        assertNull(
                "Invalid result for search term. Expected null.",
                searchTerms.get(1)
        );
    }
}
