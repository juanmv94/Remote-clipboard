package clipboard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class Bthread extends Thread {
    Socket socket;
    byte[] buffer = new byte[1024*32];
    int bbuffer;
    InputStream in;
    //OutputStream out;
    static List<Socket> sockets = new CopyOnWriteArrayList<>();   //Thread-safe
    
    public Bthread(Socket clientSocket) {
        this.socket = clientSocket;
    }
    
    public void run() {
        try {
            in=socket.getInputStream();
            //out=socket.getOutputStream();
            sockets.add(socket);
            while (true) {
                bbuffer = in.read(buffer);
                if (bbuffer<=0) break;
                System.out.println("Received: " + bbuffer + " bytes from " + socket);
                for (Socket dest : sockets) {
                    if (dest!=socket)
                        try {
                            dest.getOutputStream().write(buffer, 0, bbuffer);
                        } catch (Exception e){
                            System.out.println("Error sendind " + socket);
                        }

                }
            }
        } catch (Exception e) {}
        finally {
            sockets.remove(socket);
            try {
                socket.close();
            } catch (IOException ex) {}
        }
    }
}

public class Broadcast {
    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            new Bthread(connectionSocket).start();
        }
    }
}
