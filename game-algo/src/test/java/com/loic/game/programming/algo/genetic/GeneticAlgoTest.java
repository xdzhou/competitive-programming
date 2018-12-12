package com.loic.game.programming.algo.genetic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

class GeneticAlgoTest {
  private static final MockGene EXPECTED = new MockGene(2, 7, 9);

  @Test
  void testHeuristicEvaluator() {
    GeneticAlgo<MockGene> geneticAlgo = new GeneticAlgo<MockGene>(new MockResolver(), gene -> {
      double value = 0d;
      for (int i = 0; i < 3; i++) {
        if (gene.dates[i] == EXPECTED.dates[i]) {
          value += 10;
        }
      }
      return value;
    });

    MockGene best = geneticAlgo.iterate(50, 25);
    Assertions.assertEquals(EXPECTED, best);
  }

  @Test
  void testComparatorEvaluator() {
    GeneticAlgo<MockGene> geneticAlgo = new GeneticAlgo<MockGene>(new MockResolver(), Comparator.comparingInt(EXPECTED::distance));

    MockGene best = geneticAlgo.iterate(50, 25);
    Assertions.assertEquals(EXPECTED, best);
  }


  private static class MockResolver implements CandidateResolver<MockGene> {

    @Override
    public MockGene generateRandomly(Random random) {
      return new MockGene(random.nextInt(10), random.nextInt(10), random.nextInt(10));
    }

    @Override
    public MockGene merge(MockGene gene1, MockGene gene2, Random random) {
      int[] dates = new int[3];
      for (int i = 0; i < 3; i++) {
        dates[i] = random.nextBoolean() ? gene1.dates[i] : gene2.dates[i];
      }
      return new MockGene(dates);
    }

    @Override
    public MockGene mutate(MockGene mockGene, Random random) {
      int[] dates = Arrays.copyOf(mockGene.dates, 3);
      dates[random.nextInt(3)] = random.nextInt(10);
      return new MockGene(dates);
    }
  }


  private static class MockGene {
    private final int[] dates;

    private MockGene(int... dates) {
      this.dates = dates;
    }

    private int distance(MockGene other) {
      int delta = 0;
      for (int i = 0; i < 3; i++) {
        delta += Math.abs(dates[i] - other.dates[i]);
      }
      return -delta;
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(dates);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      MockGene mockGene = (MockGene) o;
      return Arrays.equals(dates, mockGene.dates);
    }

    @Override
    public String toString() {
      return "MockGene{" + "dates=" + Arrays.toString(dates) + '}';
    }
  }

}