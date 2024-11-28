import java.io.*;

public class MulticastServer2 {
    public static void main(String[] args) throws IOException {
        new MulticastServerThread().start();
    }
}
