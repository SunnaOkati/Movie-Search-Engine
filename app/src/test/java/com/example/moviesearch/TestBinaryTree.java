package com.example.moviesearch;

import com.example.moviesearch.BinaryTree.BinaryTree;
import com.example.moviesearch.BinaryTree.EmptyBinaryTree;
import com.example.moviesearch.BinaryTree.NonEmptyBinaryTree;
import com.example.moviesearch.Utils.Soundex;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBinaryTree {

    NonEmptyBinaryTree tree;
   /* Movie m8=new Movie(Soundex.encode("Stand by Me"),"Stand by Me","Adventure",1986);
    Movie m2=new Movie(Soundex.encode("Top Gun"),"Top Gun","Action",1986);
    Movie m3=new Movie(Soundex.encode("The Allnighter"),"The Allnighter","Action",1987);
    Movie m4=new Movie(Soundex.encode("Young Guns"),"Young Guns","Action",1988);
    Movie m5=new Movie(Soundex.encode("The Adventures of Baron Munchausen"),"The Adventures of Baron Munchausen","Adventure",1988);
    Movie m6=new Movie(Soundex.encode("Harry Potter and the Chamber of Secrets"),"Harry Potter and the Chamber of Secrets","Adventure",2002);
    Movie m7=new Movie(Soundex.encode("Catch Me If You Can"),"Catch Me If You Can","Biography",2002);
    Movie m1=new Movie(Soundex.encode("Equilibrium"),"Equilibrium","Action",2002);
    Movie m9=new Movie(Soundex.encode("Blade II"),"Blade II","Action",2002);
    Movie m10=new Movie(Soundex.encode("Ice Age"),"Ice Age","Animation",2002);
    Movie m11=new Movie(Soundex.encode("Orange County"),"Orange County","comedy",2003);
    Movie m12=new Movie(Soundex.encode("The Medallion"),"The Medallion","Action",2003);

   {
    "country": "Hong Kong",
    "director": "Stephen Chow",
    "genre": "Action",
    "name": "Shaolin Soccer",
    "rating": "PG",
    "star": "Stephen Chow",
    "writer": "Stephen Chow",
    "runtime": 113,
    "year": 2001,
    "price": 2342,
    "score": 7.3
  },

    */
   Movie m10=new Movie(Soundex.encode("Shaolin Soccer"),"Hong Kong","Stephen Chow","Action","Shaolin Soccer","PG"
   ,113,7.2,"Stephen Chow","Stephen Chow",2001,2342);
   Movie m9=new Movie(Soundex.encode("The Experiment"),"Germany","Oliver Hirschbiegel","Drama","The Experiment","R",
           114,7.8,"Moritz Bleibtreu","Mario Giordano",2001,2136);
   Movie m8=new Movie(Soundex.encode("Lagaan Once Upon a Time in India"),"India","Ashutosh Gowariker","Adventure","Lagaan Once Upon a Time in India","PG",
   224,8.2,"Aamir Khan","Ashutosh Gowariker",2001,676);
   Movie m7=new Movie(Soundex.encode("The Year My Voice Broke"),"Australia","John Duigan","Drama","The Year My Voice Broke","PG-13",
           103,7.5,"Noah Taylor","John Duigan",1987,2032);

   Movie m6=new Movie(Soundex.encode("El libro de cabecera"),"Netherlands","Peter Greenway","Drama","El libro de cabecera","NOT RATED",
           126,6.7,"Vivian Wu","Sei Shonagan",1996,1958);

Movie m5=new Movie(Soundex.encode("Bend It Like Beckham"),"UK","Gurinder chadha","Comedy","Bend It Like Beckham",
           "PG-13",112,6.7,"Parminder Nagra","Gurinder Chadha",2002,1696);

   Movie m4=new Movie(Soundex.encode("xXx"),"USA","Rob Cohen","Action","xXx",
           "PG-13",124,5.8,"Vin Diesel","Rich Wilkes",2002,1539);

   Movie m1=new Movie(Soundex.encode("Equilibrium"),
           "USA",
            "Kurt Wimmer",
             "Action",
             "Equilibrium",
             "R",
             107,
           7.5,
           "Christian Bale",
             "Kurt Wimmer",
            2002,
           613
            );
   Movie m2=new Movie(Soundex.encode("Die Another Day"),
           "UK",
           "Lee Tamahori",
           "Action",
           "Die Another Day",
           "PG-13",
           133,
           6.1,
           "Pierce Brosnan",
           "Ian Fleming",
           2002,
           1719);
   Movie m3=new Movie(Soundex.encode("Ice Age"),"USA","Chris Wedge","Animation","Ice Age","PG",81,7.6,"Denis Leary","Michael J. Wilson",2002,796);
    @Before
    public void beforeEachTestMethod() {
        tree = new NonEmptyBinaryTree(m1,null);

        tree = (NonEmptyBinaryTree) tree.insert(m2)
        .insert(m3)
        .insert(m4)
        .insert(m5)
                .insert(m6).insert(m7).insert(m8).insert(m9).insert(m10);

    }

    @Test
    public void testEmpty(){
        Assert.assertFalse(tree.isEmpty());
    }
    @Test
    public void testTreeSize(){
        Assert.assertEquals(10,tree.size());
    }
    @Test
    public void testPreorder(){


        Assert.assertEquals("E241 D536 B533 I220 E441 X000 T651 L255 T216 S452",tree.preOrderShow());


    }
    @Test
    public void testHeight(){
        Assert.assertEquals(6,tree.height());
        //System.out.println(tree.treeshow());
    }


}
