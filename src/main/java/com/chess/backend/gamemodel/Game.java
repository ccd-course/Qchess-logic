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

import com.chess.backend.services.ChessboardService;
import lombok.Data;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
@Data
public class Game {

    public Settings settings;
    public Chessboard chessboard;
    public GameClock gameClock;
    public Moves moves;
    private Player activePlayer;
    private int id;
    private Player[] players;

//    public Game()
//    {
//        this.moves = new Moves(this);
//        settings = new Settings();
//        //TODO: handle the settings, current situation gives an error when creating a game
//        chessboard = new Chessboard(this.settings, this.moves);
//        //this.chessboard.
//        // gameClock = new GameClock(this); // TODO: Implement from old jchess
//
//    }

//    /** Method to Start new game
//     *
//     */
//    public void newGame()
//    {
//        chessboard.setPieces("", settings.playerWhite, settings.playerBlack);
//        activePlayer = settings.playerWhite;
//    }

    /**
     * Method to switch active players after move
     */
    public void switchActive() {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(activePlayer)) {
                if (i == (players.length - 1)) {
                    activePlayer = players[0];
                    break;
                } else {
                    activePlayer = players[i+1];
                    break;
                }
            }
        }
    }

    /**
     * Method of getting accualy active player
     *
     * @return player The player which have a move
     */
    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public String getActivePlayerName(){
        return this.activePlayer.getName();
    }

    public int getId() {
        return this.id;
    }

    /**
     * Method to go to next move (checks if game is local/network etc.)
     */
    public void nextMove() {
        switchActive();
        System.out.println("next move, active player: " + activePlayer.getName() + ", color: " + activePlayer.color.name());
    }

    /**
     * Method to simulate Move to check if it's correct etc. (usable for network game).
     *
     * @param beginX from which X (on chessboard) move starts
     * @param beginY from which Y (on chessboard) move starts
     * @param endX   to   which X (on chessboard) move go
     * @param endY   to   which Y (on chessboard) move go
     */
    public boolean simulateMove(int beginX, int beginY, int endX, int endY) {
        try {
            if (chessboard.squares[beginX][beginY].getPiece().getAllowedMoves(this).contains(chessboard.squares[endX][endY])) //move
            {
                ChessboardService.move(chessboard, beginX, beginY, endX, endY);
            } else {
                System.out.println("Bad move");
                return false;
            }
            nextMove();

            return true;

        } catch (StringIndexOutOfBoundsException exc) {
            return false;
        } catch (ArrayIndexOutOfBoundsException exc) {
            return false;
        } catch (NullPointerException exc) {
            return false;
        } finally {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, "ERROR");
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}

class ReadGameError extends Exception {
}