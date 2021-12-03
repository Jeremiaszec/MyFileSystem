public class CmdChangeDirectory extends Command {
    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
        filesSystem.changeDirectory(args[1]);
    }

    public CmdChangeDirectory(){
        setCmdId("cd");
        setUserPermission(true);
    }
}
