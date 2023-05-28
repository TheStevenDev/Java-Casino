import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Roulette extends JFrame implements ActionListener, MouseListener {
    private String name;
    private int saldo;
    private int puntata;
    private Color coloreScelto;

    //Frame e label del titolo
    private Color buttonsColor = new Color(87,87,87);
    private ImageIcon background = new ImageIcon("src/backgrounds/roulette.png");
    private Color buttonsColorHover = new Color(120,120,120);
    private JLabel mainLabel = new JLabel();
    private JLabel titleLabel = new JLabel();
    private Font subTitleFont = new Font("LEMON MILK",Font.BOLD,25);
    private Font buttonsFont = new Font("LEMON MILK",Font.BOLD,20);

    //Bottoni di gioco e regolamento
    private JButton redButton = new JButton("Rosso");
    private JButton blackButton = new JButton("Rosso");
    private JButton greenButton = new JButton("Rosso");
    private JButton regolamentoButton = new JButton("📖");


    public Roulette(String name, int puntata,int saldo){
        this.name = name;
        this.puntata = puntata;
        this.saldo=saldo;

        this.setTitle("Roulette - Puntata: "+this.puntata+"$");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        mainLabel.setBounds(0, 0, 1300, 900);
        mainLabel.setIcon(background);

        titleLabel.setBounds(499, 70, 301, 57);
        titleLabel.setText("Puntata: "+this.puntata+"$");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(subTitleFont);

        redButton.setBounds(90, 371, 270, 158);
        redButton.setText("ROSSO");
        redButton.setFont(buttonsFont);
        redButton.setVerticalTextPosition(JLabel.CENTER);
        redButton.setHorizontalTextPosition(JLabel.CENTER);
        redButton.setOpaque(true);
        redButton.setFocusable(false);
        redButton.setBorder(null);
        redButton.setForeground(Color.WHITE);
        redButton.setBackground(Color.RED);
        redButton.addActionListener(this);
        redButton.addMouseListener(this);

        blackButton.setBounds(515, 371, 270, 158);
        blackButton.setText("NERO");
        blackButton.setFont(buttonsFont);
        blackButton.setVerticalTextPosition(JLabel.CENTER);
        blackButton.setHorizontalTextPosition(JLabel.CENTER);
        blackButton.setOpaque(true);
        blackButton.setFocusable(false);
        blackButton.setBorder(null);
        blackButton.setForeground(Color.WHITE);
        blackButton.setBackground(Color.BLACK);
        blackButton.addActionListener(this);
        blackButton.addMouseListener(this);

        greenButton.setBounds(940, 371, 270, 158);
        greenButton.setText("VERDE");
        greenButton.setFont(buttonsFont);
        greenButton.setVerticalTextPosition(JLabel.CENTER);
        greenButton.setHorizontalTextPosition(JLabel.CENTER);
        greenButton.setOpaque(true);
        greenButton.setFocusable(false);
        greenButton.setBorder(null);
        greenButton.setForeground(Color.WHITE);
        greenButton.setBackground(new Color(77,164,71));
        greenButton.addActionListener(this);
        greenButton.addMouseListener(this);

        regolamentoButton.setBounds(1110, 709, 100, 100);
        regolamentoButton.setFont(new Font("Arial",Font.PLAIN,45));
        regolamentoButton.setVerticalTextPosition(JLabel.CENTER);
        regolamentoButton.setHorizontalTextPosition(JLabel.CENTER);
        redButton.setVerticalAlignment(JLabel.CENTER);
        regolamentoButton.setOpaque(true);
        regolamentoButton.setFocusable(false);
        regolamentoButton.setBorder(null);
        regolamentoButton.setForeground(Color.WHITE);
        regolamentoButton.setBackground(buttonsColor);
        regolamentoButton.addActionListener(this);
        regolamentoButton.addMouseListener(this);




        this.add(regolamentoButton);
        this.add(blackButton);
        this.add(greenButton);
        this.add(redButton);
        this.add(titleLabel);
        this.add(mainLabel);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==regolamentoButton){
            JOptionPane.showMessageDialog(null, "VINCITE:\n\nROSSO: X2\nNERO: X2\nVERDE: X25", "REGOLAMENTO", JOptionPane.PLAIN_MESSAGE);
        }

        if(e.getSource()==redButton){
            coloreScelto = Color.RED;
            this.dispose();
            new GiraRoulette(coloreScelto, this.puntata,this.name,this.saldo);
        }

        if(e.getSource()==greenButton){
            coloreScelto = Color.GREEN;
            this.dispose();
            new GiraRoulette(coloreScelto, this.puntata,this.name,this.saldo);
        }

        if(e.getSource()==blackButton){
            coloreScelto = Color.BLACK;
            this.dispose();
            new GiraRoulette(coloreScelto, this.puntata,this.name,this.saldo);
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
        if(e.getSource()==redButton) redButton.setBackground(new Color(248, 50, 50));
        if(e.getSource()==greenButton) greenButton.setBackground(new Color(5, 72, 5));
        if(e.getSource()==blackButton) blackButton.setBackground(new Color(20, 28, 20, 207));
        if (e.getSource()==regolamentoButton) regolamentoButton.setBackground(buttonsColorHover);


    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==redButton) redButton.setBackground(Color.RED);
        if(e.getSource()==greenButton) greenButton.setBackground(new Color(77, 164, 71));
        if(e.getSource()==blackButton) blackButton.setBackground(Color.BLACK);
        if (e.getSource()==regolamentoButton) regolamentoButton.setBackground(buttonsColor);


    }
}
