package com.loic.game.programming.algo.minimax;

import java.util.List;
import java.util.Objects;

import com.loic.game.programming.algo.common.GameLogic;
import com.loic.game.programming.algo.observer.GameDisableObserver;
import com.loic.game.programming.algo.observer.GameObserver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;

public class MiniMax<B extends GameBoard, M extends GameMove<B>> {
  private GameObserver<B, M> observer = GameDisableObserver.INSTANCE;
  private final GameLogic<B, M> gameLogic;

  public MiniMax(GameLogic<B, M> gameLogic) {
    this.gameLogic = Objects.requireNonNull(gameLogic);
  }

  public M bestMove(B board, int depth) {
    EvaluatedMove bestMove = null;
    for (int i = 1; i <= depth; i++) {
      bestMove = alphaBeta(board, i, Double.NEGATIVE_INFINITY, Double.MAX_VALUE, true);
      observer.currentBestMove(bestMove.move);
    }
    return bestMove.move;
  }

  private EvaluatedMove alphaBeta(B board, int depth, double alpha, double beta, boolean maxPlayer) {
    List<M> moves;
    if (depth == 0 || (moves = gameLogic.moveGenerator().generate(board)).isEmpty()) {
      return new EvaluatedMove(null, gameLogic.heuristicEvaluator().evaluate(board));
    }

    if (maxPlayer) {
      double best = Double.NEGATIVE_INFINITY;
      M bestMove = null;
      for (M move : moves) {
        B newBoard = move.apply(board);
        observer.onMoveApplied(board, move, newBoard);
        EvaluatedMove childMove = alphaBeta(newBoard, depth - 1, alpha, beta, false);
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
        EvaluatedMove childMove = alphaBeta(newBoard, depth - 1, alpha, beta, true);
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
