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
public class MyAlphaFilter extends DocumentFilter {
    @Override
    public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0,doc.getLength()));
      sb.replace(i, i+i1, string);
      if (test(sb.toString())) {
        super.replace(fb, i, i1, string, as);
         
      } else {
         JOptionPane.showMessageDialog(null,"Not Allowed");
      
    }
    }
    private boolean test(String string) {
      for(int n = 0; n < string.length(); n++) {
        if (Character.isLetter(string.charAt(n))|| string.charAt(n) == ' ') {
         return true;
         
            } 
        
       }
       return false;
   }

    @Override
    public void remove(FilterBypass fb, int i, int i1) throws BadLocationException {
        super.remove(fb, i, i1);
    }

    @Override
    public void insertString(FilterBypass fb, int i, String string, AttributeSet as) throws BadLocationException {
        
        for (int n = string.length(); n >= 0; n--) {//an inserted string may be more than a single character i.e a copy and paste of 'aaa123d', also we iterate from the back as super.XX implementation will put last insterted string first and so on thus 'aa123d' would be 'daa', but because we iterate from the back its 'aad' like we want
            char c = string.charAt(n - 1);//get a single character of the string
            //System.out.println(c);
            if (Character.isAlphabetic(c) || c == ' ') {//if its an alphabetic character or white space
                super.insertString(fb, i, string, as);
            } else {
                JOptionPane.showMessageDialog(null,"Not Allowed");
            }
        }
        

    }
}
