package com.loic.game.programming.algo.bruteforce;

import com.loic.game.programming.algo.MoveResolverTester;
import org.junit.jupiter.api.Test;

class BruteForceTest {

  @Test
  void testAlgo() {
    MoveResolverTester.checkNim(new BruteForce(), 6);
  }
}