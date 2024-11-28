import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MulticastServer {
    private static final String MULTICAST_GROUP_IP = "230.0.0.0"; // Endereço de grupo multicast
    private static final int PORT = 2048; // Porta de comunicação

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP_IP);
            socket.joinGroup(group); // Une-se ao grupo multicast
            System.out.println("Servidor multicast iniciado no grupo " + MULTICAST_GROUP_IP);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Recebe pacotes multicast

                String message = new String(packet.getData(), 0, packet.getLength());
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String clientIp = packet.getAddress().getHostAddress();
                String formattedMessage = String.format("[%s][%s]: %s", timestamp, clientIp, message);

                System.out.println(formattedMessage); // Exibe a mensagem no servidor
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
