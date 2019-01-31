import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;

/** UI class for Tira2016 Priorityqueue program
 *  which reads commands and executes them by calling operations
 *  from the Priorityqueue class.
 *
 * @author Mikael Paajanen
 * paajanen.mikael.a@student.uta.fi
 */
public class UI {
   
   // attributes
   
   // Error notifications
   private final String ERR = "Virheellinen syöte.";
   private final String EMPTY = "Jono on tyhjä.";
   // flag variable showing the program is still running
   private boolean programOn;
   private PriorityQueue pq;
   private String outputfileName;
   
   // accessories
   public String getOutputfileName() {
      return outputfileName;
   }
   
   public void setOutputfileName(String opfn) {
      outputfileName = opfn;
   }
   
   /* Operation which creates the priorityqueue and reads the commands
    * and sends them to "execute" operation
    *
    * @param the name of the input file as String
    * throws exception if the file can't be found.
    */
   public void readInput(String input) {
      try {
         pq = new PriorityQueue();
         programOn = true;
         String line;
         BufferedReader br = new BufferedReader(new FileReader(input));
         // read the comments one by one and send them onwards
         while (br.ready() && programOn) {
            line = br.readLine();
            String[] values = line.split(", ");
            programOn = execute(values);
         }
      }
      catch (IOException e) {
         System.out.println("Tiedostoa ei löytynyt.");
      }
   }
   
   /* Operations to execute the commands. Calls operations from Priorityqueue
    * class to do so. Handles empty queue if needed.
    *
    * @param String type table which includes all parts of the command
    * @return boolean value whether the program should still continue
    */
   private boolean execute(String[] a) {
      boolean carryOn = true;
      // The line to be writen in the output file.
      String outputLine = "";
      if (a.length == 1) {
         // size
         if (a[0].equals("s")) {
            int size = pq.size();
            if (size == 0)
               outputLine = EMPTY;
            else
               outputLine = "Keon koko on " + size + ".";
         }
         // removing the min element
         else if (a[0].equals("r")) {
            int minKey = pq.minKey();
            String minDatum = pq.removeMinElement();
            if (minKey == -1)
               outputLine = EMPTY;
            else
               outputLine = "(" + minKey + "," + minDatum + ") poistettu.";
         }
         // returning the min key
         else if (a[0].equals("m")) {
            int minKey = pq.minKey();
            String minDatum = pq.minElement();
            if (minKey == -1) {
               outputLine = EMPTY;
            }
            else {
               outputLine = "Pienin alkio on (" + minKey + "," + minDatum + ").";
            }
         }
         // printing
         else if (a[0].equals("p")) {
            if (pq.minKey() == -1) {
               outputLine = EMPTY;
            }
            else {
               outputLine = pq.print();
            }
         }
         // ending  
         else if (a[0].equals("q")) {
            carryOn = false;
            programOn = false;
            outputLine = "Ohjelma lopetettu.";
         }
         else {
            outputLine = ERR;
         }
      }
      else if (a.length == 3) {
         // adding an element
         if (a[0].equals("i")) {
            int key;
            String datum;
            try {
               key = Integer.parseInt(a[1]);
               datum = a[2];
               if (key < 1)
                  outputLine = ERR;
               // Checks if the key already is in the queue
               else if (!pq.okToInsert(key))
                  outputLine = "Avain " + key + " on jo jonossa.";
               else {
                  pq.insertElement(key, datum);
                  outputLine = "(" + key + "," + datum + ") lis.";
               }
            }
            catch (Exception e) {
               outputLine = ERR;
            }
         }
      }
      else 
         outputLine = ERR;
      writeOutput(outputLine);
      return carryOn;
   }
   
   /* Operation which writes the output file.
    *
    * @param the line to be writen into the output file
    */
   private void writeOutput(String line) {
      try {
         BufferedWriter bw = new BufferedWriter(new FileWriter(getOutputfileName(), true));
         bw.write(line);
         if (programOn) {
            bw.newLine();
            bw.close();
         }
         else
            bw.close();
      }
      catch (Exception e) {
         System.out.println("Tapahtui virhe.");
      }
   }
}