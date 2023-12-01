package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.CWD;
import static gitlet.Utils.*;

public class Blob implements Serializable {
    private String fileName;
    private File file;
    private byte[] fileContent;
    private String contentID; // only differs in content!

    /** Construct a Blob by given file name. */
    Blob(String fileName) {
        this.fileName = fileName;
        this.file = join(CWD, fileName);
        this.fileContent = readContents(file);
        this.contentID = sha1(this.fileContent);
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
    public String getContentID() {
        return contentID;
    }
}
