package com.example.moviesearch;
// written by Maitreyi Singh <u7075106>
// reference : Lab and Lecture codes

// re-written by lawrence flint, u6961306

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTokenizer {
    private static Tokenizer tokenizer;

    @Test(timeout=1000)
    public void testMovieFieldToken() {
        tokenizer = new Tokenizer("\"movie\"");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testGenreFieldToken() {
        tokenizer = new Tokenizer("\"genre\"");

        assertEquals("wrong token type", Token.Type.GENRE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"genre\"", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testYearFieldToken() {
        tokenizer = new Tokenizer("\"year\"");

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testStrangeField() {
        tokenizer = new Tokenizer("\"year??@#");

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());
    }

    //-----------------------------

    @Test(timeout=1000)
    public void testTokenPartialQuery(){
        tokenizer = new Tokenizer("movie: happy sunrise 2: very happy");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.MATCH, tokenizer.current().type());
        assertEquals("wrong token value", "happy sunrise 2: very happy", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testTokenPartialStrangeField(){
        tokenizer = new Tokenizer("\"g?#$enre??!@#$: comedy");

        assertEquals("wrong token type", Token.Type.GENRE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"genre\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.MATCH, tokenizer.current().type());
        assertEquals("wrong token value", "comedy", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testTokenTrailingMatch(){
        tokenizer = new Tokenizer("movie:      happy feet");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.MATCH, tokenizer.current().type());
        assertEquals("wrong token value", "happy feet", tokenizer.current().token());
    }

    //-----------------------------

    @Test(timeout=1000)
    public void testQuantQuery(){
        tokenizer = new Tokenizer("year >= 1904");

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.GTEQ, tokenizer.current().type());
        assertEquals("wrong token value", ">=", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.QUANTITY, tokenizer.current().type());
        assertEquals("wrong token value", "1904", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testQuantStrangeQuery(){
        tokenizer = new Tokenizer("year$$$ = 198????4sjd");

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.EQ, tokenizer.current().type());
        assertEquals("wrong token value", "=", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.QUANTITY, tokenizer.current().type());
        assertEquals("wrong token value", "1984", tokenizer.current().token());
    }

    //-----------------------------

    @Test(timeout=1000)
    public void testFullQuery(){
        tokenizer = new Tokenizer("movie: big money 2: huge bucks, year >= 1978");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.MATCH, tokenizer.current().type());
        assertEquals("wrong token value", "big money 2: huge bucks", tokenizer.current().token());

        tokenizer.next(); // accessing the fourth token

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());

        tokenizer.next(); // accessing the fifth token

        assertEquals("wrong token type", Token.Type.GTEQ, tokenizer.current().type());
        assertEquals("wrong token value", ">=", tokenizer.current().token());

        tokenizer.next(); // accessing the sixth token

        assertEquals("wrong token type", Token.Type.QUANTITY, tokenizer.current().type());
        assertEquals("wrong token value", "1978", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testFullStrangeQuery(){
        tokenizer = new Tokenizer("mov**(@#&ie:    22    jump street , year?? < 1944, genre: philosophical");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.MATCH, tokenizer.current().type());
        assertEquals("wrong token value", "22 jump street", tokenizer.current().token());

        tokenizer.next(); // accessing the fourth token

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());

        tokenizer.next(); // accessing the fifth token

        assertEquals("wrong token type", Token.Type.LT, tokenizer.current().type());
        assertEquals("wrong token value", "<", tokenizer.current().token());

        tokenizer.next(); // accessing the sixth token

        assertEquals("wrong token type", Token.Type.QUANTITY, tokenizer.current().type());
        assertEquals("wrong token value", "1944", tokenizer.current().token());

        tokenizer.next(); // accessing the seventh token

        assertEquals("wrong token type", Token.Type.GENRE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"genre\"", tokenizer.current().token());

        tokenizer.next(); // accessing the eighth token

        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the ninth token

        assertEquals("wrong token type", Token.Type.MATCH, tokenizer.current().type());
        assertEquals("wrong token value", "philosophical", tokenizer.current().token());
    }

    //-----------------------------

    @Test(timeout=1000)
    public void testMatchStrangeSymbol(){
        tokenizer = new Tokenizer("movie>=riki-oh: story of ricky");

        assertEquals("wrong token type", Token.Type.MOVIE_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"movie\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.COL, tokenizer.current().type());
        assertEquals("wrong token value", ":", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.MATCH, tokenizer.current().type());
        assertEquals("wrong token value", "riki-oh: story of ricky", tokenizer.current().token());
    }

    @Test(timeout=1000)
    public void testQuantStrangeSymbol(){
        tokenizer = new Tokenizer("year:  1984");

        assertEquals("wrong token type", Token.Type.YEAR_FIELD, tokenizer.current().type());
        assertEquals("wrong token value", "\"year\"", tokenizer.current().token());

        tokenizer.next(); // accessing the second token

        assertEquals("wrong token type", Token.Type.EQ, tokenizer.current().type());
        assertEquals("wrong token value", "=", tokenizer.current().token());

        tokenizer.next(); // accessing the third token

        assertEquals("wrong token type", Token.Type.QUANTITY, tokenizer.current().type());
        assertEquals("wrong token value", "1984", tokenizer.current().token());
    }
}
