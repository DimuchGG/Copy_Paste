import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LibDEVICEID {
    public static final int BYTE_D1 = -47;
    public static final int BYTE_B0 = -80;
    public static final int BYTE_B1 = -79;
    public static final int BYTE_23 = 35;
    public static final int BYTE_A7 = -89;
    public static final int BYTE_A9 = -87;
    static boolean flag;

    public void findFilesAndReplacementBytes(String path, String find) throws IOException {
        File f = new File(path);
        String[] list = f.list();
        if (list == null) {
            System.out.println("list is null");
            return;
        }
        for (String file : list) {
            if (file.equals(find)) {
//                System.out.println("*");
                replacementBytes(new File(path + "\\" + file));
                return;
            }
            if (!path.endsWith("\\")) {
                path = path + "\\";
            }
            File tempFile = new File(path + file);
            if (file.equals(".") || file.equals("..") || !tempFile.isDirectory()) continue;
            findFilesAndReplacementBytes(path + file, find);
            if (!flag) continue;
            return;
        }
    }

    private void replacementBytes(File file) throws IOException {
        byte[] fileBArray = getBytesFromFile(file);
        for (int i = 0; i < fileBArray.length - 2; ++i) {
            if (fileBArray[i] == BYTE_D1 && fileBArray[i + 1] == BYTE_B0 && fileBArray[i + 2] == BYTE_23) {
                fileBArray[i + 1] = BYTE_A9;
                i += 2;
                System.out.print("replacement - 1\t*\t");
            }
            if (fileBArray[i] == BYTE_D1 && fileBArray[i + 1] == BYTE_B1 && fileBArray[i + 2] == BYTE_23) {
                fileBArray[i + 1] = BYTE_A9;
                i += 2;
                System.out.println("replacement - 2");
            }
        }
        rewriteFile(file, fileBArray);
    }

    private void rewriteFile(File file, byte[] fileBArray) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileBArray);
        fos.close();
    }

    private void showFile(File file) throws IOException {
        byte[] fileBArray = getBytesFromFile(file);
        System.out.println("\n**********************");
        for (byte b : fileBArray) {
            System.out.print("" + b + " ");
        }
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        int offset;
        FileInputStream is = new FileInputStream(file);
        long length = file.length();
        byte[] bytes = new byte[(int)length];
        int numRead;
        for (offset = 0; offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead) {
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }
}