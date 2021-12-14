package ibf2021;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//FOR TESTING PURPOSES
public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 3000;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();

            output.writeUTF(msg); // sending message to the server
            String receivedMsg = input.readUTF(); // response message

            System.out.println("Received from server: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


