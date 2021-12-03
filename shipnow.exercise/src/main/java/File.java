import java.io.Serializable;

import static java.lang.System.*;
import static sun.misc.MessageUtils.out;

public class File implements Serializable {
    String name;
    String metadata;



    String data;

    public File(String _name, String _metadata, String _data) {
        this.name = _name;
        this.metadata = _metadata;
        this.data = _data;
    }

    public Object getName() {
        return name;
    }

    public String getContent() {
        return data;
    }

    public String getMetadata() {
        return metadata;
    }
}
