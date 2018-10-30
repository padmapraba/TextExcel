// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18
public class PercentCell extends RealCell{
 
   public PercentCell(String input){
      super(input);
   }
 
   public String abbreviatedCellText(){
      String value = "" + ((int) (getDoubleValue() * 100)) + "%";
      return Spreadsheet.padTrunc(value); 
   }
 
   public double getDoubleValue(){
      String input = fullCellText().substring(0,fullCellText().indexOf("%"));
      double input1 = Double.parseDouble(input);
      return input1 / 100;
   }
 
 
 
 
}