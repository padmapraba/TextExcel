// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18
import java.util.*;
public class Spreadsheet implements Grid {
   private Cell[][] table;
 
   public Spreadsheet(){ // Creates a spreadsheet of empty cells
      this.table = new Cell[getRows()][getCols()];
      for(int row = 0; row < getRows(); row++){
         for(int col = 0; col < getCols(); col++){
            table[row][col] = new EmptyCell();
         }
      }
   }
 
   @Override
   public String processCommand(String command) {
      if(command.equals(" ") || command.equals("")){
         return "";
      }
      else if(command.equalsIgnoreCase("clear")){ // clear
         clearSpreadsheet();
         return getGridText();
      }
      else if(!command.contains(" ")){ // A1
         String assignment = command;
         SpreadsheetLocation loc = new SpreadsheetLocation(assignment);
         return table[loc.getRow()][loc.getCol()].fullCellText();
      }
      else if(command.length() > 5 && command.substring(0, 5).equalsIgnoreCase("clear")){ //clear A1
         String assignment = command.substring(6);
         SpreadsheetLocation loc = new SpreadsheetLocation(assignment);
         table[loc.getRow()][loc.getCol()] = new EmptyCell();
         return getGridText();
      }
      else if(command.length() > 5 && command.substring(0, 5).equalsIgnoreCase("sorta") || command.length() > 5 && command.substring(0, 5).equalsIgnoreCase("sortd") ){ // sorta/sortd
         String[] values = command.split(" ");//command.toLowerCase().split(" ");
         getCellLoc(command, values);
         return getGridText();
      }
      else { 
         String assignment = command.substring(0,command.indexOf(" "));
         SpreadsheetLocation loc = new SpreadsheetLocation(assignment);
         String input = command.substring((command.indexOf("="))+2);
      
         if (command.contains("\"")){ // A1 = "hello"
            table[loc.getRow()][loc.getCol()] = new TextCell(input);
         }
         else if(command.contains("%")){ // A1 = 7.8%
            table[loc.getRow()][loc.getCol()] = new PercentCell(input);
         }
         else if(command.contains("(")){ // A1 = (5 + 8 + 1 * 2)
            table[loc.getRow()][loc.getCol()] = new FormulaCell(input,this);
         }
         else{ // A1 = 5.6
            table[loc.getRow()][loc.getCol()] = new ValueCell(input);
         }
      }
      return getGridText();
   }
 
   @Override
   public int getRows() {
   	// 1 - 20
      return 20;
   }
 
   @Override
   public int getCols() {
   	// A - L
      return 12;
   }
 
   @Override
   public Cell getCell(Location loc) {
      return table[loc.getRow()][loc.getCol()];
   }
 
   @Override 
   public String getGridText() { // Builds new spreadsheet
      String grid = "";
      // A-L line
      grid += "   |";
      for(int col = 0; col < getCols(); col++){
         grid += ((char) (65 + col));
         grid += "         |";
      }
      grid += "\n";
      // rest of the sheet
      for(int row = 0; row < getRows(); row++){
         if(row < 9){
            grid += (row + 1) + "  |";
         } 
         else {
            grid += (row + 1) + " |";
         }
      
         for (int col = 0; col < getCols(); col++){
            grid += table[row][col].abbreviatedCellText();  
            grid += "|";
         
         }
         grid += "\n";
      
      }
   
      return grid;
   }
 
   public void clearSpreadsheet(){ // clear sheet
      for (int row = 0; row < getRows(); row++){
         for (int col = 0; col < getCols(); col++){
            table[row][col] = new EmptyCell();
         }
      }
   }
   public static String padTrunc(String input){ // padding and truncating
      if (input.length() < 10){
         String pad = input;
         int SpacesRequired = 10 - input.length();
         for (int i = 0; i < SpacesRequired; i++){
            pad += " ";
         }
         return pad;
      }
      else if (input.length() > 10){
         return input.substring(0, 10);
      }
      else{
         return input;
      }
   }
   
