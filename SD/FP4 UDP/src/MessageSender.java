import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MessageSender extends Thread {
    private Socket socket;
    public MessageSender(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            Scanner scanner = new Scanner(System.in);
            String inputLine;

            while (true){
                inputLine = scanner.nextLine();
                out.println(inputLine);
                if (inputLine.equalsIgnoreCase("bye")) {
                    break;
                }
            }
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
