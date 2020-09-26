package com.example.moviesearch;// written by Maitreyi Singh <u7075106>
// reference : Lab and Lecture codes

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTokenizer {
    private static Tokenizer tokenizer;
    @Test(timeout=1000)
    public void testMovieFieldToken() {
        tokenizer = new Tokenizer("\"movie\"");

        //check the type of the first token
        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());

        //check the actual token value
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testMovToken() {
        tokenizer = new Tokenizer("2005");

        assertEquals("wrong token type", Token.Type.YEAR, tokenizer.current().type());
        assertEquals("wrong token value", "2005", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testYearFieldToken() {
        tokenizer = new Tokenizer("\"year\"");

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testYearToken() {
        tokenizer = new Tokenizer("1964");

        assertEquals("wrong token type", Token.Type.YEAR, tokenizer.current().type());
        assertEquals("wrong token value", "1964", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testTokenPartialQuery(){
        tokenizer = new Tokenizer("\"movie\": happy");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.COL_MOVIE, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.MOVIE_NAME, tokenizer.current().type());
        assertEquals("wrong token value", "happy", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testTokenQuery(){
        tokenizer = new Tokenizer("\"movie\" : lucky \"year\" >= 1964 \"genre\" : romantic");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token ':'

        assertEquals("wrong token type", Token.Type.COL_MOVIE, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token 'lucky'

        assertEquals("wrong token type", Token.Type.MOVIE_NAME, tokenizer.current().type());
        assertEquals("wrong token value", "lucky", tokenizer.current().token());

        tokenizer.next(); // accessing the fourth token '"year"'
        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());

        tokenizer.next(); // accessing the fifth token '>='

        assertEquals("wrong token type", Token.Type.GTEQ, tokenizer.current().type());
        assertEquals("wrong token value", ">=", tokenizer.current().token());

        tokenizer.next(); // accessing the sixth token '1964'

        assertEquals("wrong token type", Token.Type.YEAR, tokenizer.current().type());
        assertEquals("wrong token value", "1964", tokenizer.current().token());

        tokenizer.next(); // accessing the seventh token '"genre"'
        assertEquals("wrong token type", Token.Type.GENRE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"genre\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token ':'

        assertEquals("wrong token type", Token.Type.COL_GENRE, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token 'romantic'

        assertEquals("wrong token type", Token.Type.GENRE, tokenizer.current().type());
        assertEquals("wrong token value", "romantic", tokenizer.current().token());
    }
}
