package com.loic.game.programming.algo.astar;

import com.loic.game.programming.algo.EvaluationConverter;
import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;
import com.loic.game.programming.api.Transformer;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;

public class AStar implements BestMoveResolver {
  private final EvaluationConverter converter;

  public AStar(EvaluationConverter converter) {
    this.converter = converter;
  }

  @Override
  public <B extends GameBoard, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, Transformer<B, M> transformer, int maxDepth) {
    PriorityQueue<AStarNode<B>> priorityQueue = new PriorityQueue<>(Comparator.comparing(node -> -node.value));

    AStarNode<B> rootNode = new AStarNode<>(null, rootBoard.copy(), converter.convert(rootBoard.evaluate(0), 0, rootBoard.currentPlayer()));
    priorityQueue.add(rootNode);

    while (!priorityQueue.isEmpty()) {
      AStarNode<B> node = priorityQueue.poll();
      B board = node.board;
      for (M move : moveGenerator.generate(board)) {
        B newBoard = board.copy();
        transformer.applyMove(newBoard, move);
        AStarNode<B> newNode = createNode(node, newBoard);
        priorityQueue.add(newNode);
      }
    }
    return null;
  }

  private <B extends GameBoard> AStarNode<B> createNode(AStarNode<B> parent, B newBoard) {
    int depth = 0;
    AStarNode<B> curNode = parent;
    while (curNode != null) {
      depth++;
      curNode = curNode.parent;
    }
    return new AStarNode<>(parent, newBoard, converter.convert(newBoard.evaluate(depth), depth, newBoard.currentPlayer()));
  }


  private static class AStarNode<B extends GameBoard> {
    private final AStarNode<B> parent;
    private final B board;
    private final double value;

    private AStarNode(AStarNode<B> parent, B board, double value) {
      this.parent = parent;
      this.board = board;
      this.value = value;
    }
  }
}
