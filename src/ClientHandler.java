import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    // Keeps track of all the clients
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;


    // Constructor
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            // add this object to the arraylist
            clientHandlers.add(this);
            // This method send the other clients a message that another client has joined
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch(IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        // while connected to a client listen for messages
        while(socket.isConnected()) {
            try {
                // this is a blocking operation, so we run it on a separate thread so the program is not waiting
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, bufferedReader, bufferedWriter);
                // when the client disconnects, break the loop
                break;
            }
        }

    }

    // Method to send a message to all the clients
    public void broadcastMessage(String messageToSend){
        for(ClientHandler clientHandler : clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    // sending the message to the clients
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    // Manually flushing the buffer because the buffer won't be full
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
                closeEverything(socket, bufferedReader, bufferedWriter);
            }

        }

    }
}
