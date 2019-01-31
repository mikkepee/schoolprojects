/** Priorityqueue class for the Tira2016 Priorityqueue program
 * Done by using properties of minheap and linked binarytree.
 *
 * @author Mikael Paajanen
 * paajanen.mikael.a@student.uta.fi
 */
public class PriorityQueue {
   
   // attributes
   private int size;
   private HeapNode root;
   private HeapNode last;
   
   /* constructor
    * creates an empty priorityqueue
    */
   
   public PriorityQueue() {
      size = 0;
      root = null;
      last = null;
   }
   
   // methods
   
   /* Returns the size of the priorityqueue.
    */
   public int size() {
      return size;
   }
   
   /* Returns true if pq is empty, false otherwise.
    */
   public boolean isEmpty() {
      return size == 0;
   }
      
   /* Inserts the element into the last position of pq.
    * Then percolates the element to the right position.
    * Increases the size of the pq by one.
    *
    * @param key and datum of the element inserted
    */
   public void insertElement(int key, String datum) {
      HeapNode insert = new HeapNode(key, datum, null, null, null);
      // help nodes for percolating
      HeapNode n, m;
      if (isEmpty()) {
         root = insert;
         last = insert;
      }
      else {      
         HeapNode iter = last;
         while (isLeftChild(iter) == 2) {
            iter = iter.getParent();
         }
         if (iter == root) {
            while (iter.getLeftChild() != null) {
               iter = iter.getLeftChild();
            }
            iter.setLeftChild(insert);
            insert.setParent(iter);
            m = percolateUp(insert);
            n = percolateDown(m);
            last = insert;
         }
         else if (isLeftChild(iter) == 1) {
            iter = iter.getParent();
            if (iter.getRightChild() == null) {
               iter.setRightChild(insert);
               insert.setParent(iter);
               m = percolateUp(insert);
               n = percolateDown(m);
               last = insert;
            }
            else {
               iter = iter.getRightChild();
               while (iter.getLeftChild() != null) {
                  iter = iter.getLeftChild();
               }
               iter.setLeftChild(insert);
               insert.setParent(iter);
               m = percolateUp(insert);
               n = percolateDown(m);
               last = insert;
            }
         }
      }
      size++;
   }
   
   /* Removes the min element from the pq.
    * Then moves the the last to the root and percolates it
    * to its right position.
    * Decreases the size of the pq by one.
    *
    * @return the datum of the removed element.
    */
   public String removeMinElement() {
      // help node for percolating
      HeapNode n;
      if (isEmpty())
         return null;
      else {
         String del = root.getDatum();
         root.setKey(last.getKey());
         root.setDatum(last.getDatum());
         if (last == root) {
            root = last = null;
         }
         else if (isLeftChild(last) == 2) {
            HeapNode tmp = last.getParent();
            last.setParent(null);
            tmp.setRightChild(null);
            last = tmp.getLeftChild();
            n = percolateDown(root);
         }
         else {
            HeapNode iter = last.getParent();
            last.setParent(null);
            iter.setLeftChild(null);
            while (isLeftChild(iter) == 1) { 
               iter = iter.getParent();
            }
            if (iter == root) {
               while (iter.getRightChild() != null) {
                  iter = iter.getRightChild();
               }
               last = iter;
            }
            else if (isLeftChild(iter) == 2) {
               iter = iter.getParent().getLeftChild();
               while (iter.getRightChild() != null) {
                  iter = iter.getRightChild();
               }
               last = iter;
            }
            n = percolateDown(root);
         }
         size--;
         return del;
      }
   }
   
   /* Returns the datum of the min element.
    * null if empty pq
    */
   public String minElement() {
      if (isEmpty())
         return null;
      else
         return root.getDatum();
   }
   
   /* Returns the key of the min elemetn
    * -1 if empty pq
    */
   public int minKey() {
      if (isEmpty())
         return -1;
      else
         return root.getKey();
   }
   
   /* Returns the String to be printed into the output file.
    */
   public String print() {
      String print = "";
      if (root != null)
         print = preOrderPrint(root);
      return print;
   }
   
   /* Checks whether the key already exists in the pq
    */
   public boolean okToInsert(int key) {
      boolean ok = true;
      if (root != null) {
         ok = preOrderSearch(root, key);
      }
      return ok;
   }
   
   // helping methods
   
   /* Goes through the pq and checks if the key already exists in the pq.
    * for okToInsert()
    */
   private boolean preOrderSearch(HeapNode n, int key) {
      boolean ok = true;
      if (n.getKey() == key) {
         ok = false;
      }
      else {
         if (n.getLeftChild() != null)
            ok = preOrderSearch(n.getLeftChild(), key);
         if (n.getRightChild() != null)
            ok = preOrderSearch(n.getRightChild(), key);
      }
      return ok;
   }
   
   /* Creates String to be printed to the output file.
    * Only keys are printed, one key per line, in pre-order.
    * Amount of indents tell the level of the key.
    */
   private String preOrderPrint(HeapNode n) {
      String print = "";
      String indent = "     ";
      String newLine = System.getProperty("line.separator");
      if (n != root)
         print = print + newLine;
      for (int i = level(n); i > 0; i--) {
         print = print + indent;
      }
      print = print + n.getKey();
      if (n.getLeftChild() != null)
         print = print + preOrderPrint(n.getLeftChild());
      if (n.getRightChild() != null)
         print = print + preOrderPrint(n.getRightChild());
      return print;
   }
   
   /* Checks whether the node is root (0), left child (1) or rigth child (2).
    * 
    * @param the node to be investigated
    * @return number telling the position
    */
   private int isLeftChild(HeapNode n) {
      if (n == root)
         return 0;
      else if (n == n.getParent().getLeftChild())
         return 1;
      else
         return 2;
   }
   
   /* Swaps keys and datums of two nodes
    *
    * @param the two nodes to be swapped
    */
   private void swap(HeapNode n, HeapNode m) {
      String tmp1 = n.getDatum();
      int tmp2 = n.getKey();
      n.setDatum(m.getDatum());
      n.setKey(m.getKey());
      m.setDatum(tmp1);
      m.setKey(tmp2);
   }
   
   /* Percolates the node up until its parent's key is smaller.
    *
    * @param the node to be percolated
    * @return the position where the node ends up.
    */
   private HeapNode percolateUp(HeapNode n) {
      HeapNode m = n.getParent();
      while (m != null && n.getKey() < m.getKey()) {
         swap(n,m);
         n = n.getParent();
         m = m.getParent();
      }
      return n;
   }
   
   /* Percolates the node down until both of its children's key are bigger.
    *
    * @param the node to be percolated
    * @return the position where the node ends up.
    */
   private HeapNode percolateDown(HeapNode n) { 
      while ((n.getLeftChild() != null && n.getKey() > n.getLeftChild().getKey()) ||
              (n.getRightChild() != null && n.getKey() > n.getRightChild().getKey())) {
         if (n.getRightChild() == null) {
            swap(n,n.getLeftChild());
            n = n.getLeftChild();
         }
         else if (n.getRightChild().getKey() > n.getLeftChild().getKey()) {
            swap(n,n.getLeftChild());
            n = n.getLeftChild();
         }
         else {
            swap(n,n.getRightChild());
            n = n.getRightChild();
         }
      }
      return n;
   }
   
   /* Tells the level of the node. Root is 0, root's children are 1 and so on.
    *
    * @param the node to be investigated
    * @return the level of the node
    */
   private int level(HeapNode n) {
      int l = 0;
      while (n.getParent() != null) {
         n = n.getParent();
         l++;
      }
      return l;
   }
 
}