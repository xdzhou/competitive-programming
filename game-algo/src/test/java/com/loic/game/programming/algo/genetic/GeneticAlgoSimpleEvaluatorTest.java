package com.loic.game.programming.algo.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneticAlgoSimpleEvaluatorTest {

  @Test
  void notNullConstructor() {
    Assertions.assertThrows(NullPointerException.class, () -> new GeneticAlgo.SimpleEvaluator<>(null));
  }

  @Test
  void testEvaluate() {
    GeneticAlgo.SimpleEvaluator<Double> evaluator = new GeneticAlgo.SimpleEvaluator<>(Function.identity());

    List<Double> values = new ArrayList<>();
    values.add(1d);
    values.add(4d);
    values.add(2d);
    values.add(3d);


    evaluator.apply(values)
      .forEach((k, v) -> {
        Assertions.assertEquals(k, v);
        Assertions.assertTrue(values.contains(k));
      });
  }
}