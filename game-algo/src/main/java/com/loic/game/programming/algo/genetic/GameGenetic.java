package com.loic.game.programming.algo.genetic;

import com.loic.game.programming.algo.timemanagement.TimeoutException;
import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class GameGenetic implements BestMoveResolver {

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    GeneticAlgo<GameGene<M>> geneticAlgo = new GeneticAlgo<>(new GameResolver<>(rootBoard, moveGenerator, transformer, maxDepth), gene -> gene.values()[0]);

    try {
      GameGene<M> gene = geneticAlgo.iterate(100, 80);
      return gene.firstMove();
    } catch (TimeoutException e) {
      return geneticAlgo.cueentBest().firstMove();
    }
  }

  private static final class GameResolver<B extends GameBoard, M> implements CandidateResolver<GameGene<M>> {
    private final B rootBoard;
    private final MoveGenerator<B, M> moveGenerator;
    private final Transformer<B, M> transformer;
    private final int depth;

    private GameResolver(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int depth) {
      this.rootBoard = rootBoard;
      this.moveGenerator = moveGenerator;
      this.transformer = transformer;
      this.depth = depth;
    }

    @Override
    public GameGene<M> generateRandomly(Random random) {
      B board = rootBoard.copy();
      Set<M> moves = moveGenerator.generate(board);
      M[] geneMoves = (M[]) Array.newInstance(moves.iterator().next().getClass(), depth);
      int curIndex = 0;
      while (curIndex < depth && !moves.isEmpty()) {
        geneMoves[curIndex] = new ArrayList<>(moves).get(random.nextInt(moves.size()));
        transformer.applyMove(board, geneMoves[curIndex]);
        curIndex++;
        moves = moveGenerator.generate(board);
      }

      GameGene<M> gene = new GameGene<>(geneMoves, board.evaluate(curIndex));
      System.out.println(gene);
      return gene;
    }

    @Override
    public GameGene<M> merge(GameGene<M> gene1, GameGene<M> gene2, Random random) {
      int cutIndex = random.nextInt(depth - 1);
      M[] geneMoves = Arrays.copyOf(gene1.moves(), depth);

      for (int i = cutIndex + 1; i < depth; i++) {
        geneMoves[i] = gene2.moves()[i];
      }
      return new GameGene<>(geneMoves, evaluate(geneMoves));
    }

    @Override
    public GameGene<M> mutate(GameGene<M> gene, Random random) {
      M[] geneMoves = Arrays.copyOf(gene.moves(), depth);
      int index1 = random.nextInt(depth);
      int index2 = random.nextInt(depth);
      if (index1 == index2) {
        return gene;
      } else {
        M move = geneMoves[index1];
        geneMoves[index1] = geneMoves[index2];
        geneMoves[index2] = move;
        return new GameGene<>(geneMoves, evaluate(geneMoves));
      }
    }

    private double[] evaluate(M[] geneMoves) {
      B curBoard = rootBoard.copy();
      int curIndex = 0;
      Set<M> moves = moveGenerator.generate(curBoard);
      while (curIndex < depth && moves.contains(geneMoves[curIndex])) {
        transformer.applyMove(curBoard, geneMoves[curIndex]);
        curIndex++;
        moves = moveGenerator.generate(curBoard);
      }
      double[] evaluate = curBoard.evaluate(curIndex);
      for (int i = curIndex; i < depth; i++) {
        geneMoves[i] = null;
      }
      return evaluate;
    }
  }
}
