/*
 * Copyright (c) 2010-2026, sikuli.org, sikulix.com, oculix-org - MIT license
 */
package org.sikuli.ide.ui.recorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Non-modal floating dialog for the OculiX Modern Recorder.
 * Stays on top while the user interacts with the target application.
 */
public class RecorderAssistant extends JDialog {

  public RecorderAssistant(Frame parent) {
    super(parent, "OculiX Modern Recorder (beta)", false);
    setSize(360, 200);
    setLocationRelativeTo(parent);
    setAlwaysOnTop(true);
    setType(Window.Type.UTILITY);
    setResizable(false);
    buildUI();
  }

  private void buildUI() {
    JPanel content = new JPanel(new MigLayout("wrap 1, insets 20, gap 10", "[grow, fill]", ""));
    content.setBackground(UIManager.getColor("Panel.background"));

    JLabel title = new JLabel("Modern Recorder (beta)");
    title.setFont(UIManager.getFont("h2.font"));
    title.setHorizontalAlignment(SwingConstants.CENTER);
    content.add(title, "align center");

    JLabel subtitle = new JLabel("Coming soon \u2014 recorder skeleton loaded.");
    subtitle.setFont(UIManager.getFont("defaultFont"));
    subtitle.setForeground(UIManager.getColor("Label.disabledForeground"));
    subtitle.setHorizontalAlignment(SwingConstants.CENTER);
    content.add(subtitle, "align center");

    JButton closeBtn = new JButton("Close");
    closeBtn.addActionListener(e -> dispose());
    content.add(closeBtn, "align center, gaptop 10");

    setContentPane(content);
  }
}
