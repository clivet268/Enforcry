package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.Asymmetric;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class ServerConnection {
    protected static Socket socket = null;
    protected EFCDataInputStream in = null;
    protected EFCDataOutputStream out = null;
    protected DataInputStream rawin = null;
    protected DataOutputStream rawout = null;
    static KeyPair sessionKeyStore;

    static {
        try {
            sessionKeyStore = Asymmetric.generateRSAKkeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static PublicKey cPk = null;
    static ServerSocket server = null;

    public void connect(int portnum, int timeout) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        //Initialization
        server = new ServerSocket(portnum);

        if (timeout > 0) {
            long t = timeout;
            timeout = (int) Math.min(Integer.MAX_VALUE - 1, t * 1000);
            server.setSoTimeout(timeout);
        }
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
        //Accept the client

        socket = server.accept();
        System.out.println("Client accepted");
        //Check for socket existence
        rawin = new DataInputStream(socket.getInputStream());
        rawout = new DataOutputStream(socket.getOutputStream());
        System.out.println("Connected to " + socket.getRemoteSocketAddress());
        handshake();
        keyTransfer();
        postConnect();
    }

    public void keyTransfer() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        rawout.writeInt(404);
        rawout.flush();
        int code = rawin.readInt();
        if (!(code == 402)) {
            close();
        }
        int lenr = rawin.readInt();
        byte[] cpkin = new byte[lenr];
        rawin.readFully(cpkin);
        cPk = Asymmetric.byteArrayToKey(cpkin);
        code = rawin.readInt();
        if (!(code == 404)) {
            close();
        }
        rawout.writeInt(402);
        rawout.flush();
        byte[] encodedPrivateKey = sessionKeyStore.getPublic().getEncoded();
        rawout.writeInt(encodedPrivateKey.length);
        rawout.flush();
        rawout.write(encodedPrivateKey);
        rawout.flush();
        cPk = Asymmetric.byteArrayToKey(cpkin);
        in = new EFCDataInputStream(rawin, sessionKeyStore.getPrivate(), cPk);
        out = new EFCDataOutputStream(rawout, sessionKeyStore.getPrivate(), cPk);
    }

    public void close() throws IOException {
        System.out.println("\n---Closing connection---\n");
        socket.close();
        in.close();
        out.close();
    }

    public void handshake() throws IOException {
        //Handshake
        rawout.writeInt(1000);
        rawout.flush();
        int handshake = rawin.readInt();
        if (!(handshake == 1000)) {
            //TODO what to do if improper handshake
            close();
        }
        rawout.writeInt(1000);
        rawout.flush();
    }

    public void postConnect() {

    }
}
