package com.loic.game.programming.algo.sample.eightpuzzle;

import com.loic.game.programming.api.MoveGenerator;

import java.util.HashSet;
import java.util.Set;

public class EightPuzzleMoveGenerator implements MoveGenerator<EightPuzzleBoard, Direction> {
  @Override
  public Set<Direction> generate(EightPuzzleBoard board) {
    Set<Direction> directions = new HashSet<>(4);
    int blankIndex = board.blankSquareIndex();
    int row = blankIndex / 3;
    int col = blankIndex % 3;
    if (row - 1 >= 0) {
      directions.add(Direction.UP);
    }
    if (row + 1 < 3) {
      directions.add(Direction.DOWN);
    }
    if (col - 1 >= 0) {
      directions.add(Direction.LEFT);
    }
    if (col + 1 < 3) {
      directions.add(Direction.RIGHT);
    }
    directions.remove(board.dirApplied.reverse());
    return directions;
  }
}
