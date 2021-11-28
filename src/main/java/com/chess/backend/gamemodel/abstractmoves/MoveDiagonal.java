package com.chess.backend.gamemodel.abstractmoves;

import com.chess.backend.gamemodel.AbstractMove;
import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Move;

import java.util.HashSet;
import java.util.Set;

public class MoveDiagonal implements AbstractMove {
    private Game game;
    private boolean attack; // allow moves to occupied fields (pawn may not attack straight forward)
    private boolean jump; // allow moves that pass occupied fields (knight)


    public MoveDiagonal(Game game) {
        this.game = game;
        this.attack = true;
        this.jump = false;
    }

    public MoveDiagonal(Game game, boolean attack, boolean jump) {
        this.game = game;
        this.attack = attack;
        this.jump = jump;
    }

    public Set<Move> concretise(){

        return new HashSet<>();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
