//package servlet.rest;

import java.rmi.server.ExportException;

/**
 * Created by govardhanreddy on 1/22/16.
 */
public class RemoteFetcher {

    private static final String IP_BEGIN = "192.168.2.";
    private static final String USER = "gopi";
    private static final String HOME = "/home/gopi";
    private static final String AUDIO_DIR = ".aa";
    private static final String PIC_DIR = ".sc";

    public static void main(String[] args) {
        System.out.println("Starting fetcher.");
            try {
                String ip = IP_BEGIN + args[0];
                String scpCommand = "sshpass -p 'unix' scp gopi@" + ip + ":" + HOME + "/";

                String fetchPicsCommand = scpCommand + PIC_DIR + "/.*.png /Users/govardhanreddy/.sc/";
//                System.out.println("Running audio command : " + audioCommand);
//                Process audioProcess = Runtime.getRuntime().exec(audioCommand);
//                audioProcess.waitFor();
//                System.out.println("Executed audio command");
                System.out.println("Running pic command : " + fetchPicsCommand);
                Process picProcess = Runtime.getRuntime().exec(fetchPicsCommand);
                picProcess.waitFor();
                System.out.println("Executed pic command");
            } catch (Exception e) {
                System.out.println(" Exception : " + e);
            }
    }
}
