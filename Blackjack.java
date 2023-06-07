import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Blackjack extends JFrame implements ActionListener, MouseListener {
    //Atrributi di gioco
    private Random random = new Random();
    private int cartaAttuale=0;
    private int mioPunteggio=0, punteggioB=0;
    private String name;
    private int saldo;
    private int puntata;
    private int[] mazzo = new int[10];
    private String title = "Casinò Galaxy - BlackJack";

    //Font e sfondo
    private Color buttonsColor = new Color(87,87,87);
    private ImageIcon background = new ImageIcon("backgrounds/blackjack.png");
    private Color buttonsColorHover = new Color(120,120,120);
    private JLabel mainLabel = new JLabel();
    private Font titleFont = new Font("LEMON MILK",Font.BOLD,50);
    private Font subTitleFont = new Font("LEMON MILK",Font.BOLD,25);
    private Font buttonsFont = new Font("LEMON MILK",Font.BOLD,20);

    //Bottoni e label
    private JButton regolamentoButton = new JButton("📖");
    private JLabel punteggioBanco = new JLabel();
    private JLabel punteggio = new JLabel();
    private JButton tieniButton = new JButton();
    private JButton prendiButton = new JButton();
    private JLabel puntataLabel = new JLabel();

    public Blackjack(String name, int saldo, int puntata){
        this.name=name;
        this.saldo = saldo;
        this.puntata=puntata;

        this.setTitle("Puntata: "+this.puntata+"$");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        mainLabel.setBounds(0, 0, 1300, 900);
        mainLabel.setIcon(background);

        regolamentoButton.setBounds(1140, 90, 100, 100);
        regolamentoButton.setText("📖");
        regolamentoButton.setFont(new Font("Arial",Font.PLAIN,45));
        regolamentoButton.setVerticalTextPosition(JLabel.CENTER);
        regolamentoButton.setHorizontalTextPosition(JLabel.CENTER);
        regolamentoButton.setOpaque(true);
        regolamentoButton.setFocusable(false);
        regolamentoButton.setBorder(null);
        regolamentoButton.setForeground(Color.WHITE);
        regolamentoButton.setBackground(buttonsColor);
        regolamentoButton.addActionListener(this);
        regolamentoButton.addMouseListener(this);

        punteggioBanco.setBounds(457, 77, 386, 118);
        punteggioBanco.setText("Punteggio Banco: 0");
        punteggioBanco.setBackground(buttonsColor);
        punteggioBanco.setOpaque(true);
        punteggioBanco.setFont(subTitleFont);
        punteggioBanco.setForeground(Color.WHITE);
        punteggioBanco.setHorizontalAlignment(JLabel.CENTER);
        punteggioBanco.setHorizontalTextPosition(JLabel.CENTER);

        punteggio.setBounds(457, 568, 386, 118);
        punteggio.setText("Punteggio: 0");
        punteggio.setBackground(buttonsColor);
        punteggio.setOpaque(true);
        punteggio.setFont(subTitleFont);
        punteggio.setForeground(Color.WHITE);
        punteggio.setHorizontalAlignment(JLabel.CENTER);
        punteggio.setHorizontalTextPosition(JLabel.CENTER);

        tieniButton.setBounds(90, 687, 272, 123);
        tieniButton.setText("Tieni");
        tieniButton.setFont(subTitleFont);
        tieniButton.setVerticalTextPosition(JLabel.CENTER);
        tieniButton.setHorizontalTextPosition(JLabel.CENTER);
        tieniButton.setOpaque(true);
        tieniButton.setFocusable(false);
        tieniButton.setBorder(null);
        tieniButton.setForeground(Color.WHITE);
        tieniButton.setBackground(buttonsColor);
        tieniButton.addActionListener(this);
        tieniButton.addMouseListener(this);

        prendiButton.setBounds(938, 687, 272, 123);
        prendiButton.setText("Prendi");
        prendiButton.setFont(subTitleFont);
        prendiButton.setVerticalTextPosition(JLabel.CENTER);
        prendiButton.setHorizontalTextPosition(JLabel.CENTER);
        prendiButton.setOpaque(true);
        prendiButton.setFocusable(false);
        prendiButton.setBorder(null);
        prendiButton.setForeground(Color.WHITE);
        prendiButton.setBackground(buttonsColor);
        prendiButton.addActionListener(this);
        prendiButton.addMouseListener(this);

        puntataLabel.setBounds(500, 720, 301, 57);
        puntataLabel.setText("Puntata: "+this.puntata+"$");
        puntataLabel.setFont(subTitleFont);
        puntataLabel.setForeground(Color.WHITE);
        puntataLabel.setHorizontalAlignment(JLabel.CENTER);
        puntataLabel.setHorizontalTextPosition(JLabel.CENTER);
        puntataLabel.setOpaque(false);
        puntataLabel.setFocusable(false);
        puntataLabel.setBorder(null);



        this.add(puntataLabel);
        this.add(prendiButton);
        this.add(tieniButton);
        this.add(punteggio);
        this.add(punteggioBanco);
        this.add(regolamentoButton);
        this.add(mainLabel);

        //Gioco
        mioPunteggio+= random.nextInt(2,21);
        punteggio.setText("Punteggio: "+String.valueOf(mioPunteggio));

        punteggioB+=random.nextInt(2,21);
        punteggioBanco.setText("Punteggio banco: ?");

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        int guadagnoF=0;

        if(e.getSource()==regolamentoButton){
            String regolamento = "Regolamento Blackjack:\n" + "Il pulsante \"Tieni\" permette ai giocatori di mantenere il loro punteggio attuale senza richiedere ulteriori carte.\nIl pulsante \"Prendi\" consente loro di chiedere una carta aggiuntiva per avvicinarsi a 21.\n" + "Se il punteggio di un giocatore supera 21, si dichiara \"sballato\" e perde automaticamente la mano.\n" + "Alla fine delle giocate, il punteggio di ciascun giocatore viene confrontato con quello del banco e il punteggio più vicino a 21 vince la mano.";
            JOptionPane.showMessageDialog(this, regolamento,"REGOLAMENTO",JOptionPane.PLAIN_MESSAGE);
        }

        if(e.getSource()==tieniButton){

            if (punteggioB<17){
                punteggioB+=random.nextInt(1,11);

                if(punteggioB>21){
                    guadagnoF = puntata*2;
                    JOptionPane.showMessageDialog(this, "Il banco ha sforato", title, JOptionPane.INFORMATION_MESSAGE);

                    int finalGuadagnoF = guadagnoF;
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                        results.setVisible(true);
                    });
                }

                if(punteggioB==21){
                    guadagnoF = puntata*-1;
                    JOptionPane.showMessageDialog(this, "Il banco ha fatto blackJack", title, JOptionPane.INFORMATION_MESSAGE);

                    int finalGuadagnoF = guadagnoF;
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                        results.setVisible(true);
                    });
                }



            }
            else{
                //Se il banco tiene
                if(mioPunteggio==punteggioB){
                    JOptionPane.showMessageDialog(this, "Pareggio", title, JOptionPane.INFORMATION_MESSAGE);
                    guadagnoF=puntata;

                    int finalGuadagnoF = guadagnoF;
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                        results.setVisible(true);
                    });

                }
                else if(mioPunteggio>punteggioB){
                    JOptionPane.showMessageDialog(this, "Hai vinto", title, JOptionPane.INFORMATION_MESSAGE);
                    guadagnoF = puntata*2;
                    int finalGuadagnoF = guadagnoF;
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                        results.setVisible(true);
                    });

                }else{
                    JOptionPane.showMessageDialog(this, "Il banco ha vinto", title, JOptionPane.INFORMATION_MESSAGE);
                    guadagnoF = puntata*-1;

                    int finalGuadagnoF = guadagnoF;
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                        results.setVisible(true);
                    });

                }


            }


            if(mioPunteggio==21){
                JOptionPane.showMessageDialog(this, "Hai fatto 21!", title, JOptionPane.INFORMATION_MESSAGE);
                guadagnoF = puntata*2;

                int finalGuadagnoF = guadagnoF;
                dispose();
                SwingUtilities.invokeLater(() -> {
                    Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                    results.setVisible(true);
                });

            }


        }

        if(e.getSource()==prendiButton){
            mioPunteggio+=random.nextInt(1,11);
            punteggio.setText("Punteggio:"+mioPunteggio);

            if(mioPunteggio>21){
                JOptionPane.showMessageDialog(this, "Hai sforato", title, JOptionPane.INFORMATION_MESSAGE);
                guadagnoF = puntata*-1;


                int finalGuadagnoF = guadagnoF;
                dispose();
                SwingUtilities.invokeLater(() -> {
                    Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                    results.setVisible(true);
                });

            }
            if(mioPunteggio==21){
                JOptionPane.showMessageDialog(this, "Hai fatto blackJack", title, JOptionPane.INFORMATION_MESSAGE);
                guadagnoF = puntata*2;


                int finalGuadagnoF = guadagnoF;
                dispose();
                SwingUtilities.invokeLater(() -> {
                    Results results =  new Results(puntata, finalGuadagnoF,saldo,name);
                    results.setVisible(true);
                });


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
        if(e.getSource()==regolamentoButton) regolamentoButton.setBackground(buttonsColorHover);
        if(e.getSource()==tieniButton) tieniButton.setBackground(buttonsColorHover);
        if(e.getSource()==prendiButton) prendiButton.setBackground(buttonsColorHover);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==regolamentoButton) regolamentoButton.setBackground(buttonsColor);
        if(e.getSource()==tieniButton) tieniButton.setBackground(buttonsColor);
        if(e.getSource()==prendiButton) prendiButton.setBackground(buttonsColor);

    }

}
