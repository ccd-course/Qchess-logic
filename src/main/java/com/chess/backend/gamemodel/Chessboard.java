/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package com.chess.backend.gamemodel;

import lombok.Data;

/**
 * Class to represent chessboard.
 * <p>
 * Chessboard is made from squares. It also contains a move history as well as the number of players.
 */
@Data
public class Chessboard {
    private int numberOfPlayers;
    private Square[][] squares;//squares of chessboard
    //    ----------------------------
    //    For En passant:
    //    |-> Pawn whose in last turn moved two square
    private Piece twoSquareMovedPawn = null;
    private Piece twoSquareMovedPawn2 = null;
    private boolean breakCastling = false; //if last move break castling
    private Moves moves_history;

//    public void move(Square begin, Square end) {
//        move(begin, end, true);
//    }

//    /**
//     * Method to move piece over chessboard
//     *
//     * @param xFrom from which x move piece
//     * @param yFrom from which y move piece
//     * @param xTo   to which x move piece
//     * @param yTo   to which y move piece
//     */
//    public void move(int xFrom, int yFrom, int xTo, int yTo) {
//        Square fromSQ = null;
//        Square toSQ = null;
//        try {
//            fromSQ = this.squares[xFrom][yFrom];
//            toSQ = this.squares[xTo][yTo];
//        } catch (IndexOutOfBoundsException exc) {
//            System.out.println("error moving piece: " + exc);
//            return;
//        }
//        this.move(this.squares[xFrom][yFrom], this.squares[xTo][yTo], true);
//    }
//
//    /**
//     * Method move piece from square to square
//     *
//     * @param begin   square from which move piece
//     * @param end     square where we want to move piece         *
//     */
//    public void move(Square begin, Square end, boolean clearForwardHistory) {
//
//        castling wasCastling = castling.none;
//        Piece promotedPiece = null;
//        boolean wasEnPassant = false;
//        if (end.piece != null) {
//            end.piece.square = null;
//        }
//
//        Square tempBegin = new Square(begin);//4 moves history
//        Square tempEnd = new Square(end);  //4 moves history
//        //for undo
//        breakCastling = false;
//        // ---
//
//        twoSquareMovedPawn2 = twoSquareMovedPawn;
//
//        begin.piece.square = end;//set square of piece to ending
//        end.piece = begin.piece;//for ending square set piece from beginin square
//        begin.piece = null;//make null piece for begining square
//
//        if (end.piece.getType().equals(PieceType.KING)) {
//            if (!end.piece.isMotioned()) {
//                breakCastling = true;
//                end.piece.setMotioned(true);
//            }
//
//            //Castling
//            if (begin.pozX + 2 == end.pozX) {
//                move(squares[7][begin.pozY], squares[end.pozX - 1][begin.pozY], false);
//                // ifWasCastling = end.piece;  //for undo
//                wasCastling = castling.shortCastling;
//                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
//                //return;
//            } else if (begin.pozX - 2 == end.pozX) {
//                move(squares[0][begin.pozY], squares[end.pozX + 1][begin.pozY], false);
//                // ifWasCastling = end.piece;  // for undo
//                wasCastling = castling.longCastling;
//                //this.moves_history.addMove(tempBegin, tempEnd, clearForwardHistory, wasCastling, wasEnPassant);
//                //return;
//            }
//            //endOf Castling
//        } else if (end.piece.getType().equals(PieceType.ROOK)) {
//            if (!end.piece.isMotioned()) {
//                breakCastling = true;
//                end.piece.setMotioned(true);
//            }
//        } else if (end.piece.getType().equals(PieceType.PAWN)) {
//            if (twoSquareMovedPawn != null && squares[end.pozX][begin.pozY] == twoSquareMovedPawn.square) //en passant
//            {
//                // ifWasEnPassant = squares[end.pozX][begin.pozY].piece; //for undo
//
//                tempEnd.piece = squares[end.pozX][begin.pozY].piece; //ugly hack - put taken pawn in en passant plasty do end square
//
//                squares[end.pozX][begin.pozY].piece = null;
//                wasEnPassant = true;
//            }
//
//            if (begin.pozY - end.pozY == 2 || end.pozY - begin.pozY == 2) //moved two square
//            {
//                breakCastling = true;
//                twoSquareMovedPawn = end.piece;
//            } else {
//                twoSquareMovedPawn = null; //erase last saved move (for En passant)
//            }
//
//            if (end.piece.square.pozY == 0 || end.piece.square.pozY == 7) //promote Pawn
//            {
//                if (clearForwardHistory) {
//                    String color;
//                    if (end.piece.player.color == Color.WHITE) {
//                        color = "W"; // promotionWindow was show with pieces in this color
//                    } else {
//                        color = "B";
//                    }
//
//                    // TODO: Get new piece type from frontend
//                    String newPiece = "Queen"; // JChessApp.jcv.showPawnPromotionBox(color); //return name of new piece
//
//                    if (newPiece.equals("Queen")) // transform pawn to queen
//                    {
//                        Piece queen = new Piece(PieceType.QUEEN, end.piece.player, true);
//                        queen.chessboard = end.piece.chessboard;
//                        queen.player = end.piece.player;
//                        queen.square = end.piece.square;
//                        end.piece = queen;
//                    } else if (newPiece.equals("Rook")) // transform pawn to rook
//                    {
//                        Piece rook = new Piece(PieceType.ROOK, end.piece.player, true);
//                        rook.chessboard = end.piece.chessboard;
//                        rook.player = end.piece.player;
//                        rook.square = end.piece.square;
//                        end.piece = rook;
//                    } else if (newPiece.equals("Bishop")) // transform pawn to bishop
//                    {
//                        Piece bishop = new Piece(PieceType.BISHOP, end.piece.player, true);
//                        bishop.chessboard = end.piece.chessboard;
//                        bishop.player = end.piece.player;
//                        bishop.square = end.piece.square;
//                        end.piece = bishop;
//                    } else // transform pawn to knight
//                    {
//                        Piece knight = new Piece(PieceType.KNIGHT, end.piece.player, true);
//                        knight.chessboard = end.piece.chessboard;
//                        knight.player = end.piece.player;
//                        knight.square = end.piece.square;
//                        end.piece = knight;
//                    }
//                    promotedPiece = end.piece;
//                }
//            }
//        } else if (!end.piece.getType().equals(PieceType.PAWN)) {
//            twoSquareMovedPawn = null; //erase last saved move (for En passant)
//        }
//        //}
//
//        if (clearForwardHistory) {
//            this.moves_history.clearMoveForwardStack();
//            this.moves_history.addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
//        } else {
//            this.moves_history.addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
//        }
//    }/*endOf-move()-*/


//    public boolean redo()
//    {
//        return redo(true);
//    }

//    public boolean redo(boolean refresh)
//    {
//        if ( this.settings.gameType == Settings.gameTypes.local ) //redo only for local game
//        {
//            Move first = this.moves_history.redo();
//
//            Square from = null;
//            Square to = null;
//
//            if (first != null)
//            {
//                from = first.getFrom();
//                to = first.getTo();
//
//                this.move(this.squares[from.pozX][from.pozY], this.squares[to.pozX][to.pozY], true, false);
//                if (first.getPromotedPiece() != null)
//                {
//                    Piece pawn = this.squares[to.pozX][to.pozY].piece;
//                    pawn.square = null;
//
//                    this.squares[to.pozX][to.pozY].piece = first.getPromotedPiece();
//                    Piece promoted = this.squares[to.pozX][to.pozY].piece;
//                    promoted.square = this.squares[to.pozX][to.pozY];
//                }
//                return true;
//            }
//
//        }
//        return false;
//    }

//    public boolean undo()
//    {
//        return undo(true);
//    }

//    public synchronized boolean undo(boolean refresh) //undo last move
//    {
//        Move last = this.moves_history.undo();
//
//
//        if (last != null && last.getFrom() != null) {
//            Square begin = last.getFrom();
//            Square end = last.getTo();
//            try {
//                Piece moved = last.getMovedPiece();
//                this.squares[begin.pozX][begin.pozY].piece = moved;
//
//                moved.square = this.squares[begin.pozX][begin.pozY];
//
//                Piece taken = last.getTakenPiece();
//                if (last.getCastlingMove() != castling.none) {
//                    Piece rook = null;
//                    if (last.getCastlingMove() == castling.shortCastling) {
//                        rook = this.squares[end.pozX - 1][end.pozY].piece;
//                        this.squares[7][begin.pozY].piece = rook;
//                        rook.square = this.squares[7][begin.pozY];
//                        this.squares[end.pozX - 1][end.pozY].piece = null;
//                    } else {
//                        rook = this.squares[end.pozX + 1][end.pozY].piece;
//                        this.squares[0][begin.pozY].piece = rook;
//                        rook.square = this.squares[0][begin.pozY];
//                        this.squares[end.pozX + 1][end.pozY].piece = null;
//                    }
//                    moved.setMotioned(false);
//                    rook.setMotioned(false);
//                    this.breakCastling = false;
//                } else if (moved.getType().equals(PieceType.ROOK)) {
//                    moved.setMotioned(false);
//                } else if (moved.getType().equals(PieceType.PAWN) && last.wasEnPassant()) {
//                    Piece pawn = last.getTakenPiece();
//                    this.squares[end.pozX][begin.pozY].piece = pawn;
//                    pawn.square = this.squares[end.pozX][begin.pozY];
//
//                } else if (moved.getType().equals(PieceType.PAWN) && last.getPromotedPiece() != null) {
//                    Piece promoted = this.squares[end.pozX][end.pozY].piece;
//                    promoted.square = null;
//                    this.squares[end.pozX][end.pozY].piece = null;
//                }
//
//                //check one more move back for en passant
//                Move oneMoveEarlier = this.moves_history.getLastMoveFromHistory();
//                if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove()) {
//                    Piece canBeTakenEnPassant = this.squares[oneMoveEarlier.getTo().pozX][oneMoveEarlier.getTo().pozY].piece;
//                    if (canBeTakenEnPassant.getType().equals(PieceType.PAWN)) {
//                        this.twoSquareMovedPawn = canBeTakenEnPassant;
//                    }
//                }
//
//                if (taken != null && !last.wasEnPassant()) {
//                    this.squares[end.pozX][end.pozY].piece = taken;
//                    taken.square = this.squares[end.pozX][end.pozY];
//                } else {
//                    this.squares[end.pozX][end.pozY].piece = null;
//                }
//
//            } catch (ArrayIndexOutOfBoundsException exc) {
//                return false;
//            } catch (NullPointerException exc) {
//                return false;
//            }
//
//            return true;
//        } else {
//            return false;
//        }
//    }
}
