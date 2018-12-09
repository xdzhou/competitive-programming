package com.loic.game.programming.algo.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneticAlgoComparatorEvaluatorTest {

  @Test
  void notNullConstructor() {
    Assertions.assertThrows(NullPointerException.class, () -> new GeneticAlgo.ComparatorEvaluator<>(null));
  }

  @Test
  void testEvaluate() {
    GeneticAlgo.ComparatorEvaluator<Double> evaluator = new GeneticAlgo.ComparatorEvaluator<>(Comparator.comparingDouble(d -> d));

    List<Double> values = new ArrayList<>();
    values.add(1d);
    values.add(4d);
    values.add(2d);
    values.add(3d);


    List<Double> collect = evaluator.apply(values)
      .entrySet()
      .stream()
      .sorted(Comparator.comparingDouble(Map.Entry::getValue))
      .map(Map.Entry::getKey)
      .collect(Collectors.toList());

    Collections.sort(values);
    Assertions.assertEquals(values, collect);
  }
}