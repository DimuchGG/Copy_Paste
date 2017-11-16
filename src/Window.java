import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Window {

    private static final int WIDTH_FRAME = 600;
    private static final int HEIGHT_FRAME = 220;
    private static final int WIDTH_TEXT_FIELD = 450;
    private static final int HEIGHT_TEXT_FIELD = 26;
    private static final String COPY_PASTE = "Калькулятор";

    private final String START = "Начать";
//    private final String START = "Start";
    private final String REPLACEMENT_BYTES = "Замена байт";
//    private final String REPLACEMENT_BYTES = "Replacement bytes";
    private final String DONE = "Выполнено";
//    private final String DONE = "Done";
    private final String STATUS = "Статус";
//    private final String STATUS = "Status";
    private final String GLUE_AND_COPY_FILES = "Объединить и скопировать файлы";
//    private final String GLUE_AND_COPY_FILES = "Glue and copy files";
    private final String REPLACED_BYTES = "Байты заменены";
//    private final String REPLACED_BYTES = "Replaced bytes";
    private final String VEHICLE = "Vehicle";
//    private final String VEHICLE = "Vehicle";
    private final String SELECT = "Выбрать";
//    private final String SELECT = "Select";
    private final String EOBD = "EOBD";
//    private final String EOBD = "EOBD";
    private final String SELECT_DIRECTORY = "Выбрать директорию";
//    private final String SELECT_DIRECTORY = "Select directory";
    private final String sEOBD = "Введите путь к EOBD";
//    private final String sEOBD = "Enter the path to the EOBD";
    private final String sVehicle = "Введите путь к Vehicle";
//    private final String sVehicle = "Enter the path to the Vehicle";

    private final String START_DIR_EOBD = "startDirectoryEOBD.txt";
    private final String START_DIR_VEHICLE = "startDirectoryVehicle.txt";
    private final String sProgramFiles = "C:\\Program Files\\Copy_Paste\\";
    private final String LAUNCH_BAT = "copy_paste.bat";
    private final String LIB_CFG = "lib.cfg";
    private final String sFindFileLibDeviceid = "libDEVICEID.so";
    private final String sFindFileLibDiag = "libDIAG.so";
    private String sEOBDPath = "C:\\Users\\Dimuch\\Desktop\\test\\EOBD2\\V22.37";
    private String sVehiclePath = "C:\\Users\\Dimuch\\Desktop\\test\\Vehicle";

    private JFrame frame;
    private JPanel panel;
    private JPanel pFirstRow;
    private JPanel pSecondRow;
    private JPanel pThirdRow;
    private JPanel pFourthRow;
    private JLabel lEOBD;
    private JLabel lVehicle;
    private JLabel lStatus;
    private HintTextField tfEOBDPath;
    private HintTextField tfVehiclePath;
    private JButton bEOBDPath;
    private JButton bVehiclePath;
    private JButton bStart;
    private JCheckBox cbGlueAndCopy;
    private JCheckBox cbReplaced;
//    private JCheckBox cbDemo;
    private JPanel pArrayBytes;
//    private JPanel pLastRow;
    private HintTextField tfFirstByte;
    private HintTextField tfSecondByte;
    private HintTextField tfNewByte;
//    private JTextField tfLastRow;

    private final MyResourceUtils myResourceUtils;

    public Window() {
        initFrame();
        initPanel();
        fillFirstRow();
        fillSecondRow();
        fillThirdRow();
        fillFourthRow();
        fillPanel();
        addPanel();
        myResourceUtils = new MyResourceUtils();
    }

    private void initFrame() {
        frame = new JFrame(COPY_PASTE);
        frame.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initPanel() {
        panel = new JPanel();
        pFirstRow = new JPanel();
        pSecondRow = new JPanel();
        pThirdRow = new JPanel();
        pFourthRow = new JPanel();
    }

    private void fillFirstRow() {
        pFirstRow.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lEOBD = new JLabel(EOBD);
        tfEOBDPath = new HintTextField(sEOBD);
        tfEOBDPath.setPreferredSize(new Dimension(WIDTH_TEXT_FIELD, HEIGHT_TEXT_FIELD));
        bEOBDPath = new JButton(SELECT);
        bEOBDPath.addActionListener(e -> {
            String path = selectDirectory(myResourceUtils.readFiles(START_DIR_EOBD));
//            String path = selectDirectory(myResourceUtils.getStartDirEOBD(START_DIR_EOBD));
            if (!path.equals("")) {
                tfEOBDPath.setText(path);
//                myResourceUtils.rewriteStartDir(START_DIR_EOBD, path);
            }
        });
        pFirstRow.add(new JTextField());
//        pFirstRow.add(lEOBD);
        pFirstRow.add(tfEOBDPath);
        pFirstRow.add(bEOBDPath);
    }

    private void fillSecondRow() {
        pSecondRow.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lVehicle = new JLabel(VEHICLE);
        tfVehiclePath = new HintTextField(sVehicle);
        tfVehiclePath.setPreferredSize(new Dimension(WIDTH_TEXT_FIELD, HEIGHT_TEXT_FIELD));
        bVehiclePath = new JButton(SELECT);
        bVehiclePath.addActionListener(e -> {
            String path = selectDirectory(myResourceUtils.readFiles(START_DIR_VEHICLE));
//            String path = selectDirectory(myResourceUtils.getStartDirEOBD(START_DIR_VEHICLE));
            if (!path.equals("")) {
                tfVehiclePath.setText(path);
//                myResourceUtils.rewriteStartDir(START_DIR_VEHICLE, path);
            }
        });
//        pSecondRow.add(lVehicle);
        pSecondRow.add(tfVehiclePath);
        pSecondRow.add(bVehiclePath);
    }

    private void fillThirdRow() {
        pThirdRow.setLayout(new FlowLayout(FlowLayout.CENTER));
        cbGlueAndCopy = new JCheckBox(GLUE_AND_COPY_FILES);
        cbGlueAndCopy.setSelected(true);
        cbGlueAndCopy.setVisible(false);
        pThirdRow.add(cbGlueAndCopy);
//        pThirdRow.add(createBlockGlueAndCopy());
        cbReplaced = new JCheckBox(REPLACEMENT_BYTES);
        cbReplaced.addActionListener(e -> {
//            System.out.println("cbReplaced");
            if (cbReplaced.isSelected()) {
//                pArrayBytes.setVisible(true);
            } else {
                pArrayBytes.setVisible(false);
            }
        });
        pThirdRow.add(cbReplaced);
        pThirdRow.add(createBlockReplaced());
    }

//    private JPanel createBlockGlueAndCopy() {
//        pLastRow = new JPanel();
//        pLastRow.setBorder(BorderFactory.createLineBorder(Color.black));
//
//        tfLastRow = new JTextField(sEOBDVersion);
//        pLastRow.add(tfLastRow);
//
//        pLastRow.setVisible(false);
//        return pLastRow;
//    }

    private JPanel createBlockReplaced() {
        pArrayBytes = new JPanel();
        pArrayBytes.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel pThreeBytes = new JPanel();
        pThreeBytes.setLayout(new BoxLayout(pThreeBytes, BoxLayout.Y_AXIS));

        JPanel pFirstArray = new JPanel();
        pFirstArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        pFirstArray.add(new JLabel("D1"));
        tfFirstByte = new HintTextField("B0");
        pFirstArray.add(tfFirstByte);
        pFirstArray.add(new JLabel("23"));
        pThreeBytes.add(pFirstArray);

        JPanel pSecondArray = new JPanel();
        pSecondArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSecondArray.add(new JLabel("D1"));
        tfSecondByte = new HintTextField("B1");
        pSecondArray.add(tfSecondByte);
        pSecondArray.add(new JLabel("23"));
        pThreeBytes.add(pSecondArray);

        pArrayBytes.add(pThreeBytes);
        JLabel label = new JLabel(" ➔ ");
        label.setFont(new Font("MS Gothic", Font.BOLD, 18));
        pArrayBytes.add(label);

        JPanel pThirdArray = new JPanel();
        pThirdArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        pThirdArray.add(new JLabel("D1"));
        tfNewByte = new HintTextField("A9");
        pThirdArray.add(tfNewByte);
        pThirdArray.add(new JLabel("23"));
        pArrayBytes.add(pThirdArray);

        pArrayBytes.setVisible(false);
        return pArrayBytes;
    }

    private void fillFourthRow() {
        pFourthRow.setLayout(new FlowLayout(FlowLayout.CENTER));
//        cbDemo = new JCheckBox("Demo");
//        pFourthRow.add(cbDemo);
        bStart = new JButton(START);
        bStart.addActionListener(e -> {
            sEOBDPath = tfEOBDPath.getText();
            sVehiclePath = tfVehiclePath.getText();
            myResourceUtils.rewriteFile(START_DIR_EOBD, tfEOBDPath.getText());
            myResourceUtils.rewriteFile(START_DIR_VEHICLE, tfVehiclePath.getText());
            if (cbGlueAndCopy.isSelected() && checkCorrectnessDataEntry()) {
                System.out.println("glueAndCopyFiles");
                Thread thread = new Thread(() -> new GlueFiles(sProgramFiles, sEOBDPath, sVehiclePath));
                thread.start();
            }
            if (cbReplaced.isSelected()) {
                lStatus.setText(REPLACED_BYTES);
//                System.out.println("findFilesAndReplacementBytes");
                Thread thread = new Thread(() ->
                        new LibDEVICEID(tfFirstByte.getText(), tfSecondByte.getText(), tfNewByte.getText())
                        .findFilesAndReplacementBytes(sVehiclePath, sFindFileLibDeviceid));
                thread.start();
                thread = new Thread(() ->
                        new LibDIAG(tfFirstByte.getText(), tfSecondByte.getText(), tfNewByte.getText())
                        .findFilesAndReplacementBytes(sVehiclePath, sFindFileLibDiag));
                thread.start();
                try {
                    thread.join();
                    lStatus.setText(DONE);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        lStatus = new JLabel(STATUS);
        pFourthRow.add(bStart);
        pFourthRow.add(lStatus);
    }

    private void fillPanel() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(pFirstRow);
        panel.add(pSecondRow);
        panel.add(pThirdRow);
        panel.add(pFourthRow);
    }

    private void addPanel() {
        frame.add(panel);
    }

    public void setVisible(Boolean b) {
        if (b) {
            frame.setVisible(true);
        } else {
            frame.setVisible(false);
        }
    }

    private String selectDirectory(String sStartDirectory) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(sStartDirectory));
        int ret = fileChooser.showDialog(null, SELECT_DIRECTORY);
        if (ret == 0) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    private boolean checkCorrectnessDataEntry() {
//        System.out.println(sProgramFiles + sLaunchFile);
        if ((new File(sProgramFiles + LAUNCH_BAT)).exists() &&
                (new File(sEOBDPath + "\\" + LIB_CFG)).exists())
            return true;
        return false;
    }
}