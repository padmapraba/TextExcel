// Checkpoint C1 of TextExel Lab
// Author: Padma Prabagaran
// Date: 4/23/18

public class SpreadsheetLocation implements Location {
   private int row;
   private int col;
   
   @Override
   public int getRow() {
      return this.row;
   }

   @Override
   public int getCol() {
      return this.col;
   }
   
   public SpreadsheetLocation(String cellName) {
     this.row = Integer.parseInt(cellName.substring(1))-1;
     this.col = cellName.toUpperCase().charAt(0) - 65; // 65 is the ascii value for A
   }
   
   public SpreadsheetLocation(int row, int col){
      this.row = row;
      this.col = col;
   }
}
