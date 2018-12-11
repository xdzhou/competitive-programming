package com.loic.game.programming.algo.mcts;

import com.loic.game.programming.algo.MoveResolverTester;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class UTCTest {

  @Test
  void testAlgo() {
    MoveResolverTester.check(new UTC((values, playerId) -> {
      double sum = Arrays.stream(values).sum();
      return values[playerId] / sum;
    }, 100));
  }
}