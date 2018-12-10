package com.loic.game.programming.api;

public interface GameBoard {
  int currentPlayer();

  double[] evaluate(int depth);

  <B extends GameBoard> B copy();
}
