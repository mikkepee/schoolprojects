/** Main class for Tira2016 Priorityqueue program.
 *
 * @author Mikael Paajanen
 * paajanen.mikael.a@student.uta.fi
 */
public class Tira2016 {

   /** Main operation which read the names of input and output files
    * and calls for the UI class to execute command from the input file.
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      if (args.length > 0) {
         String input = args[0];
         String output = args[1];
         UI program = new UI();
         program.setOutputfileName(output);
         program.readInput(input);
      }
   }
   
}
