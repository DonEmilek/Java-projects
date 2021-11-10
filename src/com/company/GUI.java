package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class GUI {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,40));
    public GUI(){
        initComp();
        frame.setDefaultCloseOperation(3);
        frame.requestFocus();
        frame.setBounds(0,0,400,200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().add(panel);
    }
    int x = 0;
    JButton countB = new JButton("Dodaj");
    JButton export = new JButton("Export");
    JButton reset = new JButton("Reset");
    JLabel ilosc = new JLabel("Ilość: ");
    JLabel value = new JLabel(String.valueOf(x));
    ButtonGroup g = new ButtonGroup();
    JRadioButton radioButton1 = new JRadioButton("Matematyka");
    JRadioButton radioButton2 = new JRadioButton("Fizyka");
    JRadioButton radioButton3 = new JRadioButton("Informatyka");

    public void initComp(){
        g.add(radioButton1);
        g.add(radioButton2);
        g.add(radioButton3);
        panel.add(radioButton1);
        panel.add(radioButton2);
        panel.add(radioButton3);
        panel.add(ilosc);
        panel.add(value);
        panel.add(countB);
        panel.add(export);
        panel.add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value.setText("0");
                g.clearSelection();
            }
        });
        radioButton1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);


                if(e.getKeyCode()==KeyEvent.VK_ADD){
                    value.setText(String.valueOf(++x));
                }

            }
        });
        radioButton2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);


                if(e.getKeyCode()==KeyEvent.VK_ADD){
                    value.setText(String.valueOf(++x));
                }
            }
        });
        radioButton3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);


                if(e.getKeyCode()==KeyEvent.VK_ADD){
                    value.setText(String.valueOf(++x));
                }
            }
        });
        countB.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);


                if(e.getKeyCode()==KeyEvent.VK_ADD){
                    value.setText(String.valueOf(++x));
                }
            }
        });
        countB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value.setText(String.valueOf(++x));
            }
        });
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String names = null;
                if(radioButton1.isSelected())names="Matematyka";
                if(radioButton2.isSelected())names="Fizyka";
                if(radioButton3.isSelected())names="Informatyka";
                Date date = Calendar.getInstance().getTime();
                final String ext = ".txt";
                File dir = new File("C:\\Users\\User\\Desktop\\ExportedFiles");
                File file = new File(dir+File.separator+names+ext);
                while(!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                try {
                    PrintWriter writer = new PrintWriter(file);
                    writer.println(String.valueOf(date));
                    writer.println("Ilość zadań: " + value.getText());
                    JOptionPane.showMessageDialog(panel,"Utworzono plik!");
                    writer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        });
    }
}
