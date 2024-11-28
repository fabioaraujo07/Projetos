import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class MulticastClient {
    private static final String MULTICAST_GROUP_IP = "230.0.0.0"; // Mesmo endereço do servidor
    private static final int PORT = 2048;

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP_IP);
            socket.joinGroup(group); // Une-se ao grupo multicast

            System.out.println("Conectado ao grupo multicast " + MULTICAST_GROUP_IP);
            new MulticastHandler(socket, group).start(); // Inicia a thread para enviar e receber

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
