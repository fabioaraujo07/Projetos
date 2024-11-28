import javax.swing.JFrame;
import javax.swing.JLabel;

public class Janela3 implements Runnable {
    protected Monitor m;
    protected int i;

    public Janela3(Monitor m, int i) {
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

        // synchronized( m ) { // o método espera já está declarado como synchronized
        do {
            m.espera(); // main thread deve executar "accordaTodas()" depois de todos os threads
            // executarem "espera()"
        } while (m.getVez() != i);
        // só um thread executa dentro do bloco - acesso exclusivo!
        // }

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
        Thread[] ths = new Thread[8];

        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(new Janela3(mon, i), "Th" + i);
            ths[i].start();
        }
        System.out.println("[Main] All threads created!");
        System.out.println("[Main] Activating Threads!");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
        }
        // despertar as threads uma por uma com desfasamento de um segundo
        for (int i = 0; i < ths.length; i++) {
            mon.defineVez(i); //Qual thread deve ser acordada a seguir
            mon.acordaTodas();

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