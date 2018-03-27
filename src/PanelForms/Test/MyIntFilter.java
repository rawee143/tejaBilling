/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PanelForms.Test;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author ranjan
 */
public class MyIntFilter extends DocumentFilter{
    
    @Override
   public void insertString (FilterBypass fb, int offset, String string,
         AttributeSet attr) throws BadLocationException {

      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.insert(offset, string);
      //System.out.println("insert "+sb);
      if (test(sb.toString())) {
         super.insertString(fb, offset, string, attr);
      } else {
         JOptionPane.showMessageDialog(null,"Not Allowed");
      }
   }

   private boolean test(String text) {
      try {
             Long.parseLong(text);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }

   @Override
   public void replace(FilterBypass fb, int offset, int length, String text,
         AttributeSet attrs) throws BadLocationException {

      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0,doc.getLength()));
      sb.replace(offset, offset+length, text);
      
      if (test(sb.toString())) {
         super.replace(fb, offset, length, text, attrs);
         
      } else {
         JOptionPane.showMessageDialog(null,"Not Allowed");
      
      }

   }

   @Override
   public void remove(FilterBypass fb, int offset, int length)
         throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset);
      //System.out.println("remove "+sb);
      if (test(sb.toString())) {
         super.remove(fb, offset, length);
      } else {
         JOptionPane.showMessageDialog(null,"Not Allowed");
      }

   }
}
    

