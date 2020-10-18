package com.example.moviesearch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestParser {
    private static Tokenizer tokenizer;
    private static Parser parser;

    @Test(timeout=1000)
    public void testParser() {
        tokenizer = new Tokenizer("\"movie\" : lucky luck \"year\" >= 1964 \"genre\" : romantic");
        parser = new Parser(tokenizer);

        List<SearchTerm> searchTerms = parser.getSearchTerms();

        for (SearchTerm term : searchTerms) {
            System.out.println(term.debugShow());
        }
    }
}
