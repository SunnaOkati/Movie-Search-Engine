package com.example.moviesearch;

import android.util.Log;

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

    // array of actual quantifiers tokens (>, >=, =, <=, <)
    ArrayList<Token.Type> quantifiers = new ArrayList<Token.Type>(
            Arrays.asList(
                    Token.Type.LT,
                    Token.Type.LTEQ,
                    Token.Type.EQ,
                    Token.Type.GTEQ,
                    Token.Type.GT
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

            // skips over twice to the next tokens, ensures that the next
            // token's type is valid.

            // moves on to the next token
            _tokenizer.next();

            if (_tokenizer.hasNext()) {
                // if the token type is not a colon, return null
                if (_tokenizer.current().type() != Token.Type.COL) {
                    return null;
                }
            }
            else {
                return null;
            }

            // gets the next token
            _tokenizer.next();

            // checks again, given that no extra logic to account for the colon
            // token is required.
            if (_tokenizer.hasNext()) {
                // if the next token type is not a MATCH value, then return null
                // as the input is invalid.
                if (_tokenizer.current().type() != Token.Type.MATCH) {
                    return null;
                }
            }
            else {
                return null;
            }

            // gets the current token
            String searchQuery = _tokenizer.current().token();

            // moves on to the next token
            _tokenizer.next();

            return new SearchMatch(searchCategory, searchQuery);
        }
        else if (quantTokens.contains(_tokenizer.current().type())) {
            // case where token assesses against a quantity
            // sets the current type
            String searchCategory = _tokenizer.current().token();
            int quantity;

            // gets the next token
            _tokenizer.next();

            // checks if there is a valid "next token" in the sequence,
            // if there isn't then return null
            if (_tokenizer.hasNext()) {
                if (!(quantifiers.contains(_tokenizer.current().type()))) {
                    return null;
                }
            }
            else {
                return null;
            }

            // checks the token type, gets the quantifier based on the result
            String quantifierType = _tokenizer.current().token();

            // gets the next token
            _tokenizer.next();

            // checks if the end of the query has been reached.
            if (_tokenizer.hasNext()) {
                // gets the quantity based on the current token, this will have already
                // been processed by the tokenizer so it should already be a numerical value
                if (_tokenizer.current().token().equals("")) {
                    quantity = 0;
                }
                else {
                    quantity = Integer.parseInt(_tokenizer.current().token());
                }

                // moves on to the next token
                _tokenizer.next();

                return new SearchQuant(searchCategory, quantifierType, quantity);
            }
            else {
                return null;
            }
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
//            Log.d("Parsing activity", "Debug: " + searchTerm.debugShow());
            // adds to the search terms list
            searchTerms.add(searchTerm);
        }

        return searchTerms;
    }
}
