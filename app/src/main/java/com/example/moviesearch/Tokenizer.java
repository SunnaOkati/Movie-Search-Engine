package com.example.moviesearch;/* code by Maitreyi Singh<u7075106>
 Code referenced from lecture and lab code related to parsing
 Query format : "movie" : <movie_name> "year" <|=|> <year> "genre" : <genre_name>
*/

public class Tokenizer {

    private String savedText;		//save text
    private Token currentToken;	 //saves token extracted from next()

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

        char firstChar = savedText.charAt(0);
        if(firstChar == '"')
        {
            String field_name = "";
            int pos = savedText.indexOf(firstChar)+1, end = pos + 1;
            while(end < savedText.length() && Character.isLetter(savedText.charAt(end)) && savedText.charAt(end) != '"')
                end++;
            field_name += savedText.substring(pos, end);
            if(field_name.toLowerCase().compareTo("movie") == 0)
                currentToken = new Token("\"movie\"", Token.Type.MOVIE_FIELD);
            if(field_name.toLowerCase().compareTo("year") == 0)
                currentToken = new Token("\"year\"", Token.Type.YEAR_FIELD);
            if(field_name.toLowerCase().compareTo("genre") == 0)
                currentToken = new Token("\"genre\"", Token.Type.GENRE_FIELD);
        }
        if(firstChar == ':')
        {
            if(currentToken.type() == Token.Type.MOVIE_FIELD)
                currentToken = new Token(":", Token.Type.COL_MOVIE);
            else if(currentToken.type() == Token.Type.GENRE_FIELD)
                currentToken = new Token(":", Token.Type.COL_GENRE);
        }
        if(Character.isLetter(firstChar))
        {
            String string = "";
            int pos = savedText.indexOf(firstChar), end = pos + 1;
            while(end < savedText.length() && Character.isLetter(savedText.charAt(end)))
                end++;
            string += savedText.substring(pos, end);
            if(currentToken.type() == Token.Type.COL_MOVIE)
                currentToken = new Token(string, Token.Type.MOVIE_NAME);
            if(currentToken.type() == Token.Type.COL_GENRE)
                currentToken = new Token(string, Token.Type.GENRE);
        }
        if(Character.isDigit(firstChar))
        {
            String num = "";
            int pos = savedText.indexOf(firstChar), end = pos + 1;
            while(end < savedText.length() && Character.isDigit(savedText.charAt(end)))
                end++;
            num += savedText.substring(pos, end);
            currentToken = new Token(num, Token.Type.YEAR);
        }
        if(firstChar == '>' || firstChar == '<' || firstChar == '=')
        {
            String comparison = "";
            if(savedText.charAt(savedText.indexOf(firstChar) + 1) == '=')
                comparison += firstChar + "=";
            else
                comparison += firstChar;
            switch(comparison)
            {
                case ">": currentToken = new Token(comparison, Token.Type.GT);
                            break;
                case "<": currentToken = new Token(comparison, Token.Type.LT);
                            break;
                case "=": currentToken = new Token(comparison, Token.Type.EQ);
                            break;
                case ">=": currentToken = new Token(comparison, Token.Type.GTEQ);
                            break;
                case "<=": currentToken = new Token(comparison, Token.Type.LTEQ);
                            break;
            }
        }
        // Remove the extracted token from buffer
        int tokenLen = currentToken.token().length();
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
