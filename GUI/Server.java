import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.io.FileOutputStream;

class Server {
    private static final int PORT = 8888;
    private static BlockingQueue<Socket> requestQueue = new LinkedBlockingDeque<>();
    private static boolean isLocked = false;
    
    synchronized static void outputLogsToFile(String input){
        FileOutputStream outputFile = null;
        try {
            outputFile = new FileOutputStream("serverOutput.txt");
            
            int c;
            while (input != null) {
                outputFile.write(c);
            }
        } catch (IOException e) {
            System.err.println(e);
        } 

        try {
            if (outputFile != null){
                outputFile.close();
            }
        } catch (IOException e) {
            System.err.println(e);;
        }
    }

    private static void handleClientRequest(Socket socket) {
        try (
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true)) {
            String clientRequest;
            while((clientRequest = in.readLine()) != null){
                System.out.println("Received request from client: " + clientRequest);
                if (clientRequest.equals("REQUEST")) {
                    if (!isLocked) {
                        out.println("GRANTED");
                        isLocked = true;
                    } else {
                        out.println("DENIED");
                        requestQueue.add(socket);
                    }
                } else if (clientRequest.equals("RELEASE")) {
                    if (!requestQueue.isEmpty()) {
                        Socket nextClient = requestQueue.poll();
                        PrintWriter toNext = new PrintWriter(nextClient.getOutputStream(), true);
                        toNext.println("GRANTED");
                    } 
                    else{
                       isLocked = false;
                    }
                } else if(clientRequest.equals("QUIT")){
                    in.close();
                    out.close();
                    socket.close();
                    break;
                }
            }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        

    public static void main(String[] args) {

        try {

            ServerSocket s = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);
            while (true) {
                System.out.println("Waiting for new client connection");
                Socket clientSocket = s.accept();
                new Thread(() -> handleClientRequest(clientSocket)).start();   
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


