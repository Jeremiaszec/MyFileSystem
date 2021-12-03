public class CmdCreateUser extends Command {
    @Override
    public void execute(FilesSystem filesSystem, String[] args) {
        try{
            filesSystem.add(new User(args[1],args[2]));
        }
        catch(ArrayIndexOutOfBoundsException exc){
            filesSystem.println("few arguments to create a user");
        }
        filesSystem.println("user created");
    }

    public CmdCreateUser(){
        setCmdId("create_user");
        setUserPermission(true);
    }
}
