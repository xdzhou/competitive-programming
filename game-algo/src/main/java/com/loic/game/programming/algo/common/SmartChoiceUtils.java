package com.loic.game.programming.algo.common;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class SmartChoiceUtils {
  private SmartChoiceUtils() {
    //utils
  }

  public static <I> I randomChoice(List<I> list, Function<I, Double> valueFun, Random random) {
    if(list.isEmpty()) {
      throw new IllegalArgumentException("Nothing to choose");
    }
    I best = null;
    return  best;
  }
}
