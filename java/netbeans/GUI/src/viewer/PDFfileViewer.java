/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import java.awt.Adjustable;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Box.Filler;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.common.views.DocumentViewControllerImpl;
import tcp.stream.ControllCommands;
import tcp.stream.Messanger;
import tcp.stream.TCPCommunication;


/**
 *
 * @author patlas
 */
public class PDFfileViewer {
    
    private JPanel pdfPane = null;
    private JScrollPane pdfScrollPane = null;
    private JButton pdfPrevBtn = null, pdfNextBtn = null, pdfRotateBtn = null;
    private JToggleButton pdfFullBtn = null;
    private JTextField pdfPageNrTxt = null;
    private JLabel pdfPageCntLbl = null;
    private Filler filler2 = new Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
    
    private Messanger messanger = null;
    
    public SwingController controller = null;
    public static byte is_moved_up = 1;
    public static byte is_moved_down = 0;
    public static int lastPosition = 0;
    
    public String filePath = null;
    
    public PDFfileViewer(JPanel pane, JScrollPane sp, Messanger m)
    {
        pdfPane = pane;
        pdfScrollPane = sp;
        messanger = m;
    }
    
    public void viewPDF(String path){
        
        filePath = path;//"stmEncoder.pdf";

        // build a controller
        controller = new SwingController();

        // Build a SwingViewFactory configured with the controller
        SwingViewBuilder factory = new SwingViewBuilder(controller);

//         Use the factory to build a JPanel that is pre-configured
//        with a complete, active Viewer UI.
//        JPanel viewerComponentPanel = factory.buildViewerPanel();
    
    
    
        // Create a JFrame to display the panel in
//        JFrame window = new JFrame("Using the Viewer Component");
//        window.getContentPane().add(viewerComponentPanel);
//        window.pack();
//        window.setVisible(true);

       // pdfPane = factory.buildViewerPanel();
        System.out.println(factory.buildViewerPanel().toString());
        
        
        
        DocumentViewController dvc = controller.getDocumentViewController();
        
        DocumentViewControllerImpl documentViewController = (DocumentViewControllerImpl) dvc;
        documentViewController.setDocumentViewType(DocumentViewControllerImpl.ONE_PAGE_VIEW/*ONE_COLUMN_VIEW*/,DocumentViewController.PAGE_FIT_WINDOW_HEIGHT);

        
      
        pdfScrollPane = (JScrollPane)dvc.getViewContainer();
        pdfScrollPane.setWheelScrollingEnabled(false);
        
        Component comp = dvc.getViewContainer();
        //comp.setSize(300, 300);
//        pdfPane.add(comp);
//        pdfScrollPane.setVisible(true);
//        System.out.println(dvc.getViewContainer().toString());
        // Open a PDF document to view
        
        pdfPrevBtn = new JButton();
        pdfPageNrTxt = new JTextField();
        pdfNextBtn = new JButton();
        pdfRotateBtn = new JButton();
        pdfFullBtn = new JToggleButton();
        pdfPageCntLbl = new JLabel();
        

        controller.setNextPageButton(pdfNextBtn);
        controller.setPreviousPageButton(pdfPrevBtn);
        controller.setRotateRightButton(pdfRotateBtn);
        controller.setFitWidthButton(pdfFullBtn);
        controller.setCurrentPageNumberTextField(pdfPageNrTxt);
        controller.setNumberOfPagesLabel(pdfPageCntLbl);
        
        setPDFlayout();
        //pdfScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pdfScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pdfScrollPane.setWheelScrollingEnabled(false);
        //MouseWheelListener[] a = pdfScrollPane.getMouseWheelListeners();
        pdfScrollPane.removeMouseWheelListener(pdfScrollPane.getMouseWheelListeners()[0]);
      
        controller.openDocument(filePath);
        pdfScrollPane.removeMouseWheelListener(pdfScrollPane.getMouseWheelListeners()[0]);
        pdfScrollPane.setWheelScrollingEnabled(false);
        pdfScrollPane.getVerticalScrollBar().setMinimum(5);

    }
    
