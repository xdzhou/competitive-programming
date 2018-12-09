package com.loic.game.programming.algo;

import java.util.Arrays;

import com.loic.game.programming.api.GameBoard;

public class TestBoard implements GameBoard {
  final int playerId;
  final Node node;

  public TestBoard(int playerId, Node node) {
    this.playerId = playerId;
    this.node = node;
  }

  @Override
  public int currentPlayer() {
    return playerId;
  }

  @Override
  public double[] evaluate(int depth) {
    double[] retVal = new double[node.values.length];
    for (int i = 0; i < retVal.length; i++) {
      retVal[i] = node.values[i];
    }
    return retVal;
  }

  @Override
  public String toString() {
    return "TestBoard{" + Arrays.toString(node.values) + '}';
  }
}
