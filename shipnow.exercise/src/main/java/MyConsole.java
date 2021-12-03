import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.prefs.Preferences;


public class MyConsole {
    public static void main(String [] args){

        FilesSystem filesSystem = new FilesSystem();

        String fileName = null;

        if(args.length>=2){
            args[0].equals("-persisted");
            fileName = args[1] + ".bin";
            filesSystem.read(fileName);

            filesSystem.setPersistedEnable();
        }
        else{
            filesSystem.persistedDisabled();
        }

        CmdChangeDirectory cmdChangeDirectory = new CmdChangeDirectory();
        CmdCreateDirectory cmdCreateDirectory = new CmdCreateDirectory();
        CmdCreateFile cmdCreateFile = new CmdCreateFile();
        CmdDestroy cmdDestroy = new CmdDestroy();
        CmdGetPath cmdGetPath = new CmdGetPath();
        CmdLs cmdLs = new CmdLs();
        CmdShowContentFile cmdShowContentFile = new CmdShowContentFile();
        CmdShowMetadata cmdShowMetadata = new CmdShowMetadata();

        filesSystem.add(cmdChangeDirectory);
        filesSystem.add(cmdCreateDirectory);
        filesSystem.add(cmdCreateFile);
        filesSystem.add(cmdDestroy);
        filesSystem.add(cmdGetPath);
        filesSystem.add(cmdLs);
        filesSystem.add(cmdShowContentFile);
        filesSystem.add(cmdShowMetadata);


        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);
        String msn = null;

        String commandRecibed= "";

        filesSystem.print(filesSystem.getPath() + "> ");

        while(!commandRecibed.equals("exit")){
            try {
                msn = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String parts[] = msn.split(" ");
            filesSystem.executeCmd(msn);
            filesSystem.print(filesSystem.getPath() + "> ");
            commandRecibed = parts[0];
        }
        if(filesSystem.persistedEnable()){
            filesSystem.write(fileName);
        }
    }
}
