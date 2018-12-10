package com.loic.game.programming.api;

public interface BestMoveResolver {
  <B extends GameBoard<M>, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, EvaluationConverter converter, int maxDepth);
}
