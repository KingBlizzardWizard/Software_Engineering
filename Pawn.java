// class Pawn extends Piece {



   
//     int movementOffset = 1; // pawn needs to be able to move twice on its first turn. After its first turn
//     // we will change this to 0 so the calculateMovements for loop only runs one time


// public Pawn (int boardPos, boolean playerWhite){
//                 isWhite = playerWhite;
//                 pos = boardPos;
//                 validMoves[pos] = 0; // current position should not be an eligible move
//                 pieceValue=1;
//             }


//             public void calculateMovements(){
//                 // needs specific moves for being white and black
//                 if (isWhite == false){ // case if piece is black
//                     try{
//                         for(int i = 1; i <= movementOffset+1;i++){
//                             validMoves[pos + (8*i)] = 1;
//                         }
//                     }
//                     catch(IndexOutOfBoundsException e){
//                         System.out.println("Pawn tried to move out of bounds");
//                     }
//                 } // end of white case
//                 if (isWhite == true){ // case if piece is white
//                     try{
//                         for(int i = 1; i <= movementOffset+1;i++){
//                             validMoves[pos+ ((8*i)*-1)] = 1;
//                         }
//                     }
//                     catch(IndexOutOfBoundsException e){
//                         System.out.println("Pawn tried to move out of bounds");
//                     }
//             }
    
//     }
//     public void updatePosition(int newpos){
//         for(int i=0;i<64;i++)
//             validMoves[i]=0;
//         pos = newpos;
//         calculateMovements();
//     }

//     public int getPosition(){
//         return pos;
//     }
//     // return where piece can currently move
//     public int[] getValidMoves(){
//         return validMoves;
//     }
//     // return if pice is white 
//     public boolean checkIfWhite(){
//         return isWhite;
//     }
//     // returns the value of the piece
//     public int getPieceValue(){
//         return pieceValue;
//     }

//     public void printValidMoves(){
//         for(int i = 1 ; i < 65; i++){
//             System.out.print(validMoves[i-1] + " ");
//             if(i%8==0)
//                 System.out.println();
//         }
//     }
// }