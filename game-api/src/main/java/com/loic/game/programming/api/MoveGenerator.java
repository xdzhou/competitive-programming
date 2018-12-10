package com.loic.game.programming.api;

import java.util.Set;

public interface MoveGenerator<B extends GameBoard<M>, M> {
  Set<M> generate(B board);
}
