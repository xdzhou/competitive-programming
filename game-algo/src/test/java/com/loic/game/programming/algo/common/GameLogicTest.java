package com.loic.game.programming.algo.common;

import com.loic.game.programming.api.GameMoveGenerator;
import com.loic.game.programming.api.HeuristicEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameLogicTest {

  @Test
  void moveGenerator() {
    GameMoveGenerator moveGenerator = Mockito.mock(GameMoveGenerator.class);
    GameLogic gameLogic = new GameLogic(moveGenerator, null);

    Assertions.assertEquals(moveGenerator, gameLogic.moveGenerator());
  }

  @Test
  void heuristicEvaluator() {
    HeuristicEvaluator evaluator = Mockito.mock(HeuristicEvaluator.class);
    GameLogic gameLogic = new GameLogic(null, evaluator);

    Assertions.assertEquals(evaluator, gameLogic.heuristicEvaluator());
  }
}