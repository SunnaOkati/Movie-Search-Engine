package com.example.moviesearch;// written by Maitreyi Singh <u7075106>
// reference : Lab and Lecture codes

public class Token {
    public enum Type {MOVIE_FIELD, MOVIE_NAME, COL_MOVIE, YEAR_FIELD, GTEQ, GT, EQ, LTEQ, LT, YEAR, GENRE_FIELD, GENRE,
    COL_GENRE};
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
