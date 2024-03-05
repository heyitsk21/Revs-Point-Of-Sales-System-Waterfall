import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class Server {
    private static final int PORT = 8888;
    private static BlockingQueue<Socket> requestQueue = new LinkedBlockingDeque<>();
    private static boolean isLocked = false;
    
    synchronized static void outputLogsToFile(String input){
        FileWriter outputFile = null;
        try {
            // outputFile = new FileOutputStream("serverOutput.txt");
            outputFile = new FileWriter("ServerLog.txt", true); //append:true means that it will just add onto the existing file, rather than overwrite (hopefully)
            // int c;
            outputFile.write(input + "\n");
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
                outputLogsToFile("Received request from client: " + clientRequest);
                // System.out.println("Received request from client: " + clientRequest);
                if (clientRequest.equals("REQUEST")) {
                    if (!isLocked) {
                        outputLogsToFile("GRANTED");
                        // out.println("GRANTED");
                        isLocked = true;
                    } else {
                        outputLogsToFile("DENIED");
                        // out.println("DENIED");
                        requestQueue.add(socket);
                    }
                } else if (clientRequest.equals("RELEASE")) {
                    if (!requestQueue.isEmpty()) {
                        Socket nextClient = requestQueue.poll();
                        PrintWriter toNext = new PrintWriter(nextClient.getOutputStream(), true);
                        toNext.println("GRANTED");
                        outputLogsToFile("GRANTED");
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
            outputLogsToFile("Server is running on port " + PORT);
            // System.out.println("Server is running on port " + PORT);
            while (true) {
                // System.out.println("Waiting for new client connection");
                Socket clientSocket = s.accept();
                new Thread(() -> handleClientRequest(clientSocket)).start();   
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


