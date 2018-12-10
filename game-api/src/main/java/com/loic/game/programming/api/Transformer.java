package com.loic.game.programming.api;

public interface Transformer<B extends GameBoard, M> {
  void applyMove(B board, M move);

  void cancelMove(B board, M move);
}
