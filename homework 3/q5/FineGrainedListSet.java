package q5;

import java.util.concurrent.locks.ReentrantLock;

import q5.CoarseGrainedListSet.Node;


public class FineGrainedListSet implements ListSet {
	boolean debug = false;
	Node headNode;
	
  public FineGrainedListSet() {
	  headNode = null;
  }
	  
  public boolean add(int value) {
	//empty node
		if(headNode == null) {
			headNode = new Node(value);
			return true;
		}
		Node previous = headNode;
		previous.lock.lock();
		try {
			if(previous.next == null) {
				if(previous.value == value) {
					return false;
				}else {
					Node x = new Node(value);
					previous.next = x;
					return true;
				}
			}
			//search for spot to put it
			Node searchNode = headNode.next;
			searchNode.lock.lock();
			try {
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
					previous.lock.unlock();
					previous = searchNode;
					searchNode = previous.next;
					searchNode.lock.lock();
				}
				return false;
			}finally {
				searchNode.lock.unlock();
			}
		}finally {
			previous.lock.unlock();
		}
  }
	  
  public boolean remove(int value) {
	  Node previous = headNode;
	  previous.lock.lock();
	  try {
		  if(previous.value == value) {
			  headNode = previous.next;
			  return true;
		  }
		  Node searchNode = headNode.next;
		  searchNode.lock.lock();
		  try {
			  while(searchNode != null) {
				  if(searchNode.value == value) {
					  previous.next = searchNode.next;
					  return true;
				  }
				  previous.lock.unlock();
				  previous = searchNode;
				  searchNode = previous.next;
				  searchNode.lock.lock();
			  }
			  return false;
		  }finally {
			  searchNode.lock.unlock();
		  }
	  }finally {
		  previous.lock.unlock();
	  }
  }
	  
  public boolean contains(int value) {
	if(headNode == null) {
		return false;
	}
	Node previous = headNode;
	previous.lock.lock();
	try {
		if(previous.value == value) {
			return true;
		}
		Node searchNode = headNode.next;
		searchNode.lock.lock();
		try {
			while(searchNode != null) {
				if(searchNode.value == value) {
					return true;
				}
				previous.lock.unlock();
				previous = searchNode;
				searchNode = previous.next;
				searchNode.lock.lock();
			}
		    return false;
		}finally {
			searchNode.lock.unlock();
		}
	}finally {
		previous.lock.unlock();
	}
  }
	  
  protected class Node {
    public Integer value;
    public Node next;
	public ReentrantLock lock;
    
  	public Node(Integer x) {
  		value = x;
  		lock = new ReentrantLock();
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

/*if(headNode == null) {
	    headNode = new Node(value);
	    return true;
	}
	else {
		headNode.lock.lock();
	    Node previousNode = headNode;
	    try {
	    if(previousNode.next == null) {
	    	if(previousNode.value == value) {
	    	    previousNode.lock.unlock();
	    	    return false;
	    	}else{//(previousNode.value < value) {
	    		Node x = new Node(value);
				previousNode.next = x;
				previousNode.lock.unlock();
				return true;
	    	}
//	    	else {
//	    		Node x = new Node(value);
//	    		x.lock.lock();  //lock the new head
//	    		x.next = previousNode;   //switch the two heads
//	    		previousNode = x;
//	    		previousNode.next.lock.unlock(); //unlock previous head
//	    	}
	    }else {
		    previousNode.next.lock.lock();
		    Node searchNode = previousNode.next;
			//Move to correct Node
			while(searchNode != null) {
			    if(searchNode.value == value) { //Found matching value
			    	//System.out.println(this.toString());
			    	previousNode.lock.unlock();
			    	searchNode.lock.unlock();
			    	return false;
			    }
			    // add the new value
			    if(searchNode.value < value && (searchNode.next == null || searchNode.next.value > value)) {
			    	Node x = new Node(value);
			    	x.next = searchNode;
			    	searchNode.next = x;
			    	previousNode.lock.unlock();
			    	searchNode.lock.unlock();
			    	return true;
			    }else {
			    	previousNode.lock.unlock();
			    	previousNode = searchNode;
			    	searchNode = previousNode.next;
			    	searchNode.lock.lock();
			    }
			 }
			 previousNode.lock.unlock();
			 return false;
	    }
	    }finally {
	    	previousNode.lock.unlock();
	    }
	}
 * 
 * 
 */


