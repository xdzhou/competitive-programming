package com.loic.game.programming.api;

public interface GameMove<B extends GameBoard> {
  B apply(B board);

  B cancel(B board);
}
