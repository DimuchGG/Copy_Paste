import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by Dimuch on 02.03.2017.
 */
public class GlueFiles {
    private static final int EOBD_MIN = 2237;
    private static final int DEMO_MIN = 1477;
    private final String DPU_VER = "Dpu.ver";
    private final String LAST_ROW_SO = "last_row.so";
    private final String LIST_SO = "lib.cfg";
    private final String ADAP_VER = "Adap.ver";
    private final String LICENSE_DAT = "License.dat";
    private final String ANDROID_DEV = "Android.dev";
    private final String DEMO = "DEMO";
    private final String EOBD_2 = "EOBD2";
    private final String LAUNCH_BAT = "copy_paste.bat";
    private final String sProgramFilesPathForCmd = "C:\\\"Program Files\"\\Copy_Paste\\";
    private String sEOBDPath;
    private String sProgramFilesPath;
    private String sVehiclePath;

    public GlueFiles(String sProgramFilesPath, String sEOBDPath, String sVehiclePath) {
        this.sProgramFilesPath = sProgramFilesPath;
        this.sEOBDPath = sEOBDPath;
        this.sVehiclePath = sVehiclePath;

        glue();
    }

    private void glue() {
//        if (renameLICENSE()) {
//            System.out.println("renameLICENSE");
//        }

        if (copyFiles(new File(sEOBDPath + "\\" + LICENSE_DAT),
                new File(sEOBDPath + "\\" + ANDROID_DEV))) {
            System.out.println("copy " + LICENSE_DAT);
        }

        if (copyFiles(new File(sProgramFilesPath + "\\" + DPU_VER),
                new File(sEOBDPath + "\\" + DPU_VER))) {
            System.out.println("copy " + DPU_VER);
        }

        if (createLastRow(new File(sEOBDPath + "\\" + LAST_ROW_SO))) {
            System.out.println("create " + LAST_ROW_SO);
        }

        List<String> alFilesForCopy = getFilesForCopy(new File(sEOBDPath + "\\" + LIST_SO));
        if (glueFiles(new File(sEOBDPath + "\\" + ADAP_VER), alFilesForCopy)) {
            System.out.println("glue files to " + ADAP_VER);
        }

        if (startBatFile()) {
            System.out.println("start " + LAUNCH_BAT);
        }
    }

    private boolean startBatFile() {
        boolean isStart = false;
//        System.out.println("cmd /c start " + sProgramFilesPath + sLaunchFile + " " + sEOBDPath + " " + sVehiclePath + " " + sEOBDVersion);
        try {
            Runtime.getRuntime().exec("cmd /c start " + sProgramFilesPathForCmd + "\\" + LAUNCH_BAT
                    + " " + sEOBDPath + " " + sVehiclePath);
            isStart = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return isStart;
    }

    private boolean createLastRow(File file) {
        boolean isCreate = false;
        String[] str = sEOBDPath.split(Matcher.quoteReplacement("\\"));
        if (isEOBD(str[str.length-2], EOBD_2) && isNewVersion(str[str.length-1], EOBD_MIN)
                || isEOBD(str[str.length-2], DEMO) && isNewVersion(str[str.length-1], DEMO_MIN)) {
            rewriteFile(file, str[str.length-2] + str[str.length-1]);
            isCreate = true;
        } else if (isEOBD(str[str.length-2], EOBD_2) && !isNewVersion(str[str.length-1], EOBD_MIN)
                || isEOBD(str[str.length-2], DEMO) && !isNewVersion(str[str.length-1], DEMO_MIN)) {
            rewriteFile(file, "");
            isCreate = true;
        }
        return isCreate;
    }

    private boolean isNewVersion(String s, int value) {
        String[] sNum = s.replace("V", "").split("\\.");
        if (Integer.parseInt(sNum[sNum.length-2])*100 + Integer.parseInt(sNum[sNum.length-1]) > value)
            return true;
        return false;
    }

    private boolean isEOBD(String s, String sFolderName) {
        return s.equals(sFolderName);
    }

    private boolean glueFiles(File file, List<String> alFilesForCopy) {
        boolean isGlue = false;
        try {
            if (file.exists()) {
                file.delete();
            }
            for (String fileName : alFilesForCopy) {
                appendFile(file, Files.readAllBytes(Paths.get((new File(sEOBDPath + "\\" + fileName)).getPath())));
            }
            appendFile(file, Files.readAllBytes(Paths.get((new File(sEOBDPath + "\\" + LAST_ROW_SO)).getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isGlue;
    }

    private List<String> getFilesForCopy(File file) {
        if (file.exists()) {
            String str = readFiles(file);
//            System.out.println(str);
            String[] fileNames = str.split(";");
            return stringArrayToArrayList(fileNames);
        }
        return null;
    }

    private List<String> stringArrayToArrayList(String[] fileNames) {
        List<String> alFiles = new ArrayList<>();
        for (String s : fileNames) {
            alFiles.add(s);
        }
        return  alFiles;
    }

    private void printFileNames(List<String> alFiles) {
        alFiles.forEach(System.out::println);
    }

    private String readFiles(File file)  {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, Charset.defaultCharset());
    }

    private void rewriteFile(File file, String str) {
        byte[] fileBArray = str.getBytes();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(fileBArray);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendFile(File file, byte[] baAppend){
        try (FileOutputStream output = new FileOutputStream(file, true)) {
            output.write(baAppend);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean copyFiles(File source, File dest) {
        boolean isCopy = false;
        try {
            if (dest.exists()) {
                dest.delete();
            }
            Files.copy(source.toPath(), dest.toPath());
            isCopy = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isCopy;
    }

    private boolean renameLICENSE() {
        boolean isRename = false;
        File file = new File(sEOBDPath + "\\" + LICENSE_DAT); // создаем объект на файл LICENSE.DAT
        if(file.exists()){ // если файл существует, то переименовываем его
            File fAndroidDev = new File(sEOBDPath + "\\" + ANDROID_DEV);
            if (fAndroidDev.exists()) fAndroidDev.delete();
            isRename = file.renameTo(fAndroidDev);
        } else {
            System.out.println("File not found!");
        }
        return isRename;
    }
}
