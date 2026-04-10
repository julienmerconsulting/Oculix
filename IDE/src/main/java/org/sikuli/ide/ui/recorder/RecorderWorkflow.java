/*
 * Copyright (c) 2010-2026, sikuli.org, sikulix.com, oculix-org - MIT license
 */
package org.sikuli.ide.ui.recorder;

/**
 * State machine driving the Modern Recorder step-by-step workflow.
 * Phase 1: enum declaration only. Logic added in Phase 2.
 */
public class RecorderWorkflow {

  public enum RecorderState {
    IDLE,
    CAPTURING_REGION,
    WAITING_OCR,
    WAITING_KEY_COMBO,
    WAITING_PATTERN_VALIDATION,
    WAITING_USER_INPUT
  }

  private RecorderState state = RecorderState.IDLE;

  public RecorderState getState() {
    return state;
  }
}
