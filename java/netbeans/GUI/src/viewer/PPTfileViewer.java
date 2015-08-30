/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

/**
 *
 * @author PatLas
 */
public class PPTfileViewer {
    private String fPath = null;
    private XMLSlideShow pptxShow = null;
    private SlideShow pptShow = null;
    public int slidesCount = 0;
    public int currentSlide = 1;
    private Dimension pptDim = null;
    
    public PPTfileViewer(String filePath){
        fPath = filePath;
        if(fPath.toLowerCase().endsWith(".pptx")==true){
            try{
                pptxShow  = new XMLSlideShow(new FileInputStream(fPath));

            }catch(IOException e){

            }

           XSLFSlide[] s = pptxShow.getSlides();
           slidesCount = s.length;
           System.out.println(s.length);
           pptDim = pptxShow.getPageSize();
        }
        else if(fPath.toLowerCase().endsWith(".ppt")==true){
            try {
                pptShow = new SlideShow(new HSLFSlideShow(fPath));
            }catch(IOException e){
                
            }
            
            Slide[] s = pptShow.getSlides();
            slidesCount = s.length;
            System.out.println(s.length);
            pptDim = pptShow.getPageSize();    
        }   
    }
        
    
    public ImageIcon showSlide(int nr){
        
        BufferedImage img = new BufferedImage(pptDim.width, pptDim.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pptDim.width, pptDim.height));
       
        if(fPath.toLowerCase().endsWith(".pptx")==true){
            pptxShow.getSlides()[nr-1].draw(graphics);
        }
        else if(fPath.toLowerCase().endsWith(".ppt")==true){
            pptShow.getSlides()[nr-1].draw(graphics);
        }
        
        return (new ImageIcon(img));
        //lblPresentasi.setIcon(icon);
    }
    
}
