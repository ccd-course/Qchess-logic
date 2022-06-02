package com.chess.backend.services;

import com.chess.backend.ChessGameInitializationTests;
import com.chess.backend.gamemodel.ChessGame;
import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.constants.Color;
import com.chess.backend.restController.objects.ExecutedMoveObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class ChessboardServiceTests {
    @Test
    void testNumberOfPlayers() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(Arrays.asList(players));
        assertEquals(chessboard.getNumberOfPlayers(), 3);

    }
    @Test
    void testChessboardSize() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(Arrays.asList(players));
        assertEquals(chessboard.getSquares().size(), 5);
        assertEquals(chessboard.getSquares().get(0).size(), 30);

    }

    @Test
    void testMoveToAction() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(Arrays.asList(players));
        ArrayList<Integer> actions = ChessboardService.getAllPossibleActions(chessboard, firstPlayer);
        assertEquals(24, actions.size());
    }

    @Test
    void testActionToMove() {

        Player firstPlayer = new Player("Amro",Color.WHITE, 0);
        Player secondPlayer = new Player("Valentin",Color.BLACK, 1);
        Player thirdPlayer = new Player("Hannes", Color.RED, 2);
        Player[] players = {firstPlayer, secondPlayer, thirdPlayer};
        Chessboard chessboard = ChessboardService.initNewGameBoard(Arrays.asList(players));
        ArrayList<Integer> actions = ChessboardService.getAllPossibleActions(chessboard, secondPlayer);
        ArrayList<ExecutedMoveObject> moveObjects = new ArrayList<>();
        for (int action : actions) {
            moveObjects.add(ChessboardService.actionToMove(chessboard, action, 1));
        }
        assertEquals(24, moveObjects.size());
    }
}
