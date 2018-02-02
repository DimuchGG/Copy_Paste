import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileUtils {

    public static void replacementBytes(File oldFile, File newFile) throws IOException {
        ArrayList<String> alByteArray = fileToArrayListStringBytes(oldFile);

        for (int i = 0; i < alByteArray.size() - 2; i++) {
            if (alByteArray.get(i).equals("20")
                    && (alByteArray.get(i + 1).equals("77") || alByteArray.get(i + 1).equals("00"))
                    && alByteArray.get(i + 2).equals("6F")) {
                alByteArray.set(i + 1, "FF");
                System.out.println("replacement");
            }
        }

        rewriteFile(newFile, hexStringToByteArray(alByteArray));
    }

    public static ArrayList<String> fileToArrayListStringBytes(File file) {
        byte[] fileBArray = getBytesFromFile(file);

        ArrayList<String> alByteArray = new ArrayList<>();
        for (byte b : fileBArray) {
            alByteArray.add(String.format("%02X", b));
        }
        return alByteArray;
    }

    public static void rewriteFile(File file, byte[] fileBArray) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileBArray);
        fos.close();
    }

    public static void showByteArray(byte[] fileBArray) {
        System.out.println();
        for (byte b : fileBArray) {
            System.out.print("" + b + " ");
        }
    }

    public static byte[] getBytesFromFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] hexStringToByteArray(ArrayList<String> list) {
        int len = list.size();
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            data[i] = (byte) ((Character.digit(list.get(i).charAt(0), 16) << 4)
                    + Character.digit(list.get(i).charAt(1), 16));
        }
        return data;
    }
}
