/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package superpos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Ashwi
 */
public class MonthlySalesReportGUI extends JFrame{
        public MonthlySalesReportGUI() {
        initComponents();
    }

    private void initComponents() {
        JButton sendButton = new JButton("Send Report");
        sendButton.addActionListener(this::sendButtonActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(sendButton)
                                .addContainerGap(150, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(sendButton)
                                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        MonthlySalesReport reportGenerator = new MonthlySalesReport();
            try {
                reportGenerator.generateMonthlySalesReport();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MonthlySalesReportGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MonthlySalesReportGUI().setVisible(true);
            }
        });
    }
    
    
}
