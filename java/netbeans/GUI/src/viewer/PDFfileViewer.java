/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import java.awt.Component;
import javax.swing.Box.Filler;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.common.views.DocumentViewControllerImpl;


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
    
    public PDFfileViewer(JPanel pane, JScrollPane sp)
    {
        pdfPane = pane;
        pdfScrollPane = sp;
    }
    
    public void viewPDF(){
        String filePath = "stmEncoder.pdf";

        // build a controller
        SwingController controller = new SwingController();

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
        pdfScrollPane.setWheelScrollingEnabled(false);
        
       
        
        
        
        controller.openDocument(filePath);
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
    
    
}
