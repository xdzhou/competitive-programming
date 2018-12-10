package com.loic.game.programming.algo.genetic;

import java.util.Random;

import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

public class GameGenetic implements BestMoveResolver {

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    GeneticAlgo<GameGene<M>> geneticAlgo = new GeneticAlgo<>(new GameResolver<>(rootBoard, moveGenerator, maxDepth),
      gene -> {
        B curBoard = rootBoard.copy();
        int curDepth = 0;
        for (M move : gene.moves()) {
          if (move != null) {
            transformer.applyMove(curBoard, move);
            curDepth++;
          } else {
            break;
          }
        }
        return curBoard.evaluate(curDepth)[0];
      });

    GameGene<M> gene = geneticAlgo.iterate(1000, 100);
    return gene.firstMove();
  }

  private static final class GameResolver<B extends GameBoard, M> implements CandidateResolver<GameGene<M>> {
    private final B rootBoard;
    private final MoveGenerator<B, M> moveGenerator;
    private final int depth;

    private GameResolver(B rootBoard, MoveGenerator<B, M> moveGenerator, int depth) {
      this.rootBoard = rootBoard;
      this.moveGenerator = moveGenerator;
      this.depth = depth;
    }

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
