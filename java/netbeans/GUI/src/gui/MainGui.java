/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import tcp.stream.ControllCommands;
import tcp.stream.FileStreamer;
import tcp.stream.TCPCommunication;
import viewer.PDFfileViewer;
import viewer.PPTfileViewer;



/**
 *
 * @author Max
 */
public class MainGui extends javax.swing.JFrame {
    
    static int PORT = 12345;
    static String ADDRESS = "192.168.1.20";/*"127.0.0.1";*/
    
    public String fName = null;
    private ProgressDialog pd = null;
    private boolean pptEffects = true;
    private boolean fileOpened = false;
    private PPTfileViewer pptViewer = null;
    private TCPCommunication tcpcomm = null;
    private Thread progressThread = null;
    /**
     * Creates new form MainGui
     */
    public MainGui() {

        initComponents();
        
        pptEffectsCombo.setEnabled(false);
        pptNextBtn.setEnabled(false);
        pptPrevBtn.setEnabled(false);
        mOpenFile.setEnabled(false);
        
        
        PDFfileViewer pdf = new PDFfileViewer(pdfPane, null);
        pdf.viewPDF();
        pdfPane.setVisible(true);
       // JButton a = new JButton("A");
       // a.setSize(100, 100);
        //pdfPane.add(a);
        pack();
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpCharts = new javax.swing.JTabbedPane();
        pptPane = new javax.swing.JPanel();
        pptPreviewLabel = new javax.swing.JLabel();
        pptEffectsCombo = new javax.swing.JComboBox();
        pptPrevBtn = new javax.swing.JButton();
        pptNextBtn = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 10));
        pptSlideNrTxt = new javax.swing.JTextField();
        pdfPane = new javax.swing.JPanel();
        pdfScrollPane = new javax.swing.JScrollPane();
        pdfRotateBtn = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        pdfNextBtn = new javax.swing.JButton();
        pdfPageNrTxt = new javax.swing.JTextField();
        pdfPrevBtn = new javax.swing.JButton();
        moviePane = new javax.swing.JPanel();
        topMenuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        mConnect = new javax.swing.JMenuItem();
        mOpenFile = new javax.swing.JMenuItem();
        mSeparator = new javax.swing.JPopupMenu.Separator();
        mExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuAbout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("MainFrame"); // NOI18N
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        pptPreviewLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pptPreviewLabel.setText("PREVIEW UNAVELIABLE IN EFFECTS MODE");
        pptPreviewLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pptPreviewLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        pptEffectsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EFFECTS", "NO EFFECTS" }));
        pptEffectsCombo.setMaximumSize(new java.awt.Dimension(100, 50));
        pptEffectsCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pptEffectsComboItemStateChanged(evt);
            }
        });

        pptPrevBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/left_arrow_16x16.png"))); // NOI18N
        pptPrevBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pptPrevBtnActionPerformed(evt);
            }
        });

        pptNextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/right_arrow_16x16.png"))); // NOI18N
        pptNextBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pptNextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pptNextBtnActionPerformed(evt);
            }
        });

        pptSlideNrTxt.setBackground(getBackground());
        pptSlideNrTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pptSlideNrTxt.setText("x/n");
        pptSlideNrTxt.setBorder(null);
        pptSlideNrTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pptSlideNrTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pptPaneLayout = new javax.swing.GroupLayout(pptPane);
        pptPane.setLayout(pptPaneLayout);
        pptPaneLayout.setHorizontalGroup(
            pptPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pptPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pptPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pptPreviewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pptPaneLayout.createSequentialGroup()
                        .addComponent(pptEffectsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(pptPrevBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pptSlideNrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pptNextBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE))
                    .addComponent(filler1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pptPaneLayout.setVerticalGroup(
            pptPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pptPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pptPreviewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pptPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pptNextBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pptPrevBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pptEffectsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pptSlideNrTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tpCharts.addTab("Presentation", new javax.swing.ImageIcon(getClass().getResource("/gui/icons/ppt-20.png")), pptPane, "Start PowerPoint presentation"); // NOI18N

        pdfRotateBtn.setText("jButton2");

        pdfNextBtn.setText("jButton1");

        pdfPageNrTxt.setText("jTextField1");

        pdfPrevBtn.setText("jButton3");

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

        tpCharts.addTab("PDF", new javax.swing.ImageIcon(getClass().getResource("/gui/icons/pdf-20.png")), pdfPane, "View pdf file"); // NOI18N

        javax.swing.GroupLayout moviePaneLayout = new javax.swing.GroupLayout(moviePane);
        moviePane.setLayout(moviePaneLayout);
        moviePaneLayout.setHorizontalGroup(
            moviePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );
        moviePaneLayout.setVerticalGroup(
            moviePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        tpCharts.addTab("Movie", new javax.swing.ImageIcon(getClass().getResource("/gui/icons/mov-20.png")), moviePane, "Stream movie"); // NOI18N

        getContentPane().add(tpCharts);

        menuFile.setText("File");
        menuFile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        mConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/workflow_16x16.png"))); // NOI18N
        mConnect.setText("Connect");
        mConnect.setToolTipText("");
        mConnect.setActionCommand("mConnect");
        mConnect.setNextFocusableComponent(mOpenFile);
        mConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mConnectActionPerformed(evt);
            }
        });
        menuFile.add(mConnect);
        mConnect.getAccessibleContext().setAccessibleName("mConnect");

        mOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/folder_16x16.png"))); // NOI18N
        mOpenFile.setText("Open file");
        mOpenFile.setNextFocusableComponent(mExit);
        mOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mOpenFileActionPerformed(evt);
            }
        });
        menuFile.add(mOpenFile);
        menuFile.add(mSeparator);

        mExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/delete_16x16.png"))); // NOI18N
        mExit.setText("Exit");
        mExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mExitActionPerformed(evt);
            }
        });
        menuFile.add(mExit);

        topMenuBar.add(menuFile);

        menuEdit.setText("Edit");
        topMenuBar.add(menuEdit);

        menuAbout.setText("About");
        topMenuBar.add(menuAbout);

        setJMenuBar(topMenuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mOpenFileActionPerformed
       JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        
        //close recently opened file
        if(fName != null){
           //czy na pewno trzeba?
        }
        
        //streamowanie pliku
        
        //pd.pProgressBar.setValue(70);
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                pd.setVisible(true);
                progressThread = Thread.currentThread();
                while(!Thread.currentThread().isInterrupted());
                pd.dispatchEvent(new WindowEvent(pd, WindowEvent.WINDOW_CLOSING));
                return;
            }
        });*/
 

        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          fName = selectedFile.getPath();
          System.out.println(selectedFile.getName()+ " " + fName);
          
          //file transfer
          if(tcpcomm != null){
            FileStreamer fs = new FileStreamer(tcpcomm);
            if(fs.streamFile(fName)){
                //progressThread.interrupt();
                System.out.println("Plik wysłano pomyślnie");
            }
            else{
                //błąd transmisji pliku!!!!
                System.out.println("Bład wysyłanai pliku");
            }
          }
          
          
          
          if(fName.toLowerCase().endsWith(".ppt") || fName.toLowerCase().endsWith(".pptx")){
            pptViewer = new PPTfileViewer(fName);
            pptEffectsCombo.setEnabled(true);
            pptNextBtn.setEnabled(true);
            pptPrevBtn.setEnabled(true);
            if(pptEffects == false){
                pptPreviewLabel.setIcon(pptViewer.showSlide(pptViewer.currentSlide));
                pptSlideNrTxt.setText(pptViewer.currentSlide+"/"+pptViewer.slidesCount);
            }   
          }

        }
    }//GEN-LAST:event_mOpenFileActionPerformed

    private void mExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mExitActionPerformed
        // TODO add your handling code here:
        /*if(pd != null){
            pd.pProgressBar.setValue(100);
            pd.dispatchEvent(new WindowEvent(pd, WindowEvent.WINDOW_CLOSING));
        }*/

 
    }//GEN-LAST:event_mExitActionPerformed

    private void mConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mConnectActionPerformed

