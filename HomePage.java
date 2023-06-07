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

public class HomePage extends JFrame implements ActionListener, MouseListener {
    //VARIABILI DI GIOCO
    private String name;
    private int saldo;

    //Frame e label del titolo
    private Color buttonsColor = new Color(87,87,87);
    private ImageIcon background = new ImageIcon("backgrounds/homePage.png");
    private Color buttonsColorHover = new Color(120,120,120);
    private JLabel mainLabel = new JLabel();
    private JLabel titleLabel = new JLabel("CASINO' GALAXY");
    private JLabel subTitleLabel = new JLabel("Scegli il tuo gioco: ");
    private Font titleFont = new Font("LEMON MILK",Font.BOLD,50);
    private Font subTitleFont = new Font("LEMON MILK",Font.BOLD,25);
    private Font buttonsFont = new Font("LEMON MILK",Font.BOLD,20);

    //Bottoni
    private JButton rouletteButton = new JButton("Roulette");
    private JButton blackJackButton = new JButton("Blackjack");
    private JButton cavalliButton = new JButton("Corsa-Cavalli");
    private JButton confirmButton = new JButton("Salva Sessione");

    public HomePage(String name, int saldo){
        this.name=name;
        this.saldo=saldo;
        this.setTitle("Home - "+this.name+" | "+this.saldo+"$");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        subTitleLabel.setBounds(431, 30, 436, 206);
        subTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subTitleLabel.setHorizontalTextPosition(JLabel.CENTER);
        subTitleLabel.setFont(subTitleFont);
        subTitleLabel.setForeground(Color.WHITE);

        rouletteButton.addActionListener(this);
        rouletteButton.addMouseListener(this);
        blackJackButton.addActionListener(this);
        blackJackButton.addMouseListener(this);
        cavalliButton.addActionListener(this);
        cavalliButton.addMouseListener(this);
        confirmButton.addActionListener(this);
        confirmButton.addMouseListener(this);

        rouletteButton.setBounds(90, 387, 270, 126);
        rouletteButton.setFont(buttonsFont);
        rouletteButton.setVerticalTextPosition(JLabel.CENTER);
        rouletteButton.setHorizontalTextPosition(JLabel.CENTER);
        rouletteButton.setOpaque(true);
        rouletteButton.setFocusable(false);
        rouletteButton.setBorder(null);
        rouletteButton.setForeground(Color.WHITE);
        rouletteButton.setBackground(buttonsColor);

        blackJackButton.setBounds(515, 387, 270, 126);
        blackJackButton.setFont(buttonsFont);
        blackJackButton.setVerticalTextPosition(JLabel.CENTER);
        blackJackButton.setHorizontalTextPosition(JLabel.CENTER);
        blackJackButton.setOpaque(true);
        blackJackButton.setFocusable(false);
        blackJackButton.setBorder(null);
        blackJackButton.setForeground(Color.WHITE);
        blackJackButton.setBackground(buttonsColor);

        cavalliButton.setBounds(940, 387, 270, 126);
        cavalliButton.setFont(buttonsFont);
        cavalliButton.setVerticalTextPosition(JLabel.CENTER);
        cavalliButton.setHorizontalTextPosition(JLabel.CENTER);
        cavalliButton.setOpaque(true);
        cavalliButton.setFocusable(false);
        cavalliButton.setBorder(null);
        cavalliButton.setForeground(Color.WHITE);
        cavalliButton.setBackground(buttonsColor);

        confirmButton.setBounds(515, 717, 270, 92);
        confirmButton.setFont(buttonsFont);
        confirmButton.setVerticalTextPosition(JLabel.CENTER);
        confirmButton.setHorizontalTextPosition(JLabel.CENTER);
        confirmButton.setOpaque(true);
        confirmButton.setFocusable(false);
        confirmButton.setBorder(null);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(buttonsColor);


        this.add(confirmButton);
        this.add(cavalliButton);
        this.add(blackJackButton);
        this.add(rouletteButton);
        this.add(subTitleLabel);
        this.add(titleLabel);
        this.add(mainLabel);
        this.setLayout(null);

        if(this.saldo<=0){
            dispose();
            JOptionPane.showMessageDialog(this, "Il tuo saldo è arrivato a 0, Hai perso", "GAME-OVER", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==confirmButton){
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
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                    writer.write(this.name+","+this.saldo);
                    writer.close();
                    dispose();
                    new LoadingPage();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }


        }
        if(e.getSource()==rouletteButton) {
            int x = Integer.parseInt(JOptionPane.showInputDialog(this, "Inserisci una puntata: ", "Punta: ", JOptionPane.PLAIN_MESSAGE));
            while (x <= 0 || x > saldo) {
                x = Integer.parseInt(JOptionPane.showInputDialog(this, "Inserisci una puntata: ", "Punta: ", JOptionPane.PLAIN_MESSAGE));
            }

            dispose();
            int finalX = x;
            SwingUtilities.invokeLater(() -> {
                Roulette results =new Roulette(this.name, finalX, this.saldo);
                results.setVisible(true);
            });

            try {
                Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }

        if(e.getSource()==blackJackButton) {
            int x = Integer.parseInt(JOptionPane.showInputDialog(this, "Inserisci una puntata: ", "Punta: ", JOptionPane.PLAIN_MESSAGE));
            while (x <= 0 || x > saldo) {
                x = Integer.parseInt(JOptionPane.showInputDialog(this, "Inserisci una puntata: ", "Punta: ", JOptionPane.PLAIN_MESSAGE));
            }

            dispose();
            int finalX = x;
            SwingUtilities.invokeLater(() -> {
                Blackjack results =new Blackjack(this.name, this.saldo,finalX);
                results.setVisible(true);
            });

            try {
                Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }

        if(e.getSource()==cavalliButton){
            int x = Integer.parseInt(JOptionPane.showInputDialog(this, "Inserisci una puntata: ", "Punta: ", JOptionPane.PLAIN_MESSAGE));
            while (x <= 0 || x > saldo) {
                x = Integer.parseInt(JOptionPane.showInputDialog(this, "Inserisci una puntata: ", "Punta: ", JOptionPane.PLAIN_MESSAGE));
            }

            dispose();
            int finalX = x;
            SwingUtilities.invokeLater(() -> {
                CorsaCavalli results =new CorsaCavalli(this.name, this.saldo,finalX);
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
        if (e.getSource()==rouletteButton) rouletteButton.setBackground(buttonsColorHover);
        if (e.getSource()==blackJackButton) blackJackButton.setBackground(buttonsColorHover);
        if (e.getSource()==cavalliButton) cavalliButton.setBackground(buttonsColorHover);
        if (e.getSource()==confirmButton) confirmButton.setBackground(buttonsColorHover);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource()==rouletteButton) rouletteButton.setBackground(buttonsColor);
        if (e.getSource()==blackJackButton) blackJackButton.setBackground(buttonsColor);
        if (e.getSource()==cavalliButton) cavalliButton.setBackground(buttonsColor);
        if (e.getSource()==confirmButton) confirmButton.setBackground(buttonsColor);
    }


}
