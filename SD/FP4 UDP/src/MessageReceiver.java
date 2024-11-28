import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReceiver extends Thread {
    private Socket socket;
    public MessageReceiver(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input;
            while ((input = in.readLine()) != null){
                System.out.println(input);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
