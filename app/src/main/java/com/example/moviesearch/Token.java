package com.example.moviesearch;// written by Maitreyi Singh <u7075106>
// reference : Lab and Lecture codes

public class Token {
    public enum Type { MOVIE_FIELD, GENRE_FIELD, YEAR_FIELD, MATCH, QUANTITY, COL, GTEQ, GT, EQ, LTEQ, LT };
    //    GT = greater than, G = greater, EQ = equals, L = lesser, LT = lesser than)
    private String token;
    private Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String token() {
        return token;
    }

    public Type type() {
        return type;
    }
}
