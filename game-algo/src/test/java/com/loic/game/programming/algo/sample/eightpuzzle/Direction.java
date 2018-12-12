package com.loic.game.programming.algo.sample.eightpuzzle;

public enum Direction {

  UP, DOWN, LEFT, RIGHT, NONE;

  public Direction reverse() {
    switch (this) {
      case UP:
        return DOWN;
      case DOWN:
        return UP;
      case LEFT:
        return RIGHT;
      case RIGHT:
        return LEFT;
      default:
        return NONE;
    }
  }
}
