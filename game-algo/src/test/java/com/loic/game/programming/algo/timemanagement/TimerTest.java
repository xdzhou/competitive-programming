package com.loic.game.programming.algo.timemanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {

  @Test
  void timeCheck() throws InterruptedException {
    Timer timer = new Timer(100);
    timer.timeCheck();

    Thread.sleep(120);
    Assertions.assertThrows(TimeoutException.class, timer::timeCheck);
  }
}