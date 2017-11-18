import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class LibDIAG {
    private static final String BYTE_79 = "79";
    private static final String BYTE_B0 = "B0";
    private static final String BYTE_B1 = "B1";
    private static final String BYTE_22 = "22";
    private static final String BYTE_A7 = "A7";
    private static final String BYTE_A9 = "A9";

    private String firstByte;
    private String secondByte;
    private String newByte;

    public LibDIAG() {
        this.firstByte = BYTE_B0;
        this.secondByte = BYTE_B1;
        this.newByte = BYTE_A9;
    }

    public LibDIAG(String firstByte, String secondByte, String newByte) {
        if (firstByte.isEmpty()) {
            this.firstByte = BYTE_B0;
        } else {
            this.firstByte = firstByte;
        }
        if (secondByte.isEmpty()) {
            this.secondByte = BYTE_B1;
        } else {
            this.secondByte = secondByte;
        }
        if (newByte.isEmpty()) {
            this.newByte = BYTE_A9;
        } else {
            this.newByte = newByte;
        }
//        System.out.println(this.firstByte + this.secondByte + this.newByte);
    }

    public void findFilesAndReplacementBytes(String path, String find) {
        File f = new File(path);
        String[] list = f.list();
        if (list == null) {
            System.out.println("list is null");
            return;
        }
        for (String file : list) {
            if (file.equals(find)) {
//                System.out.println("*");
                try {
                    replacementBytes(new File(path + "\\" + file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            if (!path.endsWith("\\")) {
                path = path + "\\";
            }
            File tempFile = new File(path + file);
            if (file.equals(".") || file.equals("..") || !tempFile.isDirectory()) continue;
            findFilesAndReplacementBytes(path + file, find);
        }
    }

    private void replacementBytes(File file) throws IOException {
        byte[] fileBArray = getBytesFromFile(file);
//        showByteArray(fileBArray);
        ArrayList<String> alByteArray = new ArrayList<>();
        for (byte b : fileBArray) {
            alByteArray.add(String.format("%02X", b));
        }
//        System.out.println();
//        System.out.println(alByteArray);
        for (int i = 0; i < alByteArray.size()-2; i++) {
            if (alByteArray.get(i).equals(BYTE_79)
                    && (alByteArray.get(i+1).equals(firstByte) || alByteArray.get(i+1).equals(secondByte))
                    && alByteArray.get(i+2).equals(BYTE_22)) {
                alByteArray.set(i+1, newByte);
                System.out.println("replacement");

            }
        }
//        System.out.println(alByteArray);
        fileBArray = hexStringToByteArray(alByteArray);
//        showByteArray(fileBArray);
        rewriteFile(file, fileBArray);
    }

    private void rewriteFile(File file, byte[] fileBArray) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileBArray);
        fos.close();
    }

    private void showByteArray(byte[] fileBArray) {
        System.out.println();
        for (byte b : fileBArray) {
            System.out.print("" + b + " ");
        }
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private static byte[] hexStringToByteArray(ArrayList<String> list) {
        int len = list.size();
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            data[i] = (byte) ((Character.digit(list.get(i).charAt(0), 16) << 4)
                    + Character.digit(list.get(i).charAt(1), 16));
        }
        return data;
    }
}