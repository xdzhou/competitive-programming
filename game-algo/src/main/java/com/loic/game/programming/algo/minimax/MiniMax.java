package com.loic.game.programming.algo.minimax;

import java.util.List;
import java.util.Objects;

import com.loic.game.programming.algo.observer.GameDisableObserver;
import com.loic.game.programming.algo.observer.GameObserver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;
import com.loic.game.programming.api.MoveGenerator;

public class MiniMax<B extends GameBoard, M extends GameMove<B>> {
  private final MoveGenerator<B, M> moveGenerator;
  private GameObserver<B, M> observer = GameDisableObserver.INSTANCE;

  public MiniMax(MoveGenerator<B, M> moveGenerator) {
    this.moveGenerator = moveGenerator;
  }

  public M bestMove(B board, int depth) {
    if (board.evaluate(0).length != 2) {
      throw new IllegalStateException("MiniMax algo can only apply to two players game");
    }
    EvaluatedMove bestMove = null;
    for (int i = 1; i <= depth; i++) {
      bestMove = alphaBeta(board, i, i, Double.NEGATIVE_INFINITY, Double.MAX_VALUE, true);
      observer.currentBestMove(bestMove.move);
    }
    return bestMove.move;
  }

  private EvaluatedMove alphaBeta(B board, int remainDepth, int maxDepth, double alpha, double beta, boolean maxPlayer) {
    List<M> moves;
    if (remainDepth == 0 || (moves = moveGenerator.generate(board)).isEmpty()) {
      double[] values = board.evaluate(maxDepth - remainDepth);
      return new EvaluatedMove(null, values[0] - values[1]);
    }

    if (maxPlayer) {
      double best = Double.NEGATIVE_INFINITY;
      M bestMove = null;
      for (M move : moves) {
        B newBoard = move.apply(board);
        observer.onMoveApplied(board, move, newBoard);
        EvaluatedMove childMove = alphaBeta(newBoard, remainDepth - 1, maxDepth, alpha, beta, false);
        if (childMove.value > best) {
          best = childMove.value;
          bestMove = move;
        }
        alpha = Math.max(alpha, best);
        if (beta <= alpha) {
          break;
        }
      }
      return new EvaluatedMove(bestMove, best);
    } else {
      double best = Double.POSITIVE_INFINITY;
      M bestMove = null;
      for (M move : moves) {
        B newBoard = move.apply(board);
        observer.onMoveApplied(board, move, newBoard);
        EvaluatedMove childMove = alphaBeta(newBoard, remainDepth - 1, maxDepth, alpha, beta, true);
        if (childMove.value < best) {
          best = childMove.value;
          bestMove = move;
        }
        beta = Math.min(beta, best);
        if (beta <= alpha) {
          break;
        }
      }
      return new EvaluatedMove(bestMove, best);
    }
  }

  public void setGameObserver(GameObserver<B, M> observer) {
    this.observer = Objects.requireNonNull(observer);
  }

  private class EvaluatedMove {
    private final M move;
    private final double value;

    private EvaluatedMove(M move, double value) {
      this.move = move;
      this.value = value;
    }
  }
}
