package com.loic.game.programming.algo.bruteforce;

import com.loic.game.programming.algo.observer.GameDisableObserver;
import com.loic.game.programming.algo.observer.GameObserver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;

import java.util.Objects;
import java.util.Set;

public class BruteForce<B extends GameBoard<M>, M> {
  private final MoveGenerator<B, M> moveGenerator;
  private GameObserver<B, M> observer = GameDisableObserver.INSTANCE;

  public BruteForce(MoveGenerator<B, M> moveGenerator) {
    this.moveGenerator = moveGenerator;
  }

  public M bestMove(B rootBoard, int depth) {
    EvaluatedMove bestMove = null;
    for (int i = 1; i <= depth; i++) {
      bestMove = bestEvaluatedMove(rootBoard, i, i);
      observer.currentBestMove(bestMove.move);
    }
    return bestMove.move;
  }

  private EvaluatedMove bestEvaluatedMove(B board, int remainDepth, int maxDepth) {
    Set<M> moves;
    if (remainDepth == 0 || (moves = moveGenerator.generate(board)).isEmpty()) {
      return new EvaluatedMove(null, board.evaluate(maxDepth - remainDepth));
    }
    M best = null;
    double[] bestValues = null;
    for (M move : moves) {
      board.applyMove(move);
      observer.onMoveApplied(move, board);

      EvaluatedMove child = bestEvaluatedMove(board, remainDepth - 1, maxDepth);
      if (best == null || bestValues[board.currentPlayer()] < child.values[board.currentPlayer()]) {
        best = move;
        bestValues = child.values;
      }
      board.cancelMove(move);
    }
    return new EvaluatedMove(best, bestValues);
  }

  public void setGameObserver(GameObserver<B, M> observer) {
    this.observer = Objects.requireNonNull(observer);
  }

  private class EvaluatedMove {
    private final M move;
    private final double[] values;

    private EvaluatedMove(M move, double[] values) {
      this.move = move;
      this.values = values;
    }
  }
}
