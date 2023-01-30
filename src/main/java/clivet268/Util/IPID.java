package clivet268.Util;

import org.jetbrains.annotations.NotNull;

//TODO should definetly be used in server client communication to store the list of known users
//TODO how do i use this in the main class?
public interface IPID {
    @NotNull
    String getUsername();

    byte[] getEFCFolderKey(String userin, String passin, String challenge);


}
