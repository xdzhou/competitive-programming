package com.loic.game.programming.algo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.loic.game.programming.api.MoveGenerator;

public class TestMoveGenerator implements MoveGenerator<TestBoard, TestMove> {
  @Override
  public List<TestMove> generate(TestBoard board) {
    return IntStream.range(0, board.node.children.size())
      .mapToObj(TestMove::new)
      .collect(Collectors.toList());
  }
}
