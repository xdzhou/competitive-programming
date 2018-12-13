package com.loic.game.programming.algo.sample.nim;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.loic.game.programming.api.MoveGenerator;

public class NimMoveGenerator implements MoveGenerator<NimBoard, Integer> {

  @Override
  public Set<Integer> generate(NimBoard board) {
    int chips = board.remainChips();

    if (chips > 0 && chips <= 3) {
      return Collections.singleton(chips);
    }

    Set<Integer> moves = new HashSet<>(3);
    if (chips >= 1) {
      moves.add(1);
    }
    if (chips >= 2) {
      moves.add(2);
    }
    if (chips >= 3) {
      moves.add(3);
    }
    return moves;
  }
}
