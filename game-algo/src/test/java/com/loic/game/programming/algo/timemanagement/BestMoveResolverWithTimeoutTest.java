package com.loic.game.programming.algo.timemanagement;

import static org.junit.jupiter.api.Assertions.*;

import com.loic.game.programming.algo.MoveResolverTester;
import com.loic.game.programming.algo.astar.AStar;
import com.loic.game.programming.api.BestMoveResolver;
import org.junit.jupiter.api.Test;

class BestMoveResolverWithTimeoutTest {

  @Test
  void testEightPuzzle() {
    BestMoveResolver moveResolver = new AStar(((values, depth, playerId) -> {
      double score = values[0];
      return -(8 - score + depth);
    }));

    BestMoveResolver resolverWithTimeout = new BestMoveResolverWithTimeout(moveResolver, 100);

    MoveResolverTester.checkEightPuzzle(resolverWithTimeout, 4);
  }
}