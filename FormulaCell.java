// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18
import java.util.*;
public class FormulaCell extends RealCell{
   private Spreadsheet sheet;
 
   public FormulaCell(String input,Spreadsheet sheet){
      super(input);
      this.sheet = sheet;
   }
 
   public double getDoubleValue(){        
      String[] values = fullCellText().split(" ");
      if(values[1].equalsIgnoreCase("avg") || values[1].equalsIgnoreCase("sum")){
         return getSumOrAvg(values);
      }
      else{
         return getSolution(values);  
      } 
 
   }
 
   public double getSumOrAvg(String[] value){ // ( Sum a1-d3 )
      String solve = value[1]; // sum or avg
      String ref1 = value[2].substring(0, value[2].indexOf("-"));
      String ref2 = value[2].substring(value[2].indexOf("-") + 1);
      double output = 0.0;
 
      SpreadsheetLocation ref1Loc = new SpreadsheetLocation(ref1);
      SpreadsheetLocation ref2Loc = new SpreadsheetLocation(ref2);
 
      int count = 0; // counts the number of cells for the given range (used to help calculate average)
      for(int row = ref1Loc.getRow(); row <= ref2Loc.getRow(); row++){
         for(int col = ref1Loc.getCol(); col <= ref2Loc.getCol(); col++){
            SpreadsheetLocation loc = new SpreadsheetLocation(row, col);
            if (sheet.getCell(loc) instanceof RealCell){
               RealCell cell = (RealCell)sheet.getCell(loc);
               output += cell.getDoubleValue();
            }
            count++;
         }
      }
      if(solve.equalsIgnoreCase("avg")){
         return output /= count;
      }
      else{
         return output;
      }
   }
 
   public double getSolution(String[] values){
      double firstOperand = getOperand(values[1]);
      for(int i= 3; i < values.length - 1; i += 2){
         String operator = values[i-1];
         double operand = getOperand(values[i]);
 
         if (operator.equals("+")){
            firstOperand += operand;
         }
         else if (operator.equals("-")){
            firstOperand -= operand;
         }
         else if(operator.equals("*")){
            firstOperand *= operand;
         }
         else if (operator.equals("/")){
            firstOperand /= operand;
         }
 
      }
      return firstOperand;
 
   }
 
   public double getOperand(String input){
      if(Character.isLetter(input.charAt(0))){
         SpreadsheetLocation loc = new SpreadsheetLocation(input);
         RealCell cell = (RealCell)sheet.getCell(loc);
         return cell.getDoubleValue();
      }
      else{
         return Double.parseDouble(input);
      }
   }
}
 
 