package com.loic.game.programming.algo.astar;

import com.loic.game.programming.algo.MoveResolverTester;
import org.junit.jupiter.api.Test;

class AStarTest {

  @Test
  void testNim() {
    MoveResolverTester.checkNim(new AStar(((values, depth, playerId) -> {
      double score = values[0];
      return score * Math.pow(0.9, depth);
    })), 4);
  }

  @Test
  void testEightPuzzle() {
    MoveResolverTester.checkEightPuzzle(new AStar(((values, depth, playerId) -> {
      double score = values[0];
      return -(8 - score + depth);
    })), 4);
  }
}