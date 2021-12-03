import org.junit.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class filesTest {
    @Test
    public void createSystemFiles() throws Exception{
        FilesSystem sysFile = new FilesSystem();
        assertEquals("root", FilesSystem.currentDirectory.getName());
    }
    @Test
    public void originFileTest() throws Exception{
        FilesSystem sysFile = new FilesSystem();
        assertEquals(null, sysFile.getPreviousDirectory());
    }
    @Test
    public void createFileTest() throws Exception{
        FilesSystem sysFile = new FilesSystem();
        sysFile.createNewFile("nameTest","metadataTest", "dataTest");

        assertTrue(sysFile.getFile("nameTest")!=null);
    }
    @Test
    public void createSubdirectoryTest(){
        FilesSystem sysFiles = new FilesSystem();
        sysFiles.createNewDirectory("nameTest");

        assertTrue(sysFiles.getDirectory("nameTest") != null );
    }

    @Test
    public void changeDirectoryTest(){
        FilesSystem sysFiles = new FilesSystem();
        sysFiles.createNewDirectory("folder1");
        assertEquals("root", sysFiles.getCurrentDirectory().getName());
        sysFiles.changeDirectory("folder1");
        assertEquals("folder1", sysFiles.getCurrentDirectory().getName());
        assertEquals("root", sysFiles.getPreviousDirectory().getName());
    }

    @Test
    public void backDirectoryTest(){
        FilesSystem sysFiles = new FilesSystem();
        sysFiles.createNewDirectory("folder1");
        sysFiles.changeDirectory("folder1");
        sysFiles.createNewDirectory("folder2");
        sysFiles.changeDirectory("folder2");
        assertEquals("folder2", sysFiles.getCurrentDirectory().getName());
        sysFiles.backDirectory();
        sysFiles.backDirectory();
        assertEquals("root", sysFiles.getCurrentDirectory().getName());
    }

    @Test
    public void destroyDirectoryTest(){
        FilesSystem sysFiles = new FilesSystem();
        sysFiles.createNewDirectory("file1");
        assertTrue(sysFiles.getDirectory("file1")!=null);
        sysFiles.deleteDirectory("file1");
        assertTrue(sysFiles.getDirectory("file1")==null);
    }

    @Test
    public void destroyFileTest(){
        FilesSystem filesSys = new FilesSystem();
        filesSys.createNewFile("file1", "metadata1", "data1");
        assertTrue(filesSys.getFile("file1")!=null);
        filesSys.deleteFile("file1");
        assertTrue(filesSys.getFile("file1")==null);
    }

    @Test
    public void showPath(){
        FilesSystem filesSys = new FilesSystem();
        filesSys.createNewDirectory("dir1");
        filesSys.changeDirectory("dir1");
        filesSys.createNewDirectory("dir2");
        filesSys.changeDirectory("dir2");
        assertEquals("root/dir1/dir2",filesSys.getPath());
    }

    @Test
    public void lsTest(){

    }

    @Test
    public void cmdCreateDirectoryTest(){
        FilesSystem filesSys = new FilesSystem();
        CmdCreateDirectory  cmd = new CmdCreateDirectory();
        filesSys.add(cmd);

        String msg = "create_folder folder_1";

        String parts[] = msg.split(" ");
        String dirName = parts[1];

        filesSys.executeCmd(msg);

        assertEquals(dirName, filesSys.getDirectory(dirName).getName());
    }

    @Test
    public void cmdCreateFileTest(){
        FilesSystem filesSys = new FilesSystem();
        CmdCreateFile cmd = new CmdCreateFile();
        filesSys.add(cmd);

        String msg = "create_file file_1 \"contenido\"";
        String parts[] = msg.split(" ");
        String fileName = parts[1];

        filesSys.executeCmd(msg);

        assertEquals(fileName, filesSys.getFile(fileName).getName());
    }

    @Test
    public void cmdShowContentFileTest(){
        FilesSystem filesSys = new FilesSystem();
        filesSys.createNewFile("file_1", "metadata", "contentTest");

        CmdShowContentFile cmd = new CmdShowContentFile();
        filesSys.add(cmd);

        String msg = "show file_1";
        String parts[]= msg.split(" ");

        filesSys.executeCmd(msg);
    }

    @Test
    public void cmdShowMetadataFileTest(){
        FilesSystem filesSys = new FilesSystem();
        filesSys.createNewFile("file_1", "metadataTest", "contentTest");

        CmdShowMetadata cmd = new CmdShowMetadata();
        filesSys.add(cmd);

        String msg = "metadata file_1";
        filesSys.executeCmd(msg);
    }

    @Test
    public void cmdChangeDirectoryTest(){
        FilesSystem filesSys = new FilesSystem();
        filesSys.createNewDirectory("folder_1");
        CmdChangeDirectory cmd = new CmdChangeDirectory();
        filesSys.add(cmd);

        String msg = "cd folder_1";
        filesSys.executeCmd(msg);

        String parts[] = msg.split(" ");

        assertEquals("folder_1", filesSys.getCurrentDirectory().getName());
    }

    @Test
    public void cmdBackDirectoryTest(){
        FilesSystem filesSys = new FilesSystem();
        filesSys.createNewDirectory("folder_1");
        filesSys.changeDirectory("folder_1");

        CmdChangeDirectory cmd = new CmdChangeDirectory();
        filesSys.add(cmd);

        assertEquals("folder_1", filesSys.getCurrentDirectory().getName());

        filesSys.createNewDirectory("folder_2");
        filesSys.changeDirectory("folder_2");

        String msg = "cd ..";
        filesSys.executeCmd(msg);

        assertEquals("root/folder_1", filesSys.getCurrentDirectory().getPath());
    }

    @Test
    public void cmdDestroyFileTest(){
        FilesSystem filesSys = new FilesSystem();
        CmdDestroy cmd = new CmdDestroy();
        filesSys.add(cmd);

        filesSys.createNewFile("file_1", "metadataTest", "dataTest");
        assertTrue(filesSys.getFile("file_1")!=null);

        String msg = "destroy file_1";
        filesSys.executeCmd(msg);

        assertTrue(filesSys.getFile("file_1")==null);
    }

    @Test
    public void cmdDestroyFolderTest(){
        FilesSystem filesSys = new FilesSystem();
        CmdDestroy cmd = new CmdDestroy();
        filesSys.add(cmd);

        filesSys.createNewDirectory("folder_1");
        assertTrue(filesSys.getDirectory("folder_1")!=null);

        String msg = "destroy folder_1";
        filesSys.executeCmd(msg);

        assertTrue(filesSys.getDirectory("folder_1")==null);
    }


    @Test
    public void cmdLsTest(){
        FilesSystem filesSystem = new FilesSystem();
        filesSystem.createNewDirectory("dir_1");
        filesSystem.createNewDirectory("dir_2");
        filesSystem.createNewFile("file_1", "meteadataTest", "dataTest");

        CmdLs cmd = new CmdLs();
        filesSystem.add(cmd);

        String msg = "ls";

        filesSystem.executeCmd(msg);
    }



    @Test
    public void cmdWhereamiTest(){
        FilesSystem filesSystem = new FilesSystem();
        filesSystem.createNewDirectory("dir_1");
        filesSystem.changeDirectory("dir_1");
        filesSystem.createNewDirectory("dir_2");
        filesSystem.changeDirectory("dir_2");

        assertEquals("dir_2",filesSystem.getCurrentDirectory().getName());

        CmdGetPath cmd = new CmdGetPath();
        filesSystem.add(cmd);

        String msg = "whereami";

        filesSystem.executeCmd(msg);
    }

    @Test
    public void createNewUserTest(){
        User user = new User("jeremias","1234");
        FilesSystem filesSystem = new FilesSystem();
        filesSystem.add(user);

        assertEquals("jeremias", filesSystem.getUser("jeremias").getName());
    }

    @Test
    public void loginUserTest(){
        User user = new User("matias", "1123");
        FilesSystem filesSystem = new FilesSystem();


        filesSystem.add(user);
        filesSystem.login("matias", "1123");

        assertEquals("matias", filesSystem.getCurrentUser().getName());
    }

    @Test
    public void changeUserTest(){
        User user = new User("matias", "1123");
        User user2 = new User("mauro", "4321");

        FilesSystem filesSystem = new FilesSystem();

        filesSystem.add(user);
        filesSystem.add(user2);

        filesSystem.login("matias", "1123");
        assertEquals("matias", filesSystem.getCurrentUser().getName());
    }

    @Test
    public void permissionDeniedTest(){
        User user = new User("maria","1234");
        FilesSystem filesSystem = new FilesSystem();
        filesSystem.add(user);

        CmdCreateDirectory cmd = new CmdCreateDirectory();
        filesSystem.add(cmd);

        String mariaMsg = "create_folder mariaFolder";
        filesSystem.executeCmd(mariaMsg);

        assertTrue(filesSystem.getDirectory("mariaFolder")==null);

        filesSystem.login("maria", "1234");

        filesSystem.executeCmd(mariaMsg);
        assertTrue(filesSystem.getDirectory("mariaFolder")!=null);
    }

    @Test
    public void cmdCreateUserTest(){
        FilesSystem filesSystem = new FilesSystem();

        String msg = "create_user mauro 1234";
        filesSystem.executeCmd(msg);

        assertTrue(filesSystem.getUser("mauro")!=null);
    }

    @Test
    public void cmdLoginTest(){
        FilesSystem filesSystem = new FilesSystem();
        CmdCreateDirectory cmdCreateDirectory = new CmdCreateDirectory();
        filesSystem.add(cmdCreateDirectory);


        filesSystem.executeCmd("create_user mauro 1234");
        filesSystem.executeCmd("login mauro 1234");
        filesSystem.executeCmd("create_folder mauroFolder");

        assertTrue(filesSystem.getDirectory("mauroFolder") != null);
    }

    @Test
    public void writeObjectTest(){
        FilesSystem filesSystem = new FilesSystem();
        filesSystem.createNewDirectory("casaFolder");
        filesSystem.createNewFile("file1", "metadat", "dat");
        filesSystem.createNewFile("file2", "metadat2", "dat2");
        filesSystem.createNewFile("file3", "metadat3", "dat3");


        Directory myDir = filesSystem.getCurrentDirectory();

        final String fileName = "myFilesSystem.bin";

        //WRITE
        try{
            OutputStream is = new FileOutputStream(fileName);
            ObjectOutputStream ois = new ObjectOutputStream(is);
            ois.writeObject(filesSystem.getUsers());
            ois.writeObject(filesSystem.getRootDirectory());
            ois.close();
            System.out.println("the write was successful");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        filesSystem.ls();

    }

    @Test
    public void readObjectTest(){

        FilesSystem filesSystem = new FilesSystem();
        final String fileName = "myFilesSystem.bin";
        //READ
        try{
            InputStream is = new FileInputStream(fileName);
            ObjectInputStream ois  = new ObjectInputStream(is);


            filesSystem.setUsers((ArrayList<User>) ois.readObject());
            filesSystem.setCurrentDirectory((Directory) ois.readObject());

            System.out.println("read complete");

            if(filesSystem != null) {
                filesSystem.ls();
            }

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
