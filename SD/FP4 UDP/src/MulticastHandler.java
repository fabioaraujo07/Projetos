import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MulticastHandler extends Thread {
    private MulticastSocket socket;
    private InetAddress group;
    private Scanner scanner;

    public MulticastHandler(MulticastSocket socket, InetAddress group) {
        this.socket = socket;
        this.group = group;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        // Thread para enviar mensagens
        new Thread(() -> {
            try {
                while (true) {
                    String message = scanner.nextLine();
                    byte[] buffer = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, socket.getLocalPort());
                    socket.send(packet);

                    if (message.equalsIgnoreCase("bye")) {
                        System.out.println("Saindo do grupo multicast.");
                        socket.leaveGroup(group); // Sai do grupo multicast
                        socket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Recebe mensagens do grupo multicast
        try {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Recebe mensagens do grupo

                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Recebido: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}