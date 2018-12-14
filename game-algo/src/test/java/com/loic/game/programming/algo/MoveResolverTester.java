package com.loic.game.programming.algo;

import com.loic.game.programming.algo.common.BestMoveResolverWithLog;
import com.loic.game.programming.algo.sample.eightpuzzle.Direction;
import com.loic.game.programming.algo.sample.eightpuzzle.EightPuzzleBoard;
import com.loic.game.programming.algo.sample.eightpuzzle.EightPuzzleMoveGenerator;
import com.loic.game.programming.algo.sample.eightpuzzle.EightPuzzleTransformer;
import com.loic.game.programming.algo.sample.nim.NimBoard;
import com.loic.game.programming.algo.sample.nim.NimMoveGenerator;
import com.loic.game.programming.algo.sample.nim.NimTransformer;
import com.loic.game.programming.api.BestMoveResolver;
import org.junit.jupiter.api.Assertions;

public class MoveResolverTester {

  private MoveResolverTester() {
    //utils
  }

  public static void checkNim(BestMoveResolver moveResolver, int mawDepth) {
    BestMoveResolver resolverWithLog = new BestMoveResolverWithLog(moveResolver, System.out);
    //testSmallNimGame(resolverWithLog, mawDepth);
    testBigNimGame(resolverWithLog, mawDepth);
  }

  public static void checkEightPuzzle(BestMoveResolver moveResolver, int mawDepth) {
    BestMoveResolver resolverWithLog = new BestMoveResolverWithLog(moveResolver, System.out);
    testEightPuzzle(resolverWithLog, mawDepth);
  }

  private static void testSmallNimGame(BestMoveResolver moveResolver, int mawDepth) {
    for (int i = 1; i <= 3; i++) {
      NimBoard rootBoard = new NimBoard(i, 1);
      int move = moveResolver.bestMove(rootBoard, new NimMoveGenerator(), new NimTransformer(), mawDepth);
      Assertions.assertEquals(i, move);
    }
  }

  private static void testBigNimGame(BestMoveResolver moveResolver, int mawDepth) {
    //for (int i = 1; i <= 3; i++)
    {
      NimBoard rootBoard = new NimBoard(4 * 50 + 3, 1);
      int move = moveResolver.bestMove(rootBoard, new NimMoveGenerator(), new NimTransformer(), mawDepth);
      Assertions.assertEquals(3, move);
    }
  }

  /**
   * test case from following URL:
   * https://www.cs.princeton.edu/courses/archive/spr10/cos226/assignments/8puzzle.html
   */
  private static void testEightPuzzle(BestMoveResolver moveResolver, int mawDepth) {
    EightPuzzleBoard board = new EightPuzzleBoard(0, 1, 3, 4, 2, 5, 7, 8, 6);
    Direction dir = moveResolver.bestMove(board, new EightPuzzleMoveGenerator(), new EightPuzzleTransformer(), mawDepth);
    Assertions.assertEquals(Direction.RIGHT, dir);
  }

}
