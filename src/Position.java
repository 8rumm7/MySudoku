public class Position {
   public int row;
   public int col;
    Position (int row,int col){
        this.row =row;
        this.col =col;
    }

    public static Position newPosition(int row, int col) {
        return new Position(row,col);
    }
}
