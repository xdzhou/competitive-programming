package com.loic.game.programming.algo.mcts;

import com.loic.game.programming.algo.MoveResolverTester;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class UTCTest {

  @Test
  void testNim() {
    MoveResolverTester.checkNim(new UTC((values, depth, playerId) -> {
      if (values.length == 1) {
        return values[0];
      }
      double sum = Arrays.stream(values).sum();
      return values[playerId] / sum;
    }, 100), 5);
  }

  @Test
  void testEightPuzzle() {
    MoveResolverTester.checkEightPuzzle(new UTC((values, depth, playerId) -> values[playerId] / 10d, 10), 5);
  }
}