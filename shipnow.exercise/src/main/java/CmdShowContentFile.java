import static sun.misc.MessageUtils.out;

public class CmdShowContentFile extends Command {
    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
        out("contenido mostrado");
        filesSystem.showContentFile(args[1]);
    }

    public CmdShowContentFile(){
        setCmdId("show");
        setUserPermission(false);
    }
}
