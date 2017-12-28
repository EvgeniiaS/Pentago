package model;

import java.util.LinkedList;
import java.util.List;


/**
 * Represents a node in a game tree.
 * @author Evgeniia Shcherbinina
 *
 */
public class GameTreeNode implements Cloneable {

	// An instance of the Board class
	private MyBoard board;
	// A parent of this node
	private GameTreeNode parent;
	// A child of this node
	private GameTreeNode child;
	// A node of the same depth and with the same parent
	private GameTreeNode sibling;
		
	// Constructs a new GameTreeNode object given a board and a parent
	public GameTreeNode(MyBoard theBoard, GameTreeNode parent) {
		board = theBoard;
		this.parent = parent;
		child = null;
		sibling = null;
	}
	
	// A constructor for the root node
	public GameTreeNode(MyBoard theBoard) {
		this(theBoard, null);
	}
	
	// Returns a parent
	public GameTreeNode getParent() {
		return parent;
	}
	
	// Returns a child
	public GameTreeNode getChild() {
		return child;
	}
	
	// Returns a sibling
	public GameTreeNode getSibling() {
		return sibling;
	}
	
	// Returns a board of this node
	public MyBoard getBoard() {
		return board;
	}
	
	// Sets a child
	public void setChild(GameTreeNode child) {
		this.child = child;
	}
	
	// Sets a sibling
	public void setSibling(GameTreeNode sibling) {
		this.sibling = sibling;
	}
	
	// Adds a child to this node. If the node already has a child, the method adds a sibling to its child.
	// If a child node already has a siblings, the method adds a sibling to the child's sibling and so on.
	public GameTreeNode addChild(MyBoard b) {
		GameTreeNode node = new GameTreeNode(b, this);
		if (child == null) {
			child = node;
		} else if (child.sibling == null) {
			child.sibling = node;
		} else {
			GameTreeNode temp = child.sibling;
			while(temp.sibling != null) {
				temp = temp.sibling;
			}
			temp.sibling = node;
		}
		return node;
	}
	
	// Returns a list of children
	public List<GameTreeNode> getChildren() {
		List<GameTreeNode> children = new LinkedList<>();
		if (child != null) {
			children.add(child);
			if (child.sibling != null) {
				GameTreeNode temp = child.sibling;
				children.add(temp);
				while(temp.sibling != null) {
					temp = temp.sibling;
					children.add(temp);
				}
			}
		}
		return children;
	}
	
	// Checks whether a node has a child
	public boolean hasChild() {
		return child != null;
	}
}
