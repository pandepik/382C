package q5;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeListSet implements ListSet {
	boolean debug = false;
	Node headNode;
	Node tailNode;
	
  public LockFreeListSet() {
    headNode = new Node(null);
    tailNode = new Node(null);
    headNode.next.set(tailNode,  false);
  }
  
  
  public boolean add(int value) {
	  Node x = new Node(value);
	  while(true) {
		  Window window = find(value);
		  Node pred = window.pred;
		  Node curr = window.curr;
		  if(pred.value == null && curr.value == null) {
			  x.next.set(curr, false);
			  if(pred.next.compareAndSet(curr, x, false, false)) {
				  //System.out.println(this.toString());
				  return true;
			  }
		  }
		  else if(curr.value != null && curr.value == value) {
			  //System.out.println(this.toString());
			  return false;
		  }else {
			  if(curr.value != null && curr.value < value && curr.next.getReference() == null) {
			  		x.next.set(null,  false);
			  		if(curr.next.compareAndSet(null,  x,  false,  false)) {
			  			//System.out.println(this.toString());
			  			return true;
			  		}
			  }else {
				  x.next.set(curr, false);
				  if(pred.next.compareAndSet(curr, x, false, false)) {
					  //System.out.println(this.toString());
					  return true;
				  }
			  	}
		  }
	  }
  }
	  
  public boolean remove(int value) {
    while(true) {
    	Window window = find(value);
    	Node pred = window.pred;
    	Node curr = window.curr;
    	if(curr.value != null && curr.value != value) {
    		return false;
    	}else {
    		Node succ = curr.next.getReference();
    		if(!curr.next.compareAndSet(succ, succ, false, true)) {
    			continue;
    		}
    		pred.next.compareAndSet(curr, succ, false, false);
    		return true;
    	}
    }
  }
	  
  public boolean contains(int value) {
	  boolean[] marked = {false};
	  Node curr = headNode.next.getReference();
	  curr.next.get(marked);
	  while(curr.value < value) {
		  curr = curr.next.getReference();
		  curr.next.get(marked);
	  }
	  return (curr.value == value && !marked[0]);
  }

  public Window find(int value) {
	  Node pred = null;
	  Node curr = null;
	  Node succ = null;
	  boolean[] marked = {false};
//	  if(headNode.value == null || tailNode.value == null){ //breaks code
//		  return new Window(headNode, tailNode);
//	  }
	  boolean snip;
	  retry:while(true) {
		  pred = headNode;
		  curr = pred.next.getReference();
		  while(true) {
			  succ = curr.next.get(marked);
			  while(marked[0]) {
				  snip = pred.next.compareAndSet(curr, succ, false, false);
				  if(!snip) {
					  continue retry;
				  }
				  curr = succ;
				  succ = curr.next.get(marked);
			  }
			  if(curr == tailNode || curr.value >= value) {
				  return new Window(pred, curr);
			  }
			  pred = curr;
			  curr = succ;
		  }
	  }
  }
  
  protected class Node {
  	public Integer value;
  	public AtomicMarkableReference<Node> next;
  	public Node(Integer x) {
  	  value = x;
  	  next = new AtomicMarkableReference<Node>(null, false);
  	}
  }
  
  public class Window{
		public Node pred;
		public Node curr;
		Window(Node pred, Node curr){
			this.pred = pred;
			this.curr = curr;
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
			  if(searchNode.value != null) {
				  s = s + searchNode.value + ",";
			  }
		  }
		  searchNode = searchNode.next.getReference();
		  count++;
	  }
	  return s;
  }
}

/* Dead code
 * //		  }else if(pred.value == null) {
//			  headNode.value = value;
//			  //System.out.println(this.toString());
//			  return true;
//		  }else if(curr.value == null){
//			  if(pred.value > value) {
//				  curr.value = pred.value;
//				  pred.value = value; 
//			  }else if (pred.value == value){
//				  //System.out.println(this.toString());
//				  return false;
//			  }else {
//				  curr.value = value;
//			  }
//			  //System.out.println(this.toString());
//			  return true;
 *		//			  if(curr == tailNode && curr.value < value) {
//				  x.next.set(null, false);
//				  if(curr.next.compareAndSet(null, x, false, false)) {
//					  tailNode = x;
//					  return true;
//				  }
//			  }else {
 *
 *			//				  if(curr == tailNode && curr.value < value) {
//					  return new Window(curr, curr.next.getReference());
//				  }
 *
 *
 */
