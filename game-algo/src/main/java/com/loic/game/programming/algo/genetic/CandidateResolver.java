package com.loic.game.programming.algo.genetic;

import java.util.Random;

public interface CandidateResolver<Gene> {
  Gene generateRandomly(Random random);

  Gene merge(Gene gene1, Gene gene2, Random random);

  Gene mutate(Gene gene, Random random);
}
