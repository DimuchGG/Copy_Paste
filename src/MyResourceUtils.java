import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyResourceUtils {

	private final String START_DIR_EOBD = "startDirectoryEOBD.txt";
	private final String START_DIR_VEHICLE = "startDirectoryVehicle.txt";

	private String sStartDirEOBD = "C:\\";
	private String sStartDirVehicle = "C:\\";
	private final String sProgramFiles = "C:\\Program Files\\Copy_Paste\\";


	public MyResourceUtils() {
		File fEOBD = new File(sProgramFiles + START_DIR_EOBD);
		File fVehicle = new File(sProgramFiles + START_DIR_VEHICLE);
		createStartDirFiles(fEOBD, fVehicle);
	}

	private void createStartDirFiles(File fEOBD, File fVehicle) {
		if (!fEOBD.exists()) {
			rewriteFile(START_DIR_EOBD, sStartDirEOBD);
		}
		if (!fVehicle.exists()) {
			rewriteFile(START_DIR_VEHICLE, sStartDirVehicle);
		}
	}

	public String getStartDirEOBD(String sStartDir) {
		switch (sStartDir) {
			case START_DIR_EOBD:
				return sStartDirEOBD;
			case START_DIR_VEHICLE:
				return sStartDirVehicle;
			default:
				return "";
		}
	}

	public void rewriteStartDir(String sStartDir, String path) {
		switch (sStartDir) {
			case START_DIR_EOBD:
				sStartDirEOBD = path;
				break;
			case START_DIR_VEHICLE:
				sStartDirVehicle = path;
				break;
		}
	}

	public String readFiles(String fileName)  {
		byte[] encoded = new byte[0];
		try {
			encoded = Files.readAllBytes(Paths.get((new File(sProgramFiles + fileName)).getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, Charset.defaultCharset());
	}

	public void rewriteFile(String file, String str) {
		byte[] fileBArray = str.getBytes();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(sProgramFiles + file));
			fos.write(fileBArray);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}