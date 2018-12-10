package com.loic.game.programming.algo.mcts;

import com.loic.game.programming.api.GameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class TreeNode<B extends GameBoard<M>, M> {

  final M moveApplied;
  final TreeNode<B, M> parent;
  final List<TreeNode<B, M>> children;

  final Set<M> untriedMoves;
  final int playerJustMoved;

  private double win;
  private int visits;

  TreeNode(M moveApplied, TreeNode<B, M> parent, Set<M> untriedMoves, int playerJustMoved) {
    this.moveApplied = moveApplied;
    this.parent = parent;
    this.children = new ArrayList<>();
    this.untriedMoves = untriedMoves;
    this.playerJustMoved = playerJustMoved;
  }

  /**
   * Update this node - one additional visit and result additional wins.
   * result must be from the viewpoint of playerJustmoved.
   * result must range from 0.0 to 1.0
   */
  void update(double result) {
    visits++;
    win += result;
  }

  /**
   * add a new expand child
   *
   * @param move            the move applied to generate this child
   * @param untriedMoves    the moves not applied for this child
   * @param playerJustMoved the player id of this child
   */
  TreeNode<B, M> addChild(M move, Set<M> untriedMoves, int playerJustMoved) {
    TreeNode<B, M> child = new TreeNode<>(move, this, untriedMoves, playerJustMoved);
    untriedMoves.remove(move);
    children.add(child);
    return child;
  }

  int visitCount() {
    return visits;
  }

  double win() {
    return win;
  }
}
