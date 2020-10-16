package com.example.moviesearch.BinaryTree;

import com.example.moviesearch.Movie;

/**
 * BinaryTree - this implements a simple binary search tree for storing a set of
 * integers.
 * 
 * @author Eric McCreath - GPLv2
 * @author Huy Pham - Modified to remove unused methods for the purpose of the lab exercise.
 * @author Ranjth - Modified with respect to the project
 */

public abstract class BinaryTree  {

	public abstract BinaryTree insert(Movie movie); // add an element to the tree, this returns the new/modified tree

	public abstract int size(); // the number of element in the tree

	public abstract int height(); // the height of the tree

	public abstract String preOrderShow(); // show the tree

	public abstract String treeshow(); // print the tree using an ascii drawing 

	public abstract boolean isEmpty(); // check if the tree is empty

	//public abstract BinaryTree delete(Movie movie); // remove an element from the tree, this return the new/modifited tree

	//public abstract Movie biggest(); // find the biggest element in the tree
	
	//public abstract Movie smallest(); // find the smallest element in the tree
	
	public abstract boolean find(Movie movie); // check if the element is in the tree
}
