import static sun.misc.MessageUtils.out;

public class CmdShowMetadata extends Command {
    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
       File f = filesSystem.getFile(args[1]);
       out(f.getMetadata());
    }
    public CmdShowMetadata(){
        setCmdId("metadata");
        setUserPermission(false);
    }
}
