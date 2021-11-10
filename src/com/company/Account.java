package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Account{
    Transfer t = new Transfer();
    Account(){
        initComponents();
    }
    JPanel regPanel;
    JLabel logLabel, passLabel, infoLabel;
    JTextField logField, passField;
    JRadioButton radioButtonYes, radioButtonNo;
    JButton regButton, backButton;
    ButtonGroup group;

    private final String LOGIN = "Login";
    private final String PASS = "Password";
    private final String UNDER = "Are you under 18?";
    private final String REGISTER = "Register";
    private final String BACK = "Back";
    private final String YES = "Yes";
    private final String NO = "No";
    private void initComponents(){
        regPanel = new JPanel();

        regPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 80,30));

        group = new ButtonGroup();
        logLabel = new JLabel(LOGIN);
        logField = new JTextField(10);
        passLabel = new JLabel(PASS);
        passField = new JTextField(10);
        infoLabel = new JLabel(UNDER);
        radioButtonYes = new JRadioButton(YES, false);
        radioButtonNo = new JRadioButton(NO, false);
        backButton = new JButton(BACK);
        regButton = new JButton(REGISTER);

        group.add(radioButtonYes);
        group.add(radioButtonNo);

        regPanel.add(logLabel);
        regPanel.add(logField);
        regPanel.add(passLabel);
        regPanel.add(passField);
        regPanel.add(infoLabel);
        regPanel.add(radioButtonNo);
        regPanel.add(radioButtonYes);
        regPanel.add(backButton);
        regPanel.add(regButton);
    }
    private final String INFO_1 = "You're now allowed to register, when you're under 18";
    private final String INFO_2 = "Fill the age survery";
    private final String INFO_3 = "Success";
    protected boolean isAdult(){
        boolean isAdult = false;
        if(radioButtonYes.isSelected())
            JOptionPane.showMessageDialog(regPanel, INFO_1, "Error", 2);
        else if(!radioButtonYes.isSelected() && !radioButtonNo.isSelected())
            JOptionPane.showMessageDialog(regPanel, INFO_2, "Error", 2);
        else{
            isAdult = true;
            JOptionPane.showMessageDialog(regPanel, INFO_3, "Information", 1);
        }

        return isAdult;
    }

    private String getName(){
        return logField.getText();
    }
    private String getNameExtended(){
        return logField.getText()+".txt";
    }

    private String getPass(){
        return passField.getText();
    }
    private File loginFile;
    private PrintWriter writer;
    private List<String> results;
    private File[] files;
    private final String BUSY = "Your login is busy";

    private boolean isUserExists(){
        final String DIR_PATH = t.DATA + File.separator + t.ACCOUNTS;

        boolean isExists = false;

        results = new ArrayList<>();
        files = new File(DIR_PATH).listFiles();
        for(File file: files){
            if(file.isFile())
                results.add(file.getName());
        }
        for(String s: results){
            System.out.println("name acc: "+s);
            if(getNameExtended().equals(s)){
                isExists = false;
                JOptionPane.showMessageDialog(regPanel, BUSY, "Error", 3);
            }
            else
                isExists = true;
        }
        return isExists;
    }
    protected void createUser(){
        final String TXT = ".txt";
        final String PATH = t.DATA + File.separator + t.ACCOUNTS + File.separator + getName() + TXT;
        loginFile = new File(PATH);
        if(isUserExists() && isAdult()) {
            try {
                writer = new PrintWriter(loginFile);
                writer.println(getName());
                writer.print(getPass());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private File fundsFile;
    private PrintWriter fundsWriter;
    private String startFunds = "500";
    protected void createFunds(){
        final String FUNDS_TXT = "Funds.txt";
        final String PATH = t.DATA + File.separator + t.FUNDS + File.separator + getName() + FUNDS_TXT;
        fundsFile = new File(PATH);
        try {
            fundsWriter = new PrintWriter(fundsFile);
            fundsWriter.println(startFunds);
            fundsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
