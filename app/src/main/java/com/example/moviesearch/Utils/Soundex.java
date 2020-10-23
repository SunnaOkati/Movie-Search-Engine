package com.example.moviesearch.Utils;

public class Soundex {
    //Soundex encoding
    public static String encode(String s)
    {
        char[] x = s.toUpperCase().toCharArray();
        char firstLetter = '0';

        if (x.length > 0) {
            firstLetter = x[0];
        }

        //Encoding numeric to alphabets
        for (int i = 0; i < x.length; i++) {
            switch (x[i]) {
                case 'B':
                case 'F':
                case 'P':
                case 'V': {
                    x[i] = '1';
                    break;
                }

                case 'C':
                case 'G':
                case 'J':
                case 'K':
                case 'Q':
                case 'S':
                case 'X':
                case 'Z': {
                    x[i] = '2';
                    break;
                }

                case 'D':
                case 'T': {
                    x[i] = '3';
                    break;
                }

                case 'L': {
                    x[i] = '4';
                    break;
                }

                case 'M':
                case 'N': {
                    x[i] = '5';
                    break;
                }

                case 'R': {
                    x[i] = '6';
                    break;
                }

                default: {
                    x[i] = '0';
                    break;
                }
            }
        }
        //RULE [ 1 ]
        String output = "" + firstLetter;

        //Ignore duplicates and '0' created by vowels while concatinating final string
        for (int i = 1; i < x.length; i++)
            if (x[i] != x[i - 1] && x[i] != '0')
                output += x[i];

        //Pad "0" if needed, and truncate to a string size 4
        output = output + "0000";
        return output.substring(0, 4);
    }

    //compare Soundex two encodings
    public int compareEncodings(String s1, String s2){
        int similarity = 0;
        for (int i = 0 ; i < 4; i ++){
            if(s1.charAt(i) == s2.charAt(i))
                similarity++;
        }
        return similarity;
    }
}
