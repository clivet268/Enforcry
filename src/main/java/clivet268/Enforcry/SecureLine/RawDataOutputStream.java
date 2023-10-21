package clivet268.Enforcry.SecureLine;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RawDataOutputStream extends DataOutputStream {

    /**
     * Creates a new data output stream to write data to the specified
     * underlying output stream. The counter {@code written} is
     * set to zero.
     *
     * @param out the underlying output stream, to be saved for later
     *            use.
     * @see FilterOutputStream#out
     */
    public RawDataOutputStream(OutputStream out) {
        super(out);
    }

    public void writeMSG(byte[] b) throws IOException {
        writeInt(b.length);
        out.write(b);
    }
}
