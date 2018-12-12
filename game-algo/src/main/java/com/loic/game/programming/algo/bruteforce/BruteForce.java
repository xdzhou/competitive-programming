package com.loic.game.programming.algo.bruteforce;

import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

import java.util.Set;

public class BruteForce implements BestMoveResolver {

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    EvaluatedMove<M> bestMove = null;
    for (int i = 1; i <= maxDepth; i++) {
      bestMove = bestEvaluatedMove(rootBoard, moveGenerator, transformer, i, i);
    }
    return bestMove.move;
  }

  private <B extends GameBoard, M> EvaluatedMove<M> bestEvaluatedMove(B board, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int remainDepth, int maxDepth) {
    Set<M> moves;
    if (remainDepth == 0 || (moves = moveGenerator.generate(board)).isEmpty()) {
      return new EvaluatedMove<>(null, board.evaluate(maxDepth - remainDepth));
    }
    M best = null;
    double[] bestValues = null;
    for (M move : moves) {
      transformer.applyMove(board, move);

      EvaluatedMove child = bestEvaluatedMove(board, moveGenerator, transformer, remainDepth - 1, maxDepth);
      if (best == null || bestValues[board.currentPlayer()] < child.values[board.currentPlayer()]) {
        best = move;
        bestValues = child.values;
      }
      transformer.cancelMove(board, move);
    }
    return new EvaluatedMove<>(best, bestValues);
  }

  private static class EvaluatedMove<M> {
    private final M move;
    private final double[] values;

    private EvaluatedMove(M move, double[] values) {
      this.move = move;
      this.values = values;
    }
  }
}
