import java.util.Arrays;

class Knight extends Piece{



    // contructor to save its current position in the board
   public Knight (int xboard, boolean playerWhite){
                pos = xboard;
                isWhite = playerWhite;
                currentpos[pos] = 2; // current position on the board
                validMoves[pos] = 2; // current position should not be an eligible move
                // validMoves = 2 for testing purposes, see where the piece currently is, will normally be 0
            }


// 0  1  2  3  4  5  6  7
// 8  9 10 11 12 13 14 15
//16 17 18 19 20 21 22 23
//24 25 26 27 28 29 30 31
//32 33 34 35 36 37 38 39
//40 41 42 43 44 45 46 47
//48 49 50 51 52 53 54 55
//56 57 58 59 60 61 62 63

    public void calculateMovements(){ // try to find every point that the piece could possibly reach


                    // loop through for each point on board, if i = 6, 10, 15, or 17, check if the movement is on the board or not
                    for(int i = 0; i<64; i++){ // loop
                        try{
                            if(i == 6 || i == 10 || i == 15 || i == 17) // movements to the "left" ( left and up)

                                validMoves[pos - i] = 1;
                                    
                        }
                        catch (IndexOutOfBoundsException e){
                            System.out.println("movement to left out of bounds");
                    }

                        try{
                            if(i == 6 || i == 10 || i == 15 || i == 17) // movements "right" (right and down)
                                validMoves[pos + i] = 1;
                        }
                        catch(IndexOutOfBoundsException e){
                            System.out.println("movement to right out of bounds");
                        }

  
                } // end of for loop

                // fix errors that occur when knight is on a side edge
                try{
                    if(pos % 8 == 1){ // on 2nd from left edge

                    validMoves[pos-10] = 0;
                    validMoves[pos+6] = 0;

                    }
                    if(pos%8 ==0){ // left edge
                    validMoves[pos+6] = 0;
                    validMoves[pos-17] = 0;
                    validMoves[pos-10] = 0;
                    validMoves[pos+15] = 0;
                    }
                    if(pos%8 == 6){ // 2 from right edge
                    validMoves[pos-6] = 0;
                    validMoves[pos+10] = 0;
                    }
                    if(pos%8 == 7){ // right edge
                    validMoves[pos+17] = 0;
                    validMoves[pos+10] = 0;
                    validMoves[pos-15] = 0;
                    validMoves[pos-6] = 0;
                    }

                }

                catch(IndexOutOfBoundsException e){}


            } 



    public void printValidMoves(){
        for(int i = 1 ; i < 65; i++){
            System.out.print(validMoves[i-1] + " ");
            if(i%8==0)
                System.out.println();



        }


    }
    

            
    }





            // prevent moving if its not the user's turn
            // prevent from moving to a tile occupied by a friendly
            // if moving to a tile occupied by the opponent, capture
            // if moving would put self in check, prevent movement