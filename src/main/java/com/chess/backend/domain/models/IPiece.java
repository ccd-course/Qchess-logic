package com.chess.backend.domain.models;

import com.chess.backend.gamemodel.*;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.gamemodel.constants.PieceType;

import java.util.ArrayList;
import java.util.HashSet;

public interface IPiece {
    /**
     * Returns all allowed moves of the piece. The moves for each pieceType are composed of several abstract moves.
     *
     * @param game Game context
     * @return A HashSet of all allowed moves of the piece in this individual game context.
     */
    HashSet<Move> getAllowedFullMoves(ChessGame game);

    /**
     * Converts AllowedFullMoves to an array of Squares representing only the destination of the move.
     *
     * @param game Game context
     * @return ArrayList of possible Squares to move to.
     */
    ArrayList<Square> getAllowedMoves(ChessGame game);

    boolean getMainDirection();

    PieceType getType();

    void setType(PieceType type);

    Color getColor();

    /**
     * Method is useful for out of bounds protection
     *
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if parameters are out of bounds (array)
     */
    boolean isout(int x, int y);

    /**
     * @param x y position on chessboard
     * @param y y position on chessboard
     * @return true if can move, false otherwise
     */
    boolean checkPiece(int x, int y);

    /**
     * Method check if piece has other owner than calling piece
     *
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if owner(player) is different
     */
    boolean otherOwner(int x, int y);

    String getSymbol();

    boolean isMotioned();

    void setMotioned(boolean motioned);

    Player getPlayer();

    boolean isClockwise();

    @Override
    String toString();

    Square getSquare();

    Chessboard getChessboard();

    void setSquare(Square square);

    void setPlayer(Player player);

    void setChessboard(Chessboard chessboard);

    boolean equals(Object o);

    boolean canEqual(Object other);

    int hashCode();
}
