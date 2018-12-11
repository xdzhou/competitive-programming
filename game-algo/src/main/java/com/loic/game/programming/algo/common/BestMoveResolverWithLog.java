package com.loic.game.programming.algo.common;

import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

import java.io.PrintStream;
import java.util.Set;

public class BestMoveResolverWithLog implements BestMoveResolver {
  private final BestMoveResolver delegate;
  private final PrintStream printStream;

  public BestMoveResolverWithLog(BestMoveResolver delegate, PrintStream printStream) {
    this.delegate = delegate;
    this.printStream = printStream;
  }

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    MoveGenerator<B, M> generatorWithLog = b -> {
      Set<M> moves = moveGenerator.generate(b);
      printStream.println(b + " generate moves " + moves);
      return moves;
    };

    Transformer<B, M> transformerWithLog = new Transformer<B, M>() {
      @Override
      public void applyMove(B board, M move) {
        printStream.println("Apply " + move + " on " + board);
        transformer.applyMove(board, move);
      }

      @Override
      public void cancelMove(B board, M move) {
        printStream.println("Cancel " + move + " on " + board);
        transformer.cancelMove(board, move);
      }
    };

    return delegate.bestMove(rootBoard, generatorWithLog, transformerWithLog, maxDepth);
  }
}
