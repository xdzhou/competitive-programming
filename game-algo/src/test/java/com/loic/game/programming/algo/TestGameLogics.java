package com.loic.game.programming.algo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.loic.game.programming.api.GameMoveGenerator;
import com.loic.game.programming.api.HeuristicEvaluator;

public class TestGameLogics implements GameMoveGenerator<TestBoard, TestMove>, HeuristicEvaluator<TestBoard> {
  @Override
  public List<TestMove> generate(TestBoard board) {
    return IntStream.range(0, board.node.children.size())
      .mapToObj(TestMove::new)
      .collect(Collectors.toList());
  }

  @Override
  public double evaluate(TestBoard board) {
    return board.node.value;
  }
}
