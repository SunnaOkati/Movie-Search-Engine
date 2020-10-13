package com.example.moviesearch.Tree;

import com.example.moviesearch.Movie;

/**
 * Base class for node
 *
 * @param <T> data type
 */
public class Node<T> {

	Colour colour;			// Node colour
	Movie movie; 				// Node value
	Node<T> parent; 		// Parent node
	Node<T> left, right; 	// Children nodes

	public Node(Movie value) {
		this.movie  = value;
		this.colour = Colour.RED; //property 3 (if a node is red, both children are black) may be violated if parent is red

		this.parent = null;

		// Initialise children leaf nodes
		this.left 			= new Node<T>();  //leaf node
		this.right 			= new Node<T>();  //leaf node
		this.left.parent 	= this; //reference to parent
		this.right.parent 	= this; //reference to parent
	}

	//Leaf node
	public Node() {
		this.movie 	= null; //leaf nodes are null
		this.colour = Colour.BLACK; //leaf nodes are always black
	}
}