import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class Window implements ActionListener {

    private static final int WIDTH_FRAME = 530;
    private static final int HEIGHT_FRAME = 160;
    private static final int WIDTH_TEXT_FIELD = 384;
    private static final int HEIGHT_TEXT_FIELD = 26;

    private final String sEOBD = "Enter the path to the EOBD";
    private final String sVehicle = "Enter the path to the Vehicle";
    private final String sStartDirectory = "C:\\Data_\\";
    private final String sProgramFiles = "C:\\Program Files\\Copy_Paste\\launch.bat";
    private final String sProgramFilesPath = "C:\\\"Program Files\"\\Copy_Paste\\launch.bat";
    private final String findFile = "libDEVICEID.so";
    private String sEOBDPath = "C:\\Users\\Dimuch\\Desktop\\test\\EOBD";
    private String sVehiclePath = "C:\\Users\\Dimuch\\Desktop\\test\\Vehicle";

    private JFrame frame;
    private JPanel panel;
    private JPanel pFirstRow;
    private JPanel pSecondRow;
    private JPanel pThirdRow;
    private JLabel lEOBD;
    private JLabel lVehicle;
    private JLabel lStatus;
    private JTextField tfEOBDPath;
    private JTextField tfVehiclePath;
    private JButton bEOBDPath;
    private JButton bVehiclePath;
    private JButton bStart;

    public Window() {
        initFrame();
        initPanel();
        fillPFirstRow();
        fillPSecondRow();
        fillPThirdRow();
        fillPanel();
        addPanel();
    }

    private void initFrame() {
        frame = new JFrame("Copy_Paste");
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
    }

    private void fillPFirstRow() {
        pFirstRow.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lEOBD = new JLabel("EOBD");
        tfEOBDPath = new HintTextField(sEOBD);
        tfEOBDPath.setPreferredSize(new Dimension(WIDTH_TEXT_FIELD, HEIGHT_TEXT_FIELD));
        bEOBDPath = new JButton("Select");
        bEOBDPath.addActionListener(this);
        pFirstRow.add(lEOBD);
        pFirstRow.add(tfEOBDPath);
        pFirstRow.add(bEOBDPath);
    }

    private void fillPSecondRow() {
        pSecondRow.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lVehicle = new JLabel("Vehicle");
        tfVehiclePath = new HintTextField(sVehicle);
        tfVehiclePath.setPreferredSize(new Dimension(WIDTH_TEXT_FIELD, HEIGHT_TEXT_FIELD));
        bVehiclePath = new JButton("Select");
        bVehiclePath.addActionListener(this);
        pSecondRow.add(lVehicle);
        pSecondRow.add(tfVehiclePath);
        pSecondRow.add(bVehiclePath);
    }

    private void fillPThirdRow() {
        pThirdRow.setLayout(new FlowLayout(FlowLayout.CENTER));
        bStart = new JButton("Start");
        bStart.addActionListener(this);
        lStatus = new JLabel("Status");
        pThirdRow.add(bStart);
        pThirdRow.add(lStatus);
    }

    private void fillPanel() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(pFirstRow);
        panel.add(pSecondRow);
        panel.add(pThirdRow);
    }

    private void addPanel() {
        frame.add(panel);
    }

    public void setVisible(Boolean b) {
        if (b.booleanValue()) {
            frame.setVisible(true);
        } else {
            frame.setVisible(false);
        }
    }

    private String selectDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(sStartDirectory));
        int ret = fileChooser.showDialog(null, "Select directory");
        if (ret == 0) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(bEOBDPath)) {
            String path = selectDirectory();
            if (!path.equals("")) {
                tfEOBDPath.setText(path);
            }
        } else if (e.getSource().equals(bVehiclePath)) {
            String path = selectDirectory();
            if (!path.equals("")) {
                tfVehiclePath.setText(path);
            }
        } else if (e.getSource().equals(bStart)) {
//            sEOBDPath = tfEOBDPath.getText();
//            sVehiclePath = tfVehiclePath.getText();
//            if (checkCorrectnessDataEntry() == 0) {
//                glueAndCopyFiles(sEOBDPath, sVehiclePath);
            if (true) {
                try {
                    lStatus.setText("Wait");
                    LibDEVICEID libDEVICEID = new LibDEVICEID();
                    libDEVICEID.findFilesAndReplacementBytes(sVehiclePath, findFile);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
                lStatus.setText("Finish");
            } else if (checkCorrectnessDataEntry() == 1 || checkCorrectnessDataEntry() == 3) {
                lStatus.setText("Check Program Files");
            } else if (checkCorrectnessDataEntry() == 2 || checkCorrectnessDataEntry() == 3) {
                tfEOBDPath.setText(sVehicle + " with auto");
            }
        }
    }

    private void glueAndCopyFiles(String sEOBDPath, String sVehiclePath) {
        System.out.println("cmd /c " + sProgramFilesPath + " " + sEOBDPath + " " + sVehiclePath);
        try {
            Runtime.getRuntime().exec("cmd /c start " + sProgramFilesPath + " " + sEOBDPath + " " + sVehiclePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int checkCorrectnessDataEntry() {
        File f = new File(sProgramFiles);
        if (f.exists()) {
            return 0;
        }
        return 1;
    }
}