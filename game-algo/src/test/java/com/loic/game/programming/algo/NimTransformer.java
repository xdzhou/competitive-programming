package com.loic.game.programming.algo;

import com.loic.game.programming.api.Transformer;

public class NimTransformer implements Transformer<NimBoard, Integer> {
  @Override
  public void applyMove(NimBoard board, Integer move) {
    board.apply(move);
  }

  @Override
  public void cancelMove(NimBoard board, Integer move) {
    board.cancel(move);
  }
}
