package com.loic.game.programming.algo;

import com.loic.game.programming.algo.common.BestMoveResolverWithLog;
import com.loic.game.programming.api.BestMoveResolver;
import org.junit.jupiter.api.Assertions;

public class MoveResolverTester {

  private MoveResolverTester() {
    //utils
  }

  public static void check(BestMoveResolver moveResolver, int mawDepth) {
    BestMoveResolver resolverWithLog = new BestMoveResolverWithLog(moveResolver, System.out);
    testSmallNimGame(resolverWithLog, mawDepth);
    testBigNimGame(resolverWithLog, mawDepth);
  }

  private static void testSmallNimGame(BestMoveResolver moveResolver, int mawDepth) {
    for (int i = 1; i <= 3; i++) {
      NimBoard rootBoard = new NimBoard(i, 1);
      int move = moveResolver.bestMove(rootBoard, new NimMoveGenerator(), new NimTransformer(), mawDepth);
      Assertions.assertEquals(i, move);
    }
  }

  private static void testBigNimGame(BestMoveResolver moveResolver, int mawDepth) {
    for (int i = 1; i <= 3; i++) {
      NimBoard rootBoard = new NimBoard(4 * 50 + i, 1);
      int move = moveResolver.bestMove(rootBoard, new NimMoveGenerator(), new NimTransformer(), mawDepth);
      Assertions.assertEquals(i, move);
    }
  }

}
