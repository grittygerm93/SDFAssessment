package ibf2021;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class HttpClientConnection implements Runnable {

    private final Socket socket;

    public HttpClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            while (true) {
                String msg = input.readUTF();
                if("exit".equals(msg))
                    break;
                output.writeUTF(msg);
            }
            socket.close();

        } catch (IOException e) {
            //do something... EOFexception when client exits
        }
    }

}

