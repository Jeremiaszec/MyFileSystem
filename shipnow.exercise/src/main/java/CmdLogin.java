public class CmdLogin extends Command {
    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
        try {
            filesSystem.login(args[1], args[2]);
        }
        catch (ArrayIndexOutOfBoundsException exc){
            filesSystem.println("few arguments to login");
        }
        filesSystem.println("user logged in");
    }

    public CmdLogin(){
        setCmdId("login");
        setUserPermission(true);
    }
}
