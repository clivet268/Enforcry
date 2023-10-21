package clivet268.Enforcry.SecureLine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//TODO there noeeds to be a way that this can be interacted with while it runs in the background, a queue of all
// AMOE and AMOERs running that cna be exited with a command individually or enemas maybe even a GUI if we want this to
// run in the foreground. an infinite while loop is... unprofessional.
public class BucketServerConnection {
    public void bucketConnect(int portnum, ServerConnection sin) {
        Socket socket;
        ServerSocket server;
        try {
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            server = new ServerSocket(portnum);
            while (true) {
                try {
                    socket = server.accept();
                    new EchoThread(socket, sin).start();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class EchoThread extends Thread {
        ServerConnection sc;

        public EchoThread(Socket clientSocket, ServerConnection sin) {
            sc = sin;
            sc.predef(clientSocket);
        }

        public void run() {
            //Initialization
            try {
                sc.predefConnect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