    private void setPDFlayout()
    {
        javax.swing.GroupLayout pdfPaneLayout = new javax.swing.GroupLayout(pdfPane);
        pdfPane.setLayout(pdfPaneLayout);
        pdfPaneLayout.setHorizontalGroup(
            pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdfPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pdfPaneLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pdfPaneLayout.createSequentialGroup()
                        .addGroup(pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pdfScrollPane)
                            .addGroup(pdfPaneLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(pdfRotateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                .addComponent(pdfPrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdfPageNrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdfPageCntLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdfNextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                                .addComponent(pdfFullBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)))
                        .addContainerGap())))
        );
        
        
        pdfPaneLayout.setVerticalGroup(
            pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pdfPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pdfScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pdfRotateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pdfPrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pdfPageNrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pdfNextBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pdfFullBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pdfPageCntLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        
        pdfPrevBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/left_arrow_16x16.png")));
        pdfNextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/right_arrow_16x16.png")));
        pdfRotateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/redo_16x16.png")));
        pdfFullBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/expand_16x16.png")));
        
        //pdfPageCntLbl.setBackground(getBackground());
        pdfPageCntLbl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pdfPageCntLbl.setBorder(null);

    }
    
    
    public void setMessanger(Messanger m)
    {
        messanger = m;
    }
    
    public void setKeyBindings()
    {
        pdfPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "down");
        pdfPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0), "up");
        
        pdfPane.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("down");
                
                int min = pdfScrollPane.getVerticalScrollBar().getMinimum();
                int max = pdfScrollPane.getVerticalScrollBar().getMaximum();
                int cur = pdfScrollPane.getVerticalScrollBar().getValue();
                int value = 0;
                
                float per = (float) cur/(max-min);
                System.out.println(per);
                
                if(per < 0.25)
                {
                    value = (int)(0.25*max);
                    value++;
                    System.out.println("0,25 "+value);
                } else if(per >= 0.25 && per < 0.5)
                {
                    value = (int)(0.50*max);
                    value++;
                    System.out.println("0,5 "+value);
                } else if(per >= 0.5 && per < 0.75)
                {
                    value = (int)(0.75*max);
                    value++;
                    System.out.println("0,75 "+value);
                } else if(per >= 0.75 && per < 1)
                {
                    value = (int)(max);
                    System.out.println("1 "+value);
                } else
                {
                    int dp = controller.getCurrentPageNumber();
                    if(dp<controller.getDocument().getNumberOfPages())
                    {
                        dp++;
                        controller.goToDeltaPage(dp);
                        messanger.sendCommand(ControllCommands.F_DNEXT);
//                        PDFfileViewer.is_moved_up = false;
//                        PDFfileViewer.is_moved_down = false;
                    }
                    return;
                }
                
                pdfScrollPane.getVerticalScrollBar().setValue(value);
                
            }
        });
        
        pdfPane.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("up");
            }
        });
        
    }
    
    public void setScrollBarListener(Messanger m)
    {
        pdfScrollPane.getVerticalScrollBar().addAdjustmentListener(new MyScrollBarListener(m));
        
    }
    
    public void setButtonListeners(Messanger m)
    {
        pdfNextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                messanger.sendCommand(ControllCommands.F_DNEXT);
//                PDFfileViewer.is_moved_up = false;
//                PDFfileViewer.is_moved_down = false;

            }
        }); 
        
        pdfPrevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                messanger.sendCommand(ControllCommands.F_DPREV);
//                PDFfileViewer.is_moved_up = false;
//                PDFfileViewer.is_moved_down = false;
            }
        }); 
        
        
        pdfRotateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                messanger.sendCommand(ControllCommands.F_DROTATE);

            }
        });
        
        
        pdfFullBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                messanger.sendCommand(ControllCommands.F_DFULL);

            }
        });
                
                
                
                
    }
    
    
}


class MyScrollBarListener implements AdjustmentListener {
    
    Messanger messanger = null;
    public MyScrollBarListener(Messanger mes)
    {
        messanger = mes;
    }
    
    @Override
  public void adjustmentValueChanged(AdjustmentEvent evt) {
    Adjustable source = evt.getAdjustable();
    if (evt.getValueIsAdjusting()) {
      return;
    }

    int cur = evt.getValue();
    
    JScrollBar sb = (JScrollBar)(evt.getSource());
    int max = sb.getMaximum();
    System.out.println("SCROLL: "+cur);
    
    
    // w zaleznosci od zmapowania ile pageup/down jest w xpdf (lub innym) takie ustawic proporcje ich wysylania
    //w przypadku scrolla/pageupdown !!!!!
     float per = (float) cur/max;
//                System.out.println(per);
//                
//                if(per >= 0.25 && per < 0.5)
//                {
//                    
//                    System.out.println("0,5 ");
//                } else if(per >= 0.5 && per < 0.75)
//                {
//
//                    System.out.println("0,75 ");
//                } else if(per >= 0.75 && per < 1)
//                {
//                
//                    System.out.println("1 ");
//                } 
     
//     if(per<0.5 && per>0.2 && PDFfileViewer.is_moved_up==false)
//     {
//         messanger.sendCommand(ControllCommands.F_DPUP);
//         PDFfileViewer.is_moved_up = true;
//     }
//     
//     if(per>=0.5 && PDFfileViewer.is_moved_down==false)
//     {
//         messanger.sendCommand(ControllCommands.F_DPDOWN);
//         PDFfileViewer.is_moved_down = true;
//     }
//     
//     
//    if(per == 0)
//    {
//        sb.setValue(sb.getUnitIncrement());
//        System.out.println("NEw page");
//        PDFfileViewer.is_moved_down = false;
//        PDFfileViewer.is_moved_up = false;
//        
//        return;
//    }
     
//     if( (cur - PDFfileViewer.lastPosition) > 0)
//     {
         //moved down
        if(per<0.6 && PDFfileViewer.is_moved_up<1)
        {
            messanger.sendCommand(ControllCommands.F_DPUP);
            PDFfileViewer.is_moved_up++;
            PDFfileViewer.is_moved_down--;
        }

        if(per>0.6 && PDFfileViewer.is_moved_down<1)
        {
            messanger.sendCommand(ControllCommands.F_DPDOWN);
            PDFfileViewer.is_moved_up--;
            PDFfileViewer.is_moved_down++;
        }
         
//     }
//     else
//     {
//         //moved up
//     }
        
    if(per == 0)
    {
        sb.setValue(sb.getUnitIncrement());
        System.out.println("NEw page");
        PDFfileViewer.is_moved_down = 0;
        PDFfileViewer.is_moved_up = 1;
        
        return;
    }
    
    PDFfileViewer.lastPosition = cur;
    
  }
}