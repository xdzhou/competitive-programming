package com.loic.game.programming.algo.bruteforce;

import com.loic.game.programming.algo.Node;
import com.loic.game.programming.algo.TestBoard;
import com.loic.game.programming.algo.TestMove;
import com.loic.game.programming.algo.TestMoveGenerator;
import com.loic.game.programming.algo.observer.GameObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BruteForceTest {

  @Test
  void testWikiCase() {
    TestMoveGenerator logics = new TestMoveGenerator();
    BruteForce<TestBoard, TestMove> algo = new BruteForce<>(new TestMoveGenerator());
    algo.setGameObserver(new GameObserver<TestBoard, TestMove>() {
      @Override
      public void onMoveApplied(TestBoard oldBoard, TestMove move, TestBoard newBoard) {
        System.out.println("From " + oldBoard + " to " + newBoard + ", within move " + move);
      }

      @Override
      public void currentBestMove(TestMove bestMove) {
        System.out.println("Current best " + bestMove);
      }
    });

    TestMove move = algo.bestMove(new TestBoard(-1, Node.testCase()), 4);
    Assertions.assertEquals(1, move.index());
  }
}