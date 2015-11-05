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
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import tcp.stream.ControllCommands;
import tcp.stream.FileStreamer;
import tcp.stream.Messanger;
import tcp.stream.QueueStruct;
import tcp.stream.TCPCommunication;
import viewer.PDFfileViewer;
import viewer.PPTfileViewer;



/**
 *
 * @author Max
 */
public class MainGui extends javax.swing.JFrame {
    
    static int PORT = 12345;
    static String ADDRESS = "192.168.1.7";/*"127.0.0.1";*/
    
    public String fName = null;
    private ProgressDialog pd = null;
    private boolean pptEffects = true;
    private boolean fileOpened = false;
    private PPTfileViewer pptViewer = null;
    private TCPCommunication tcpcomm = null;
    private Messanger messanger = null;
    private Thread progressThread = null;
    public LinkedBlockingQueue receiver = new LinkedBlockingQueue();
    public LinkedBlockingQueue<QueueStruct> transmitter = new LinkedBlockingQueue<>();
    public Thread messangerThread = null;
    public boolean chartAutoChage = true;
    
    private PDFfileViewer pdf = null;
    /**
     * Creates new form MainGui
     */
    public MainGui() {

        initComponents();
        
        pptEffectsCombo.setEnabled(false);
        pptNextBtn.setEnabled(false);
        pptPrevBtn.setEnabled(false);
        mOpenFile.setEnabled(false);
        
        pdf = new PDFfileViewer(pdfPane, null, null);
        //pdf.viewPDF();
        //pdf.setKeyBindings();
        //tymczasowo tutal listener bo później po rozpoznaniu pliku z TCPobjectem
//        pdf.setScrollBarListener(messanger); //usunac
//        pdf.setButtonListeners(messanger);//usunac
        
        
//        pdfPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "forward");
//        pdfPane.getActionMap().put("forward", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("test");
//            }
//        });
        
       
        /*pdfPane.addKeyListener(new java.awt.event.KeyAdapter() {
            
            public void keyPressed(java.awt.event.KeyEvent evt) {
                System.out.println("TEST");
            }
        });*/
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

        tpCharts.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tpChartsStateChanged(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(pptPrevBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pptSlideNrTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pptNextBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE))
                    .addComponent(filler1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pptPaneLayout.setVerticalGroup(
            pptPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pptPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pptPreviewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
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

        javax.swing.GroupLayout pdfPaneLayout = new javax.swing.GroupLayout(pdfPane);
        pdfPane.setLayout(pdfPaneLayout);
        pdfPaneLayout.setHorizontalGroup(
            pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 437, Short.MAX_VALUE)
        );
        pdfPaneLayout.setVerticalGroup(
            pdfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        tpCharts.addTab("PDF", new javax.swing.ImageIcon(getClass().getResource("/gui/icons/pdf-20.png")), pdfPane, "View pdf file"); // NOI18N

        javax.swing.GroupLayout moviePaneLayout = new javax.swing.GroupLayout(moviePane);
        moviePane.setLayout(moviePaneLayout);
        moviePaneLayout.setHorizontalGroup(
            moviePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 437, Short.MAX_VALUE)
        );
        moviePaneLayout.setVerticalGroup(
            moviePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
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
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });
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
 

        pdf.setMessanger(messanger);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          fName = selectedFile.getPath();
          System.out.println(selectedFile.getName()+ " " + fName);
          
          //file transfer
//         Thread dialThread = new Thread(new Runnable(){
//             @Override
//             public void run(){
//                 createDialog();
//                 while(!Thread.currentThread().isInterrupted());
//                 System.out.println("EXIT TRANSFER");
//                    return;
//             }
//         }); 
          if(tcpcomm != null){
              
            if(sendFile(messanger,selectedFile)){
                //progressThread.interrupt();
                
                System.out.println("Plik wysłano pomyślnie");
                messanger.sendCommand(ControllCommands.F_START);
                
                
            }
            else{
                //błąd transmisji pliku!!!!
                System.out.println("Bład wysyłanai pliku");
            }
          }
          //destroyDialog();
//          dialThread.interrupt();
          
          chartAutoChage = true;
          if(fName.toLowerCase().endsWith(".ppt") || fName.toLowerCase().endsWith(".pptx"))
          {
              tpCharts.setSelectedIndex(0);
            pptViewer = new PPTfileViewer(fName);
            pptEffectsCombo.setEnabled(true);
            pptNextBtn.setEnabled(true);
            pptPrevBtn.setEnabled(true);
            if(pptEffects == false){
                pptPreviewLabel.setIcon(pptViewer.showSlide(pptViewer.currentSlide));
                pptSlideNrTxt.setText(pptViewer.currentSlide+"/"+pptViewer.slidesCount);
            }   
          }
          
          else if(fName.toLowerCase().endsWith(".pdf"))
          {
              //bing pdf keys
              pdf.viewPDF(fName);
              tpCharts.setSelectedIndex(1);
              pdf.setScrollBarListener(messanger);
              pdf.setButtonListeners(messanger);
              
          }
          chartAutoChage = false;
        }
    }//GEN-LAST:event_mOpenFileActionPerformed

    private void mExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mExitActionPerformed
        // TODO add your handling code here:
        
        //messanger.sendCommand(ControllCommands.F_NEXTP);
        /*if(pd != null){
            pd.pProgressBar.setValue(100);
            pd.dispatchEvent(new WindowEvent(pd, WindowEvent.WINDOW_CLOSING));
        }*/
        
        //createDialog();
        if(messanger != null){
            if(fName.toLowerCase().endsWith("pdf"))
                messanger.sendCommand(ControllCommands.F_DEXIT);
            else //if(fName.toLowerCase().endsWith("ppt") ||)
                messanger.sendCommand(ControllCommands.F_PEXIT);
            
            
            messanger.sendCommand(ControllCommands.RESTART_S);
            try {
                Thread.sleep(1000);
            }catch(InterruptedException ie){}

            messangerThread.interrupt();
        }
        System.exit(0);

    }//GEN-LAST:event_mExitActionPerformed

    private void mConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mConnectActionPerformed


        if(mConnect.getText().equalsIgnoreCase("connect")){
            if(tcpcomm == null){
                try{
                    tcpcomm = new TCPCommunication(ADDRESS, PORT);
                }catch(UnknownHostException he){
                    
                }catch(IOException e){
                    
                }
                
                receiver.clear();
                transmitter.clear();
                messanger = new Messanger(tcpcomm,receiver,transmitter);
                messangerThread = new Thread(messanger);
                messangerThread.start();
            }
            if(tcpcomm.isConnected()){
                mConnect.setText("Disconnect");
                mOpenFile.setEnabled(true);
                
            }
        }
        else{
            messanger.sendCommand(ControllCommands.RESTART_S);
            try{
                Thread.sleep(100);
            }catch(InterruptedException ie){}
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
                //tcpcomm.sendCommand((int)ControllCommands.ppt_nextp);
               messanger.sendCommand(ControllCommands.F_PNEXTP);
                
                
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
                //tcpcomm.sendCommand((int)ControllCommands.ppt_next);
            messanger.sendCommand(ControllCommands.F_PNEXTE);
        }
    }//GEN-LAST:event_pptNextBtnActionPerformed

    private void pptPrevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pptPrevBtnActionPerformed

        if(pptEffects == false){
            if(pptViewer.slidesCount>=pptViewer.currentSlide){
                pptViewer.currentSlide--;
                pptNextBtn.setEnabled(true);
                pptPreviewLabel.setIcon(pptViewer.showSlide(pptViewer.currentSlide));
                
                
                //TYMCZASOWA PROWIZORKA
                //tcpcomm.sendCommand((int)ControllCommands.ppt_prevp);
                messanger.sendCommand(ControllCommands.F_PPREVP);
                
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
                //tcpcomm.sendCommand((int)ControllCommands.ppt_prev);
            messanger.sendCommand(ControllCommands.F_PPREVE);
                
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
        
        messanger.sendCommand(ControllCommands.F_PFIRST);
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

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_menuAboutActionPerformed

    private void tpChartsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tpChartsStateChanged
        if(messanger != null && chartAutoChage == false){
            if(fName.toLowerCase().endsWith("pdf"))
                messanger.sendCommand(ControllCommands.F_DEXIT);
            else //if(fName.toLowerCase().endsWith("ppt") ||)
                messanger.sendCommand(ControllCommands.F_PEXIT);
            
            
            messanger.sendCommand(ControllCommands.RESTART_S);
            try {
                Thread.sleep(1000);
            }catch(InterruptedException ie){}

            messangerThread.interrupt();
            
            messanger = null;
            mConnect.setText("Connect");
            mOpenFile.setEnabled(false);
        }
    }//GEN-LAST:event_tpChartsStateChanged

    
    public void createDialog(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        pd = new ProgressDialog();
        //pd.setSize((int) Math.round(width*0.3), (int) Math.round(height*0.1));
        pd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        pd.setVisible(true);
        //pd.start();
    }
    
    public void destroyDialog(){
        //pd.exit();
        pd.setVisible(false);
        pd.dispose();
    }
    
    
    
    private boolean sendFile(Messanger m, File fd){
        m.sendCommand("F_STREAM");
        String tmpCmd = null;
        if((tmpCmd=m.recvCommand()).equalsIgnoreCase(ControllCommands.GET_SIZE)){
            System.out.println("Ask for size");        
            m.sendCommand(Integer.toString((int)fd.length()));
            
        }
        else
            System.out.println("Receiverd wrong command: " + tmpCmd + " instead of GET_SIZE");
       
        
        if((tmpCmd=m.recvCommand()).equalsIgnoreCase(ControllCommands.GET_HASH)){
            System.out.println("Ask for hash");        
            m.sendCommand(FileStreamer.getHash(fd.getAbsolutePath()));      
        }
        else
            System.out.println("Receiverd wrong command: " + tmpCmd + " instead of GET_HASH");
        
        if((tmpCmd=m.recvCommand()).equalsIgnoreCase(ControllCommands.GET_TYPE)){
            System.out.println("Ask for type");    
            String[] ext = fd.getAbsolutePath().toLowerCase().split("\\.");
            String extention = ext[ext.length-1];       
            m.sendCommand(extention); 

        }
        else
            System.out.println("Receiverd wrong command: " + tmpCmd + " instead of GET_TYPE");
                        
        m.streamFile(fd);
        while(m.streamDone != true){};
        m.streamDone = false;
        
        if(messanger.recvCommand().equalsIgnoreCase(ControllCommands.U_NOERROR)) {
            System.out.println("U_NOERROR"+" NO ERROR WHILE TRANSFER");
            //messanger.NO_STREAM_ERROR = 1;           
            messanger.sendCommand(ControllCommands.F_DONE);
        }

        String stramSucces = m.recvCommand();
        System.out.println("Stream done with: "+stramSucces);
        
        if(stramSucces.equalsIgnoreCase(ControllCommands.F_RECEIVED))
        {
            return true;
        }
        else if(stramSucces.equalsIgnoreCase(ControllCommands.F_ERROR))
        {
            return true;//false;
        }
        
        else if(stramSucces.equalsIgnoreCase(ControllCommands.U_ERROR))
        {
            //return false;
            //U_ERROR_OCCURE = true;
            //progress_dialog.dismiss();
            //IS_STREAMING = false;


            try {
                Thread.sleep(3000);
            }catch(InterruptedException ie){}

            messanger.sendCommand(ControllCommands.RESTART_S);
            try {
                Thread.sleep(700);
            }catch(InterruptedException ie){}

            messangerThread.interrupt();
            messanger = null;
            fName = null;

            return false;
        }
        else if(stramSucces.equalsIgnoreCase(ControllCommands.U_NOERROR))
        {
            return true;
        }
        
        else{
            return false;
        }
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
                MainGui GUI = new MainGui();
                GUI.setVisible(true);
                GUI.setExtendedState(GUI.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                GUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    public static javax.swing.JMenuItem mConnect;
    private javax.swing.JMenuItem mExit;
    public static javax.swing.JMenuItem mOpenFile;
    private javax.swing.JPopupMenu.Separator mSeparator;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    public javax.swing.JPanel moviePane;
    public javax.swing.JPanel pdfPane;
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
