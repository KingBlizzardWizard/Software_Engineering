public class main {
    public static void main(String[] args) {
    System.out.println("Hello world");


    Knight knight1 = new Knight (6,true);
    knight1.calculateMovements();
    knight1.printValidMoves();
        board cb = new board(); 
        // System.out.println(cb);
        cb.boardSet();
        cb.boardPrint();
    }
}
