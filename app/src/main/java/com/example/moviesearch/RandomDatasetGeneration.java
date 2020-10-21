package com.example.moviesearch;// Written by Maitreyi Singh (u7075106)

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomDatasetGeneration {
    private static ArrayList<String> name = new ArrayList<>(Arrays.asList("Sky", "Love", "Fault", "Heart", "Sad", "Happy", "Cindrella",
            "Hurt", "Friends", "Avatar", "Thor", "Pink", "Flowers", "Sunrise", "Sunset", "Titanic", "Angry", "Heaven",
            "Hell", "Train", "Midnight", "Reservations", "Black", "Avengers", "Thor", "Coco", "Logan", "Incredibles",
            "Minions", "Zootopia", "Baby", "Shazam!", "Jaws", "Skyfall", "Moana", "Goldfinger", "Aliens", "Ran", "Speed",
            "Stalker", "Looper", "Inception", "Dream", "Godzilla", "Beauty", "Snatch", "Gladiator", "Crash", "Annabelle",
            "Earth"));
    private static ArrayList<String> genre = new ArrayList<>(Arrays.asList("Action", "Comedy", "Sci-Fi", "Thriller",
            "Horror", "Adventure", "Crime", "Drama", "Historical", "Philosophical", "Political", "Romance"));
    private static ArrayList<Integer> year = new ArrayList<>();

    /**
     * Creates an instance of Movie class by randomly forming the names, picking up a genre and year.
     *
     * @return the generated instance of Movie class
     */
    public static RandomMovie createDataset()
    {
        String name;
        String genre;
        int year;
        Random random = new Random();
        int x = 0;
        int movie_name_set = random.nextInt(2) + 1;
        if (movie_name_set == 1) {
            int i = random.nextInt(RandomDatasetGeneration.name.size());
            name = RandomDatasetGeneration.name.get(i);
            i = random.nextInt(RandomDatasetGeneration.genre.size());
            genre = RandomDatasetGeneration.genre.get(i);
            i = random.nextInt(RandomDatasetGeneration.year.size());
            year = RandomDatasetGeneration.year.get(i);
        } else {
            int i = random.nextInt(RandomDatasetGeneration.name.size());
            name = RandomDatasetGeneration.name.get(i);
            i = random.nextInt(RandomDatasetGeneration.name.size());
            name += " " + RandomDatasetGeneration.name.get(i);
            i = random.nextInt(RandomDatasetGeneration.genre.size());
            genre = RandomDatasetGeneration.genre.get(i);
            i = random.nextInt(RandomDatasetGeneration.year.size());
            year = RandomDatasetGeneration.year.get(i);
        }
        int id = name.hashCode();
        return new RandomMovie(name, year, genre, id);
    }

    /**
     * Creates a list of year
     */
    public static void set_year()
    {
        for(int i = 1950; i <= 2020; i ++)
            RandomDatasetGeneration.year.add(i);
    }

    public static void main(String[] args) {
        set_year();
        ArrayList<String> movieName = new ArrayList<>();
        ArrayList<RandomMovie> movieList = new ArrayList<>();

        for(int x = 0; x < 1000; )
       {
           RandomMovie m = createDataset();
           if(!movieName.contains(m.getName()))
           {
               movieName.add(m.getName());
               movieList.add(m);
               x++;
           }
       }
        File f = new File("D:/IdeaProjects/6442Project/RandomDataset.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.newDocument();
            Element rootElement = d.createElement("MovieDataset");
            d.appendChild(rootElement);
            for(RandomMovie mov : movieList)
            {
                Element movieElement = d.createElement("Movie");
				movieElement.setAttribute("ID", String.valueOf(mov.getId()));

                Element nameElement = d.createElement("Name");
                nameElement.appendChild(d.createTextNode(mov.getName()));
                movieElement.appendChild(nameElement);

                Element genreElement = d.createElement("Genre");
                genreElement.appendChild(d.createTextNode(mov.getGenre()));
                movieElement.appendChild(genreElement);

                Element yearReleasedElement = d.createElement("YearReleased");
                String year = Integer.toString(mov.getYear());
                yearReleasedElement.appendChild(d.createTextNode(year));
                movieElement.appendChild(yearReleasedElement);

                rootElement.appendChild(movieElement);
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(d);
            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
