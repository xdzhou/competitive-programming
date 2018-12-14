package com.loic.game.programming.algo.astar;

import com.loic.game.programming.algo.EvaluationConverter;
import com.loic.game.programming.algo.timemanagement.TimeoutException;
import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class AStar implements BestMoveResolver {
  private final EvaluationConverter converter;

  public AStar(EvaluationConverter converter) {
    this.converter = converter;
  }

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    PriorityQueue<AStarNode<B, M>> priorityQueue = new PriorityQueue<>(Comparator.comparing(node -> -node.value));

    AStarNode<B, M> rootNode = new AStarNode<>(null, rootBoard.copy(), 0, converter.convert(rootBoard.evaluate(0), 0, rootBoard.currentPlayer()));
    priorityQueue.add(rootNode);

    List<CandidateMetaData<M>> candidates = new ArrayList<>();

    try {
      while (!priorityQueue.isEmpty()) {
        AStarNode<B, M> node = priorityQueue.poll();
        if (node.depth <= maxDepth) {
          int depth = node.depth + 1;
          for (M move : moveGenerator.generate(node.board)) {
            B newBoard = node.board.copy();
            transformer.applyMove(newBoard, move);
            CandidateMetaData<M> metaData;
            if (node.metaData == null) {
              metaData = new CandidateMetaData<>(move);
              candidates.add(metaData);
            } else {
              metaData = node.metaData;
            }
            AStarNode<B, M> newNode = new AStarNode<>(metaData, newBoard, depth, converter.convert(newBoard.evaluate(depth), depth, newBoard.currentPlayer()));
            priorityQueue.add(newNode);
          }
        }
        node.updateValue();
      }
    } catch (TimeoutException e) {
      // nothing to worry, just time out
      // return current best move
    }

    Optional<CandidateMetaData<M>> best = candidates.stream().max(Comparator.comparingDouble(candidate -> candidate.value));
    return best.map(mCandidateMetaData -> mCandidateMetaData.move).orElse(null);
  }


  private static class CandidateMetaData<M> {
    private final M move;
    private double value = Double.NEGATIVE_INFINITY;

    private CandidateMetaData(M move) {
      this.move = move;
    }

    private void updateValue(double anotherValue) {
      value = Math.max(value, anotherValue);
    }
  }

  private static class AStarNode<B extends GameBoard, M> {
    private final CandidateMetaData<M> metaData;
    private final B board;
    private final int depth;
    private final double value;

    private AStarNode(CandidateMetaData<M> metaData, B board, int depth, double value) {
      this.metaData = metaData;
      this.board = board;
      this.depth = depth;
      this.value = value;
    }

    private void updateValue() {
      if (metaData != null) {
        metaData.updateValue(value);
      }
    }
  }
}
