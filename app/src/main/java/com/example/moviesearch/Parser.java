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

            //TODO: Lawrence Sorry I had to comment these lines to get the code woking. Could you help us with explaining what these lines da
//            if (_tokenizer.hasNext()) {
//                // if the token type is not a colon, return null
//                // TODO: refactor to make all COL_x types COL
//                if (_tokenizer.current().type() != Token.Type.COL_MOVIE) {
//                    return null;
//                }
//            }
//            else {
//                return null;
//            }

            // gets the next token
            _tokenizer.next();

            //TODO Sorry I had to comment these lines to get the code woking. Could you help us with explaining what these lines da
//            // checks again, given that no extra logic to account for the colon
//            // token is required.
//            if (_tokenizer.hasNext()) {
//                // if the next token type is not a MATCH value, then return null
//                // as the input is invalid.
//                if (_tokenizer.current().type() != Token.Type.MOVIE_NAME) {
//                    return null;
//                }
//            }
//            else {
//                return null;
//            }
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

            // checks if there is a valid token next in the sequence
            if (_tokenizer.hasNext()) {

                // checks if the token can actually be converted into an integer, if it cannot -
                // then the system returns null.
                try {
                    Integer.parseInt(_tokenizer.current().token());
                }
                catch( Exception e ) {
                    return null;
                }
            }
            else {
                return null;
            }

            /* gets the quantity based on the current token */
            int quantity = Integer.parseInt(_tokenizer.current().token());

            // moves on to the next token
            _tokenizer.next();

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
            Log.d("Parsing activity", "Debug: " + searchTerm.debugShow());
            // adds to the search terms list
            searchTerms.add(searchTerm);
        }

        return searchTerms;
    }
}
