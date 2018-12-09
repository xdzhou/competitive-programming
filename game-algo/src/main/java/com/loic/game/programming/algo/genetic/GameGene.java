package com.loic.game.programming.algo.genetic;

import java.util.Arrays;

import com.loic.game.programming.api.GameMove;

public class GameGene<M extends GameMove> {
  private final M[] moves;

  public GameGene(M[] moves) {
    this.moves = moves;
  }

  M firstMove() {
    return moves[0];
  }

  M[] moves() {
    return moves;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GameGene<?> gene = (GameGene<?>) o;
    return Arrays.equals(moves, gene.moves);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(moves);
  }
}
