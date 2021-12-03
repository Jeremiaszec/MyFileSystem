public class CmdCreateDirectory extends Command {

    @Override
    public void execute(FilesSystem fileSystem, String args[]) {

        try{
            String fileName = args[1];
            fileSystem.createNewDirectory(fileName);
        }
        catch (ArrayIndexOutOfBoundsException exc){
            fileSystem.println("few arguments to create a directory");
        }
    }

    public CmdCreateDirectory(){
        setCmdId("create_folder");
        setUserPermission(false);
    }
}
