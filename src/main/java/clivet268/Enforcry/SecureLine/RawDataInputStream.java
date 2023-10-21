package clivet268.Enforcry.SecureLine;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RawDataInputStream extends DataInputStream implements DataInput {
    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public RawDataInputStream(@NotNull InputStream in) {
        super(in);
    }

    public byte[] readMSG() throws IOException {
        return readNBytes(readInt());
    }
}
