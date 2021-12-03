public class CmdGetPath extends Command {
    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
        filesSystem.print(filesSystem.getPath() + "\n");
    }

    public CmdGetPath(){
        setCmdId("whereami");
        setUserPermission(true);
    }
}
