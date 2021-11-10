package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

class Layout extends JPanel {
    Account account = new Account();
    Transfer t = new Transfer();
    JPanel cards = new JPanel();
    JPanel accPanel = new JPanel();
    JPanel loginPanel = new JPanel();
    CardLayout cl = new CardLayout();

    protected JMenuBar menuBar;
    protected JMenu acc;
    protected JMenuItem logout;
    final String ACCOUNT = "Account";
    final String LOGOUT = "Logout";
    Layout(){
        cards.setLayout(cl);
        cards.add(accPanel,"acc");
        cards.add(loginPanel,"log");
        cards.add(account.regPanel, "reg");
        cl.show(cards,"log");
        menuBar = new JMenuBar();
        acc = new JMenu(ACCOUNT);
        logout = new JMenuItem(LOGOUT);
        acc.add(logout);
        menuBar.add(acc);

        createDataDir();
        createFundsDir();
        createAccountsDir();
        createFundsFile();
        createAccountFile();
        writeLoginPassword();
        writeAdminFunds();
        loginLayout();
        logoutListener();
        accLayout();
        accButtonListener();
        loginButtonListener();
        backButtonListener();
        registerButtonListener();
        createAccountButtonListener();
        sendButtonActionListener();
    }

    final String LOGIN_TEXT = "Login";
    final String PASS_TEXT = "Password";
    final String SIGN_IN_TEXT = "Sign in";
    final String REGISTER_TEXT = "Register";

    protected JLabel loginLabel, passLabel;
    protected JButton registerButton, loginButton;
    protected JTextField loginField;
    protected JPasswordField passField;

