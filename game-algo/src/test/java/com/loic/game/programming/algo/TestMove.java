package com.loic.game.programming.algo;

import com.loic.game.programming.api.GameMove;

public class TestMove implements GameMove<TestBoard> {
  final int index;

  public TestMove(int index) {
    this.index = index;
  }

  public int index() {
    return index;
  }

  @Override
  public TestBoard apply(TestBoard board) {
    int playerCount = board.node.values.length;
    return new TestBoard((board.playerId + 1) % playerCount, board.node.children.get(index));
  }

  @Override
  public TestBoard cancel(TestBoard board) {
    //may have bug
    return board;
  }

  @Override
  public String toString() {
    return "TestMove{" +
      "index=" + index +
      '}';
  }
}
