package world.ucode;

import javax.imageio.ImageIO;
import java.io.File;

public class StoredImage {

    private int id;
    private final String originalName;
    private final String type;
    private final Long size;
    private final String filePath;

    public StoredImage(int id, String originalName, String type, long size, String filePath) {
        this.id = id;
        this.originalName = originalName;
        this.type = type;
        this.size = size;
        this.filePath = filePath;
    }

    public StoredImage(String originalName, String type, long size, String filePath) {
        this.originalName = originalName;
        this.type = type;
        this.size = size;
        this.filePath = filePath;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) { this.id = id; }

    public String getOriginalName() {
        return this.originalName;
    }

    public String getType() {
        return this.type;
    }

    public long getSize() {
        return this.size;
    }

    public String getFilePath() { return this.filePath; }

    public String getFilename() { return this.filePath.replaceFirst("[.][^.]+$", ""); }
}
