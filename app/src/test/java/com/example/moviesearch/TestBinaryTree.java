package com.example.moviesearch;

import com.example.moviesearch.BinaryTree.BinaryTree;
import com.example.moviesearch.BinaryTree.EmptyBinaryTree;
import com.example.moviesearch.BinaryTree.NonEmptyBinaryTree;
import com.example.moviesearch.Utils.Soundex;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBinaryTree {

    NonEmptyBinaryTree tree;
    Movie m8=new Movie(Soundex.encode("Stand by Me"),"Stand by Me","Adventure",1986);
    Movie m2=new Movie(Soundex.encode("Top Gun"),"Top Gun","Action",1986);
    Movie m3=new Movie(Soundex.encode("The Allnighter"),"The Allnighter","Action",1987);
    Movie m4=new Movie(Soundex.encode("Young Guns"),"Young Guns","Action",1988);
    Movie m5=new Movie(Soundex.encode("The Adventures of Baron Munchausen"),"The Adventures of Baron Munchausen","Adventure",1988);
    Movie m6=new Movie(Soundex.encode("Harry Potter and the Chamber of Secrets"),"Harry Potter and the Chamber of Secrets","Adventure",2002);
    Movie m7=new Movie(Soundex.encode("Catch Me If You Can"),"Catch Me If You Can","Biography",2002);
    Movie m1=new Movie(Soundex.encode("Equilibrium"),"Equilibrium","Action",2002);
    Movie m9=new Movie(Soundex.encode("Blade II"),"Blade II","Action",2002);
    Movie m10=new Movie(Soundex.encode("Ice Age"),"Ice Age","Animation",2002);
    @Before
    public void beforeEachTestMethod() {
        tree = new NonEmptyBinaryTree(m1,null);

        tree = (NonEmptyBinaryTree) tree.insert(m2)
        .insert(m3)
        .insert(m4)
        .insert(m5);
               // .insert(m6).insert(m7).insert(m8).insert(m9).insert(m10);

    }
    @Test
    public void testInsertion(){
        //System.out.println(tree.preOrderShow());
        System.out.println(tree.treeshow());
       // System.out.println(tree.height());
        //Assert.assertEquals("E241 C325 B430 T125 H613 S353 I220 T452 T315 Y522",tree.preOrderShow());
        Assert.assertEquals("E241 T125 T452 T315 Y522",tree.preOrderShow());
    }
    @Test
    public void testHeight(){
        Assert.assertEquals(3,tree.height());
    }

   //tree= tree.insert(m6).insert(m7).insert(m8).insert(m9).insert(m10);

}
