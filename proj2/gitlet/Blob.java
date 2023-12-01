package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.CWD;
import static gitlet.Utils.*;

public class Blob implements Serializable {
    private String fileName;
    private File file;
    private byte[] fileContent;
    private String fileID;

    Blob(String fileName) {
        this.fileName = fileName;
        this.file = join(CWD, fileName);
        this.fileContent = readContents(file);
        this.fileID = sha1((Object) serialize(this));
    }

    public String getName() {
        return fileName;
    }
    public File getFile() {
        return file;
    }
    public byte[] getContent() {
        return fileContent;
    }
    public String getID() {
        return fileID;
    }
}
