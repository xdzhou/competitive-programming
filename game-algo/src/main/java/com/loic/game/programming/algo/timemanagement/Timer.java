package com.loic.game.programming.algo.timemanagement;

class Timer {
  private final long timeout;

  Timer(double durationInMilliseconds) {
    timeout = System.nanoTime() + (long) (durationInMilliseconds * 1000000);
  }

  void timeCheck() throws TimeoutException {
    if (System.nanoTime() > timeout) {
      throw new TimeoutException();
    }
  }
}
