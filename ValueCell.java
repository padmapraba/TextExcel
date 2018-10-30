// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18
public class ValueCell extends RealCell{
   private String input;
 
   public ValueCell(String input){
      super(input);
      this.input = input;
   }
 
   public double getDoubleValue(){
      return Double.parseDouble(input);
   }
 
   public String fullCellText(){
      return "" + Double.parseDouble(input);
 
   }
}