import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Results extends JFrame implements ActionListener, MouseListener {
    private int importoGiocato, importoGuadagnato,saldo;
    private String name;

    private Color buttonsColor = new Color(87,87,87);
    private ImageIcon background = new ImageIcon("backgrounds/results.png");
    private Color buttonsColorHover = new Color(120,120,120);

    //Label e bottoni
    private JLabel mainLabel = new JLabel();
    private JLabel titleLabel = new JLabel("RISULTATI");
    private JLabel subLabel = new JLabel();
    private JButton saveButton = new JButton("SCARICA ⬇");
    private JButton continuaButton = new JButton();

    //FONT
    private Font titleFont = new Font("LEMON MILK",Font.BOLD,50);
    private Font buttonsFont = new Font("LEMON MILK",Font.BOLD,20);


    public Results(int importoGiocato, int importoGuadagnato,int saldo,String name){
        this.importoGuadagnato=importoGuadagnato;
        this.importoGiocato = importoGiocato;
        this.name=name;
        this.saldo=saldo;

        this.setTitle("RISULTATI");
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        mainLabel.setIcon(background);
        mainLabel.setBounds(0, 0, 1300, 900);

        titleLabel.setBounds(284, 22, 730, 90);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);

        subLabel.setBounds(393, 227, 513, 270);
        if(this.importoGuadagnato>0){
            subLabel.setBackground(new Color(86,188,108));
            this.saldo-=importoGiocato;
        }
        else subLabel.setBackground(Color.RED);
        subLabel.setForeground(Color.WHITE);
        subLabel.setFont(buttonsFont);
        subLabel.setOpaque(true);
        subLabel.setVerticalAlignment(JLabel.CENTER);
        subLabel.setHorizontalAlignment(JLabel.CENTER);

        subLabel.setHorizontalTextPosition(JLabel.CENTER);
        subLabel.setVerticalTextPosition(JLabel.CENTER);
        subLabel.setText("<html>IMPORTO GIOCATO: "+this.importoGiocato+"<br>" + "IMPORTO GUADAGNATO: "+this.importoGuadagnato+"<br>" + "<br>" + "PROFITTO = "+this.importoGuadagnato+"</html>");

        saveButton.setBounds(549, 554, 203, 93);
        saveButton.setFont(buttonsFont);
        saveButton.setVerticalTextPosition(JLabel.CENTER);
        saveButton.setHorizontalTextPosition(JLabel.CENTER);
        saveButton.setOpaque(true);
        saveButton.setFocusable(false);
        saveButton.setBorder(null);
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(buttonsColor);
        saveButton.addActionListener(this);
        saveButton.addMouseListener(this);

        continuaButton.setBounds(515, 676, 268, 118);
        continuaButton.setText("Continua");
        continuaButton.setFont(buttonsFont);
        continuaButton.setVerticalTextPosition(JLabel.CENTER);
        continuaButton.setHorizontalTextPosition(JLabel.CENTER);
        continuaButton.setOpaque(true);
        continuaButton.setFocusable(false);
        continuaButton.setBorder(null);
        continuaButton.setForeground(Color.WHITE);
        continuaButton.setBackground(buttonsColor);
        continuaButton.addActionListener(this);
        continuaButton.addMouseListener(this);



        this.add(continuaButton);
        this.add(saveButton);
        this.add(subLabel);
        this.add(titleLabel);
        this.add(mainLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==saveButton){

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("File di testo (.txt)", "txt");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                // Aggiungi l'estensione .txt se necessario
                if (!filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                }

                try {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String dataOra = now.format(formatter);
                    String schedaVittoria = "********** Scheda di Gioco **********\n" +"Nome: "+this.name+ "\nData e Ora: " + dataOra + "\n" + "Importo Giocato: $" + this.importoGiocato + "\n" + "Importo Guadagnato: $" +  this.importoGuadagnato + "\n" +"\n*****************************************";


                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                    writer.write(schedaVittoria);
                    writer.close();
                    dispose();



                    SwingUtilities.invokeLater(() -> {
                        HomePage results = new HomePage(this.name,(this.saldo+=importoGuadagnato));
                        results.setVisible(true);
                    });

                    try {
                        Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }

        if(e.getSource()==continuaButton){
            dispose();
            SwingUtilities.invokeLater(() -> {
                HomePage results = new HomePage(this.name,(this.saldo+=importoGuadagnato));
                results.setVisible(true);
            });

            try {
                Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==saveButton) saveButton.setBackground(buttonsColorHover);
        if(e.getSource()==continuaButton) continuaButton.setBackground(buttonsColorHover);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==saveButton) saveButton.setBackground(buttonsColor);
        if(e.getSource()==continuaButton) continuaButton.setBackground(buttonsColor);
    }
}
