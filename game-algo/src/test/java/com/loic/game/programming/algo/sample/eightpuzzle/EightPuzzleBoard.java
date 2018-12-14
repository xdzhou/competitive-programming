package com.loic.game.programming.algo.sample.eightpuzzle;

import com.loic.game.programming.api.GameBoard;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * https://www.cs.princeton.edu/courses/archive/spr10/cos226/assignments/8puzzle.html
 */
public class EightPuzzleBoard implements GameBoard {
  private final int[] board;

  public EightPuzzleBoard(int... board) {
    if (board.length != 9) {
      throw new IllegalArgumentException("Board length should be 9");
    }
    this.board = board;
  }

  @Override
  public int currentPlayer() {
    //only one player
    return 0;
  }

  @Override
  public double[] evaluate(int depth) {
    int archive = archiveCount();
    double addition = depth == 0 ? 1.1d : 1 / (double) depth;
    return new double[]{archive + addition};
  }

  @Override
  public EightPuzzleBoard copy() {
    return new EightPuzzleBoard(Arrays.copyOf(board, board.length));
  }

  boolean isWin() {
    return archiveCount() == 8;
  }

  private int archiveCount() {
    int archive = 0;
    for (int i = 0; i < 8; i++) {
      if (board[i] == i + 1) {
        archive++;
      }
    }
    return archive;
  }

  int blankSquareIndex() {
    for (int i = 0; i < board.length; i++) {
      if (board[i] == 0) {
        return i;
      }
    }
    throw new IllegalStateException("No empty square");
  }

  void switchSquare(int index1, int index2) {
    int square1 = board[index1];
    board[index1] = board[index2];
    board[index2] = square1;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", EightPuzzleBoard.class.getSimpleName() + "[", "]").add("board=" + Arrays.toString(board)).toString();
  }
}
