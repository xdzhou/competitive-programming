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
    return new TestBoard(board.node.children.get(index));
  }

  @Override
  public TestBoard cancel(TestBoard board) {
    throw new UnsupportedOperationException("Not supported");
  }

  @Override
  public String toString() {
    return "TestMove{" +
      "index=" + index +
      '}';
  }
}
