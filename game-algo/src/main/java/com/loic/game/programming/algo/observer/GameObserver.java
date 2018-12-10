package com.loic.game.programming.algo.observer;

import com.loic.game.programming.api.GameBoard;

public interface GameObserver<B extends GameBoard<M>, M> {
  void onMoveApplied(M move, B board);

  void currentBestMove(M bestMove);
}
