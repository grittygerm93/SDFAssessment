package ibf2021;

import java.io.*;
import java.net.Socket;

public class HttpClientConnection implements Runnable {

    private final Socket socket;

    public HttpClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             BufferedReader br = new BufferedReader( new InputStreamReader(input))) {

            System.out.println("request from browser client:");
            String line;
            //reads and prints every line from the request
            while ( (line = br.readLine()) != null) {
                System.out.println(line);
            }


//            byte[] msg = input.readAllBytes();
//            String msg = input.readUTF();
//            System.out.println(msg);

                //sample inputs GET<space>/index.html<space>HTTP/1.1

                //sample output HTTP/1.1<space>200<space>OK\r\n
                //\r\n
                //contents in bytes

                //msg.

                //output.writeUTF(msg);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
            //do something... EOFexception when client exits
        }
    }

}

