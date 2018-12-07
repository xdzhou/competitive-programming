package com.loic.game.programming.algo.common;

import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;
import com.loic.game.programming.api.GameMoveGenerator;
import com.loic.game.programming.api.HeuristicEvaluator;

public class GameLogic<B extends GameBoard, M extends GameMove> {
  private final GameMoveGenerator<B, M> moveGenerator;
  private final HeuristicEvaluator<B> heuristicEvaluator;

  public GameLogic(GameMoveGenerator<B, M> moveGenerator, HeuristicEvaluator<B> heuristicEvaluator) {
    this.moveGenerator = moveGenerator;
    this.heuristicEvaluator = heuristicEvaluator;
  }

  public GameMoveGenerator<B, M> moveGenerator() {
    return moveGenerator;
  }

  public HeuristicEvaluator<B> heuristicEvaluator() {
    return heuristicEvaluator;
  }
}
