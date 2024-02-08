import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    // Constructor
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    // Method that is responsible for keeping the server running
    public void startsServer(){
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                // Threads share a memory space, when you launch an executable, it is running in a thread within a process
                Thread thread =new  Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    // Method to close the socket
    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startsServer();
    }
}
