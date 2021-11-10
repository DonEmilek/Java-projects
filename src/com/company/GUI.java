package com.company;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.*;
import java.text.DecimalFormat;

public class GUI extends JPanel{
    final String[] percentsComboBox = {"2%","3%", "5%", "8%", "10%", "15%", "20%"};
    JFrame frame = new JFrame();
    JPanel mainP;
    JButton calculate, clear;
    JComboBox comboBox;
    JTextField buyf,sellf,amountf;
    JLabel buyl, selll, amountl, earn, earnMoney;
    JCheckBox checkBox = new JCheckBox("Percent Mode");
    Image img;
    public GUI(){
        this.setBackground(Color.black);
        img = Toolkit.getDefaultToolkit().createImage("C:\\Users\\User\\Desktop\\CryptoHelper\\binance.jpg");
        this.repaint();
        initComp();
        conditionsCheckBox();
    }
    public void initComp(){
        mainP = new JPanel(new FlowLayout(FlowLayout.TRAILING,100,10));
        comboBox = new JComboBox(percentsComboBox);
        calculate = new JButton("Calculate");
        clear = new JButton("Clear all");
        buyf = new JTextField(10);
        sellf = new JTextField(10);
        amountf = new JTextField(10);
        buyl = new JLabel("Buy price: ");
        selll = new JLabel("Sell price: ");
        amountl = new JLabel("Amount[$]: ");
        earnMoney = new JLabel("0");
        earn = new JLabel("Earn: ");

        mainP.add(buyl);
        mainP.add(buyf);
        mainP.add(selll);
        mainP.add(sellf);
        mainP.add(amountl);
        mainP.add(amountf);
        mainP.add(checkBox);
        mainP.add(comboBox);
        mainP.add(calculate);
        mainP.add(clear);
        mainP.add(earn);
        mainP.add(earnMoney);

        comboBox.setEnabled(false);

        calculate.setPreferredSize(new Dimension(100,30));
        clear.setPreferredSize(new Dimension(100,30));

        calculate.addActionListener(new Aritmetic());
        clear.addActionListener(new ClearHandler());

    }
    public void conditionsCheckBox(){
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(!checkBox.isSelected()){
                    comboBox.setEnabled(false);
                    sellf.setEnabled(true);
                    amountf.setEnabled(true);
                }else{
                    comboBox.setEnabled(true);
                    sellf.setEnabled(false);
                    amountf.setEnabled(false);

                    System.out.println("dziala zaznaczenie");
                }

            }
        });
    }
    private class Aritmetic implements ActionListener{

        public float getBuy(){
            float x = Float.parseFloat(buyf.getText().replace(',','.'));
            return x;
        }
        public double getBuyD(){
            double x = Double.parseDouble(buyf.getText().replace(',','.'));
            return x;
        }
        public float getSell(){
            float x = Float.parseFloat(sellf.getText().replace(',','.'));
            return x;
        }
        public float getAmount(){
            float x = Float.parseFloat(amountf.getText().replace(',','.'));
            return x;
        }
        public float getCoins(){
            float x = getAmount()/getBuy();
            return x;
        }
        public float getEarn(){
            float select = comboBox.getSelectedIndex(); // 2;3;5;8;10;15;20
            float score = 0;
            score = (getSell()-getBuy())*getCoins();
            return score;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkBox.isSelected()){
                double q = 1.02;
                double w = 1.03;
                double ee = 1.05;
                double r = 1.08;
                double t = 1.1;
                double y = 1.15;
                double u = 1.2;
                double z=0;
                switch(comboBox.getSelectedIndex()){
                    case 0:
                        z = getBuyD()*q;
                        break;
                    case 1:
                        z = getBuyD()*w;
                        break;
                    case 2:
                        z = getBuyD()*ee;
                        break;
                    case 3:
                        z = getBuyD()*r;
                        break;
                    case 4:
                        z = getBuyD()*t;
                        break;
                    case 5:
                        z = getBuyD()*y;
                        break;
                    case 6:
                        z = getBuyD()*u;
                        break;
                }
                System.out.println("else command");
                earn.setText("For "+comboBox.getSelectedItem().toString()+" sell for :"+z);
                earnMoney.setText("");
                mainP.remove(earnMoney);
            }else {
                if (buyf.getText().isEmpty() || sellf.getText().isEmpty() || amountf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mainP, "Fill in the empty space", "Alert", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (!checkBox.isSelected()) {
                        mainP.add(earnMoney);
                        DecimalFormat df = new DecimalFormat("###.##");
                        earn.setText("Earn: ");
                        earnMoney.setText("$" + df.format(getEarn()));
                        System.out.println(getCoins());
                        System.out.println(getEarn());
                        System.out.println("AricPrivListener");
                    } else {
                        DecimalFormat df2 = new DecimalFormat("###.##");
                        double q = 1.02;
                        double w = 1.03;
                        double ee = 1.05;
                        double r = 1.08;
                        double t = 1.1;
                        double y = 1.15;
                        double u = 1.2;
                        double z = 0;
                        switch (comboBox.getSelectedIndex()) {
                            case 0:
                                z = getBuyD() * q;
                                break;
                            case 1:
                                z = getBuyD() * w;
                                break;
                            case 2:
                                z = getBuyD() * ee;
                                break;
                            case 3:
                                z = getBuyD() * r;
                                break;
                            case 4:
                                z = getBuyD() * t;
                                break;
                            case 5:
                                z = getBuyD() * y;
                                break;
                            case 6:
                                z = getBuyD() * u;
                                break;
                        }
                        System.out.println("else command");
                        earn.setText("For " + comboBox.getSelectedItem().toString() + " sell for :" + df2.format(z));
                        earnMoney.setText("");
                        mainP.remove(earnMoney);
                    }
                }
            }
        }
    }
    private class ClearHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            buyf.setText("");
            sellf.setText("");
            amountf.setText("");
        }
    }
}
