package scannerhk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class ScannerHK {
    JFrame frame = new JFrame();
    JButton dodaj = new JButton("Dodaj");
    JButton szukaj = new JButton("Szukaj");
    JButton szukajS = new JButton("Szukaj");
    JButton wymaz = new JButton("Wyczyść listę");
    
    JButton zapisz = new JButton("Zapisz");
    JButton wroc = new JButton("Wróć");
    JButton wrocS = new JButton("Wróć");
    
    CardLayout cl = new CardLayout();
    
    JPanel cards = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    
    JTextField kodField = new JTextField(15);
    JTextField kodFieldS = new JTextField(15);
    
    //JTextArea ta = new JTextArea(5, 20);

    DefaultListModel dlm = new DefaultListModel();
    JList list = new JList(dlm);
    JScrollPane sp = new JScrollPane(list);
    
    JLabel kodLabel = new JLabel("Podaj kod: ");
    JLabel exkodLabel = new JLabel("Przykladowy kod: H040X180LO-2552");
    JLabel kodLabelS = new JLabel("Podaj kolor: ");
    JLabel exkodLabelS = new JLabel("Przykladowy kolor: 2552");
    
    File file = new File("baza.txt");
    
    public ScannerHK() {
        initComp();
        cardChanger();
        dodajListener();
        wrocListener();
        wrocSListener();
        zapiszListener();
        szukajListener();
        szukajSListener();
        kodFieldSListener();
        wymazListener();
        
        createFile();
        
        frame.setTitle("Scanner");
        frame.setBounds(300, 300, 300, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(cards);
    }
    void initComp(){
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 100));
        panel1.add(dodaj);
        panel1.add(szukaj);
        
        dodaj.setPreferredSize(new Dimension(200, 50));
        szukaj.setPreferredSize(new Dimension(200, 50));
        
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 100));
        panel2.add(exkodLabel);
        panel2.add(kodLabel);
        panel2.add(kodField);
        panel2.add(wroc);
        panel2.add(zapisz);
        
        exkodLabel.setFont(new Font("Monospaced", Font.ITALIC, 12));
        
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        panel3.add(exkodLabelS);
        panel3.add(kodLabelS);
        panel3.add(kodFieldS);
        panel3.add(wrocS);
        panel3.add(szukajS);
        panel3.add(wymaz);
        panel3.add(sp);
        
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        sp.setPreferredSize(new Dimension(250,100));
        
        
        exkodLabelS.setFont(new Font("Monospaced", Font.ITALIC, 12));
    }
    
    void cardChanger(){
        cards.setLayout(cl);
        cards.add(panel1, "main");
        cards.add(panel2, "doda");
        cards.add(panel3, "szuk");
        cl.show(cards, "main");
    }
    
    void cleanAdd(){
        kodField.setText("");
    }
    
    void successWrite(){
        JOptionPane.showMessageDialog(panel2, "Zapisano", "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }
    
    String getKodFieldS(){
        return kodFieldS.getText();
    }

    void createFile(){
        while(!(file.exists()))
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ScannerHK.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    void writeFile() throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        
        String kod = kodField.getText();

        writer.write(kod);
        writer.newLine();
        
        System.out.println("kod: " + kod);
        
        writer.close();
    }
    
    static final String NEWLINE = "\n";
    String line;
    void readFile() throws IOException{
        kodFieldSListener();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            
            System.out.println(getKodFieldS());
            
            while((line = reader.readLine()) != null){
                if(line.contains(getKodFieldS())){
                    System.out.println("kod4: " + line);
                    dlm.addElement(line + NEWLINE);
                }
                else if(getKodFieldS().equals("*")){
                    System.out.println(line + NEWLINE);
                    dlm.addElement(line + NEWLINE);
                }
            }
        }
    }
    
    void dodajListener(){
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "doda");
                
            } 
        });
    }
    
    void wrocListener(){
        wroc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "main");
            }
        }); 
    }
    
    void zapiszListener(){
        zapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(kodField.getText().isEmpty())
                        JOptionPane.showMessageDialog(panel2, "Brak danych");
                    else {
                        writeFile();
                        successWrite();
                        cleanAdd();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ScannerHK.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
     
    void szukajListener(){
        szukaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "szuk");
            }
        });
    } 
    
    void wrocSListener(){
        wrocS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "main");
            }
        }); 
    }
    
    void szukajSListener(){
        szukajS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    readFile();
                } catch (IOException ex) {
                    Logger.getLogger(ScannerHK.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    void kodFieldSListener(){
        kodFieldS.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getKodFieldS();
            }
        });
    }
    
    void wymazListener(){ 
        wymaz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("wymaz");
                dlm.removeAllElements();
            }
        });
    }
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScannerHK();
            }
        });
    }
    
}
