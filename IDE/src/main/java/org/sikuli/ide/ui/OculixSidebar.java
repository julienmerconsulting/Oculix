/*
 * Copyright (c) 2010-2026, sikuli.org, sikulix.com, oculix-org - MIT license
 */
package org.sikuli.ide.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.sikuli.support.ide.SikuliIDEI18N._I;

/**
 * Vertical sidebar replacing the classic JMenuBar + JToolBar.
 * Organized in logical groups: Run, Scripts, Tools, Help.
 */
public class OculixSidebar extends JPanel {

  private boolean collapsed = false;
  private int collapsedWidth = 50;

  // Action buttons
  private SidebarItem btnRun;
  private SidebarItem btnRunSlow;
  private SidebarItem btnCapture;
  private SidebarItem btnRecord;

  // Navigation items
  private SidebarItem navFile;
  private SidebarItem navEdit;
  private SidebarItem navTools;
  private SidebarItem navHelp;

  // Theme toggle
  private SidebarItem btnTheme;
  private boolean isDark = true;

  // Version label
  private JLabel versionLabel;

  // Panels for layout sections
  private JPanel mainPanel;
  private JPanel footerPanel;

  public OculixSidebar() {
    setLayout(new BorderLayout());
    setMinimumSize(new Dimension(collapsedWidth, 0));
    putClientProperty(FlatClientProperties.STYLE, "background: darken(@background, 3%)");

    mainPanel = new JPanel(new MigLayout("wrap 1, insets 8, gap 0", "[fill, grow]", ""));
    mainPanel.setOpaque(false);

    footerPanel = new JPanel(new MigLayout("wrap 1, insets 8, gap 2", "[fill, grow]", ""));
    footerPanel.setOpaque(false);

    add(mainPanel, BorderLayout.NORTH);
    add(footerPanel, BorderLayout.SOUTH);

    addResizeHandle();
  }

  private JLabel addSectionHeader(String text) {
    JLabel header = new JLabel(text);
    header.setFont(UIManager.getFont("small.font"));
    header.setForeground(UIManager.getColor("Label.disabledForeground"));
    header.setBorder(BorderFactory.createEmptyBorder(12, 8, 4, 0));
    mainPanel.add(header);
    return header;
  }

  /**
   * Initializes the action buttons section.
   */
  public void initActionButtons(ActionListener runAction, ActionListener runSlowAction,
                                 ActionListener captureAction, ActionListener recordAction) {
    // Logo
    JLabel logo = new JLabel("OculiX");
    logo.setFont(UIManager.getFont("h3.font"));
    logo.setHorizontalAlignment(SwingConstants.CENTER);
    logo.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
    mainPanel.add(logo);

    mainPanel.add(new JSeparator(), "growx, gaptop 4, gapbottom 4");

    // Run group
    btnRun = new SidebarItem("Run",
        loadIcon("/icons/run_big_green.png", 18), runAction);
    btnRun.setToolTipText(_I("btnRunLabel") + " (Ctrl+R)");
    btnRun.setMnemonic(java.awt.event.KeyEvent.VK_R);
    mainPanel.add(btnRun);

    btnRunSlow = new SidebarItem("Run Slow",
        loadIcon("/icons/runviz.png", 18), runSlowAction);
    btnRunSlow.setToolTipText(_I("btnRunSlowMotionLabel"));
    mainPanel.add(btnRunSlow);

    // Scripts group
    addSectionHeader("SCRIPTS");

    navFile = new SidebarItem(_I("menuFile") + "  \u25B8", null);
    navFile.setMnemonic(java.awt.event.KeyEvent.VK_F);
    mainPanel.add(navFile);

    navEdit = new SidebarItem(_I("menuEdit") + "  \u25B8", null);
    navEdit.setMnemonic(java.awt.event.KeyEvent.VK_E);
    mainPanel.add(navEdit);

    // Tools group
    addSectionHeader("TOOLS");

    btnCapture = new SidebarItem("Capture",
        loadIcon("/icons/capture-small.png", 18), captureAction);
    btnCapture.setToolTipText(_I("btnCaptureLabel"));
    mainPanel.add(btnCapture);

    btnRecord = new SidebarItem("Record",
        loadIcon("/icons/record.png", 18), recordAction);
    btnRecord.setToolTipText(_I("btnRecordLabel"));
    mainPanel.add(btnRecord);

    navTools = new SidebarItem(_I("menuTool") + "  \u25B8", null);
    navTools.setMnemonic(java.awt.event.KeyEvent.VK_T);
    mainPanel.add(navTools);

    // Help group
    addSectionHeader("HELP");

    navHelp = new SidebarItem(_I("menuHelp") + "  \u25B8", null);
    navHelp.setMnemonic(java.awt.event.KeyEvent.VK_H);
    mainPanel.add(navHelp);
  }

  /**
   * Wires navigation items to popup submenus built from existing JMenu actions.
   */
  public void initNavigation(SidebarSubmenu fileSub, SidebarSubmenu editSub,
                              SidebarSubmenu toolsSub, SidebarSubmenu helpSub) {
    navFile.addActionListener(e -> fileSub.showBelow(navFile));
    navEdit.addActionListener(e -> editSub.showBelow(navEdit));
    navTools.addActionListener(e -> toolsSub.showBelow(navTools));
    navHelp.addActionListener(e -> helpSub.showBelow(navHelp));
  }

  /**
   * Initializes the footer with theme toggle and version label.
   */
  public void initFooter(String version, ActionListener themeAction) {
    footerPanel.add(new JSeparator(), "growx, gapbottom 4");

    btnTheme = new SidebarItem("\u263C  Dark / Light", null, e -> {
      toggleTheme();
      if (themeAction != null) {
        themeAction.actionPerformed(e);
      }
    });
    footerPanel.add(btnTheme);

    versionLabel = new JLabel("v" + version);
    versionLabel.setFont(UIManager.getFont("small.font"));
    versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    versionLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
    footerPanel.add(versionLabel);
  }

  private void toggleTheme() {
    isDark = !isDark;
    try {
      if (isDark) {
        FlatDarkLaf.setup();
      } else {
        FlatLightLaf.setup();
      }
      FlatLaf.updateUI();
    } catch (Exception ex) {
      // Fallback: ignore theme switch failure
    }
  }

  public boolean isDarkTheme() {
    return isDark;
  }

  /**
   * Toggle between collapsed (icons only) and expanded (icons + labels) mode.
   */
  public void toggleCollapsed() {
    collapsed = !collapsed;

    for (Component c : mainPanel.getComponents()) {
      if (c instanceof SidebarItem) {
        ((SidebarItem) c).setCollapsed(collapsed);
      }
      if (c instanceof JLabel && !(c instanceof SidebarItem)) {
        c.setVisible(!collapsed);
      }
    }
    for (Component c : footerPanel.getComponents()) {
      if (c instanceof SidebarItem) {
        ((SidebarItem) c).setCollapsed(collapsed);
      }
    }
    versionLabel.setVisible(!collapsed);

    revalidate();
    repaint();
  }

  public boolean isCollapsed() {
    return collapsed;
  }

  private void addResizeHandle() {
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          toggleCollapsed();
        }
      }
    });
  }

  private ImageIcon loadIcon(String path, int size) {
    try {
      java.net.URL url = getClass().getResource(path);
      if (url != null) {
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
      }
    } catch (Exception e) {
      // ignore, return null
    }
    return null;
  }
}
