/*
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final Socket socket;
    private static int PORT;
    private static String HOST = "127.0.0.1";

    public HttpServer(Socket socket) {
        this.socket = socket;
    }

    public static void startServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                HttpClientConnection session = new HttpClientConnection(server.accept());
                ExecutorService es = Executors.newFixedThreadPool(3);
                session.start(); // it does not block this thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
*/
