package com.loic.game.programming.api;

public interface GameBoard {
  int currentPlayer();

  void evaluate();

  double score(int playerId);
}
