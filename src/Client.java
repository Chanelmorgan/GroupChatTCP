import java.io.*;
import java.net.Socket;

public class Client {

    // class variables
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    // constructor
    public Client(Socket socket, String username){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch(IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);

    }

}
