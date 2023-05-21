package clivet268.Enforcry.Util;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class PIDManager implements IPID {

    LinkedList<IPID> familiar = new LinkedList<>();
    private String username = "";
    //TODO dont store passwords as plaintext strings in memory and ESPECIALLY not on disk
    private String password = "";

    @Override
    public @NotNull String getUsername() {
        return null;
    }

    @Override
    public byte[] getEFCFolderKey(String userin, String passin, String challenge) {
        return new byte[0];
    }
}