/*
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        pd = new ProgressDialog();
        pd.setSize((int) Math.round(width*0.3), (int) Math.round(height*0.1));
        pd.pProgressBar.setValue(70);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                pd.setVisible(true);
            }
        });

        //pd.setSize(WIDTH, WIDTH);
 */ 
        if(mConnect.getText().equalsIgnoreCase("connect")){
            if(tcpcomm == null){
                try{
                    tcpcomm = new TCPCommunication(ADDRESS, PORT);
                }catch(UnknownHostException he){
                    
                }catch(IOException e){
                    
                }
            }
            if(tcpcomm.isConnected()){
                mConnect.setText("Disconnect");
                mOpenFile.setEnabled(true);
            }
        }
        else{
            while(!tcpcomm.disconnect());
            tcpcomm = null;
            mConnect.setText("Connect");
            mOpenFile.setEnabled(false);
        }
    }//GEN-LAST:event_mConnectActionPerformed

    private void pptNextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pptNextBtnActionPerformed
        if(pptEffects == false){
            if(pptViewer.slidesCount>pptViewer.currentSlide){
                pptViewer.currentSlide++;
                pptPrevBtn.setEnabled(true);
                pptPreviewLabel.setIcon(pptViewer.showSlide(pptViewer.currentSlide));
                
                //TYMCZASOWA PROWIZORKA
                tcpcomm.sendCommand((int)ControllCommands.ppt_nextp);
               
                
                
            }

            if(pptViewer.slidesCount == pptViewer.currentSlide){
                pptNextBtn.setEnabled(false);
            }
            pptSlideNrTxt.setText(pptViewer.currentSlide+"/"+pptViewer.slidesCount);
        }
        else{
            pptPrevBtn.setEnabled(true);
            pptNextBtn.setEnabled(true);
            //TYMCZASOWA PROWIZORKA
                tcpcomm.sendCommand((int)ControllCommands.ppt_next);
        }
    }//GEN-LAST:event_pptNextBtnActionPerformed

    private void pptPrevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pptPrevBtnActionPerformed

        if(pptEffects == false){
            if(pptViewer.slidesCount>=pptViewer.currentSlide){
                pptViewer.currentSlide--;
                pptNextBtn.setEnabled(true);
                pptPreviewLabel.setIcon(pptViewer.showSlide(pptViewer.currentSlide));
                
                
                //TYMCZASOWA PROWIZORKA
                tcpcomm.sendCommand((int)ControllCommands.ppt_prevp);
                
            }

            if(1 == pptViewer.currentSlide){
                pptPrevBtn.setEnabled(false);
            }
            pptSlideNrTxt.setText(pptViewer.currentSlide+"/"+pptViewer.slidesCount);
        }
        else{
            pptPrevBtn.setEnabled(true);
            pptNextBtn.setEnabled(true);
            
            //TYMCZASOWA PROWIZORKA
                tcpcomm.sendCommand((int)ControllCommands.ppt_prev);
                
        }
    }//GEN-LAST:event_pptPrevBtnActionPerformed

    private void pptEffectsComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pptEffectsComboItemStateChanged
        JComboBox combo = (JComboBox)evt.getSource();
        int currentIndex = combo.getSelectedIndex();

        //Integer.valueOf(currentQuantity);
        pptViewer.currentSlide=1;
        pptEffects = (currentIndex==0);
        if(pptEffects == true){
            pptPreviewLabel.setText("PREVIEW UNAVELIABLE IN EFFECTS MODE");
            pptPreviewLabel.setIcon(null);
            pptNextBtn.setEnabled(true);
            pptPrevBtn.setEnabled(false);
        }
        else{
            pptSlideNrTxt.setText(pptViewer.currentSlide+"/"+pptViewer.slidesCount);
            pptPreviewLabel.setText("");
            pptPreviewLabel.setIcon(pptViewer.showSlide(pptViewer.currentSlide));
            if(pptViewer.slidesCount>1){
                pptNextBtn.setEnabled(true);
                pptPrevBtn.setEnabled(false);
            }
        }
    }//GEN-LAST:event_pptEffectsComboItemStateChanged

    private void pptSlideNrTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pptSlideNrTxtActionPerformed
        
        if(pptEffects == false){
            JTextField tf = (JTextField)evt.getSource();
            String text = tf.getText();
            if(text.contains("/")){
                int page = Integer.parseInt(text.split("/")[0]);
                if(page<=pptViewer.slidesCount && page>0){
                    pptViewer.currentSlide = page;
                    
                    if(page==pptViewer.slidesCount){
                        pptNextBtn.setEnabled(false);
                        pptPrevBtn.setEnabled(true);
                    }
                    else{
                        pptNextBtn.setEnabled(true);
                    }
                    if(page==1){
                        pptPrevBtn.setEnabled(false);
                        pptNextBtn.setEnabled(true);
                    }
                    else{
                        pptPrevBtn.setEnabled(true);
                    }
                }
            }
            else{
                int page = Integer.parseInt(text);
                if(page<=pptViewer.slidesCount && page>0){
                    pptViewer.currentSlide = Integer.parseInt(text);
                    
                    if(page==pptViewer.slidesCount){
                        pptNextBtn.setEnabled(false);
                        pptPrevBtn.setEnabled(true);
                    }
                    else{
                        pptNextBtn.setEnabled(true);
                    }
                    if(page==1){
                        pptPrevBtn.setEnabled(false);
                        pptNextBtn.setEnabled(true);
                    }
                    else{
                        pptPrevBtn.setEnabled(true);
                    }
                }
            }
            pptPreviewLabel.setIcon(pptViewer.showSlide(pptViewer.currentSlide));
        }
        
    }//GEN-LAST:event_pptSlideNrTxtActionPerformed

    
    public void createDialog(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        pd = new ProgressDialog();
        pd.setSize((int) Math.round(width*0.3), (int) Math.round(height*0.1));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows"/*"Nimbus"*/.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
      
        
        //</editor-fold>

        
        //XMLSlideShow pptx = new XMLSlideShow(new FileInputStream("slideshow.pptx"));
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JMenuItem mConnect;
    private javax.swing.JMenuItem mExit;
    private javax.swing.JMenuItem mOpenFile;
    private javax.swing.JPopupMenu.Separator mSeparator;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    public javax.swing.JPanel moviePane;
    private javax.swing.JButton pdfNextBtn;
    private javax.swing.JTextField pdfPageNrTxt;
    private javax.swing.JPanel pdfPane;
    private javax.swing.JButton pdfPrevBtn;
    private javax.swing.JButton pdfRotateBtn;
    private javax.swing.JScrollPane pdfScrollPane;
    public javax.swing.JComboBox pptEffectsCombo;
    public javax.swing.JButton pptNextBtn;
    public javax.swing.JPanel pptPane;
    public javax.swing.JButton pptPrevBtn;
    public javax.swing.JLabel pptPreviewLabel;
    public javax.swing.JTextField pptSlideNrTxt;
    private javax.swing.JMenuBar topMenuBar;
    private javax.swing.JTabbedPane tpCharts;
    // End of variables declaration//GEN-END:variables
}
