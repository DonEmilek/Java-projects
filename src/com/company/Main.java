package com.company;
import javax.swing.*;
public class Main extends JFrame{
    public Main(){
        GUI gui = new GUI();
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setTitle("CryptoHelper");
        this.requestFocus();
        this.setResizable(false);
        this.setBounds(0,0,500,250);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(gui.mainP);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();


            }
        });
    }
}
