import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GiraRoulette extends JFrame {
    private int saldo;
    private String name;
    private Color coloreScelto;
    private int puntata;
    private JLabel mainLabel = new JLabel();
    private JLabel titleLabel = new JLabel();
    private JLabel percentageLabel = new JLabel();
    private Font titleFont = new Font("LEMON MILK", Font.BOLD, 50);
    private ImageIcon background = new ImageIcon("backgrounds/rouletteLoading.png");
    private Font buttonsFont = new Font("LEMON MILK", Font.BOLD, 20);
    private JProgressBar bar = new JProgressBar(0, 100);

    public GiraRoulette(Color coloreScelto, int puntata, String name, int saldo) {
        this.coloreScelto = coloreScelto;
        this.puntata = puntata;
        this.name=name;
        this.saldo=saldo;

        String temp = null;
        if (coloreScelto.equals(Color.RED)) temp = "Rosso";
        if (coloreScelto.equals(Color.BLACK)) temp = "Nero";
        if (coloreScelto.equals(Color.GREEN)) temp = "Verde";

        this.setTitle("Colore scelto: " + temp + " - Puntata: " + this.puntata + "$");
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
        titleLabel.setText("GIRANDO...");
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
            protected void done() {
               dispose();

               //Gira Roulette
               Random random = new Random();
               int x = random.nextInt(0, 101);
               Color risultato=null;
               int guadagno=0;

               if(x==0) risultato = Color.GREEN;
               if(x>0 && x<50) risultato = Color.RED;
               if(x>=50) risultato = Color.BLACK;

               if(coloreScelto == risultato){
                   if(coloreScelto==Color.GREEN) guadagno = puntata*25;
                   else guadagno = puntata*2;
               }
               else{
                   guadagno = puntata*-1;
               }


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
