package com.loic.game.programming.algo;

import com.loic.game.programming.api.GameBoard;

public class TestBoard implements GameBoard {
  final Node node;

  public TestBoard(Node node) {
    this.node = node;
  }

  @Override
  public int currentPlayer() {
    return 0;
  }

  @Override
  public void evaluate() {

  }

  @Override
  public double score(int playerId) {
    return node.value;
  }

  @Override
  public String toString() {
    return "TestBoard{" + node.value + '}';
  }
}
