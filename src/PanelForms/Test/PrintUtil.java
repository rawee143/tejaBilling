/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PanelForms.Test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.RepaintManager;

public class PrintUtil implements Printable, Pageable {
   private Component componentToBePrinted;
   private PageFormat format;
   private int numPages;

   public PrintUtil(Component componentToBePrinted) {
      this.componentToBePrinted = componentToBePrinted;
      
   }
   
   public static void printComponent(Component c) {
       
        new PrintUtil(c).print();
    }
    

   public void print() {
      PrinterJob printJob = PrinterJob.getPrinterJob();
      printJob.setPrintable(this);
      printJob.setPageable(this);
      format = printJob.defaultPage();
      Dimension page = this.componentToBePrinted.getPreferredSize();
      numPages =(int) Math.ceil(page.height/format.getImageableY());

   if (printJob.printDialog())
      try {
        printJob.print();
      } catch(PrinterException pe) {
        System.out.println("Error printing: " + pe);
      }
   }

   @Override
 public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
   if ((pageIndex < 0) | (pageIndex >= numPages)) {
      return(NO_SUCH_PAGE);
   } else {
     Graphics2D g2d = (Graphics2D)g;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY()-pageIndex*componentToBePrinted.getPreferredSize().height);
     g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY()- pageIndex*componentToBePrinted.getPreferredSize().height);
     disableDoubleBuffering(componentToBePrinted);
     componentToBePrinted.paint(g2d);
     enableDoubleBuffering(componentToBePrinted);
     return(PAGE_EXISTS);
  }
}

public static void disableDoubleBuffering(Component c) {
   RepaintManager currentManager = RepaintManager.currentManager(c);
   currentManager.setDoubleBufferingEnabled(false);
}

   public static void enableDoubleBuffering(Component c) {
       RepaintManager currentManager = RepaintManager.currentManager(c);
       currentManager.setDoubleBufferingEnabled(true);
   }

   @Override
   public int getNumberOfPages() {
      // TODO Auto-generated method stub
      return numPages;
   }

 @Override
 public PageFormat getPageFormat(int arg0) throws IndexOutOfBoundsException {
      return format;
 }

 @Override
    public Printable getPrintable(int arg0) throws IndexOutOfBoundsException {
      // TODO Auto-generated method stub
      return this;
    }
}
