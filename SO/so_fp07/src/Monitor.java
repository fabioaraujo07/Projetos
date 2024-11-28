public class Monitor {
    protected int next = 0;

    public synchronized void acordaTodas(){
        this.notifyAll();
    }

    public synchronized void acorda(){
        this.notify();
    }

    public synchronized void espera(){
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void defineVez(int next){
        this.next = next;
    }

    public synchronized int getVez(){
        return this.next;
    }
}
