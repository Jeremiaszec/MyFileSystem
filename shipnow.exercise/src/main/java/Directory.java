import com.sun.deploy.net.MessageHeader;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.System.*;
import static sun.misc.MessageUtils.out;

public class Directory implements Serializable {
    private String name ="";
    private Directory originDirectory;
    private ArrayList <File> files;
    private ArrayList<Directory> subdirectories;

    public Directory(String _name, Directory origin){
        name = _name;
        setOriginDirectory(origin);
        files = new ArrayList<>();
        subdirectories = new ArrayList<>();
    }

    public void setOriginDirectory(Directory origin) {
        originDirectory = origin;
    }

    public String getName(){
        return name;
    }

    public void createNewFile(String name, String metadata, String data) {
        files.add(new File(name, metadata, data));
    }

    public File getFile(String _name) {

        for(File f: files){
            if(f.getName().equals(_name)) {
                return f;
            }
        }
        return null;
    }

    public void createNewDirectory(String _name, Directory origin) {
        subdirectories.add(new Directory(_name, origin));
    }

    public Directory getDirectory(String _name) {
        for(Directory d: subdirectories){
            if(_name.equals(d.getName())){
                return d;
            }
        }
        return null;
    }

    public Directory getOriginDirectory() {
        return originDirectory;
    }

    public void deleteDirectory(String _name) {
        int index=-1;
        for(Directory d: subdirectories){
            if(d.name.equals(_name)){
                index=subdirectories.indexOf(d);
            }
        }
        if(index!=-1) {
            subdirectories.remove(index);
        }
    }

    public void deleteFile(String _name) {
        int index = -1;
        for(File f: files){
            if(f.getName().equals(_name)){
                index = files.indexOf(f);
            }
        }
        if(index!=-1) {
            files.remove(index);
        }
    }

    public String getPath() {
        if(originDirectory==null){
            return name;
        }
        return originDirectory.getPath() + "/" + name;
    }

    public void ls() {
        System.out.println("\nfiles:");
        for(File f: files){
            System.out.println(f.getName());
        }
        System.out.println("directories:");
        for(Directory d: subdirectories){
            System.out.println(d.getName());
        }
    }
}
