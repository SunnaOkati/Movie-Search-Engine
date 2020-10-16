package com.example.moviesearch.BinaryTree;

import com.example.moviesearch.Movie;

/**
 * 
 * EmptyBinaryTree - the is the empty tree.  Note it has no fields so all empty trees are the same.  
 * Note I have added a static factory method that just return the same object.  
 * This saves on creating many objects which are all just the same.
 * 
 * @author Eric McCreath - GPLv2
 * @author Huy Pham - Modified to remove unused methods for the purpose of the lab exercise.
 * @author Ranjth - Modified wrt project
 */

public class EmptyBinaryTree extends BinaryTree {
	
	public int size() {
		return 0;
	}

	public BinaryTree insert(Movie movie) {
		return new NonEmptyBinaryTree(movie);
	}

	@Override
	public int height() {
		return -1;
	}

	@Override
	public String preOrderShow() {
		return "";
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public String treeshow() {
		return " ";
	}

	@Override
	public boolean find(Movie movie) {
		return false;
	}

}
