package cooper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

//@@author Rrraaaeee
/**
 * Utility class containing helper methods to help in some of cOOPer's features.
 */
public class Util {

    // https://mkyong.com/java/how-to-convert-inputstream-to-file-in-java/
    // https://github.com/tlylt/ip/blob/master/src/main/java/tlylt/haha/Storage.java
    // https://www.baeldung.com/convert-input-stream-to-a-file
    /**
     * Converts input stream (resources) to file during deployment.
     */
    public static File inputStreamToTmpFile(InputStream inputStream, String baseDir, String fileName)
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

    /**
     * Converts input stream from a file to a string object. Used in converting the LaTeX template files to a string
     * for easier handling.
     * @param inputStream File to read from
     * @return String representing the content of the file.
     */
    public static String inputStreamToString(InputStream inputStream) {
        return new BufferedReader(
               new InputStreamReader(inputStream, StandardCharsets.UTF_8))
               .lines()
               .collect(Collectors.joining("\n"));
    }
}
