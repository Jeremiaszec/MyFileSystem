public class CmdDestroy extends Command {

    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
        filesSystem.deleteFile(args[1]);
        filesSystem.deleteDirectory(args[1]);
    }

    public CmdDestroy(){
        setCmdId("destroy");
        setUserPermission(false);
    }
}
