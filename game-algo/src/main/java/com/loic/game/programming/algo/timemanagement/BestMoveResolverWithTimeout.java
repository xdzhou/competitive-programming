package com.loic.game.programming.algo.timemanagement;

import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

public class BestMoveResolverWithTimeout implements BestMoveResolver {
  private final BestMoveResolver delegate;
  private final double timeoutInMilliseconds;

  public BestMoveResolverWithTimeout(BestMoveResolver delegate, double timeoutInMilliseconds) {
    this.delegate = delegate;
    this.timeoutInMilliseconds = timeoutInMilliseconds;
  }

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    final Timer timer = new Timer(timeoutInMilliseconds);

    Transformer<B, M> transformerWithTimeout = new Transformer<B, M>() {
      @Override
      public void applyMove(B board, M move) {
        timer.timeCheck();
        transformer.applyMove(board, move);
      }

      @Override
      public void cancelMove(B board, M move) {
        timer.timeCheck();
        transformer.cancelMove(board, move);
      }
    };

    return delegate.bestMove(rootBoard, moveGenerator, transformerWithTimeout, maxDepth);
  }
}
