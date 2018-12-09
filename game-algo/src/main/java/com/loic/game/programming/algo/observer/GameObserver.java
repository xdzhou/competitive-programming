package com.loic.game.programming.algo.observer;

import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;

public interface GameObserver<B extends GameBoard, M extends GameMove<B>> {
  void onMoveApplied(B oldBoard, M move, B newBoard);

  void currentBestMove(M bestMove);
}
