import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;


public class LoadingPage extends JFrame implements ActionListener, MouseListener {
    //VARIABILI DI GIOCO
    private String nome;
    private int saldo;
    //----//
    private Color buttonsColor = new Color(87,87,87);
    private ImageIcon background = new ImageIcon("src/backgrounds/loadingPage.png");
    private Color buttonsColorHover = new Color(120,120,120);
    private JLabel mainLabel = new JLabel();

    //Titolo e sottotitoloù
    private JLabel titleLabel = new JLabel("BENVENUTO AL CASINO'");
    private JLabel subTitleLabel = new JLabel("Scegli come accedere:");
    private Font titleFont = new Font("LEMON MILK",Font.BOLD,50);
    private Font subTitleFont = new Font("LEMON MILK",Font.BOLD,25);
    private Font buttonsFont = new Font("LEMON MILK",Font.BOLD,20);
    private JLabel footerLabel = new JLabel("Casinò Galaxy");

    //Bottoni
    private JButton newGameButton = new JButton("Nuova Partita");
    private JButton logGameButton = new JButton("Carica Partita");

    //Carica partita
    private JFileChooser apriFile = new JFileChooser();



    public LoadingPage(){
        this.setTitle("Casinò Galaxy");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        mainLabel.setIcon(background);
        mainLabel.setBounds(0, 0, 1300, 900);

        titleLabel.setBounds(100, 20, 1110, 60);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);

        subTitleLabel.setBounds(100, 100, 1110, 60);
        subTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subTitleLabel.setHorizontalTextPosition(JLabel.CENTER);
        subTitleLabel.setFont(subTitleFont);
        subTitleLabel.setForeground(Color.WHITE);

        newGameButton.addActionListener(this);
        logGameButton.addActionListener(this);
        newGameButton.addMouseListener(this);
        logGameButton.addMouseListener(this);

        newGameButton.setBounds(119,387, 270, 126);
        newGameButton.setFont(buttonsFont);
        newGameButton.setVerticalTextPosition(JLabel.CENTER);
        newGameButton.setHorizontalTextPosition(JLabel.CENTER);
        newGameButton.setOpaque(true);
        newGameButton.setFocusable(false);
        newGameButton.setBorder(null);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setBackground(buttonsColor);

        logGameButton.setBounds(910,387, 270, 126);
        logGameButton.setFont(buttonsFont);
        logGameButton.setVerticalTextPosition(JLabel.CENTER);
        logGameButton.setHorizontalTextPosition(JLabel.CENTER);
        logGameButton.setOpaque(true);
        logGameButton.setFocusable(false);
        logGameButton.setBorder(null);
        logGameButton.setForeground(Color.WHITE);
        logGameButton.setBackground(buttonsColor);

        footerLabel.setBounds(480, 752, 340, 58);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setHorizontalTextPosition(JLabel.CENTER);
        footerLabel.setHorizontalAlignment(JLabel.CENTER);
        footerLabel.setFont(new Font("Arial",Font.BOLD,36));

        this.add(footerLabel);
        this.add(logGameButton);
        this.add(newGameButton);
        this.add(newGameButton);
        this.add(logGameButton);
        this.add(subTitleLabel);
        this.add(titleLabel);
        this.add(mainLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            this.nome = JOptionPane.showInputDialog(this, "Inserisci il tuo nome", "Nuova Partita - Nome", JOptionPane.PLAIN_MESSAGE);

            int tempSaldo = 1000;
            boolean saldoValido = false;

            while (!saldoValido) {
                String inputSaldo = JOptionPane.showInputDialog(this, "Inserisci saldo iniziale (1-1000)\n(Inserisci '0' per tornare indietro)", "Nuova Partita - Saldo", JOptionPane.PLAIN_MESSAGE);
                try {
                    tempSaldo = Integer.parseInt(inputSaldo);

                    if (tempSaldo == 0) {
                        break;
                    } else if (tempSaldo >= 1 && tempSaldo <= 1000) {
                        saldoValido = true;
                    }
                } catch (NumberFormatException e1) {

                }
            }

            if (tempSaldo != 0) {
                this.saldo = tempSaldo;
            }


            if (tempSaldo!=0){
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    HomePage results = new HomePage(this.nome, this.saldo);
                    results.setVisible(true);
                });

                try {
                    Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }





        //CARICA PARTITA
        if (e.getSource()==logGameButton) {
            String userHome = System.getProperty("user.home");
            File desktopDirectory = new File(userHome, "Desktop");
            apriFile.setCurrentDirectory(desktopDirectory);

            // Imposta il filtro per visualizzare solo file .txt
            FileNameExtensionFilter filter = new FileNameExtensionFilter("File di testo (.txt)", "txt");
            apriFile.setFileFilter(filter);

            // Mostra il selettore di file
            int result = apriFile.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                // Ottiene il file selezionato
                File selectedFile = apriFile.getSelectedFile();
                //LEGGERE FILE E IMPOSTARE SALDO INIZIALE E NOME
                String[] data;
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    data = reader.readLine().split(",");
                    reader.close();

                    try{
                        this.nome = data[0];
                        this.saldo = Integer.parseInt(data[1]);
                    }
                    catch (Exception exception){
                        JOptionPane.showMessageDialog(this, "FILE non valido", "Errore", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ex) {throw new RuntimeException(ex);}

                JOptionPane.showMessageDialog(this, "Partita selezionata\nNome: "+this.nome+"\nSaldo: "+this.saldo+"$","Operazione completata",JOptionPane.PLAIN_MESSAGE);
                this.dispose();

                SwingUtilities.invokeLater(() -> {
                    HomePage results = new HomePage(this.nome, this.saldo);
                    results.setVisible(true);
                });

                try {
                    Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

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
        if(e.getSource()==logGameButton) logGameButton.setBackground(buttonsColorHover);
        if(e.getSource()==newGameButton) newGameButton.setBackground(buttonsColorHover);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==logGameButton) logGameButton.setBackground(buttonsColor);
        if(e.getSource()==newGameButton) newGameButton.setBackground(buttonsColor);
    }


}
