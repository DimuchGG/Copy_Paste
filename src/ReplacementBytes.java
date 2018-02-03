import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class ReplacementBytes {
    private static final String BYTE_79 = "79";
    private static final String BYTE_B0 = "B0";
    private static final String BYTE_B1 = "B1";
    private static final String BYTE_22 = "22";
    private static final String BYTE_A7 = "A7";
    private static final String BYTE_A9 = "A9";

    private String firstByte;
    private String secondByte;
    private String thirdByte;
    private String fourthByte;
    private String fifthByte;
    private String sixthByte;
    private String newFirstByte;
    private String newSecondByte;
    private String newThirdByte;

    public ReplacementBytes(String firstByte, String secondByte, String thirdByte,
                            String fourthByte, String fifthByte, String sixthByte,
                            String newFirstByte, String newSecondByte, String newThirdByte) {
        if (firstByte.isEmpty()) {
            this.firstByte = BYTE_79;
        } else {
            this.firstByte = firstByte;
        }
        if (firstByte.isEmpty()) {
            this.secondByte = BYTE_B0;
        } else {
            this.secondByte = secondByte;
        }
        if (firstByte.isEmpty()) {
            this.thirdByte = BYTE_22;
        } else {
            this.thirdByte = thirdByte;
        }
        if (firstByte.isEmpty()) {
            this.fourthByte = BYTE_79;
        } else {
            this.fourthByte = fourthByte;
        }
        if (firstByte.isEmpty()) {
            this.fifthByte = BYTE_B1;
        } else {
            this.fifthByte = fifthByte;
        }
        if (firstByte.isEmpty()) {
            this.sixthByte = BYTE_22;
        } else {
            this.sixthByte = sixthByte;
        }
        if (firstByte.isEmpty()) {
            this.newFirstByte = BYTE_79;
        } else {
            this.newFirstByte = newFirstByte;
        }
        if (firstByte.isEmpty()) {
            this.newSecondByte = BYTE_A9;
        } else {
            this.newSecondByte = newSecondByte;
        }
        if (firstByte.isEmpty()) {
            this.newThirdByte = BYTE_22;
        } else {
            this.newThirdByte = newThirdByte;
        }
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
                replacementBytes(new File(path + "\\" + file));
                return;
            }
            if (!path.endsWith("\\")) {
                path.concat("\\");
            }
            File tempFile = new File(path + file);
            if (file.equals(".") || file.equals("..") || !tempFile.isDirectory()) continue;
            findFilesAndReplacementBytes(path + file, find);
        }
    }

    private void replacementBytes(File file) {
        ArrayList<String> alByteArray = fileToArrayListStringBytes(file);

        for (int i = 0; i < alByteArray.size() - 2; i++) {
            searchAndReplacedBytes(alByteArray, i,
                    firstByte, secondByte, thirdByte, newFirstByte, newSecondByte, newThirdByte);
            searchAndReplacedBytes(alByteArray, i,
                    fourthByte, fifthByte, sixthByte, newFirstByte, newSecondByte, newThirdByte);
        }
        rewriteFile(file, hexStringToByteArray(alByteArray));
    }

    private void searchAndReplacedBytes(ArrayList<String> alByteArray, int i, String firstByte, String secondByte,
                                        String thirdByte, String newFirstByte, String newSecondByte, String newThirdByte) {
        if (alByteArray.get(i).equals(firstByte)
                && alByteArray.get(i + 1).equals(secondByte)
                && alByteArray.get(i + 2).equals(thirdByte)) {
            alByteArray.set(i, newFirstByte);
            alByteArray.set(i + 1, newSecondByte);
            alByteArray.set(i + 2, newThirdByte);
            System.out.println("replacement");
        }
    }

    private ArrayList<String> fileToArrayListStringBytes(File file) {
        byte[] fileBArray = getBytesFromFile(file);

        ArrayList<String> alByteArray = new ArrayList<>();
        for (byte b : fileBArray != null ? fileBArray : new byte[0]) {
            alByteArray.add(String.format("%02X", b));
        }
        return alByteArray;
    }

    private void rewriteFile(File file, byte[] fileBArray) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileBArray);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showByteArray(byte[] fileBArray) {
        System.out.println();
        for (byte b : fileBArray) {
            System.out.print("" + b + " ");
        }
    }

    private static byte[] getBytesFromFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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