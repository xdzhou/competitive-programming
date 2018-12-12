package com.loic.game.programming.algo.sample.eightpuzzle;

import com.loic.game.programming.api.Transformer;

public class EightPuzzleTransformer implements Transformer<EightPuzzleBoard, Direction> {

  @Override
  public void applyMove(EightPuzzleBoard board, Direction move) {
    int blankIndex = board.blankSquareIndex();
    int row = blankIndex / 3;
    int col = blankIndex % 3;
    switch (move) {
      case LEFT:
        col--;
        break;
      case DOWN:
        row++;
        break;
      case UP:
        row--;
        break;
      case RIGHT:
        col++;
        break;
    }
    board.switchSquare(blankIndex, row * 3 + col);
  }

  @Override
  public void cancelMove(EightPuzzleBoard board, Direction move) {
    applyMove(board, move.reverse());
  }
}
