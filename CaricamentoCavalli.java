import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CaricamentoCavalli extends JFrame {

    //Attributi
    private int saldo;
    private String name;
    private Cavallo[] cavalliDisponibili;
    private int indiceCavalloScelto;
    private int puntata;

    //ELEMENTI PAGINA
    private JLabel mainLabel = new JLabel();
    private JLabel titleLabel = new JLabel();
    private JLabel percentageLabel = new JLabel();
    private Font titleFont = new Font("LEMON MILK", Font.BOLD, 50);
    private ImageIcon background = new ImageIcon("src/backgrounds/corsaLoading.png");
    private Font buttonsFont = new Font("LEMON MILK", Font.BOLD, 20);
    private JProgressBar bar = new JProgressBar(0, 100);

    public CaricamentoCavalli(String name, int saldo, int puntata, int indiceCavalloScelto, Cavallo[] cavalliDisponibili){
        this.name= name;
        this.saldo= saldo;
        this.puntata= puntata;
        this.indiceCavalloScelto= indiceCavalloScelto;
        this.cavalliDisponibili= cavalliDisponibili;

        this.setTitle("Cavallo scelto: " + cavalliDisponibili[indiceCavalloScelto].getName() +" - Stelle: "+cavalliDisponibili[indiceCavalloScelto].getStars()+ " - Puntata: " + this.puntata + "$");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1300, 900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        mainLabel.setBounds(0, 0, 1300, 900);
        mainLabel.setIcon(background);

        titleLabel.setBounds(400, 303, 499, 105);
        titleLabel.setFont(titleFont);
        titleLabel.setText("Correndo...");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        percentageLabel.setBounds(250, 396, 800, 30);
        percentageLabel.setFont(buttonsFont.deriveFont(Font.BOLD, 18));
        percentageLabel.setForeground(Color.WHITE);
        percentageLabel.setHorizontalAlignment(JLabel.CENTER);

        bar.setValue(0);
        bar.setVisible(true);
        bar.setFont(buttonsFont);
        bar.setBounds(250, 441, 800, 50);
        bar.setStringPainted(false);
        bar.setBackground(Color.WHITE);
        bar.setForeground(Color.WHITE);

        this.add(bar);
        this.add(percentageLabel);
        this.add(titleLabel);
        this.add(mainLabel);

        fillProgressBar();
    }

    private void fillProgressBar() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                Random random = new Random();
                for (int i = 0; i <= 100; i++) {
                    publish(i);
                    Thread.sleep(random.nextInt(91) + 10);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                bar.setValue(progress);
                percentageLabel.setText(progress + "%");
            }

            @Override
            protected void done(){
                int guadagno = puntata*-1;
                //TROVO CAVALLO VINCENTE

                //Totale stelle
                int totaleStelle=0;
                for (int i=0; i<cavalliDisponibili.length;i++) totaleStelle+= cavalliDisponibili[i].getStars();

                //Probabilità di un cavallo = nStelleCavallo/totaleStelle * 100
                double mioCavallo = (double) cavalliDisponibili[indiceCavalloScelto].getStars()/totaleStelle*100;
                double randomNumber = new Random().nextInt(101);

                //Cavallo vincitore
                if(randomNumber<=mioCavallo){
                    JOptionPane.showMessageDialog(null, "Hai vinto!", "Casinò Galaxy - Cavalli", JOptionPane.INFORMATION_MESSAGE);
                    Cavallo cavalloScelto = cavalliDisponibili[indiceCavalloScelto];

                    if(cavalloScelto.getStars()==5) guadagno = puntata*2;
                    if(cavalloScelto.getStars()==4) guadagno = puntata*3;
                    if(cavalloScelto.getStars()==3) guadagno = puntata*5;
                    if(cavalloScelto.getStars()==2) guadagno = puntata*8;
                    else guadagno = puntata*2;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Hai perso!", "Casinò Galaxy - Cavalli", JOptionPane.INFORMATION_MESSAGE);
                }




                //CHIUDO e passo ai risultati
                dispose();
                int finalGuadagno = guadagno;
                SwingUtilities.invokeLater(() -> {
                    Results results =  new Results(puntata, finalGuadagno,saldo,name);
                    results.setVisible(true);
                });

                try {
                    Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }


            }
        };

        worker.execute();
    }


}
