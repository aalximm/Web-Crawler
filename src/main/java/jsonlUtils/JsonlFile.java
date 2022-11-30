package jsonlUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonlFile {
    private String path = "results/";
    private FileWriter writer = null;
    public JsonlFile(String fileName){
        this.path = path + fileName + ".jsonl";
    }

    public void createFile() throws IOException {
        File file = new File(path);
        if(file.exists()) file.delete();
        file.createNewFile();
    }

    public void open() throws IOException {
        assert (writer == null);
        writer = new FileWriter(path);
    }

    public void write(String url, String html) throws IOException {
        assert (writer != null);
        String newLine = "{\"url\": \"" + url + "\", \"html\": \"" + html + "\"}\n";
        writer.write(newLine);
    }

    public void close() throws IOException {
        assert (writer != null);
        writer.close();
        writer = null;
    }
}
