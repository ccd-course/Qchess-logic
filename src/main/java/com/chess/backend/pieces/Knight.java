package com.chess.backend.pieces;

import com.chess.backend.board.Chessboard;
import com.chess.backend.board.Move;
import com.chess.backend.constants.COLOR;

import java.util.List;

public class Knight extends Piece{
    public Knight(final COLOR color, final Chessboard chessboard, int currentPosition) {
        super(color, chessboard, "King", currentPosition);
    }

    @Override
    public List<Move> getAllPossibleMoves() {
        return null;
    }
}
