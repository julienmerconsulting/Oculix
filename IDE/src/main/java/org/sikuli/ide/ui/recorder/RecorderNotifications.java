/*
 * Copyright (c) 2010-2026, sikuli.org, sikulix.com, oculix-org - MIT license
 */
package org.sikuli.ide.ui.recorder;

import raven.toast.Notifications;

import javax.swing.*;

/**
 * Thin wrapper around DJ-Raven toast notifications for the Recorder.
 * Phase 1: skeleton with 4 methods. Initialized with the IDE parent frame.
 */
public class RecorderNotifications {

  private static boolean initialized = false;

  public static void init(JFrame parent) {
    if (!initialized) {
      Notifications.getInstance().setJFrame(parent);
      initialized = true;
    }
  }

  public static void info(String message) {
    Notifications.getInstance().show(Notifications.Type.INFO, message);
  }

  public static void warning(String message) {
    Notifications.getInstance().show(Notifications.Type.WARNING, message);
  }

  public static void error(String message) {
    Notifications.getInstance().show(Notifications.Type.ERROR, message);
  }

  public static void success(String message) {
    Notifications.getInstance().show(Notifications.Type.SUCCESS, message);
  }
}
