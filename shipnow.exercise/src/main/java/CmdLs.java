public class CmdLs extends Command {
    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
        filesSystem.ls();
    }
    public CmdLs(){
        setCmdId("ls");
        setUserPermission(true);
    }
}
