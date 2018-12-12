package com.loic.game.programming.algo.minimax;

import com.loic.game.programming.algo.MoveResolverTester;
import org.junit.jupiter.api.Test;

class MiniMaxTest {

  @Test
  void testAlgo() {
    MoveResolverTester.checkNim(new MiniMax(), 6);
  }
}