package clivet268.Enforcry.SecureLine;

import clivet268.Enforcry.Encryption.Asymmetric;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

//TODO implement in secure line
//TODO THERE NEEDS TO BE A WAY TO VERIFY REPEATED CONNECTIONS WITH CHALLENGE QUESTIONS OR ANOTHER METHOD
public class ClientConnection {


    private static Socket socket = null;
    protected static EFCDataInputStream in = null;
    protected static EFCDataOutputStream out = null;
    protected DataInputStream rawin = null;
    protected DataOutputStream rawout = null;
    private static final KeyPair sessionKeyStore;

    static {
        try {
            sessionKeyStore = Asymmetric.generateRSAKkeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PublicKey sPk = null;

    public void connect(String ip, int portnum, int timeout) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        try {
            socket = new Socket(ip, portnum);
            if (timeout < 0) {
                socket.setSoTimeout(Integer.MAX_VALUE);
            } else {
                socket.setSoTimeout(timeout);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Check for socket existence
        rawin = new DataInputStream(socket.getInputStream());
        rawout = new DataOutputStream(socket.getOutputStream());
        System.out.println("Connected to " + socket.getRemoteSocketAddress());
        handshake();
        keyTransfer();
        postConnect();
    }

    public void handshake() throws IOException {

        //TODO prevent man in the middle attacks, public key is sent unencrypted, can be used to impersonate
        //Handshake
        int handshake = rawin.readInt();
        if (!(handshake == 1000)) {
            //TODO what to do if improper handshake
            close();
        }
        rawout.writeInt(1000);
        rawout.flush();
        handshake = rawin.readInt();
        if (!(handshake == 1000)) {
            close();
        }

        //Public key transfer
        handshake = rawin.readInt();
        if (!(handshake == 404)) {
            close();
        }
    }

    public void postConnect() {

    }

    public void keyTransfer() {
        try {
            rawout.writeInt(402);
            rawout.flush();
            byte[] encodedPrivateKey = sessionKeyStore.getPublic().getEncoded();
            rawout.writeInt(encodedPrivateKey.length);
            rawout.flush();
            rawout.write(encodedPrivateKey);
            rawout.flush();
            rawout.writeInt(404);
            rawout.flush();
            int code = rawin.readInt();
            if (!(code == 402)) {
                close();
            }
            int lenr = rawin.readInt();
            byte[] cpkin = new byte[lenr];
            rawin.readFully(cpkin);
            sPk = Asymmetric.byteArrayToKey(cpkin);
            in = new EFCDataInputStream(rawin, sessionKeyStore.getPrivate(), sPk);
            out = new EFCDataOutputStream(rawout, sessionKeyStore.getPrivate(), sPk);
        } catch (Exception e) {

        }
    }

    /**
     * Closes connection
     */
    void close() throws IOException {
        System.out.println("\n---Closing connection---\n");
        socket.close();
        in.close();
        out.close();
    }
}
