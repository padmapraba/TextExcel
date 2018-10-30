// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18
import java.io.FileNotFoundException;
import java.util.Scanner;

// Update this file with your own code.

public class TextExcel {

   public static void main(String[] args) {
       Grid sheet = new Spreadsheet(); // Create new spreadsheet
       System.out.println(sheet.getGridText()); // Prints new spreadsheet
       Scanner console = new Scanner(System.in);
       System.out.print("Provide process command: ");
       String input =  console.nextLine();
       
       
       while(!input.equalsIgnoreCase("quit")){ // Command loop
         String output = sheet.processCommand(input);
         System.out.print(output + "\nProvide process command: ");
         input = console.nextLine();
       }
       
   }
}
