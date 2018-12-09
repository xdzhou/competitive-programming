package com.loic.game.programming.algo.observer;

import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;

public class GameLogObserver<B extends GameBoard, M extends GameMove<B>> implements GameObserver<B, M> {
  private final GameObserver<B, M> delegate;

  public GameLogObserver(GameObserver<B, M> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void onMoveApplied(B oldBoard, M move, B newBoard) {
    System.out.println("");
    delegate.onMoveApplied(oldBoard, move, newBoard);
  }

  @Override
  public void currentBestMove(M bestMove) {

  }
}
