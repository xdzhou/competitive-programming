package com.loic.game.programming.algo.observer;

import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;

public enum GameDisableObserver implements GameObserver {
  INSTANCE;

  @Override
  public void onMoveApplied(GameBoard oldBoard, GameMove move, GameBoard newBoard) {
    //nothing to do
  }
}
