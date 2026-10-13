public class board {
    String[][] chessBoard;

    public board(){
        this.chessBoard = new String[8][8];
    }

    public void boardSet(){
        for (int i = 0; i < 8; i++) {
            for (int j =0; j < 8; j++) {
                chessBoard[i][j]=Integer.toString(i) + "," + Integer.toString(j);
            }
        }
                // chessBoard[0][0]="#";
    }
    
    public void boardPrint(){
        for (int i = 0; i < 8; i++) {
            for (int j =0; j < 8; j++) {
                System.out.print(chessBoard[i][j] +"\n");
            }
        }
    }
}
