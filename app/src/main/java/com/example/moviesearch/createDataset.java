package com.example.moviesearch;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.moviesearch.Utils.Soundex;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class createDataset {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void create() {
        List<Movie> movieList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Random rand = new Random();
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("movies.csv"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] tokens = line.split(splitBy);    // use comma as separator
                String name = "";
                if (tokens[3].matches("^[a-zA-Z][a-zA-Z ]*$")) {
                    name = tokens[3];
                    int runtime = Integer.parseInt(tokens[5]);
                    int year = Integer.parseInt(tokens[9]);
                    double score = Double.parseDouble(tokens[6]);
                    int price = rand.nextInt(2000) + 500;
                    movieList.add(new Movie(Soundex.encode(tokens[3]),tokens[0], tokens[1], tokens[2], name, tokens[4], runtime, score, tokens[7], tokens[8], year, price));
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter("dataset.json")) {
            gson.toJson(movieList, fw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
