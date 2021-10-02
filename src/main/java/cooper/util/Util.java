package cooper.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Util {

    // https://mkyong.com/java/how-to-convert-inputstream-to-file-in-java/
    // https://github.com/tlylt/ip/blob/master/src/main/java/tlylt/haha/Storage.java
    // https://www.baeldung.com/convert-input-stream-to-a-file
    /**
     * Convert input stream (resources) to file during deployment.
     */
    public static File inputStreamtoTmpFile(InputStream inputStream, String baseDir, String fileName)
            throws IOException {
        File tmpFile = null;
        Path folderDir = Paths.get(baseDir);
        Path fileDir = Paths.get(baseDir + fileName);
        if (Files.notExists(folderDir)) {
            Files.createDirectories(folderDir);
        }
        // append == false
        tmpFile = new File(fileDir.toString());
        OutputStream tmpFileStream = new FileOutputStream(tmpFile);

        byte[] buffer = inputStream.readAllBytes();
        tmpFileStream.write(buffer);
        tmpFileStream.close();

        return tmpFile;
    }


}
