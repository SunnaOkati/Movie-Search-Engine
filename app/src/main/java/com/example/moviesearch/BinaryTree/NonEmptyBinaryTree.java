package com.example.moviesearch.BinaryTree;

import android.util.Log;
import android.widget.Toast;

import com.example.moviesearch.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * NonEmptyBinaryTree - this is a binary search tree that is either an inner node
 * of the tree or a leaf node. Leaf nodes will have 2 empty nodes attached to 
 * the right and the left subtrees. Note that the insert and remove operation return 
 * the changed tree and they will generally modify existing trees. 
 * 
 * 
 * @author Eric McCreath - GPLv2
 * @author Huy Pham - Modified to remove unused methods for the purpose of the lab exercise.
 * @author dongwookim - add javadocs
 * @auhtor Ranjth - added parent variable for reverse traversal
 */


public class NonEmptyBinaryTree extends BinaryTree {

	Movie movie;		// movie class instance representing a movie
	BinaryTree left, right;	// left and right sub-trees
	NonEmptyBinaryTree parent;
	/**
	 * Create a NonEmptyBinaryTree tree with root node.
	 * The tree will not have sub-trees.
	 * 
	 * @param movie movie represented by the root node.
	 */
	public NonEmptyBinaryTree(Movie movie, NonEmptyBinaryTree parent) {
		this.movie = movie;
		left = new EmptyBinaryTree();
		right = new EmptyBinaryTree();
		this.parent = parent;
	}

	/** 
	 * Create a NonEmptyBinaryTree with left and right sub-trees
	 * @param movie movie represented by the root node.
	 * @param left sub-tree of the new NonEmptyBinaryTree tree
	 * @param right sub-tree of the new NonEmptyBinaryTree tree
	 */
	public NonEmptyBinaryTree(Movie movie, BinaryTree left, BinaryTree right) {
		this.movie = movie;
		this.left = left;
		this.right = right;
	}

	/**
	 * Insert a new movie node into the existing tree.
	 * This function should return the binary tree with movie inserted.
	 *
	 * Yet to be discussed:
	 *  If the tree has already a node with d, do not create a new node
	 * and return the original tree.
	 *
	 * 
	 * @param newMovie new movie instance
	 * @return BinaryTree<T> 
	 */
	public BinaryTree insert(Movie newMovie) {

		if(newMovie.getID().compareTo(movie.getID()) < 0){
			if(left.isEmpty()){
				left = new NonEmptyBinaryTree(newMovie, this);
				return this;
			}
			left = left.insert(newMovie);
		}
		else if(newMovie.getID().compareTo(movie.getID()) > 0){
			if(right.isEmpty()){
				right = new NonEmptyBinaryTree(newMovie, this);
				return this;
			}
			right = right.insert(newMovie);
		}

		return this;
	}

	/**
	 * Returns the size of the tree (number of nodes)
	 */
	public int size() {
		return 1 + left.size() + right.size();
	}

	/**
	 * Compute the height of {@code this} tree
	 */
	@Override
	public int height() {
		return 1 + Math.max(left.height(), right.height());
	}

	/**
	 * Preorder traversing 
	 */
	@Override
	public String preOrderShow() {
		if (left.isEmpty() && right.isEmpty()) return movie.getID() + "";
		else {
			String leftStr = left.preOrderShow();
			String rightStr = right.preOrderShow();
			return movie.getID() + (leftStr.isEmpty() ? leftStr : " " + leftStr) + (rightStr.isEmpty() ? rightStr : " " + rightStr);
		}
	}


	/**
	 * NonEmptyBinaryTree is by definition non-empty. It will return false always.
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/**
	 * Helper functions for visualizing tree.
	 */
	@Override
	public String treeshow() {
		if (left.isEmpty() && right.isEmpty()) return " " + movie.getID() + " ";
		String stl = left.treeshow();
		String str = right.treeshow();
		String stal[] = stl.split("\n");
		String star[] = str.split("\n");
		int lenl = stal[0].length();
		int lenr = star[0].length();
		StringBuffer res = new StringBuffer();
		String strdata = movie.getID() + "";
		
		res.append(repeat(" ", (lenl)) + strdata + repeat(" ", lenr ) + "\n");
		int lcentre = (left.isEmpty() ? 0 : centre(stal[0]));
		int rcentre = (right.isEmpty() ? 0 :centre(star[0]));
		
		res.append(repeat(" ",lcentre) + "+" + repeat("-",lenl-lcentre-1) + "+" + repeat("-",rcentre-1+strdata.length()) +  "+" + repeat(" ",lenr-rcentre -1) + "\n");
		res.append(repeat(" ",lcentre) + (left.isEmpty()? " " : "|") + repeat(" ",lenl-lcentre-1) +  repeat(" ",rcentre + strdata.length()) +  (right.isEmpty()? " " : "|") + repeat(" ",lenr-rcentre-1) +"\n");
		
		for(int i = 0;i<Math.max(stal.length, star.length);i++) {
			res.append( (i<stal.length ? stal[i] : repeat(" ", lenl)) +repeat(" ",strdata.length()) +  (i<star.length? star[i] : repeat(" ", lenr)) + "\n");
		}
		return res.toString();
	}

	protected int centre(String string) {
		int i = 0;
		while (i < string.length() && string.charAt(i) == ' ') i++;
		return i;
	}

	protected String repeat(String string, int n) {
		String res = "";
		for (int i = 0; i<n;i++) res += string;
		return res;
	}


	public List<Movie> relavantResults(){
		List<Movie> path = new ArrayList<Movie>();
		NonEmptyBinaryTree tree = this;
		path.add(tree.movie);

		//Log.d("Search activity", "Parent: " + tree.movie.toString());
		while (tree.parent != null ){
			Log.d("Search activity", "Movie added: " + tree.parent.movie.toString());
			path.add(tree.parent.movie);
			if(tree.parent.left == tree && !tree.parent.right.isEmpty()){
				path.add(((NonEmptyBinaryTree)tree.parent.right).movie);
			}
			else if (tree.parent.right == tree && !tree.parent.left.isEmpty()){
				path.add(((NonEmptyBinaryTree)tree.parent.left).movie);
			}
			tree = tree.parent;
		}
		return path;
	}

	/**
	 * Find whether a specific data is in the tree or not.
	 * if not present, send the leaf node it reaches
	 * @param d
	 * @return Leaf node most relevant to d based on ID
	 */
	@Override
	public BinaryTree find(Movie d) {

		if (d.getID().compareTo(movie.getID()) < 0 && !left.isEmpty()) return left.find(d);
		else if (d.getID().compareTo(movie.getID()) > 0 && !right.isEmpty()) return right.find(d);
		else return this;
	}
}
