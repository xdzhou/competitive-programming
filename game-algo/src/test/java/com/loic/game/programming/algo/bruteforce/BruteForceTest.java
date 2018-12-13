package com.loic.game.programming.algo.bruteforce;

import com.loic.game.programming.algo.MoveResolverTester;
import org.junit.jupiter.api.Test;

class BruteForceTest {

  @Test
  void testNim() {
    MoveResolverTester.checkNim(new BruteForce(), 6);
  }

  @Test
  void testEightPuzzle(){
    MoveResolverTester.checkEightPuzzle(new BruteForce(), 6);
  }
}