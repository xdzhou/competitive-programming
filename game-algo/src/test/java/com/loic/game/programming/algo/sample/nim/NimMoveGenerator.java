package com.loic.game.programming.algo.sample.nim;

import com.loic.game.programming.api.MoveGenerator;

import java.util.HashSet;
import java.util.Set;

public class NimMoveGenerator implements MoveGenerator<NimBoard, Integer> {

  @Override
  public Set<Integer> generate(NimBoard board) {
    Set<Integer> moves = new HashSet<>(3);
    if (board.remainChips() >= 1) {
      moves.add(1);
    }
    if (board.remainChips() >= 2) {
      moves.add(2);
    }
    if (board.remainChips() >= 3) {
      moves.add(3);
    }
    return moves;
  }
}
