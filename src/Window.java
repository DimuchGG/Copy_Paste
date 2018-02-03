import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Window {

    private final int WIDTH_FRAME = 640;
    private final int HEIGHT_FRAME = 300;
    private final int WIDTH_TEXT_FIELD = 450;
    private final int HEIGHT_TEXT_FIELD = 26;
    private final String COPY_PASTE = "Калькулятор";
    private final int DEFAULT_WIDTH = 26;
    private final int DEFAULT_HEIGHT = 26;

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
    private JLabel lStatus;
    private HintTextField tfEOBDPath;
    private HintTextField tfVehiclePath;
    private JCheckBox cbGlueAndCopy;
    private JCheckBox cbReplaced;
    private JCheckBox cbDemo;
    private JPanel pArrayBytes;
    private HintTextField tfFirstByte;
    private HintTextField tfSecondByte;
    private HintTextField tfThirdByte;
    private HintTextField tfFourthByte;
    private HintTextField tfFifthByte;
    private HintTextField tfSixthByte;
    private HintTextField tfNewFirstByte;
    private HintTextField tfNewSecondByte;
    private HintTextField tfNewThirdByte;
    private JTextField tfLastRow;

    private final MyResourceUtils myResourceUtils;
    private HintTextField tfFileNameForChanging;

    public Window() {
        myResourceUtils = new MyResourceUtils();
        initFrame();
        initPanel();
        fillFirstRow();
        fillSecondRow();
        fillThirdRow();
        fillFourthRow();
        fillPanel();
        addPanel();
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
        JLabel lEOBD = new JLabel(Constants.EOBD);
        tfEOBDPath = new HintTextField(Constants.sEOBD, WIDTH_TEXT_FIELD, HEIGHT_TEXT_FIELD);
        String path = myResourceUtils.readFiles(START_DIR_EOBD);
        if (!path.equals("")) {
            tfEOBDPath.setText(path);
        }
        JButton bEOBDPath = new JButton(Constants.SELECT);
        bEOBDPath.addActionListener(e -> {
            String newPath = selectDirectory(myResourceUtils.readFiles(START_DIR_EOBD));
            tfEOBDPath.setText(newPath);
            myResourceUtils.rewriteFile(START_DIR_EOBD, newPath);

        });
        pFirstRow.add(new JTextField());
        pFirstRow.add(lEOBD);
        pFirstRow.add(tfEOBDPath);
        pFirstRow.add(bEOBDPath);
    }

    private void fillSecondRow() {
        pSecondRow.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel lVehicle = new JLabel(Constants.VEHICLE);
        tfVehiclePath = new HintTextField(Constants.sVehicle);
        tfVehiclePath.setPreferredSize(new Dimension(WIDTH_TEXT_FIELD, HEIGHT_TEXT_FIELD));
        String path = myResourceUtils.readFiles(START_DIR_VEHICLE);
        if (!path.equals("")) {
            tfVehiclePath.setText(path);
        }
        JButton bVehiclePath = new JButton(Constants.SELECT);
        bVehiclePath.addActionListener(e -> {
            String newPath = selectDirectory(myResourceUtils.readFiles(START_DIR_VEHICLE));
            tfVehiclePath.setText(newPath);
            myResourceUtils.rewriteFile(START_DIR_VEHICLE, newPath);

        });
        pSecondRow.add(lVehicle);
        pSecondRow.add(tfVehiclePath);
        pSecondRow.add(bVehiclePath);
    }

    private void fillThirdRow() {
        pThirdRow.setLayout(new FlowLayout(FlowLayout.CENTER));
        cbGlueAndCopy = new JCheckBox(Constants.GLUE_AND_COPY_FILES);
        cbGlueAndCopy.setSelected(true);
//        cbGlueAndCopy.setVisible(false);
        pThirdRow.add(cbGlueAndCopy);
        pThirdRow.add(createBlockGlueAndCopy());
        cbReplaced = new JCheckBox(Constants.REPLACEMENT_BYTES);
        cbReplaced.addActionListener(e -> {
            if (cbReplaced.isSelected()) {
                pArrayBytes.setVisible(true);
            } else {
                pArrayBytes.setVisible(false);
            }
        });
        pThirdRow.add(cbReplaced);
        pThirdRow.add(createBlockReplaced());
    }

    private JPanel createBlockGlueAndCopy() {
        JPanel pLastRow = new JPanel();
        pLastRow.setBorder(BorderFactory.createLineBorder(Color.black));

        tfLastRow = new JTextField("test");
        pLastRow.add(tfLastRow);

        pLastRow.setVisible(false);
        return pLastRow;
    }

    private JPanel createBlockReplaced() {
        pArrayBytes = new JPanel();
        pArrayBytes.setLayout(new BoxLayout(pArrayBytes, BoxLayout.Y_AXIS));
        pArrayBytes.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel pVertival = new JPanel();
        JPanel pThreeBytes = new JPanel();
        pThreeBytes.setLayout(new BoxLayout(pThreeBytes, BoxLayout.Y_AXIS));

        JPanel pFirstArray = new JPanel();
        pFirstArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        tfFirstByte = new HintTextField("D1", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pFirstArray.add(tfFirstByte);
        tfSecondByte = new HintTextField("B0", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pFirstArray.add(tfSecondByte);
        tfThirdByte = new HintTextField("23", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pFirstArray.add(tfThirdByte);
        pThreeBytes.add(pFirstArray);

        JPanel pSecondArray = new JPanel();
        pSecondArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        tfFourthByte = new HintTextField("D1", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pSecondArray.add(tfFourthByte);
        tfFifthByte = new HintTextField("B1", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pSecondArray.add(tfFifthByte);
        tfSixthByte = new HintTextField("23", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pSecondArray.add(tfSixthByte);
        pThreeBytes.add(pSecondArray);
        pVertival.add(pThreeBytes);

        JLabel label = new JLabel(" ➔ ");
        label.setFont(new Font("MS Gothic", Font.BOLD, 18));
        pVertival.add(label);

        JPanel pThirdArray = new JPanel();
        pThirdArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        tfNewFirstByte = new HintTextField("D1", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pThirdArray.add(tfNewFirstByte);
        tfNewSecondByte = new HintTextField("A9", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pThirdArray.add(tfNewSecondByte);
        tfNewThirdByte = new HintTextField("23", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        pThirdArray.add(tfNewThirdByte);
        pVertival.add(pThirdArray);

        JPanel pForTextFieldFileNameForChanging = new JPanel();
        tfFileNameForChanging = new HintTextField(Constants.NAME_FILE_FOR_CHANGING, 200, 26);
        pForTextFieldFileNameForChanging.add(tfFileNameForChanging);

        pArrayBytes.add(pVertival);
        pArrayBytes.add(pForTextFieldFileNameForChanging);
        pArrayBytes.setVisible(false);
        return pArrayBytes;
    }

    private void fillFourthRow() {
        pFourthRow.setLayout(new FlowLayout(FlowLayout.CENTER));
//        cbDemo = new JCheckBox("Demo");
//        pFourthRow.add(cbDemo);
        JButton bStart = new JButton(Constants.START);
        bStart.addActionListener(e -> {
            sEOBDPath = tfEOBDPath.getText();
            sVehiclePath = tfVehiclePath.getText();
            if (!tfEOBDPath.getText().isEmpty() && !tfVehiclePath.getText().isEmpty()) {
                myResourceUtils.rewriteFile(START_DIR_EOBD, tfEOBDPath.getText());
                myResourceUtils.rewriteFile(START_DIR_VEHICLE, tfVehiclePath.getText());
            }
            if (cbGlueAndCopy.isSelected() && checkCorrectnessDataEntry()) {
                System.out.println("glueAndCopyFiles");
                Thread thread = new Thread(() -> new GlueFiles(sProgramFiles, sEOBDPath, sVehiclePath));
                thread.start();
            }
            if (cbReplaced.isSelected()) {
                lStatus.setText(Constants.REPLACED_BYTES);
                Thread thread = new Thread(() ->
                        new ReplacementBytes(tfFirstByte.getText(), tfSecondByte.getText(), tfThirdByte.getText(),
                                tfFourthByte.getText(), tfFifthByte.getText(), tfSixthByte.getText(),
                                tfNewFirstByte.getText(), tfNewSecondByte.getText(), tfNewThirdByte.getText())
                                .findFilesAndReplacementBytes(sVehiclePath, tfFileNameForChanging.getText()));
                thread.start();
                try {
                    thread.join();
                    lStatus.setText(Constants.DONE);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        lStatus = new JLabel(Constants.STATUS);
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
        int ret = fileChooser.showDialog(null, Constants.SELECT_DIRECTORY);
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