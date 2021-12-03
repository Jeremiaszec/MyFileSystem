import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static sun.misc.MessageUtils.out;

public class FilesSystem implements Serializable {

    public static Directory currentDirectory;
    public Directory rootDirectory;
    private static ArrayList<Command> commands;
    private  ArrayList<User> users;
    private User currentUser;
    private boolean persistedEnable;

    public FilesSystem() {
        rootDirectory = new Directory("root", null);
        currentDirectory = rootDirectory;
        commands = new ArrayList<>();
        currentUser=null;
        users = new ArrayList<>();
        commands.add(new CmdCreateUser());
        commands.add(new CmdLogin());
        persistedEnable = false;
    }

    public static Directory getCurrentDirectory() {
        return currentDirectory;
    }

    public static void createNewFile(String name, String metadata, String data) {
        currentDirectory.createNewFile(name, metadata, data);
    }

    public static void add(Command cmd) {
        commands.add(cmd);
    }

    public File getFile(String nameTest) {
        return currentDirectory.getFile(nameTest);
    }

    public void createNewDirectory(String _name) {
        currentDirectory.createNewDirectory(_name, currentDirectory);
        println("directory created");
    }

    public Directory getDirectory(String _name) {
        return currentDirectory.getDirectory(_name);
    }

    public void changeDirectory(String nameDir) {
        if(nameDir.equals("..")){
                Directory backDir = currentDirectory.getOriginDirectory();
                if(backDir!=null) {
                    setCurrentDirectory(backDir);
                }
        }
        else {
            if(currentDirectory.getDirectory(nameDir)!=null) {
                currentDirectory=currentDirectory.getDirectory(nameDir);
            }
        }
    }

    public void setCurrentDirectory(Directory _directory) {
        currentDirectory = _directory;
    }

    public Directory getPreviousDirectory() {
        return currentDirectory.getOriginDirectory();
    }

    public void backDirectory() {
        setCurrentDirectory(currentDirectory.getOriginDirectory());
    }

    public void deleteDirectory(String _name) {
        currentDirectory.deleteDirectory(_name);
    }

    public void deleteFile(String file1) {
        currentDirectory.deleteFile(file1);
    }

    public String getPath() {
        return currentDirectory.getPath();
    }

    public void executeCmd(String msg) {
        String parts[] = msg.split(" ");
        for (Command cmd : commands) {
            if (parts[0].equals(cmd.getCmdId())) {
                if(cmd.getUserPermission()==true || currentUser != null) {
                    cmd.execute(this, parts);
                }
                else{
                    println("you most be logged first");
                    println("refused command: " + cmd.getCmdId());
                }
            }
        }

    }

    public void showContentFile(String _name) {
        File f = currentDirectory.getFile(_name);
        out(_name + "\n");
        out(f.getContent());
    }

    public void ls() {
        currentDirectory.ls();
    }

    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
    }


    public void login(String _user, String _password) {
        for(User u: users){
            if(u.getName().equals(_user) && u.verifyPassword(_password)){
                currentUser = u;
            }
        }
    }

    public void add(User _user) {
        for(User u: users){
            if(u.getName().equals(_user)){
                System.out.println(_user + " already exists");
            }
        }
        users.add(_user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUser(String _name) {
        for(User u: users){
            if(u.getName().equals(_name)){
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> _users) {
        users = _users;
    }

    public Object getRootDirectory() {
        return  rootDirectory;
    }

    public void read(String fileName) {
        if(Files.exists(Paths.get(fileName))){
            System.out.println("one existing file will be opened");
            try{
                InputStream is = new FileInputStream(fileName);
                ObjectInputStream ois  = new ObjectInputStream(is);

                ArrayList<User> myUsers = (ArrayList<User>) ois.readObject();
                Directory myRootDirectory = (Directory) ois.readObject();
                if(myUsers!=null) {
                    setUsers(myUsers);
                    System.out.println("users load complete..");
                }
                if(myRootDirectory!=null) {
                    setRootDirectory(myRootDirectory);
                    setCurrentDirectory(myRootDirectory);
                    System.out.println("directories load complete..");
                }
            } catch (ClassNotFoundException | IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        else {
            System.out.println("one file will be created");
        }


    }

    private void setRootDirectory(Directory myRootDirectory) {
        rootDirectory = myRootDirectory;
    }

    public boolean persistedEnable() {
        return persistedEnable;
    }

    public void write(String fileName) {
        //WRITE
        try{
            OutputStream is = new FileOutputStream(fileName);
            ObjectOutputStream ois = new ObjectOutputStream(is);
            ois.writeObject(getUsers());
            ois.writeObject(getRootDirectory());
            ois.close();
            System.out.println("the write was successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPersistedEnable() {
        persistedEnable = true;
    }

    public void persistedDisabled() {
        persistedEnable=false;
    }
}
