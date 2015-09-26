/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import java.awt.Component;
import javax.swing.Box.Filler;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.views.DocumentViewController;


/**
 *
 * @author patlas
 */
public class PDFfileViewer {
    
    private JPanel pdfPane = null;
    private JScrollPane pdfScrollPane = null;
    private JButton pdfPrevBtn = null, pdfNextBtn = null, pdfRotateBtn = null;
    private JTextField pdfPageNrTxt = null;
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
        
       // DocumentViewControllerImpl documentViewController = (DocumentViewControllerImpl) dvc;
        //documentViewController.setDocumentViewType(DocumentViewControllerImpl.ONE_COLUMN_VIEW,DocumentViewController.PAGE_FIT_WINDOW_HEIGHT);

        
      
        pdfScrollPane = (JScrollPane)dvc.getViewContainer();
        
        Component comp = dvc.getViewContainer();
        //comp.setSize(300, 300);
//        pdfPane.add(comp);
//        pdfScrollPane.setVisible(true);
//        System.out.println(dvc.getViewContainer().toString());
        // Open a PDF document to view
        
        pdfPrevBtn = new JButton("A");
        pdfPageNrTxt = new JTextField("F");
        pdfNextBtn = new JButton("C");
        pdfRotateBtn = new JButton("D");
        setPDFlayout();
        
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
                    .addGroup(pdfPaneLayout.createSequentialGroup()
                        .addComponent(pdfScrollPane)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pdfPaneLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(filler2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pdfPaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pdfPrevBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(pdfRotateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pdfPageNrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pdfNextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 131, Short.MAX_VALUE))))
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
                        .addComponent(pdfNextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pdfPageNrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pdfPrevBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }
    
    
}
