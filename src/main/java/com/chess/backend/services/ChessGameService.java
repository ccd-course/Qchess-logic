package com.chess.backend.services;

import com.chess.backend.domain.services.IGameService;
import com.chess.backend.gamemodel.Chessboard;
import com.chess.backend.gamemodel.Game;
import com.chess.backend.gamemodel.Player;
import com.chess.backend.gamemodel.Square;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Game service to initialize new Game and do operations on it
 */
@Component
public class ChessGameService implements IGameService {
    private final PlayerService playerService = new PlayerService();

    private static final ChessGameService CHESS_GAME_SERVICE = new ChessGameService();

    private Game game;

    /**
     * generate new ID for a game object
     *
     * @return id
     */
    @Override
    public Integer getNewGameID() {
        return 1;
    }

    /**
     * get current game service instance
     * @return GameService instance
     */
    public static ChessGameService getInstance() {
        return CHESS_GAME_SERVICE;
    }

    public ChessGameService() {
    }

    /**
     * create new Game
     * @param playerNames players names
     * @return boolean that's true if game is created
     */
    public boolean createNewGame(String[] playerNames) {
        game = new Game();

        //getting and setting the gameID
        game.setId(this.getNewGameID());

        //initialize the players
        Player[] players = playerService.initPlayers(playerNames);
        game.setPlayers(players);
        game.setActivePlayer(players[0]);

        //initialize the chessboard
        Chessboard newGameChessboard = ChessboardService.initNewGameBoard(game.getPlayers());
        game.setChessboard(newGameChessboard);

        return true;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public int getGameID() {
        return game.getId();
    }

    public Square[][] getBoard(int gameID) {
        if (verifyGameID(gameID)) {
            //TODO: handle the getting of the chessboard with the gameID
            //TODO: implement this call: game.getChessboard(gameID)
            return game.getChessboard().getSquares();
        } else {
            return null;
        }
    }

    /**
     * check if piece can move to the target position
     *
     * @param gameID                game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition      [x,y] position
     * @return true if valid
     */
    @Override
    public boolean validateMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition) {
        int[][] possibleMoves = getPossibleMoves(gameID, previousPiecePosition);

        for (int[] possibleMove : possibleMoves) {
            if ((possibleMove[0] == newPiecePosition[0]) && (possibleMove[1] == newPiecePosition[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     * returns possible moves for a piece position
     *         return value looks like that
     *         [[x,y], [2,2], [2,3], [3,3]]
     * @param gameID game id
     * @param piecePosition position of a piece
     * @return 2D array of possible positions [x,y]
     */
    @Override
    public int[][] getPossibleMoves(int gameID, int[] piecePosition){
        if(verifyGameID(gameID)){
            //TODO: resolve this, use delegation
            //we need a method getPieceByPosition(int x, int y) in game or in the chessboardService
            ArrayList<Square> moveList = game.getChessboard().getSquares()[piecePosition[0]][piecePosition[1]].getPiece().getAllowedMoves(game);
            int[][] moves = new int[moveList.size()][2];

            for(int i = 0; i < moveList.size(); i++){
                moves[i][0] = moveList.get(i).getPosX();
                moves[i][1] = moveList.get(i).getPosY();
            }

            return moves;
        } else {
            return new int[][]{};
        }
    }

    /**
     * move piece from one position to another
     * @param gameID game id
     * @param previousPiecePosition [x,y] position
     * @param newPiecePosition [x,y] position
     * @return if verified game id and valid move return the Activate player name otherwise return empty string
     */
    @Override
    public String executedMove(int gameID, int[] previousPiecePosition, int[] newPiecePosition){
        if(verifyGameID(gameID)){
            if(this.validateMove(gameID, previousPiecePosition, newPiecePosition)){
                ChessboardService.move(game.getChessboard(), previousPiecePosition[0], previousPiecePosition[1], newPiecePosition[0], newPiecePosition[1]);
                game.switchActive();

                return game.getActivePlayerName();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * returns activate player
     * @param gameID game id
     * @return player name
     */
    @Override
    public String getPlayerTurn(int gameID) {
        if (verifyGameID(gameID)) {
            return game.getActivePlayerName();
        } else {
            return "";
        }
    }

    /**
     * End game function
     * @param gameID game id
     * @return true if game is valid and ended successfully
     */
    @Override
    public boolean endGame(int gameID){
        if(verifyGameID(gameID)){
            game = null;
            System.gc();

            return true;
        } else {
            return false;
        }
    }

}
