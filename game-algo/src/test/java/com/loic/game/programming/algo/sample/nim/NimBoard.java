package com.loic.game.programming.algo.sample.nim;

import com.loic.game.programming.api.GameBoard;

import java.util.StringJoiner;

/**
 * A state of the game Nim. In Nim, players alternately take 1,2 or 3 chips with the
 * winner being the player to take the last chip.
 * In Nim any initial state of the form 4n+k for k = 1,2,3 is a win for player 0
 * (by choosing k) chips.
 * Any initial state of the form 4n is a win for player 2.
 */
public class NimBoard implements GameBoard {
  private int remainChips;
  private int playerId;

  public NimBoard(int remainChips, int playerId) {
    this.remainChips = remainChips;
    this.playerId = playerId;
  }

  public void apply(int chips) {
    remainChips -= chips;
    playerId = 1 - playerId;
  }

  public void cancel(int chips) {
    remainChips += chips;
    playerId = 1 - playerId;
  }

  public int remainChips() {
    return remainChips;
  }

  @Override
  public int currentPlayer() {
    return playerId;
  }

  @Override
  public double[] evaluate(int depth) {
    double[] values = new double[2];
    int winIndex = (remainChips % 4 == 0) ? playerId : 1 - playerId;

    values[winIndex] = 0.9;
    values[1 - winIndex] = 0.1;
    return values;
  }

  @Override
  public NimBoard copy() {
    return new NimBoard(remainChips, playerId);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", NimBoard.class.getSimpleName() + "[", "]").add("remainChips=" + remainChips).add("playerId=" + playerId).toString();
  }
}
