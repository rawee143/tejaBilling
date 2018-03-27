/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PanelForms.Test;

//import com.itextpdf.text.Image;
//import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.JPanel;

/**
 *
 * @author ranjan
 */
public class LargePanelToImageMCVE {

    public LargePanelToImageMCVE(JPanel panel, String pid) {
        
        final java.awt.Image image = getImageFromPanel(panel);

        /* This was just a text panel to make sure the full panel was
         * drawn to the panel.
         */
        JPanel newPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(595, 842);
            }
        };

        /* Print Image to PDF */
        String fileName = "report/"+pid+".pdf";
        printToPDF(image, fileName);

        /* This was just a test to show the newPanel drew the entire
         * original panel (scaled)
         */
        //JOptionPane.showMessageDialog(null, newPanel);

    }

    public void printToPDF(java.awt.Image awtImage, String fileName) {
        try {
            Document d = new Document();
            PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(
                    fileName));
            d.open();


            Image iTextImage = Image.getInstance(writer, awtImage, 1);
            iTextImage.setAbsolutePosition(25, 25);
            iTextImage.scalePercent(90);
            d.add(iTextImage);

            d.close();

        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    public static java.awt.Image getImageFromPanel(Component component) {

        BufferedImage image = new BufferedImage(component.getWidth(),
                component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;
    }
}
