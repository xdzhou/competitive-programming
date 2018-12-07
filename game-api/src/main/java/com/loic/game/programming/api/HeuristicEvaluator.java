package com.loic.game.programming.api;

public interface HeuristicEvaluator<B extends GameBoard> {
  double evaluate(B board);
}
