import java.io.*;
import java.net.*;

/**
 * The `Client` class represents a client that communicates with a server over a network using sockets.
 * 
 * @author Team 21 Best Table Winners 
 */
class Client {
    private static final int PORT = 8888;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isActive = false;

    /**
     * Constructs a new `Client` instance.
     *
     * @param isActive Specifies whether the client is active and should establish a connection with the server.
     */
    Client(boolean isActive){
        this.isActive = isActive;
        if(isActive){
            try{
                socket = new Socket("localhost", PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a request to the server and waits for a lock.
     * If the lock is denied, the method waits until the lock is available.
     *
     * @throws IOException if an I/O error occurs during communication with the server.
     */
    public void requestAndWaitForLock(){
        if(isActive){
            try{
                out.println("REQUEST");
                String response = in.readLine();
                if(response.equals("DENIED")){
                    response = in.readLine();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return;
    }
    }
    
    /**
     * Releases the lock by sending a release signal to the server.
     */
    public void releaseLock(){
        if(isActive){
            out.println("RELEASE");
        }
    }

    /**
     * Closes the connections and signals the server that the client is quitting.
     * Must be called before Client is garbage collected.
     * 
     * @throws IOException if an I/O error occurs while closing connections.
     */
    public void quit() {
        if(isActive){
            try{
                out.println("QUIT");
                in.close();
                out.close();
                socket.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
