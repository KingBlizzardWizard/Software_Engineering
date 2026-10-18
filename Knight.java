// import java.util.Arrays;

// class Knight extends Piece{

//     int pieceValue = 2;


//     // contructor to save its current position in the board
//    public Knight (int boardPos, boolean playerWhite){
//                 isWhite = playerWhite;
//                 pos = boardPos;
//                 validMoves[pos] = 0; // current position should not be an eligible move

//             }

// // graphic of int to board position
// // 0  1  2  3  4  5  6  7
// // 8  9 10 11 12 13 14 15
// //16 17 18 19 20 21 22 23
// //24 25 26 27 28 29 30 31
// //32 33 34 35 36 37 38 39
// //40 41 42 43 44 45 46 47
// //48 49 50 51 52 53 54 55
// //56 57 58 59 60 61 62 63


//                 // main method that finds every moveable position given the current pos value 
//                 // this should only be run on initialization
//                 // if moving the piece, use updatePos() to change its position, wiping its old movements
    
//     public void calculateMovements(){
//                     // loop through for each point on board, if i = 6, 10, 15, or 17, check if the movement is on the board or not
//                     for(int i = 0; i<64; i++){ // loop
//                         try{
//                             if(i == 6 || i == 10 || i == 15 || i == 17) // movements to the "left" ( left and up)
//                                 validMoves[pos - i] = 1;               
//                         }
//                         catch (IndexOutOfBoundsException e){
//                             System.out.println("movement to left out of bounds");
//                     }

//                         try{
//                             if(i == 6 || i == 10 || i == 15 || i == 17) // movements "right" (right and down)
//                                 validMoves[pos + i] = 1;
//                         }
//                         catch(IndexOutOfBoundsException e){
//                             System.out.println("movement to right out of bounds");
//                         }

  
//                 } // end of for loop

//                 // fix errors that occur when knight is on a side edge
//                 try{
//                     if(pos % 8 == 1){ // on 2nd from left edge

//                     validMoves[pos-10] = 0;
//                     validMoves[pos+6] = 0;

//                     }
//                     if(pos%8 ==0){ // left edge
//                     validMoves[pos+6] = 0;
//                     validMoves[pos-17] = 0;
//                     validMoves[pos-10] = 0;
//                     validMoves[pos+15] = 0;
//                     }
//                     if(pos%8 == 6){ // 2 from right edge
//                     validMoves[pos-6] = 0;
//                     validMoves[pos+10] = 0;
//                     }
//                     if(pos%8 == 7){ // right edge
//                     validMoves[pos+17] = 0;
//                     validMoves[pos+10] = 0;
//                     validMoves[pos-15] = 0;
//                     validMoves[pos-6] = 0;
//                     }

//                 }
//                 catch(IndexOutOfBoundsException e){}
//             } 

//     //end of calculateMovements


//     // update the position of the knight to a new given position and recalculate movements
//     public void updatePosition(int newpos){
//         for(int i=0;i<64;i++)
//             validMoves[i]=0;
//         pos = newpos;
//         calculateMovements();
//     }

//     public int getPosition(){
//         return pos;
//     }


//     // return where the knight can move currently
//     public int[] getValidMoves(){
//         return validMoves;
//     }
//     // return if the knight is the white player or not
//     public boolean checkIfWhite(){
//         return isWhite;
//     }
//     public int getPieceValue(){
//         return pieceValue;
//     }
            
//     }

