import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class Window implements ActionListener {

    private static final int WIDTH_FRAME = 530;
    private static final int HEIGHT_FRAME = 220;
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
    private JPanel pFourthRow;
    private JLabel lEOBD;
    private JLabel lVehicle;
    private JLabel lStatus;
    private JTextField tfEOBDPath;
    private JTextField tfVehiclePath;
    private JButton bEOBDPath;
    private JButton bVehiclePath;
    private JButton bStart;
    private JCheckBox cbGlueAndCopy;
    private JCheckBox cbReplaced;
    private JPanel pArrayBytes;
    private HintTextField firstByte;
    private HintTextField secondByte;
    private HintTextField newByte;

    public Window() {
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
        pFourthRow = new JPanel();
    }

    private void fillFirstRow() {
        pFirstRow.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lEOBD = new JLabel("EOBD");
        tfEOBDPath = new HintTextField(sEOBD);
        tfEOBDPath.setPreferredSize(new Dimension(WIDTH_TEXT_FIELD, HEIGHT_TEXT_FIELD));
        bEOBDPath = new JButton("Select");
        bEOBDPath.addActionListener(this);
        pFirstRow.add(new JTextField());
        pFirstRow.add(lEOBD);
        pFirstRow.add(tfEOBDPath);
        pFirstRow.add(bEOBDPath);
    }

    private void fillSecondRow() {
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

    private void fillThirdRow() {
        pThirdRow.setLayout(new FlowLayout(FlowLayout.CENTER));
        cbGlueAndCopy = new JCheckBox("Glue and copy files");
        cbReplaced = new JCheckBox("Replaced bytes");
        cbReplaced.addActionListener(this);
        pThirdRow.add(cbGlueAndCopy);
        pThirdRow.add(cbReplaced);

        pArrayBytes = new JPanel();
        pArrayBytes.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel pThreeBytes = new JPanel();
        pThreeBytes.setLayout(new BoxLayout(pThreeBytes, BoxLayout.Y_AXIS));

        JPanel pFirstArray = new JPanel();
        pFirstArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        pFirstArray.add(new JLabel("D1"));
        firstByte = new HintTextField("B0");
        pFirstArray.add(firstByte);
        pFirstArray.add(new JLabel("23"));
        pThreeBytes.add(pFirstArray);

        JPanel pSecondArray = new JPanel();
        pSecondArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        pSecondArray.add(new JLabel("D1"));
        secondByte = new HintTextField("B1");
        pSecondArray.add(secondByte);
        pSecondArray.add(new JLabel("23"));
        pThreeBytes.add(pSecondArray);

        pArrayBytes.add(pThreeBytes);
        JLabel label = new JLabel(" ➔ ");
        label.setFont(new Font("MS Gothic", Font.BOLD, 18));
        pArrayBytes.add(label);

        JPanel pThirdArray = new JPanel();
        pThirdArray.setLayout(new FlowLayout(FlowLayout.CENTER));
        pThirdArray.add(new JLabel("D1"));
        newByte = new HintTextField("A9");
        pThirdArray.add(newByte);
        pThirdArray.add(new JLabel("23"));
        pArrayBytes.add(pThirdArray);

        pArrayBytes.setVisible(false);
        pThirdRow.add(pArrayBytes);
    }

    private void fillFourthRow() {
        pFourthRow.setLayout(new FlowLayout(FlowLayout.CENTER));
        bStart = new JButton("Start");
        bStart.addActionListener(this);
        lStatus = new JLabel("Status");
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
            if (cbGlueAndCopy.isSelected() && checkCorrectnessDataEntry()) {
//                System.out.println("glueAndCopyFiles");
                glueAndCopyFiles(sEOBDPath, sVehiclePath);
            }
            if (cbReplaced.isSelected()) {
                lStatus.setText("Replacement bytes");
//                System.out.println("findFilesAndReplacementBytes");
                Thread thread = new Thread(() -> {
                    try {
                        LibDEVICEID libDEVICEID = new LibDEVICEID(firstByte.getText(),
                                secondByte.getText(), newByte.getText());
                        libDEVICEID.findFilesAndReplacementBytes(sVehiclePath, findFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                thread.start();
                try {
                    thread.join();
                    lStatus.setText("Done");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource().equals(cbReplaced)) {
            if (cbReplaced.isSelected()) {
                pArrayBytes.setVisible(true);
            } else {
                pArrayBytes.setVisible(false);
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

    private boolean checkCorrectnessDataEntry() {
        return (new File(sProgramFiles)).exists();
    }
}