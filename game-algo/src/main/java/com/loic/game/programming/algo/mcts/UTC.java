package com.loic.game.programming.algo.mcts;

import com.loic.game.programming.api.BestMoveResolver;
import com.loic.game.programming.api.EvaluationConverter;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.MoveGenerator;

import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class UTC implements BestMoveResolver {
  private static final Random RANDOM = new Random();
  private static final double EPSILON = 1e-6;

  private final int maxIterations;

  public UTC(int maxIterations) {
    if (maxIterations < 1) {
      throw new IllegalArgumentException("iteration count must be positive");
    }
    this.maxIterations = maxIterations;
  }


  @Override
  public <B extends GameBoard<M>, M> M bestMove(B rootBoard, MoveGenerator<B, M> moveGenerator, EvaluationConverter converter, int maxDepth) {
    TreeNode<B, M> rootNode = new TreeNode<>(null, null, moveGenerator.generate(rootBoard), 0);

    for (int i = 0; i < maxIterations; i++) {
      TreeNode<B, M> curNode = rootNode;
      B curBoard = (B) rootBoard.copy();
      int curDepth = 0;

      //selection
      while (curNode.untriedMoves.isEmpty() && !curNode.children.isEmpty()) {
        //node is fully expanded and non-terminal
        curNode = UCTSelectChild(curNode);
        curBoard.applyMove(curNode.moveApplied);
        curDepth++;
      }

      //expansion
      if (!curNode.untriedMoves.isEmpty()) {
        M nextMove = randomChoice(curNode.untriedMoves);
        curBoard.applyMove(nextMove);
        curDepth++;
        curNode = curNode.addChild(nextMove, moveGenerator.generate(curBoard), curBoard.currentPlayer());
      }

      //Rollout - this can often be made orders of magnitude quicker using a state.GetRandomMove() function
      Set<M> moves = null;
      while (curDepth < maxDepth && !(moves = moveGenerator.generate(curBoard)).isEmpty()) {
        M nextMove = randomChoice(moves);
        curBoard.applyMove(nextMove);
        curDepth++;
      }

      //back propagate
      while (curNode != null) {
        curNode.update(converter.convert(curBoard.evaluate(curDepth), curNode.playerJustMoved));
        curNode = curNode.parent;
      }
    }

    Optional<M> bestMove = rootNode.children.stream().max(Comparator.comparingInt(TreeNode::visitCount)).map(node -> node.moveApplied);
    return bestMove.orElse(null);
  }

  /**
   * Use the UCB1 formula to select a child node. Often a constant UCTK is applied so we have
   * lambda c: c.wins/c.visits + UCTK * sqrt(2*log(self.visits)/c.visits to vary the amount of
   * exploration versus exploitation.
   */
  private <B extends GameBoard<M>, M> TreeNode<B, M> UCTSelectChild(TreeNode<B, M> from) {
    TreeNode<B, M> selected = null;
    double bestValue = Double.MIN_VALUE;
    for (TreeNode<B, M> c : from.children) {
      double uctValue = c.win() / (c.visitCount() + EPSILON) + Math.sqrt(2 * Math.log(from.visitCount()) / (c.visitCount() + EPSILON)) + RANDOM.nextDouble() * EPSILON;
      // small random number to break ties randomly in unexpanded nodes
      if (uctValue > bestValue) {
        selected = c;
        bestValue = uctValue;
      }
    }
    return selected;
  }

  private <M> M randomChoice(Set<M> moves) {
    //TODO
    return moves.iterator().next();
  }

}
