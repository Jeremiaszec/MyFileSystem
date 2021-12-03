import java.io.Serializable;

public abstract class Command implements Serializable {

    private String cmdId;
    private boolean userPermission=false;

    public abstract void execute(FilesSystem filesSystem, String args[]);

    public void setCmdId(String _cmd){
        cmdId = _cmd;
    }

    public void setUserPermission(boolean permission){
        userPermission = permission;
    }

    public String getCmdId() {
        return cmdId;
    }


    public boolean getUserPermission(){
        return userPermission;
    }
}
