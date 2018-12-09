package com.loic.game.programming.algo;

import java.util.Arrays;
import java.util.List;

public class Node {
  final int value;
  final List<Node> children;

  public Node(int value, List<Node> children) {
    this.value = value;
    this.children = children;
  }

  /*
   * data using from this picture : https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning#/media/File:AB_pruning.svg
   */
  public static Node testCase() {
    return create(6,
      create(3,
        create(5,
          create(5,
            create(5),
            create(6)),
          create(4,
            create(7),
            create(4),
            create(5))),
        create(3,
          create(3,
            create(3)))),
      create(6,
        create(6,
          create(6), // remove sub level "6"
          create(6,
            create(6),
            create(9))),
        create(7,
          create(7,
            create(7)))),
      create(5,
        create(5),
        create(8,
          create(8,
            create(9),
            create(8)),
          create(6,
            create(6)))));
  }

  private static Node create(int value, Node... children) {
    return new Node(value, Arrays.asList(children));
  }
}
