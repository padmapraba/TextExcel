// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18
public abstract class RealCell implements Cell, Comparable<Cell>{
   private String input;
 
   public RealCell(String input){
      this.input = input;   
   }
 
   public String abbreviatedCellText(){
      String value = "" + getDoubleValue();
      return Spreadsheet.padTrunc(value);
   }
 
   public String fullCellText(){
      return input.toUpperCase();
   }
 
   public abstract double getDoubleValue();
   
   public int compareTo(Cell other){
      if(other instanceof RealCell){
         RealCell rc = (RealCell) other;
         if (this.getDoubleValue() < rc.getDoubleValue()){
            return -1;
         }
         if (this.getDoubleValue() > rc.getDoubleValue()){
            return 1;
         }
      }
      return 0;
      
   }
 
 
 
 
 
 
 
}