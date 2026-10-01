public class Pieces {

    public class Knight{

        int xpos;
        int ypos;
        int boardSize = 8 ;
        boolean isWhite;
        // constructor that saves its current position in the board
            public Knight (int xboard, int yboard, boolean playerWhite){
                xpos = xboard;
                ypos = yboard;
                isWhite = playerWhite;
                
            }

            // initialize an array that saves its current position inside
            int[][] currentpos = new int[boardSize][boardSize];

            // calculate all possible positions that it can move to
            public void calculateMovements(){
                for(int i = 0; i < 8 ;i++){






            }
        }


            // prevent moving if its not the user's turn
            // prevent from moving to a tile occupied by a friendly
            // if moving to a tile occupied by the opponent, capture
            // if moving would put self in check, prevent movement
        }
    
    public class Pawn{
        // find current position
        // 




    }




    }

