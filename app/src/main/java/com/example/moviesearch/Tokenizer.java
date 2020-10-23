package com.example.moviesearch;/* code by Maitreyi Singh<u7075106>
 Code referenced from lecture and lab code related to parsing
 Query format : "movie" : <movie_name> "year" <|=|> <year> "genre" : <genre_name>
*/

/* rewritten by lawrence flint, u6961306

   the tokenizer can now accept inputs which have unusual queries, such as
   letters and numbers.

   uses the following language:
   Q : Query, S : Segment, F : Field, MATCH : Matching Field, QUANT : Quantity Field

   based on the following grammar, which uses the above language.
   Q = S | S, Q
   S = F : MATCH | F > QUANT | F < QUANT | F = QUANT | F >= QUANT | F <= QUANT

   references:
   https://stackoverflow.com/questions/21074485/remove-special-characters-in-the-string-in-java
   https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
*/

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {

    private String savedText;  // save text
    private Token currentToken;	 // saves token extracted from next()

    private String lastFieldType = ""; // saves the last type of field used.

    // the following have been done as arraylists for scalability and
    // modification, any additional changes to the accepted token
    // types can be done here.

    // list of delimiting characters for a field
    ArrayList<Character> fieldDelimiters = new ArrayList<Character>(
            Arrays.asList(':', '>', '=', '<')
    );

    // array of any field tokens (such as year, movie, etc.)
    ArrayList<Token.Type> fieldTokens = new ArrayList<Token.Type>(
            Arrays.asList(
                    Token.Type.MOVIE_FIELD,
                    Token.Type.GENRE_FIELD,
                    Token.Type.YEAR_FIELD
            )
    );

    // array of actual quantifiers tokens + the colon (>, >=, =, <=, <, :)
    ArrayList<Token.Type> quantifiers = new ArrayList<Token.Type>(
            Arrays.asList(
                    Token.Type.LT,
                    Token.Type.LTEQ,
                    Token.Type.EQ,
                    Token.Type.GTEQ,
                    Token.Type.GT,
                    Token.Type.COL
            )
    );

    /**
     *  The constructor extracts the first token and saves it to the currentToken.
     */
    public Tokenizer(String text) {
        savedText = text;
        next();
    }

    /**
     *  This function will find and extract next token from savedText and save the token to currentToken.
     */
    public void next() {
        savedText = savedText.trim();

        if(savedText.isEmpty()) {
            currentToken = null;
            return;
        }

        // gets the first character in the saved text
        char firstChar = savedText.charAt(0);
        int currShift = 0;

        // tokens used will be determined by the current token, ie. if the last token was
        // a match or quant, the next will be a field of some kind.

        // if the current token is null or the last token was a null or quant, this means
        // that the token is a field.
        if ((currentToken == null)
                || (currentToken.type() == Token.Type.MATCH)
                || (currentToken.type() == Token.Type.QUANTITY)) {

            // intialises the field name
            String field_name = "";

            // for each character, moves until the end of the currently assessed token
            // checks by determining "field end delimiting tokens" such as ">" or ":"

            int pos = savedText.indexOf(firstChar), end = pos + 1;

            // while the current character isn't a field delimiters, add to the saved text
            while (end < savedText.length() && !(fieldDelimiters.contains(savedText.charAt(end))))
                end++;

            // if the end has been reached, compare the query to the field types and check
            // if there's a match
            field_name = savedText.substring(pos, end);

            currShift = end - pos;

            // scrubs the field name of special characters
            field_name = field_name.replaceAll("[^a-zA-Z0-9]", "");

            // removes all the whitespaces from the expression
            field_name = field_name.replaceAll("\\s+","");

            if(field_name.toLowerCase().compareTo("movie") == 0) {
                currentToken = new Token("\"movie\"", Token.Type.MOVIE_FIELD);
                lastFieldType = "MATCH";
            }
            if(field_name.toLowerCase().compareTo("year") == 0) {
                currentToken = new Token("\"year\"", Token.Type.YEAR_FIELD);
                lastFieldType = "QUANT";
            }
            if(field_name.toLowerCase().compareTo("genre") == 0) {
                currentToken = new Token("\"genre\"", Token.Type.GENRE_FIELD);
                lastFieldType = "MATCH";
            }
        }
        // if the last token was a match or quant, this token will be a colon or quantifier
        else if (fieldTokens.contains(currentToken.type())) {
            // initialises the comparison string
            String comparison = "";

            // if the second character is an equals, then it assumes the possibility that the first
            // character could be an gt-or-equals or lt-or-equals.
            if (savedText.indexOf(firstChar) + 1 < savedText.length()) {
                if (savedText.charAt(savedText.indexOf(firstChar) + 1) == '=')
                    comparison += firstChar + "=";
                else
                    comparison += firstChar;
            }
            else {
                comparison += firstChar;
            }

            // switch statement on all the possible comparators + the colon
            if (lastFieldType.equals("MATCH")) {
                currentToken = new Token(":", Token.Type.COL);
            }
            else if (lastFieldType.equals("QUANT")) {
                switch (comparison) {
                    case ":":
                        currentToken = new Token("=", Token.Type.EQ);
                        break;
                    case ">":
                        currentToken = new Token(comparison, Token.Type.GT);
                        break;
                    case "<":
                        currentToken = new Token(comparison, Token.Type.LT);
                        break;
                    case "=":
                        currentToken = new Token(comparison, Token.Type.EQ);
                        break;
                    case ">=":
                        currentToken = new Token(comparison, Token.Type.GTEQ);
                        break;
                    case "<=":
                        currentToken = new Token(comparison, Token.Type.LTEQ);
                        break;
                }
            }

            currShift = comparison.length();
        }
        // if the current token is part of the quant/col tokens, then the next part of the sequence
        // should be a matching token or a quantity
        else if (quantifiers.contains(currentToken.type())) {
            // until a comma is reached, then the rest of the statement is assumed to be part
            // of the query. eliminates issues such as trying to look up sequels or anything with
            // some kind of tagline.

            // TODO: comma delimiting is not idea, perhaps change to another approach later?

            // for each character, moves until the end of the currently assessed token
            // checks by determining "field end delimiting tokens" such as ">" or ":"

            // intialises the query & trim
            String query_input = "";
            String trimmed_query = "";

            int pos = savedText.indexOf(firstChar), end = pos + 1;

            // while the current character isn't a field delimiters, add to the saved text
            while (end < savedText.length() && savedText.charAt(end) != ',')
                end++;

            // if the end has been reached, compare the query to the field types and check
            // if there's a match
            query_input = savedText.substring(pos, end);

            // strips the query input of all extra whitespaces
            trimmed_query = query_input.trim().replaceAll(" +", " ");

            // checks the last input type, creates an appropriate token
            if (lastFieldType.equals("MATCH")) {
                currentToken = new Token(trimmed_query, Token.Type.MATCH);
            }
            else if (lastFieldType.equals("QUANT")) {
                // given that it's a quant, strip all non-numerical values
                trimmed_query = trimmed_query.replaceAll("[^0-9]", "");
                currentToken = new Token(trimmed_query, Token.Type.QUANTITY);
            }

            currShift = query_input.length();
        }

        // Remove the extracted token from buffer
        int tokenLen = currShift;
        savedText = savedText.substring(tokenLen);
    }

    /**
     *  returns the current token extracted by next()
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     *  checks if there are still any tokens in the buffer
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}
