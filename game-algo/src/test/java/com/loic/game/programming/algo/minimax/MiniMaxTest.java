package com.loic.game.programming.algo.minimax;

import com.loic.game.programming.algo.MoveResolverTester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MiniMaxTest {

  @Test
  void testNim() {
    MoveResolverTester.checkNim(new MiniMax(), 6);
  }

  @Test
  void testEightPuzzle() {
    IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> MoveResolverTester.checkEightPuzzle(new MiniMax(), 6));
    Assertions.assertEquals("MiniMax algo can only apply to two players game", e.getMessage());
  }
}