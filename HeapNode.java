/** Node Class for the priorityqueue of the Tira2016 Priorityqueue program
 *
 * @author Mikael Paajanen
 * paajanen.mikael.a@student.uta.fi
 */
public class HeapNode {
   
   // attributes
   private int key;
   private String datum;
   private HeapNode parent;
   private HeapNode leftChild;
   private HeapNode rightChild;
   
   // constructor
   public HeapNode(int k, String d, HeapNode p, HeapNode lc, HeapNode rc) {
      key = k;
      datum = d;
      parent = p;
      leftChild = lc;
      rightChild = rc;
   }
   
   // Get and set methods for the attributes.
   
   public int getKey() {
      return key;
   }
   
   public void setKey(int k) {
      key = k;
   }
   
   public String getDatum() {
      return datum;
   }
   
   public void setDatum(String d) {
      datum = d;
   }
   
   public HeapNode getLeftChild() {
      return leftChild;
   }
   
   public void setLeftChild(HeapNode lc) {
      leftChild = lc;
   }
   
   public HeapNode getRightChild() {
      return rightChild;
   }
   
   public void setRightChild(HeapNode rc) {
      rightChild = rc;
   }
   
   public HeapNode getParent() {
      return parent;
   }
   
   public void setParent(HeapNode p) {
      parent = p;
   }
   
}
