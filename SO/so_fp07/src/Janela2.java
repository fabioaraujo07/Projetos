import javax.swing.JFrame;
import javax.swing.JLabel;

public class Janela2 implements Runnable {
    protected Monitor m;
    protected int i;

    public Janela2(Monitor m, int i) {
        this.m = m;
        this.i = i;
    }

    public void run() {
        String myname = Thread.currentThread().getName();
        JFrame f = new JFrame(myname);
        JLabel l = new JLabel("#");
        f.add(l);
        f.setSize(150, 200);
        f.setLocation(i * 200, 100);
        f.setVisible(true);

        synchronized (m) {
            m.espera(); // esperar ate serem despertados na main
        }

        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {

            }
            l.setText("" + l.getText() + "#");
        }
        f.dispose();
    }

    public static void main(String[] args) {
        Monitor mon = new Monitor();
        Thread[] ths = new Thread[5];

        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(new Janela2(mon, i), "Th" + i);
            ths[i].start();
        }
        System.out.println("[Main] All threads created!");
        System.out.println("[Main] Activating Threads!");

        // sleep por 5 segundos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        // Desvazamento de um segundo, acrescentar o método sleep dentro do loop principal
        for (int i = 0; i < ths.length; i++) {
            synchronized (mon) {
                mon.acorda();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        try {
            for (int i = 0; i < ths.length; i++) {
                ths[i].join();
            }
        } catch (InterruptedException e) {
        }

        System.out.println("{Main] All threads ended!");
    }

}
