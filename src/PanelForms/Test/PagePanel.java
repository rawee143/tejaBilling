/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PanelForms.Test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
/**
 *
 * @author ranjan
 */
public class PagePanel extends JPanel implements Printable
{
    public static final Dimension A4_SIZE = new Dimension(595, 842);
    public static final Dimension A3_SIZE = new Dimension(842, 595 * 2);
    public static final Dimension A2_SIZE = new Dimension(595 * 2, 842 * 2);
    public static Dimension DEFAULT_SIZE = A4_SIZE;    
    private GridLayout grid = new GridLayout(4, 2);
    public int id = 0;
               
    public PagePanel(int p_number)
    {
        initComponents();
        this.id=p_number;
    }

    private void initComponents()
    {
        grid.setHgap(10);
        grid.setVgap(10);
        this.setMaximumSize(DEFAULT_SIZE);
        this.setMinimumSize(DEFAULT_SIZE);
        this.setPreferredSize(DEFAULT_SIZE);
        this.setBackground(Color.white);
        this.setLayout(grid);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        this.setSize(DEFAULT_SIZE);       
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);        
        g.setColor(Color.BLUE);
        g.drawString("This is page: "+id, 30, 30);
    }
                    
    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException
    {                           
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());        
        this.paint(g2d);
        return (PAGE_EXISTS);        
    } 
}