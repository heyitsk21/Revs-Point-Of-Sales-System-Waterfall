import java.io.*;
import java.net.*;

class Client {
    private static final int PORT = 8888;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isActive = false;

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
    
    public void releaseLock(){
        if(isActive){
            out.println("RELEASE");
        }
    }
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
