import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CorsaCavalli extends JFrame implements ActionListener, MouseListener {
    // Variabili di gioco
    private String name;
    private int saldo;
    private int puntata;
    private File file = new File("files/cavalli.txt");
    Cavallo[] cavalli = new Cavallo[217];

    // Label e font pricipali
    private Color buttonsColor = new Color(87, 87, 87);
    private ImageIcon background = new ImageIcon("backgrounds/corsaCavalli.png");
    private Color buttonsColorHover = new Color(120, 120, 120);
    private JLabel puntataLabel = new JLabel();
    private JLabel mainLabel = new JLabel();
    private JButton regolamentoButton = new JButton("📖");
    private Font titleFont = new Font("LEMON MILK", Font.BOLD, 50);
    private Font subTitleFont = new Font("LEMON MILK", Font.BOLD, 25);
    private Font buttonsFont = new Font("LEMON MILK", Font.BOLD, 20);

    // Cavalli e puntate
    private JPanel sfondo = new JPanel();
    private String[] listaCavalliBox = new String[5];
    private JComboBox listaCavalli = new JComboBox<>();
    private JButton confirmButton = new JButton("SCOMMETTI");

    private JLabel scegliLabel = new JLabel("SCEGLI UN CAVALLO: ");

    public CorsaCavalli(String name, int saldo, int puntata) {
        this.name = name;
        this.saldo = saldo;
        this.puntata = puntata;

        this.setTitle("Puntata: " + this.puntata + "$");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1300, 900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        leggiCavalli();
        for (int i=0; i<5;i++) listaCavalliBox[i] = cavalli[i].getName() + " - Stelle: "+cavalli[i].getStars();

        mainLabel.setBounds(0, 0, 1300, 900);
        mainLabel.setIcon(background);

        regolamentoButton.setBounds(1140, 710, 100, 100);
        regolamentoButton.setText("📖");
        regolamentoButton.setFont(new Font("Arial", Font.PLAIN, 45));
        regolamentoButton.setVerticalTextPosition(JLabel.CENTER);
        regolamentoButton.setHorizontalTextPosition(JLabel.CENTER);
        regolamentoButton.setOpaque(true);
        regolamentoButton.setFocusable(false);
        regolamentoButton.setBorder(null);
        regolamentoButton.setForeground(Color.WHITE);
        regolamentoButton.setBackground(buttonsColor);
        regolamentoButton.addActionListener(this);
        regolamentoButton.addMouseListener(this);

        puntataLabel.setBounds(500, 21, 301, 57);
        puntataLabel.setText("Puntata: " + this.puntata + "$");
        puntataLabel.setFont(subTitleFont);
        puntataLabel.setForeground(Color.WHITE);
        puntataLabel.setHorizontalAlignment(JLabel.CENTER);
        puntataLabel.setHorizontalTextPosition(JLabel.CENTER);
        puntataLabel.setOpaque(false);
        puntataLabel.setFocusable(false);
        puntataLabel.setBorder(null);

        sfondo.setBounds(321, 228, 657, 446);
        sfondo.setBackground(buttonsColor);
        sfondo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sfondo.setAlignmentY(Component.CENTER_ALIGNMENT);
        sfondo.setLayout(new FlowLayout());

        listaCavalli.setBounds(500,392,301,57);
        for (int i=0; i<5;i++) listaCavalli.addItem(listaCavalliBox[i]);


        confirmButton.setBounds(538,547,223,92);
        confirmButton.setFont(buttonsFont);
        confirmButton.setVerticalTextPosition(JLabel.CENTER);
        confirmButton.setHorizontalTextPosition(JLabel.CENTER);
        confirmButton.setOpaque(true);
        confirmButton.setFocusable(false);
        confirmButton.setBorder(null);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(98,130,76));
        confirmButton.addActionListener(this);
        confirmButton.addMouseListener(this);

        sfondo.add(confirmButton);

        scegliLabel.setBounds(500, 247, 301, 57);
        scegliLabel.setFont(buttonsFont);
        scegliLabel.setHorizontalAlignment(JLabel.CENTER);
        scegliLabel.setForeground(Color.WHITE);

        this.add(scegliLabel);
        this.add(listaCavalli);
        this.add(confirmButton);
        this.add(puntataLabel);
        this.add(regolamentoButton);
        this.add(sfondo);
        this.add(mainLabel);
    }

    private void leggiCavalli() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String[] datas;

            for (int i = 0; i < 217; i++) {
                datas = reader.readLine().split(";");
                cavalli[i] = new Cavallo(datas[0], Integer.parseInt(datas[1]));
            }

            // Shuffle array
            Random rnd = ThreadLocalRandom.current();
            for (int i = cavalli.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                Cavallo a = cavalli[index];
                cavalli[index] = cavalli[i];
                cavalli[i] = a;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regolamentoButton) {
            String r = "Regolamento:\nNella schermata troverai 5 cavalli e il relativo numero di stelle che rappresentano le seguenti probabilità:\n1 Stella - 10% possibilità vincita e x10 della puntata\n2 Stelle - 20% possibilità vincita e x8 della puntata\n3 Stelle - 30% possibilità vincita e x5 della puntata\n4 Stelle - 40% possibilità vincita e x3 della puntata\n5 Stelle - 50% possibilità vincita e x2 della puntata";
            JOptionPane.showMessageDialog(this, r, "Regolamento", JOptionPane.PLAIN_MESSAGE);
        }

        if(e.getSource()==confirmButton){

            int indiceCavallo=listaCavalli.getSelectedIndex();

            //Primi 5 cavalli
            Cavallo[] temp = new Cavallo[5];
            for (int i=0; i<5;i++) temp[i] = cavalli[i];

            dispose();
            SwingUtilities.invokeLater(() -> {
                CaricamentoCavalli results =new CaricamentoCavalli(this.name, this.saldo, this.puntata, indiceCavallo,temp);
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
        if (e.getSource() == regolamentoButton) regolamentoButton.setBackground(buttonsColorHover);
        if (e.getSource() == confirmButton) confirmButton.setBackground(new Color(37, 147, 54, 255));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == regolamentoButton) regolamentoButton.setBackground(buttonsColor);
        if (e.getSource() == confirmButton) confirmButton.setBackground(new Color(98,130,76));
    }
}
