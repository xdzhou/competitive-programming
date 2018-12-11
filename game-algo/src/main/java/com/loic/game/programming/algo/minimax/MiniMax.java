package com.loic.game.programming.algo.minimax;

import java.util.Set;

import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

public class MiniMax implements BestMoveResolver {

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    if (rootBoard.evaluate(0).length != 2) {
      throw new IllegalStateException("MiniMax algo can only apply to two players game");
    }
    EvaluatedMove<M> bestMove = null;
    for (int i = 1; i <= maxDepth; i++) {
      bestMove = alphaBeta(rootBoard, moveGenerator, transformer, i, i, Double.NEGATIVE_INFINITY, Double.MAX_VALUE);
    }
    return bestMove.move;
  }

  private <B extends GameBoard, M> EvaluatedMove<M> alphaBeta(B board, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int remainDepth, int maxDepth, double alpha, double beta) {
    Set<M> moves;
    if (remainDepth == 0 || (moves = moveGenerator.generate(board)).isEmpty()) {
      double[] values = board.evaluate(maxDepth - remainDepth);
      return new EvaluatedMove<>(null, values[0] - values[1]);
    }

    if (board.currentPlayer() == 1) { // next player is maxPlayer (player 0), so current player is 1
      double best = Double.NEGATIVE_INFINITY;
      M bestMove = null;
      for (M move : moves) {
        transformer.applyMove(board, move);
        EvaluatedMove childMove = alphaBeta(board, moveGenerator, transformer, remainDepth - 1, maxDepth, alpha, beta);
        transformer.cancelMove(board, move);
        if (childMove.value > best) {
          best = childMove.value;
          bestMove = move;
        }
        alpha = Math.max(alpha, best);
        if (beta <= alpha) {
          break;
        }
      }
      return new EvaluatedMove<>(bestMove, best);
    } else {
      double best = Double.POSITIVE_INFINITY;
      M bestMove = null;
      for (M move : moves) {
        transformer.applyMove(board, move);
        EvaluatedMove childMove = alphaBeta(board, moveGenerator, transformer, remainDepth - 1, maxDepth, alpha, beta);
        transformer.cancelMove(board, move);
        if (childMove.value < best) {
          best = childMove.value;
          bestMove = move;
        }
        beta = Math.min(beta, best);
        if (beta <= alpha) {
          break;
        }
      }
      return new EvaluatedMove<>(bestMove, best);
    }
  }

  private static class EvaluatedMove<M> {
    private final M move;
    private final double value;

    private EvaluatedMove(M move, double value) {
      this.move = move;
      this.value = value;
    }
  }
}
