package q5;



public class CoarseGrainedListSet implements ListSet {
	boolean debug = false;
	Node headNode;
	
  public CoarseGrainedListSet() {
	// implement your constructor here
	  headNode = null;
  }
  
  synchronized public boolean add(int value) {
	//empty node
	if(headNode == null) {
		headNode = new Node(value);
		return true;
	}
	//search for spot to put it
	Node searchNode = headNode;
	while(searchNode != null) {
		if(searchNode.value == value) {
		    return false;
		}
		if(searchNode.value < value && (searchNode.next == null || searchNode.next.value > value)) {
			//Insert new Node
			Node x = new Node(value);
			x.next = searchNode.next;
			searchNode.next = x;
			return true;
		}
		searchNode = searchNode.next;
	}
    return false;
  }
  
  synchronized public boolean remove(int value) {
	 if(contains(value)) {
		Node searchNode = headNode;
		while(searchNode != null) {
			if(searchNode.next.value == value) {
				searchNode.next = searchNode.next.next;
				return true;
			}
			searchNode = searchNode.next;
		}
	}
    return false;
  }
  
  synchronized public boolean contains(int value) {
	if(headNode == null) {
		return false;
	}
	Node searchNode = headNode;
	while(searchNode != null) {
		if(searchNode.value == value) {
			return true;
		}
		searchNode = searchNode.next;
	}
    return false;
  }
  
  protected class Node {
	  public Integer value;
	  public Node next;
		    
	  public Node(Integer x) {
		  value = x;
		  next = null;
	  }
  }

  /*
  return the string of list, if: 1 -> 2 -> 3, then return "1,2,3,"
  check simpleTest for more info
  */
  public String toString() {
	  String s = "";
	  int count = 0;
	  if(headNode == null) {
		  return "";
	  }
	  Node searchNode = headNode;
	  while(searchNode != null) {
		  if(count == 50 && debug) {
			  s = s + searchNode.value + "," + "\n";
			  count = 0;
		  }else {
			  s = s + searchNode.value + ",";
		  }
		  searchNode = searchNode.next;
		  count++;
	  }
	  return s;
  }
}
