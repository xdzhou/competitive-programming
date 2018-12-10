package com.loic.game.programming.api;

public interface BestMoveResolver {
  <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth);
}
