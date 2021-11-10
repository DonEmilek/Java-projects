package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends JFrame{
    Main() throws IOException {
        Layout layout = new Layout();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("BankSystem");

        this.getContentPane().add(layout.cards);
        this.setBounds(0, 0, 450, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setJMenuBar(layout.menuBar);
        layout.menuBar.setVisible(false);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Main();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
