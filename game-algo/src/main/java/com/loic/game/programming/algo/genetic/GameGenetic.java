package com.loic.game.programming.algo.genetic;

import com.loic.game.programming.algo.common.GameLogic;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;

import java.util.*;

public class GameGenetic<B extends GameBoard, M extends GameMove<B>> extends AbstractGenetic<GameGene<M>> {
  private final Map<GameGene<M>, Double> cache;
  private final GameLogic<B, M> gameLogic;
  private B rootBoard;

  public GameGenetic(GameLogic<B, M> gameLogic) {
    super(new GameResolver<>());
    this.gameLogic = gameLogic;
    this.cache = new HashMap<>();
  }

  public M bestMove(B rootBoard, int depth) {
    this.rootBoard = rootBoard;
    GameGene<M> gene = iterate(1000, 100);
    return gene.firstMove();
  }

  @Override
  protected Map<GameGene<M>, Double> computeScores(List<GameGene<M>> gameGenes) {
    gameGenes.forEach(gene -> cache.putIfAbsent(gene, evaluate(gene)));
    return cache;
  }

  private double evaluate(GameGene<M> gene) {
    B curBoard = rootBoard;
    for (M move : gene.moves()) {
      curBoard = move.apply(curBoard);
    }
    return gameLogic.heuristicEvaluator().evaluate(curBoard);
  }




  private static final class GameResolver<B extends GameBoard, M extends GameMove<B>> implements CandidateResolver<GameGene<M>> {

    @Override
    public GameGene<M> generateRandomly(Random random) {
      return null;
    }

    @Override
    public GameGene<M> merge(GameGene<M> gene1, GameGene<M> gene2, Random random) {
      return null;
    }

    @Override
    public GameGene<M> mutate(GameGene<M> mGameGene, Random random) {
      return null;
    }
  }
}
