package com.loic.game.programming.algo.genetic;

import com.loic.game.programming.api.GameMove;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameGeneTest {

  @Test
  void firstMove() {
    GameMove move = Mockito.mock(GameMove.class);
    GameGene gene = new GameGene<>(new GameMove[]{move, Mockito.mock(GameMove.class)});

    Assertions.assertEquals(move, gene.firstMove());
  }

  @Test
  void moves() {
    GameMove[] moves = new GameMove[]{Mockito.mock(GameMove.class), Mockito.mock(GameMove.class)};
    GameGene gene = new GameGene<>(moves);
    Assertions.assertEquals(moves, gene.moves());
  }
}