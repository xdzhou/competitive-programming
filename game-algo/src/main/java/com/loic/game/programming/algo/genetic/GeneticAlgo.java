package com.loic.game.programming.algo.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;

public class GeneticAlgo<Gene> {

  private final Random random = new Random(new Date().getTime());
  private final List<Gene> populations = new ArrayList<>();
  private final CandidateResolver<Gene> resolver;
  private final Function<List<Gene>, Map<Gene, Double>> evaluator;

  GeneticAlgo(CandidateResolver<Gene> resolver, Function<Gene, Double> heuristicEvaluator) {
    this.resolver = Objects.requireNonNull(resolver);
    this.evaluator = new SimpleEvaluator<>(heuristicEvaluator);
  }

  GeneticAlgo(CandidateResolver<Gene> resolver, Comparator<Gene> geneComparator) {
    this.resolver = Objects.requireNonNull(resolver);
    this.evaluator = new ComparatorEvaluator<>(geneComparator);
  }

  Gene iterate(int iterationCount, int population) {
    return iterate(iterationCount, population, population / 4, population / 4, population / 9);
  }

  Gene iterate(int iterationCount, int population, int selectionNumber, int mergedNumber, int mutatedNumber) {
    for (int i = 0; i < iterationCount; i++) {
      oneIteration(population, selectionNumber, mergedNumber, mutatedNumber);
    }
    return populations.get(0);
  }

  Gene cueentBest() {
    return populations.size() > 0 ? populations.get(0) : null;
  }

  private void oneIteration(int count, int selectionNumber, int mergedNumber, int mutatedNumber) {

    while (populations.size() < count) {
      populations.add(resolver.generateRandomly(random));
    }
    Collections.shuffle(populations, random);
    merge(mergedNumber);

    Collections.shuffle(populations, random);
    mutate(mutatedNumber);

    removeDuplicates();
    Map<Gene, Double> result = evaluator.apply(populations);

    List<Gene> nextGenerations = populations.stream().sorted((g1, g2) -> Double.compare(result.get(g2), result.get(g1))).limit(selectionNumber).collect(Collectors.toList());
    populations.clear();
    populations.addAll(nextGenerations);
  }

  private void merge(int mergedNumber) {
    for (int i = 0; i < mergedNumber; i++) {
      final int firstIndex = (2 * i) % populations.size();
      final int secondIndex = (2 * i + 1) % populations.size();
      populations.add(resolver.merge(populations.get(firstIndex), populations.get(secondIndex), random));
    }
  }

  private void mutate(int mutatedNumber) {
    for (int i = 0; i < mutatedNumber; i++) {
      final int index = i % populations.size();
      populations.add(resolver.mutate(populations.get(index), random));
    }
  }

  private void removeDuplicates() {
    final Set<Gene> set = new HashSet<>(populations);
    populations.clear();
    populations.addAll(set);
  }


  /**
   * A simple Gene evaluator, just call the heuristic function to computer fitness value
   */
  static final class SimpleEvaluator<Gene> implements Function<List<Gene>, Map<Gene, Double>> {
    private final Map<Gene, Double> cache;
    private final Function<Gene, Double> evaluator;

    SimpleEvaluator(Function<Gene, Double> evaluator) {
      this.evaluator = Objects.requireNonNull(evaluator);
      cache = new HashMap<>();
    }

    @Override
    public Map<Gene, Double> apply(List<Gene> genes) {
      genes.forEach(gene -> cache.putIfAbsent(gene, evaluator.apply(gene)));
      return cache;
    }
  }


  /**
   * compare every two genes to computer fitness value
   */
  static final class ComparatorEvaluator<Gene> implements Function<List<Gene>, Map<Gene, Double>> {
    private final Comparator<Gene> comparator;

    ComparatorEvaluator(Comparator<Gene> comparator) {
      this.comparator = Objects.requireNonNull(comparator);
    }

    @Override
    public Map<Gene, Double> apply(List<Gene> genes) {
      int[][] result = new int[genes.size()][genes.size()];

      for (int i = 0; i < result.length; i++) {
        for (int j = i + 1; j < result.length; j++) {
          int delta = comparator.compare(genes.get(i), genes.get(j));
          //2 points for a win, 1 point for draw scoring function
          result[i][j] += (delta == 0) ? 1 : (delta > 0 ? 2 : 0);
          result[j][i] += (2 - result[i][j]);
        }
      }
      Map<Gene, Double> map = new HashMap<>(genes.size());

      IntStream.range(0, genes.size()).forEach(i -> map.put(genes.get(i), (double) stream(result[i]).sum()));
      return map;
    }
  }
}
