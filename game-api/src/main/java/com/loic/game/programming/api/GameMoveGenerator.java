package com.loic.game.programming.api;

import java.util.List;

public interface GameMoveGenerator<B extends GameBoard, M extends GameMove> {
  List<M> generate(B board);
}
