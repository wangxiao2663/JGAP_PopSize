/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package src.examples.monalisa.gui;

import java.io.*;

import javax.imageio.*;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

import org.jdesktop.application.*;
import org.jdesktop.application.Action;
import org.jfree.chart.*;
import org.jfree.data.xy.*;
import org.jgap.*;

import src.examples.monalisa.core.*;

/**
 * The application's main frame.
 *
 * @author Yann N. Dauphin
 * @since 3.4
 */
public class GeneticDrawingView
    extends FrameView {
  /** String containing the CVS revision. Read out via reflection!*/
  @SuppressWarnings("unused")
private final static String CVS_REVISION = "$Revision: 1.3 $";

  public GeneticDrawingView(SingleFrameApplication app) {
    super(app);
    initComponents();
    ResourceMap resourceMap = getResourceMap();
    ImageIcon imageIcon = resourceMap.getImageIcon("targetImageLabel.icon");
    targetImage = new BufferedImage(imageIcon.getIconWidth(),
                                    imageIcon.getIconHeight(),
                                    BufferedImage.TYPE_INT_ARGB);
    imageIcon.paintIcon(null, targetImage.getGraphics(), 0, 0);
    fittestDrawingView = new FittestDrawingView();
    fittestDrawingView.setVisible(false);
    fittestDrawingView.setSize(targetImage.getWidth(), targetImage.getHeight());
  }

  @Action
  public void showAboutBox() {
    if (aboutBox == null) {
      JFrame mainFrame = GeneticDrawingApp.getApplication().getMainFrame();
      aboutBox = new GeneticDrawingAboutBox(mainFrame);
      aboutBox.setLocationRelativeTo(mainFrame);
    }
    GeneticDrawingApp.getApplication().show(aboutBox);
  }

  @Action
  public void chooseImage()
      throws IOException {
    JFileChooser fc = new JFileChooser();
    fc.setCurrentDirectory(new File("."));
    fc.showOpenDialog(mainPanel);
    File file = fc.getSelectedFile();
    targetImage = ImageIO.read(file);
    targetImageLabel.setIcon(scaleToImageLabel(targetImage));
    fittestDrawingView.setSize(targetImage.getWidth(), targetImage.getHeight());
  }

  @Action
  public void startEvolution()
      throws InvalidConfigurationException {
    if (targetImage == null) {
      return;
    }
    ResourceMap resourceMap = getResourceMap();
    if (!isEvolutionActivated) {
      startEvolution.setText(resourceMap.getString("stopEvolution.text"));
      fittestDrawingView.setVisible(true);
      isEvolutionActivated = true;
      Configuration.reset();
      GAConfiguration conf = new GAConfiguration(targetImage,
          resourceMap.getInteger("maxPolygons"));
      Thread t = new Thread(new EvolutionRunnable(this, conf));
      t.start();
    }
    else {
      startEvolution.setText(resourceMap.getString("startEvolution.text"));
      isEvolutionActivated = false;
    }
  }

  /**
   * Scale an image to fit the size of the targetImageLabel.
   *
   * @param a_image the image to scale
   * @return scaled image
   */
  public ImageIcon scaleToImageLabel(Image a_image) {
    ImageIcon scaled = new ImageIcon(a_image);
    if (scaled.getIconHeight() > targetImageLabel.getHeight()) {
      scaled = new ImageIcon(a_image.getScaledInstance(
          -1, targetImageLabel.getHeight(), Image.SCALE_FAST));
    }
    if (scaled.getIconWidth() > targetImageLabel.getWidth()) {
      scaled = new ImageIcon(a_image.getScaledInstance(
          targetImageLabel.getWidth(), -1, Image.SCALE_FAST));
    }
    return scaled;
  }

  public BufferedImage getTargetImage() {
    return targetImage;
  }

  public boolean isEvolutionActivated() {
    return isEvolutionActivated;
  }

  public FittestDrawingView getFittestDrawingView() {
    return fittestDrawingView;
  }

  public JFreeChart getChart() {
    ChartPanel cp = (ChartPanel) chartPanel;
    return cp.getChart();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    mainPanel = new javax.swing.JPanel();
    chooseImage = new javax.swing.JButton();
    startEvolution = new javax.swing.JToggleButton();
    targetImageLabel = new javax.swing.JLabel();
    JFreeChart chart = ChartFactory.createXYLineChart(
        "Fitness versus Generation",
        "Generation",
        "Fitness",
        new XYSeriesCollection(new XYSeries("")),
        org.jfree.chart.plot.PlotOrientation.VERTICAL,
        false,
        false,
        false);
    chartPanel = new ChartPanel(chart);
    menuBar = new javax.swing.JMenuBar();
    javax.swing.JMenu fileMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
    javax.swing.JMenu helpMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
    mainPanel.setName("mainPanel"); // NOI18N
    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.
        getInstance(src.examples.monalisa.gui.GeneticDrawingApp.class).
        getContext().getActionMap(GeneticDrawingView.class, this);
    chooseImage.setAction(actionMap.get("chooseImage")); // NOI18N
    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.
        Application.getInstance(src.examples.monalisa.gui.GeneticDrawingApp.class).
        getContext().getResourceMap(GeneticDrawingView.class);
    chooseImage.setText(resourceMap.getString("chooseImage.text")); // NOI18N
    chooseImage.setName("chooseImage"); // NOI18N
    startEvolution.setAction(actionMap.get("startEvolution")); // NOI18N
    startEvolution.setText(resourceMap.getString("startEvolution.text")); // NOI18N
    startEvolution.setName("startEvolution"); // NOI18N
    targetImageLabel.setIcon(resourceMap.getIcon("targetImageLabel.icon")); // NOI18N
    targetImageLabel.setText(resourceMap.getString("targetImageLabel.text")); // NOI18N
    targetImageLabel.setName("targetImageLabel"); // NOI18N
    chartPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    chartPanel.setName("chartPanel"); // NOI18N
    org.jdesktop.layout.GroupLayout chartPanelLayout = new org.jdesktop.layout.
        GroupLayout(chartPanel);
    chartPanel.setLayout(chartPanelLayout);
    chartPanelLayout.setHorizontalGroup(
        chartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.
        LEADING)
        .add(0, 399, Short.MAX_VALUE)
        );
    chartPanelLayout.setVerticalGroup(
        chartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.
        LEADING)
        .add(0, 234, Short.MAX_VALUE)
        );
    org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.
        GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
        mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.
        LEADING)
        .add(mainPanelLayout.createSequentialGroup()
             .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.
        GroupLayout.LEADING, false)
                  .add(mainPanelLayout.createSequentialGroup()
                       .add(47, 47, 47)
                       .add(chooseImage)
                       .addPreferredGap(org.jdesktop.layout.LayoutStyle.
                                        UNRELATED)
                       .add(startEvolution)
                       .add(38, 38, 38))
                  .add(org.jdesktop.layout.GroupLayout.TRAILING,
                       mainPanelLayout.createSequentialGroup()
                       .addContainerGap(org.jdesktop.layout.GroupLayout.
                                        DEFAULT_SIZE, Short.MAX_VALUE)
                       .add(targetImageLabel,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                       .add(80, 80, 80)))
             .add(chartPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                  org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
             .addContainerGap())
        );
    mainPanelLayout.setVerticalGroup(
        mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.
        LEADING)
        .add(org.jdesktop.layout.GroupLayout.TRAILING,
             mainPanelLayout.createSequentialGroup()
             .addContainerGap()
             .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.
        GroupLayout.TRAILING)
                  .add(org.jdesktop.layout.GroupLayout.LEADING, chartPanel,
                       org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                       org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                       Short.MAX_VALUE)
                  .add(mainPanelLayout.createSequentialGroup()
                       .add(targetImageLabel,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 202,
                            Short.MAX_VALUE)
                       .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                       .add(mainPanelLayout.createParallelGroup(org.jdesktop.
        layout.GroupLayout.BASELINE)
                            .add(startEvolution)
                            .add(chooseImage))))
             .addContainerGap())
        );
    chooseImage.getAccessibleContext().setAccessibleName(resourceMap.getString(
        "jButton1.AccessibleContext.accessibleName")); // NOI18N
    menuBar.setName("menuBar"); // NOI18N
    fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
    fileMenu.setName("fileMenu"); // NOI18N
    exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
    exitMenuItem.setName("exitMenuItem"); // NOI18N
    fileMenu.add(exitMenuItem);
    menuBar.add(fileMenu);
    helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
    helpMenu.setName("helpMenu"); // NOI18N
    aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
    aboutMenuItem.setName("aboutMenuItem"); // NOI18N
    helpMenu.add(aboutMenuItem);
    menuBar.add(helpMenu);
    setComponent(mainPanel);
    setMenuBar(menuBar);
  } // </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel chartPanel;

  private javax.swing.JButton chooseImage;

  private javax.swing.JPanel mainPanel;

  private javax.swing.JMenuBar menuBar;

  private javax.swing.JToggleButton startEvolution;

  private javax.swing.JLabel targetImageLabel;

  // End of variables declaration//GEN-END:variables
  private JDialog aboutBox;

  private BufferedImage targetImage;

  private boolean isEvolutionActivated;

  private FittestDrawingView fittestDrawingView;
}
