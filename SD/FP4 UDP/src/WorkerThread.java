import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerThread extends Thread{
    private Socket socket = null;

    public WorkerThread(Socket socket) {
        super("WorkerThread");
        this.socket = socket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                if (inputLine.equals("Bye"))
                    break;
            }
            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}