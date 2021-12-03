import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;

    public String getName(){
        return name;
    }

    public User(String _name, String _password){
        this.name = _name;
        this.password = _password;
    }

    public boolean verifyPassword(String _password) {
        return this.password.equals(_password);
    }
}