   public void getCellLoc(String command, String[] values){
      ArrayList<String> list = new ArrayList<String>();
      ArrayList<Double> list2 = new ArrayList<Double>();
   
      String ref1 = values[1].substring(0, values[1].indexOf("-"));
      String ref2 = values[1].substring(values[1].indexOf("-") + 1);
   
      SpreadsheetLocation ref1Loc = new SpreadsheetLocation(ref1);
      SpreadsheetLocation ref2Loc = new SpreadsheetLocation(ref2);
      
      for(int row = ref1Loc.getRow(); row <= ref2Loc.getRow(); row++){
         for(int col = ref1Loc.getCol(); col <= ref2Loc.getCol(); col++){
            SpreadsheetLocation loc = new SpreadsheetLocation(row, col);
            if(table[loc.getRow()][loc.getCol()] instanceof TextCell){
               list.add(table[loc.getRow()][loc.getCol()].fullCellText()); 
            }
            else if (table[loc.getRow()][loc.getCol()] instanceof RealCell){
               //list2.add(Double.parseDouble(table[loc.getRow()][loc.getCol()].fullCellText()));   
               if(table[loc.getRow()][loc.getCol()] instanceof PercentCell){
                  list2.add(Double.parseDouble(table[loc.getRow()][loc.getCol()].fullCellText()));
               }
               else if(table[loc.getRow()][loc.getCol()] instanceof ValueCell){
                  list2.add(Double.parseDouble(table[loc.getRow()][loc.getCol()].fullCellText()));
               }
            
            }
            
         }
      }
   
      sortString(list);
      sortDouble(list2);
        
      // puts values back into the spreadsheet ascending
      if(command.substring(0,5).equalsIgnoreCase("sorta")){
         int index = 0;
         for(int row = ref1Loc.getRow(); row <= ref2Loc.getRow(); row++){
            for(int col = ref1Loc.getCol(); col <= ref2Loc.getCol(); col++){
               SpreadsheetLocation loc = new SpreadsheetLocation(row, col);
               if(table[loc.getRow()][loc.getCol()] instanceof TextCell){
                  table[loc.getRow()][loc.getCol()] = new TextCell(list.get(index));
               }
               else if (table[loc.getRow()][loc.getCol()] instanceof RealCell){
                  if(table[loc.getRow()][loc.getCol()] instanceof PercentCell){
                     table[loc.getRow()][loc.getCol()] = new PercentCell(""+list2.get(index));
                  }
                  else if(table[loc.getRow()][loc.getCol()] instanceof ValueCell){
                     table[loc.getRow()][loc.getCol()] = new ValueCell(""+ list2.get(index));
                  }
               }
               index++;
            }
         }
      }
      else if(command.substring(0,5).equalsIgnoreCase("sortd")) { //descending
         int index = list.size()-1;
         int index2 = list2.size()-1;
         for(int row = ref1Loc.getRow(); row <= ref2Loc.getRow(); row++){
            for(int col = ref1Loc.getCol(); col <= ref2Loc.getCol(); col++){
               SpreadsheetLocation loc = new SpreadsheetLocation(row, col);
               if(table[loc.getRow()][loc.getCol()] instanceof TextCell){
                  table[loc.getRow()][loc.getCol()] = new TextCell(list.get(index));
                  index--;
               }
               else if (table[loc.getRow()][loc.getCol()] instanceof RealCell){
                  if(table[loc.getRow()][loc.getCol()] instanceof PercentCell){
                     table[loc.getRow()][loc.getCol()] = new PercentCell(""+list2.get(index2)+"%");
                     index2--;
                  }
                  else if(table[loc.getRow()][loc.getCol()] instanceof ValueCell){
                     table[loc.getRow()][loc.getCol()] = new ValueCell(""+ list2.get(index2));
                     index2--;
                  }
                  
               }
               
            }
         }
      }
   }
   
   // Sort method   
   public void sortString(ArrayList<String> list){
      for (int j = 0; j < list.size()-1; j++) {
         for (int k = j + 1; k < list.size(); k++) {
            if (list.get(j).compareTo(list.get(k)) > 0) { // swap if difference is positive
               String temp = list.get(j);
               list.set(j,list.get(k));
               list.set(k,temp);
            
            }
         }
      }
   }
   
   public void sortDouble(ArrayList<Double> list){
      for(int j = 0; j < list.size(); j++){
         for (int k = j + 1; k < list.size(); k++) {
            if (list.get(j).compareTo(list.get(k)) > 0) { // swap if difference is positive
               double temp = list.get(j);
               list.set(j,list.get(k));
               list.set(k,temp);
            }
         }
      }
   }   
}
  
 