    private void loginLayout(){
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,75));

        loginLabel = new JLabel(LOGIN_TEXT);
        loginField = new JTextField(10);
        passLabel = new JLabel(PASS_TEXT);
        passField = new JPasswordField(10);
        loginButton = new JButton(SIGN_IN_TEXT);
        registerButton = new JButton(REGISTER_TEXT);

        loginPanel.add(loginLabel);
        loginPanel.add(loginField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        loginButton.setPreferredSize(new Dimension(100, 40));
        registerButton.setPreferredSize(new Dimension(100, 40));
    }
    JButton accButton;
    JLabel accFundsLabel, accGetFunds, accWelcome;
    private void accLayout(){
        accPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200,40));

        accButton = new JButton("Make a transfer");
        accFundsLabel = new JLabel("Available funds: ");
        accGetFunds = new JLabel("test");
        accWelcome = new JLabel();

        accPanel.add(accWelcome);
        accPanel.add(accFundsLabel);
        accPanel.add(accGetFunds);
        accPanel.add(accButton);

    }

    private void accButtonListener(){
        accButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                createJDialog();
            }
        });
    }

    protected void logoutListener(){
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                cl.show(cards, "log");
                menuBar.setVisible(false);
                setCleanFields();
                removeClient();
            }
        });
    }
    final String TITLE = "Transfer";
    final String TO_USER = "Target account name";
    final String AMOUNT = "Amount";
    final String SEND = "Send";

    protected JDialog dialog = new JDialog();
    protected JPanel dialogPanel = new JPanel();
    protected JButton sendButton = new JButton(SEND);
    protected JLabel toUserLabel = new JLabel(TO_USER);
    protected JLabel amountLabel = new JLabel(AMOUNT);
    public JTextField toUserField = new JTextField(10);
    protected JTextField amountField = new JTextField(10);

    private void createJDialog(){
        System.out.println("the following information is taken from the client List");
        System.out.println(clientList.get(0));

        dialogPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        dialogPanel.add(toUserLabel);
        dialogPanel.add(toUserField);
        dialogPanel.add(amountLabel);
        dialogPanel.add(amountField);
        dialogPanel.add(sendButton);

        dialog.getContentPane().add(dialogPanel);
        dialog.setBounds(0,0,300,150);
        dialog.setTitle(TITLE);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        amountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if(!Character.isDigit(c))
                    e.consume();
            }
        });
        if(!dialog.isActive()){
            toUserField.setText("");
            amountField.setText("");
        }
    }
    public void sendButtonActionListener() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try{
                    final String RECEIVER_FUNDS_PATH = t.DATA + File.separator + t.FUNDS + File.separator + getReceiverAccountName() + "Funds.txt";
                    File receiverFile = new File(RECEIVER_FUNDS_PATH);
                    if(!receiverFile.exists()){
                        JOptionPane.showMessageDialog(loginPanel, "User doesn't exist!", "Error", 1);
                    }else {
                        transaction();
                    }
                }catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createDataDir(){
        t.dataDir = new File(t.DATA);
        t.dataDir.mkdir();
    }

    private void createFundsDir(){
        t.fundsDir = new File(t.DATA + File.separator + t.FUNDS);
        t.fundsDir.mkdir();
    }



    private void createAccountsDir(){
        t.accountDir = new File(t.DATA + File.separator + t.ACCOUNTS);
        t.accountDir.mkdir();
    }

    File moneyFile;
    final String START_FUNDS = "adminFunds.txt";

    private void createFundsFile(){
        moneyFile = new File(t.DATA + File.separator + t.FUNDS + File.separator + START_FUNDS);
        if(!moneyFile.exists()) {
            try {
                moneyFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    File userFile;
    final String ADMIN_ACCOUNT = "admin.txt";

    private void createAccountFile(){
        userFile = new File(t.DATA + File.separator + t.ACCOUNTS + File.separator + ADMIN_ACCOUNT);
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeLoginPassword(){
        PrintWriter writer;
        try {
            writer = new PrintWriter(userFile);
            writer.println("admin");
            writer.print("pass");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAdminFunds(){
        PrintWriter writer;
        try{
            writer = new PrintWriter(moneyFile);
            writer.println("12500");
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void setFundsLabel(String value){
        accGetFunds.setText(value);
    }

    protected void setAccWelcome(String sent){
        accWelcome.setText(sent);
    }

    public String getAccountName(){
        return loginField.getText().trim();
    }

    public String getFunds(){
        final String FUNDS_PREFIX = "Funds.txt";
        final String FUNDS_PATH = t.DATA + File.separator + t.FUNDS + File.separator + getAccountName() + FUNDS_PREFIX;
        File fundsFile = new File(FUNDS_PATH);
        BufferedReader reader;
        String funds=null;
        try {
            reader = new BufferedReader(new FileReader(fundsFile));
            funds = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return funds;
    }

    private List passwordInArray(){
        List list = new ArrayList();
        BufferedReader reader;
        final String PATH = t.DATA + File.separator + t.ACCOUNTS + File.separator + getAccountName() + ".txt";
        try{
            reader = new BufferedReader(new FileReader(PATH));
            String s;
            while((s=reader.readLine())!=null){
                list.add(s);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    private char[] isolatePassword(){
        String tmp = String.valueOf(passwordInArray().get(1));
        char[] res = tmp.toCharArray();
        return res;
    }
    protected boolean validatePassword(){
        BufferedReader reader;
        List<String> data = new ArrayList<>();
        boolean isTrue = false;
        final String PATH = t.DATA + File.separator + t.ACCOUNTS + File.separator + getAccountName() + ".txt";
        File isEx = new File(PATH);
        if(isEx.exists()) {
            try {
                reader = new BufferedReader(new FileReader(PATH));
                String tmp;
                while ((tmp = reader.readLine()) != null)
                    data.add(tmp);
                isTrue = data.get(0).equals(getAccountName()) && Arrays.equals(convertPassword(), isolatePassword());
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isTrue;
    }
    List<String> clientList = new ArrayList<>();
    String sender;
    protected void loginButtonListener(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if(validatePassword()) {
                    menuBar.setVisible(true);
                    setFundsLabel(getFunds());
                    setAccWelcome("Welcome back, " + getAccountName() + "!");
                    cl.show(cards,"acc");
                    sender=getAccountName();
                    clientList.add(sender);
                    System.out.println(clientList.get(0));
                    System.out.println("upper data is logged user");
                }
                else{
                    JOptionPane.showMessageDialog(loginPanel, "Incorrect login or password", "Error", 3);
                    setCleanFields();
                }

            }
        });
    }

    private void registerButtonListener(){
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                cl.show(cards, "reg");
            }
        });
    }

    private void backButtonListener(){
        account.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                cl.show(cards, "log");
                clearForm();
            }
        });
    }

    private void createAccountButtonListener(){
        account.regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                account.createUser();
                account.createFunds();
                cl.show(cards,"log");
                clearForm();
            }
        });
    }
    protected void setCleanFields(){
        loginField.setText("");
        passField.setText("");
        loginField.setBackground(Color.white);
        passField.setBackground(Color.white);
    }
    private void clearForm(){
        account.logField.setText("");
        account.passField.setText("");
        account.group.clearSelection();
    }
    private char[] convertPassword(){
        char[] password = passField.getPassword();
        return password;
    }

    protected void removeClient(){
        clientList.remove(0);
    }

    private String getReceiverAccountName(){
        return toUserField.getText().trim();
    }

    private void transaction() throws IOException{
        final String SENDER_FUNDS_PATH = t.DATA + File.separator + t.FUNDS + File.separator + clientList.get(0) + "Funds.txt";
        final String RECEIVER_FUNDS_PATH = t.DATA + File.separator + t.FUNDS + File.separator + getReceiverAccountName() + "Funds.txt";
        final String RECEIVER_NAME_PATH = t.DATA + File.separator + t.ACCOUNTS + File.separator + getReceiverAccountName() + ".txt";
        File senderFile = new File(SENDER_FUNDS_PATH);
        File receiverFile = new File(RECEIVER_FUNDS_PATH);
        BufferedReader senderReader = new BufferedReader(new FileReader(senderFile));
        BufferedReader receiverReader = new BufferedReader(new FileReader(receiverFile));
        String senderFunds = senderReader.readLine();
        String receiverFunds = receiverReader.readLine();
        String enteredFunds = amountField.getText().trim();
        receiverReader.close();
        senderReader.close();
        int subtract = Integer.parseInt(senderFunds)-Integer.parseInt(enteredFunds);
        int adding = Integer.parseInt(receiverFunds)+Integer.parseInt(enteredFunds);
        if(!(clientList.get(0).equals(getReceiverAccountName()))){
            if(subtract>=0){
                PrintWriter senderWriter = new PrintWriter(senderFile);
                PrintWriter receiverWriter = new PrintWriter(receiverFile);

                senderWriter.println(subtract);
                receiverWriter.println(adding);
                System.out.println(senderFile.getName() + "has minus: " + subtract);
                System.out.println(receiverFile.getName() + "has plus: " + adding);

                receiverWriter.close();
                senderWriter.close();
                accGetFunds.setText(String.valueOf(subtract));
                JOptionPane.showMessageDialog(dialogPanel, "Success", "Information", 1);
                dialog.setVisible(false);
                amountField.setText("");
                toUserField.setText("");
            }else
                JOptionPane.showMessageDialog(loginPanel, "Insufficient funds!", "Error", 1);
        }else{
            JOptionPane.showMessageDialog(loginPanel, "You filled own account ID, correct it, please.", "Error", 1);
        }
    }
}