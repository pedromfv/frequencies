package frequencies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private static final String FILE = "config.properties";
	
	private static Properties LoadProps() throws IOException {
		Properties pro = new Properties();
		File file = new File(FILE);
		if(file.exists()) {
			FileInputStream in = new FileInputStream(file);
			pro.load(in);
			in.close();
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		return pro;
		
	}
	
	public static void save(int theme, int wallpaper, int mic, int scale) {
		try {
			Properties pro = new Properties();
			pro.setProperty("theme", String.valueOf(theme));
			pro.setProperty("wallpaper", String.valueOf(wallpaper));
			pro.setProperty("mic", String.valueOf(mic));	
			pro.setProperty("scale", String.valueOf(scale));
			FileOutputStream out = new FileOutputStream(FILE);
			pro.store(out, "App Config");
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static int loadTheme() {
		try {
			Properties pro = new Properties();
			FileInputStream in = new FileInputStream(FILE);
			pro.load(in);
			in.close();
			return Integer.parseInt(pro.getProperty("theme", "0"));
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int loadWallpaper() {
		try {
			Properties pro = new Properties();
			FileInputStream in = new FileInputStream(FILE);
			pro.load(in);
			in.close();
			return Integer.parseInt(pro.getProperty("wallpaper", "1"));
		}catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	public static int loadMic() {
		try {
			Properties pro = new Properties();
			FileInputStream in = new FileInputStream(FILE);
			pro.load(in);
			in.close();
			return Integer.parseInt(pro.getProperty("mic", "0"));
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	public static int loadScale() {
		try {
			Properties pro = new Properties();
			FileInputStream in = new FileInputStream(FILE);
			pro.load(in);
			in.close();
			return Integer.parseInt(pro.getProperty("scale", "1"));
		}catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
}