public class CmdCreateFile extends Command {

    @Override
    public void execute(FilesSystem filesSystem, String[] args) {

        try{
            filesSystem.createNewFile(args[1], "metadata",  args[2]);
            System.out.println("file created");
        }
        catch (ArrayIndexOutOfBoundsException exc){
            filesSystem.println("few arguments to create a file");
        }
    }

    public CmdCreateFile(){
        setCmdId("create_file");
        setUserPermission(false);
    }
}
