package ibf2021;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class HttpClientConnection implements Runnable {

    private final Socket socket;
    private final List<String> DOCROOT;
//    private final PrintWriter socketWriter = null;

    public HttpClientConnection(Socket socket, List<String> DOCROOT) {
        this.socket = socket;
        this.DOCROOT = DOCROOT;
    }

    @Override
    public void run() {
        try (
            DataInputStream input = new DataInputStream(socket.getInputStream());
            BufferedReader br = new BufferedReader( new InputStreamReader(input))) {

            System.out.println("request from browser client:");
            String line;
            //reads and prints every line from the request
            if ((line = br.readLine()) != null) {
//                output.writeUTF(line); doesnt work... not sure why..
                getResource(line);


//                System.out.println(line);
            }

            //sample inputs GET<space>/index.html<space>HTTP/1.1

            //sample output HTTP/1.1<space>200<space>OK\r\n
            //\r\n
            //contents in bytes

            socket.close();

        } catch (IOException e) {
//            e.printStackTrace();
            //do something... EOFexception when client exits
        }
    }

    private void getResource(String getRequest){

        String[] parts = getRequest.split("\\s+");
        String method = parts[0];
        String filename;
        if("/".equals(parts[1])) {
            filename = "src/main/resources/test.txt";
//            filename = "index.html";
        }
        else {
            filename = parts[1].substring(1);
        }
        System.out.println(filename);
        File resource = new File(filename);


        if("GET".equals(method)) {
            if(!resource.exists()) {
                sendNotFoundResponse(resource);
            }
//            if(!resourceExists(filename)) {
//                sendNotFoundResponse(resource);
//            }
            else {
                sendResponse(method, filename);
            }
        }
        else {
            sendBadResponse(method);
        }
    }

    private boolean resourceExists(String filename) {
        for (String item: DOCROOT) {
            if(!item.equals(filename)) {
                return false;
            }
        }
        return true;
    }

    private void sendResponse(String method, String filename){

//        File path = null;
//
//        for(String location: DOCROOT) {
//            path = new File(String.format("%s.%s", location, filename));
//            if(path.exists()) {
//                break;
//            }
//        }
        File path = new File(filename);

        try (PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
             Scanner fileReader = new Scanner(path);
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {

//            String msg = String.format("HTTP/1.1 200 OK\r\n\r\n");
            StringBuilder msg = new StringBuilder();
            msg.append("HTTP/1.1 200 OK\r\n\r\n");
            msg.append("<!DOCTYPE html>\n" + "<html lang=\"en\">");

            //System.out.println(msg);
//            socketWriter.println(msg);
            while(fileReader.hasNext()){
                String line = fileReader.nextLine();
                System.out.println(line);
                msg.append(line);
            }
            msg.append("</html>");
            output.write(msg.toString().getBytes());
            System.out.println(msg.toString());



//            while(fileReader.hasNext()){
//                String line = fileReader.nextLine();
//                System.out.println(line);
//                socketWriter.println(line);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBadResponse(String line){
        try (PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true)) {

            String msg = String.format("HTTP/1.1 405 Method Not Allowed\r\n\r\n%s not supported\r\n", line);

            System.out.println(msg);
            socketWriter.println(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendNotFoundResponse(File resource) {
        try (PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true)) {

            String msg = String.format("HTTP/1.1 404 Not Found\r\n\r\n%s not found\r\n", resource);

            System.out.println(msg);
            socketWriter.println(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

