import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    // class variables
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    // constructor
    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // Method to send message
    public void sendMessage(){
        try{
            // send the username first
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // getting the input from the console
            Scanner scanner = new Scanner(System.in);

            while(socket.isConnected()){
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.close();
            }


        }catch(IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);

        }
    }

}
