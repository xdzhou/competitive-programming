package com.loic.game.programming.algo;

import java.util.Arrays;
import java.util.List;

public class Node {
  final int[] values;
  final List<Node> children;

  public Node(int[] values, List<Node> children) {
    this.values = values;
    this.children = children;
  }

  /*
   * data using from this picture : https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning#/media/File:AB_pruning.svg
   */
  public static Node testCase() {
    return create(0, 0,
      create(3, 0,
        create(3, -2,
          create(3, -2,
            create(3, -2),
            create(3, -3)),
          create(2, -2,
            create(2, -5),
            create(2, -2),
            create(2, -3))),
        create(3, 0,
          create(3, 0,
            create(3, 0)))),
      create(6, 0,
        create(6, 0,
          create(6, 0), // remove sub level "6"
          create(6, 0,
            create(6, 0),
            create(6, -3))),
        create(6, -1,
          create(6, -1,
            create(6, -1)))),
      create(5, 0,
        create(5, 0),
        create(5, -3,
          create(5, -3,
            create(5, -4),
            create(5, -3)),
          create(5, -1,
            create(5, -1)))));
  }

  private static Node create(int value1, int value2, Node... children) {
    return new Node(new int[]{value1, value2}, Arrays.asList(children));
  }
}
