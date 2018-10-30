// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18
public class TextCell implements Cell, Comparable<Cell>{
   private String input; // user input
   
   public TextCell(String input){ 
      this.input = input;
   }
      
   public String abbreviatedCellText(){
      String str = input.substring(1, input.length() -1);
      return Spreadsheet.padTrunc(str);
   }
      
   public String fullCellText(){
      String str = input.substring(1, input.length() -1);
      return "\""+str+"\"";
   }
   
   public int compareTo(Cell other){
      if(other instanceof TextCell){
         TextCell tc = (TextCell) other;
         return this.fullCellText().compareTo(other.fullCellText());
      }
      return -1;
   }
}
   

