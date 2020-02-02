package fish.eyebrow.mugen.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileUtil {
    public static String readResourceAsString(final String path) {
        try {
            return Files.readString(Paths.get(ClassLoader.getSystemResource(path).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readResourceAsBytes(final String path) {
        try {
            return Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(path).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
