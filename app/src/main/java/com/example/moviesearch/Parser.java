package com.example.moviesearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    // initialisation for the parser
    Tokenizer _tokenizer;

    // array of any colon delimited tokens (exact match searches)
    ArrayList<Token.Type> eqTokens = new ArrayList<Token.Type>(
            Arrays.asList(
                    Token.Type.MOVIE_FIELD,
                    Token.Type.GENRE_FIELD
            )
    );

    // array of quantifier delimited tokens (searches relating to an amount)
    ArrayList<Token.Type> quantTokens = new ArrayList<Token.Type>(
            Arrays.asList(
                    Token.Type.YEAR_FIELD
            )
    );


    public Parser(Tokenizer tokenizer) {
        _tokenizer = tokenizer;
    }

    public SearchTerm parseSearch() {
        // checks the type of field being assessed
        if (eqTokens.contains(_tokenizer.current().type())) {
            // case where the token requires an exact match

            // sets the current type
            String searchCategory = _tokenizer.current().token();

            // skips over twice to the next tokens
            _tokenizer.next();
            _tokenizer.next();

            // gets the current token
            String searchQuery = _tokenizer.current().token();

            return new SearchMatch(searchCategory, searchQuery);
        }

        else if (quantTokens.contains(_tokenizer.current().type())) {
            // case where token assesses against a quantity
            // sets the current type
            String searchCategory = _tokenizer.current().token();

            // gets the next token in the sequence
            _tokenizer.next();

            // checks the token type, gets the quantifier based on the result
            String quantifierType = _tokenizer.current().token();

            // gets the next token in the sequence
            _tokenizer.next();

            /* gets the quantity based on the current token
            TODO: add a check for error cases where the token can't be converted to an int
             */
            int quantity = Integer.parseInt(_tokenizer.current().token());

            return new SearchQuant(searchCategory, quantifierType, quantity);
        }

        return null;
    }

    // parses over the search query until the end is reached
    public List<SearchTerm> getSearchTerms() {
        // initialise an arraylist
        ArrayList<SearchTerm> searchTerms = new ArrayList<SearchTerm>();

        // while the tokenizer still has tokens, loop over and add to the searchTerms
        while (_tokenizer.hasNext()) {
            // gets a new search term
            SearchTerm searchTerm = parseSearch();

            // adds to the search terms list
            searchTerms.add(searchTerm);

            // if the tokenizer still has tokens, move on
            if (_tokenizer.hasNext()) {
                _tokenizer.next();
            }
        }

        return searchTerms;
    }
}
