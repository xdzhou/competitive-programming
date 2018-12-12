package com.loic.game.programming.algo.genetic;

import java.util.Arrays;
import java.util.StringJoiner;

class GameGene<M> {
  private final M[] moves;
  private final double[] values;

  GameGene(M[] moves, double[] values) {
    this.moves = moves;
    this.values = values;
  }

  M firstMove() {
    return moves[0];
  }

  M[] moves() {
    return moves;
  }

  double[] values() {
    return values;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(moves);
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
  public String toString() {
    return new StringJoiner(", ", GameGene.class.getSimpleName() + "[", "]").add("moves=" + Arrays.toString(moves)).add("values=" + Arrays.toString(values)).toString();
  }
}
