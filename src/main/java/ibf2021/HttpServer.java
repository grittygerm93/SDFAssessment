package ibf2021;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int PORT;
    private List<String> DOCROOT;
    private static String HOST = "127.0.0.1";

    public HttpServer(int PORT, List<String> DOCROOT) {
        this.PORT = PORT;
        this.DOCROOT = DOCROOT;
    }

    public void startServer() {
        checkDocRoot();
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                HttpClientConnection session = new HttpClientConnection(server.accept());
                ExecutorService es = Executors.newFixedThreadPool(3);
                es.submit(session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkDocRoot() {
//        System.out.println("start check");
        boolean invalid = false;
        for (String doc: DOCROOT) {
            File file = new File(doc);
            if(!file.exists()) {
                System.out.println(doc + " does not exist");
                invalid = true;
            }
            if(!file.isDirectory()) {
                System.out.println(doc + " Not a valid directory");
                invalid = true;
            }
            if(!file.canRead()) {
                System.out.println(doc + " Path cannot be read by server");
                invalid = true;
            }
        }
        if(invalid) {
            System.exit(1);
        }
//        System.out.println("end check success");
    }


}
