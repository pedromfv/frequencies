package frequencies;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class Options extends JFrame implements ActionListener {
	
	public static int micIndex = 0;
	public static int currentTheme = 0;
	public static int scal = 1;
	private static final int BASE_WIDTH = 300, BASE_HEIGHT = 300;
	private JComboBox wallpapers = new JComboBox();
	private JComboBox<String> mics = new JComboBox<>();
	private JComboBox theme = new JComboBox();
	private JComboBox scale = new JComboBox();
	private JLabel wal = new JLabel("WallPaper : ");
	private JLabel thm = new JLabel("Theme : ");
	private JLabel mcL = new JLabel("Select Mic : ");
	private JLabel sclL = new JLabel("Scale : ");
	private JButton ok = new JButton("Ok");
	
	public Options() {
		ImageIcon ico = new ImageIcon(getClass().getResource("gear.png"));
		setIconImage(ico.getImage());
		wal.setBounds(50, 20, 100, 20);
		thm.setBounds(50, 60, 100, 20);
		wallpapers.setBounds(140, 20, 120, 20);
		wallpapers.setFocusable(true);
		wallpapers.addItem("Blue");
		wallpapers.addItem("Purple");
		wallpapers.addItem("Pink");
		wallpapers.addItem("Green");
		wallpapers.addItem("Yellow");
		wallpapers.addItem("???");
		wallpapers.addItem("Black Hole");
		wallpapers.addItem("SynthWave");
		wallpapers.addActionListener(this);
		theme.setBounds(140, 60, 120, 20);
		theme.addItem("Dark");
		theme.addItem("Light");
		theme.addItem("IntelliJ");
		theme.addItem("Darcula");
		theme.addActionListener(this);
		currentTheme = Config.loadTheme();
		MainScreen.wallPaper = Config.loadWallpaper();
		micIndex = Config.loadMic();
		MainScreen.scale = Config.loadScale();
		if (currentTheme < 0 || currentTheme > 3) currentTheme = 0;
		if (MainScreen.wallPaper < 1 || MainScreen.wallPaper > 8) MainScreen.wallPaper = 1;
		applyTheme(currentTheme);
		theme.setSelectedIndex(currentTheme);
		int wpIndex = MainScreen.wallPaper - 1;
		if (wpIndex < 0) wpIndex = 0;
        if(MainScreen.scale < 1 || MainScreen.scale > 3) MainScreen.scale = 1;
		wallpapers.setSelectedIndex(wpIndex);
		mics.setBounds(140, 100, 120, 20);
		mcL.setBounds(50, 100, 100, 20);
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		for(Mixer.Info info : mixers) {
		    Mixer mixer = AudioSystem.getMixer(info);
		    boolean suportaCaptura = false;
		    for(javax.sound.sampled.Line.Info lineInfo : mixer.getTargetLineInfo()) {
		        if(lineInfo instanceof javax.sound.sampled.DataLine.Info dataInfo) {
		            if(TargetDataLine.class.isAssignableFrom(dataInfo.getLineClass())) {
		                suportaCaptura = true;
		                break;
		            }
		        }
		    }
		    if(suportaCaptura) {
		        mics.addItem(info.getName());
		    }
		}
		if(mics.getItemCount() > 0) {
		    if(micIndex < 0 || micIndex >= mics.getItemCount()) {
		        micIndex = 0;
		    }
		    mics.setSelectedIndex(micIndex);
		}
		mics.addActionListener(this);
		sclL.setBounds(50, 140, 100, 20);
		scale.setBounds(140, 140, 120, 20);
		scale.addItem("x1");
		scale.addItem("x2");
		scale.addItem("x3");
		scale.addActionListener(this);
		scale.setSelectedIndex(MainScreen.scale - 1);
		ok.setBounds(160, 220, 100, 20);
		ok.setFocusable(false);
		ok.addActionListener(this);
		add(ok);
		add(sclL);
		add(scale);
		add(mcL);
		add(mics);
		add(thm);
		add(theme);
		add(wal);
		add(wallpapers);
		setTitle("Options");
		setSize(BASE_WIDTH, BASE_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);

	}
	
	public static void applyTheme(int theme) {
		try {
			switch(theme) {
			case 0 -> UIManager.setLookAndFeel(new FlatDarkLaf());
			case 1 -> UIManager.setLookAndFeel(new FlatLightLaf());
			case 2 -> UIManager.setLookAndFeel(new FlatIntelliJLaf());
			case 3 -> UIManager.setLookAndFeel(new FlatDarculaLaf());
			}
			for(java.awt.Window window : java.awt.Window.getWindows()) {
				SwingUtilities.updateComponentTreeUI(window);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void applyScale(int scale) {
		MainScreen.scale = scale;
		if(MainScreen.getInstance() != null) {
			MainScreen.getInstance().updateScale();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == wallpapers) { 
			int index = wallpapers.getSelectedIndex(); 
			if(index == 7) { 
				index = 8; 
				}else { 
				index = index + 1; 
			} MainScreen.wallPaper = index; 
			MainScreen.getInstance().updateWallpaper();
			Config.save(currentTheme, MainScreen.wallPaper, micIndex, MainScreen.scale); 
			}
		
		if(e.getSource() == theme) {
			int selected = theme.getSelectedIndex();
			Options.applyTheme(selected);
			Config.save(selected, MainScreen.wallPaper, micIndex, MainScreen.scale);
			currentTheme = theme.getSelectedIndex();
		}
		if(e.getSource() == mics) {
		    micIndex = mics.getSelectedIndex();
		    Config.save(currentTheme, MainScreen.wallPaper, micIndex, MainScreen.scale);
		}
		if(e.getSource() == scale) {
			int selected = scale.getSelectedIndex() + 1;
			MainScreen.scale = selected;
			Options.applyScale(selected);	
			Config.save(currentTheme, MainScreen.wallPaper, micIndex, selected);
		    
		}
		if(e.getSource() == ok) {
			dispose();
		}
	}
}