/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;

/**
 *
 * @author Max
 */
public class ProgressDialog extends javax.swing.JFrame {

 Thread t = null;
    /**
     * Creates new form ProgressDialog
     */
    public ProgressDialog() {
   
        initComponents();

        pProgressBar.setValue(30);
        pProgressBar.setVisible(true);
        pProgressBar.setIndeterminate(true);
        
        
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pProgressBar = new javax.swing.JProgressBar();

        setTitle("Opening file...");
        setAlwaysOnTop(true);
        setIconImage((new ImageIcon(getClass().getResource("/gui/icons/conections-32.png"))).getImage());
        setName("Opening file..."); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        getContentPane().setLayout(new java.awt.CardLayout());

        pProgressBar.setValue(30);
        pProgressBar.setIndeterminate(true);
        pProgressBar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pProgressBarStateChanged(evt);
            }
        });
        getContentPane().add(pProgressBar, "card2");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pProgressBarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pProgressBarStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_pProgressBarStateChanged

    /**
     * @param args the command line arguments
     */
    public void start() {

        
        t = new Thread(new Runnable() {
            public void run() {
                
                pProgressBar.setVisible(true);
                pProgressBar.setIndeterminate(true);
                while(!Thread.currentThread().isInterrupted());
                return;
            }
        });
        t.start();
    }

    public void exit(){
        t.interrupt();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JProgressBar pProgressBar;
    // End of variables declaration//GEN-END:variables
}
