package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.Asymmetric;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class BucketServerConnection extends ServerConnection {

    //TODO accept and transfer keys properly and in order
    @Override
    public void connect(int portnum, int timeout) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        server = new ServerSocket(portnum);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
        //Accept the client
        while (true) {
            socket = server.accept();
            new EchoThread(socket).start();
        }
    }


    public class EchoThread extends Thread {
        protected Socket socket;

        public EchoThread(Socket clientSocket) {
            this.socket = clientSocket;
        }

        public void run() {
            //Initialization
            try {
                handshake();
                System.out.println("Client accepted");
                //Check for socket existence
                DataInputStream rawin = new DataInputStream(socket.getInputStream());
                DataOutputStream rawout = new DataOutputStream(socket.getOutputStream());
                System.out.println("Connected to " + socket.getRemoteSocketAddress());


                int lenr = rawin.readInt();
                byte[] cpkin = new byte[lenr];
                rawin.readFully(cpkin);
                cPk = Asymmetric.byteArrayToKey(cpkin);
                in = new EFCDataInputStream(rawin, sessionKeyStore.getPrivate(), cPk);
                out = new EFCDataOutputStream(rawout, sessionKeyStore.getPrivate(), cPk);
                postConnect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
