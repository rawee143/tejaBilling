/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PanelForms.Test;

/**
 *
 * @author ranjan
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ranjan
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PrintPreview extends JFrame
{
    private static final long serialVersionUID = 1L;
    private BorderLayout borderLayout1 = new BorderLayout();
    private FlowLayout flow = new FlowLayout();
    private Font font = new Font("Tahoma", 0, 11);
    private int panelsNo = 2;
    private Dimension buttonDimension = new Dimension(120, 25);
    private JPanel centerPanel = new JPanel();
    private JScrollPane scroll = new JScrollPane(centerPanel);
    private PagePanel[] previewPanel = null;
    private JPanel operationsPanel = new JPanel();
    private JButton nextButton = new JButton("Next");
    private JButton previousButton = new JButton("Previous");
    private JButton printButton = new JButton("Print");

    public PrintPreview(int panelsNo)
    {
        try
        {
            this.panelsNo = panelsNo;
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            previewPanel = new PagePanel[this.panelsNo];
            jbInit();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        flow.setHgap(20);
        flow.setVgap(20);
        centerPanel.setLayout(flow);
        centerPanel.setBackground(new Color(51, 51, 51));
        centerPanel.setPreferredSize(new Dimension(800, panelsNo * (842 + 40)));

        for (int i = 0; i < panelsNo; i++)
        {
            previewPanel[i] = new PagePanel(i + 1);
            centerPanel.add(previewPanel[i]);

}



printButton.setForeground(Color.green.darker());

printButton.addActionListener(new ActionListener()

{



public void actionPerformed(ActionEvent evt)

{

printActionPerformed(evt);

}

});



operationsPanel.setPreferredSize(new Dimension(10, 40));

operationsPanel.add(previousButton, null);

operationsPanel.add(nextButton, null);

operationsPanel.add(printButton, null);



Component comps[] = operationsPanel.getComponents();

for (Component comp : comps)

{

((JButton) comp).setFont(font);

((JButton) comp).setPreferredSize(buttonDimension);

}

this.setTitle("Print");

this.getContentPane().setLayout(borderLayout1);

this.setBounds(new Rectangle(10, 10, 650, 930));

this.getContentPane().add(scroll, BorderLayout.CENTER);

this.getContentPane().add(operationsPanel, BorderLayout.NORTH);

}



private void printActionPerformed(java.awt.event.ActionEvent event)

{



PrinterJob printJob = PrinterJob.getPrinterJob();

PageFormat pf = printJob.defaultPage();

pf.setOrientation(PageFormat.PORTRAIT);

Book book = new Book();

// First Page

book.append(previewPanel[0], pf);

// Second Page

book.append(previewPanel[1], pf);



printJob.setPageable(book);



if (printJob.printDialog())

{

try

{

printJob.print();

} catch (Exception ex)

{

ex.printStackTrace();

}

}

}



public static void main(String... strings)

{

PrintPreview pr = new PrintPreview(2);

pr.setVisible(true);

}
}


