package ibf2021;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static int PORT = 3000;
    private static List<String> DOCROOT = new ArrayList<>(List.of("./target"));

    public static void main(String[] args) {

        int portIndex = 0;
        int docRootIndex = 0;

        if(args!=null && args.length >= 1) {
            for (int i = 0; i < args.length; i++) {
                if ("--port".equals(args[i])) {
                    portIndex = i+1;
                }
                if ("--docRoot".equals(args[i])) {
                    docRootIndex = i+1;
                }
            }
            try {
                if(portIndex != 0) {
                    PORT = Integer.parseInt(args[portIndex]);
                }
                if(docRootIndex != 0) {
                    DOCROOT = List.of(args[docRootIndex].split(":"));
                }
            } catch (IndexOutOfBoundsException ioe) {
                System.out.println("invalid arguments");
            }


        }

        HttpServer server = new HttpServer(PORT, DOCROOT);
        server.startServer();


//        System.out.println(PORT);
//        System.out.println(DOCROOT);
//        To RUN java -jar  myserver-1.jar

    }
}
