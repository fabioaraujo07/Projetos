import java.io.*;
import java.net.*;

public class QuoteClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: java Ex1.QuoteClient <hostname>");
            return;
        }

        // Cria o socket Datagram
        DatagramSocket socket = new DatagramSocket();

        // Envia a solicitação
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);

        // Recebe a resposta
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // Exibe a resposta
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);

        socket.close();
    }
}
