package com.chess.backend.gamemodel;

import com.chess.backend.domain.models.IBoard;
import com.chess.backend.services.ChessboardService;
import lombok.Data;

import java.util.Objects;

/**
 * Represents a position (x and y coordinates).
 */
@Data
public class Position {
    private int x;
    private int y;

    public enum Direction {
        LEFT,
        RIGHT,
        FORWARD,
        BACKWARD,
        DIAGONAL_FL,
        DIAGONAL_FR,
        DIAGONAL_BL,
        DIAGONAL_BR
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position from_id(int id, int board_x_max) {
        int x = id % board_x_max;
        int y = id / board_x_max;
        return new Position(x, y);
    }

    /**
     * Returns the position left to the current position.
     * <p>
     * Left border can not be crossed.
     *
     * @param chessboard Chessboard context
     * @return Position left to the current position
     */
    public Position left(IBoard chessboard) {
        if (this.getX() + 1 > ChessboardService.getMaxX(chessboard.getSquares())) {
            return null;
        } else {
            return new Position(this.getX() + 1, this.getY());
        }
    }

    /**
     * Returns the position right to the current position.
     * <p>
     * Right border can not be crossed.
     *
     * @param chessboard Chessboard context
     * @return Position right to the current position
     */
    public Position right(IBoard chessboard) {
        if (this.getX() - 1 < 0) {
            return null;
        } else {
            return new Position(this.getX() - 1, this.getY());
        }
    }

    /**
     * Returns the position at the top of the current position.
     * <p>
     * In reality there is no top and bottom, because squares[][] represents a circle.
     * Therefore these must be linked.
     *
     * @param chessboard Chessboard context
     * @return Position at the top of the current position
     */
    public Position forward(IBoard chessboard) {
        if (this.getY() - 1 < 0) {
            return new Position(this.getX(), ChessboardService.getMaxY(chessboard.getSquares()));
        } else {
            return new Position(this.getX(), this.getY() - 1);
        }
    }

    /**
     * Returns the position at the bottom of the current position.
     * <p>
     * In reality there is no top and bottom, because squares[][] represents a circle.
     * Therefore these must be linked.
     *
     * @param chessboard Chessboard context
     * @return Position at the bottom of the current position
     */
    public Position backward(IBoard chessboard) {
        if (this.getY() + 1 > ChessboardService.getMaxY(chessboard.getSquares())) {
            return new Position(this.getX(), 0);
        } else {
            return new Position(this.getX(), this.getY() + 1);
        }
    }

    /**
     * Returns the position diagonally at the bottom left of the current position.
     * <p>
     * Composition of backwards/bottom and left.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the bottom left of the current position
     */
    public Position diagBL(IBoard chessboard) {
        return this.backward(chessboard).left(chessboard);
    }

    /**
     * Returns the position diagonally at the bottom right of the current position.
     * <p>
     * Composition of backwards/bottom and right.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the bottom right of the current position
     */
    public Position diagBR(IBoard chessboard) {
        return this.backward(chessboard).right(chessboard);
    }

    /**
     * Returns the position diagonally at the front left of the current position.
     * <p>
     * Composition of forward/top and left.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the front left of the current position
     */
    public Position diagFL(IBoard chessboard) {
        return this.forward(chessboard).left(chessboard);
    }

    /**
     * Returns the position diagonally at the front right of the current position.
     * <p>
     * Composition of forward/top and right.
     *
     * @param chessboard Chessboard context
     * @return Position diagonally at the front right of the current position
     */
    public Position diagFR(IBoard chessboard) {
        return this.forward(chessboard).right(chessboard);
    }

    /**
     * Returns the position of a knight movement after FFR.
     * <p>
     * Composition of forward/top and right.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightFFR(IBoard chessboard) {
        Position first_step = this.forward(chessboard);
        Position second_step = first_step.forward(chessboard);
        if(second_step == null) return null;
        return second_step.right(chessboard);
    }

    /**
     * Returns the position of a knight movement after FRR.
     * <p>
     * Composition of forward/top and right.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightFRR(IBoard chessboard) {
        Position first_step = this.forward(chessboard);
        Position second_step = first_step.right(chessboard);
        if(second_step == null) return null;
        return second_step.right(chessboard);
    }

    /**
     * Returns the position of a knight movement after BRR.
     * <p>
     * Composition of backwards and right.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightBRR(IBoard chessboard) {
        Position first_step = this.backward(chessboard);
        Position second_step = first_step.right(chessboard);
        if(second_step == null) return null;
        return second_step.right(chessboard);
    }

    /**
     * Returns the position of a knight movement after BBR.
     * <p>
     * Composition of backwards and right.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightBBR(IBoard chessboard) {
        Position first_step = this.backward(chessboard);
        Position second_step = first_step.backward(chessboard);
        if(second_step == null) return null;
        return second_step.right(chessboard);
    }

    /**
     * Returns the position of a knight movement after BBL.
     * <p>
     * Composition of backwards and left.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightBBL(IBoard chessboard) {
        Position first_step = this.backward(chessboard);
        Position second_step = first_step.backward(chessboard);
        if(second_step == null) return null;
        return second_step.left(chessboard);
    }

    /**
     * Returns the position of a knight movement after BLL.
     * <p>
     * Composition of backwards and left.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightBLL(IBoard chessboard) {
        Position first_step = this.backward(chessboard);
        Position second_step = first_step.left(chessboard);
        if(second_step == null) return null;
        return second_step.left(chessboard);
    }

    /**
     * Returns the position of a knight movement after FLL.
     * <p>
     * Composition of forward and left.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightFLL(IBoard chessboard) {
        Position first_step = this.forward(chessboard);
        Position second_step = first_step.left(chessboard);
        if(second_step == null) return null;
        return second_step.left(chessboard);
    }

    /**
     * Returns the position of a knight movement after FFL.
     * <p>
     * Composition of forward and left.
     *
     * @param chessboard Chessboard context
     * @return Position
     */
    public Position knightFFL(IBoard chessboard) {
        Position first_step = this.forward(chessboard);
        Position second_step = first_step.forward(chessboard);
        if(second_step == null) return null;
        return second_step.left(chessboard);
    }

    public Position getPosFromDir(IBoard chessboard, Position.Direction direction){
        return switch (direction) {
            case LEFT -> this.left(chessboard);
            case RIGHT -> this.right(chessboard);
            case FORWARD -> this.forward(chessboard);
            case BACKWARD -> this.backward(chessboard);
            case DIAGONAL_BL -> this.diagBL(chessboard);
            case DIAGONAL_BR -> this.diagBR(chessboard);
            case DIAGONAL_FL -> this.diagFL(chessboard);
            case DIAGONAL_FR -> this.diagFR(chessboard);
        };
    }

    public int get_id(int board_x_max){
        return x + board_x_max * y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return getX() == position.getX()
                && getY() == position.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
