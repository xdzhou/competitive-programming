package com.loic.game.programming.api;

public interface GameBoard<M> {
  int currentPlayer();

  double[] evaluate(int depth);

  void applyMove(M move);

  void cancelMove(M move);

  GameBoard<M> copy();
}
