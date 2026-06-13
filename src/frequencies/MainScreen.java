package frequencies;
//todas as bibliotecas utilizadas na janela principal
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * tela principal 
 * responsável pela interface gráfica ,
 * envio e recebimento de mensagens,
 * gerenciamento de arquivos,
 * controle do microfone,
 * aplicação do wallpaper e escala
 */

public class MainScreen extends JFrame implements ActionListener {
	
	private JTextArea txtMensagem;
	private JTextArea txtRecieve;
	private JLabel background;
	private JLabel status = new JLabel("Pronto");
	private static MainScreen instance;
	private JButton limpar = new JButton("Limpar");
	private JButton ouvir = new JButton("Ouvir");
	private JButton transmitir = new JButton("Transmitir");
	private JButton transferir = new JButton("^");
	JMenuItem nw, save, open, options;
	private JScrollPane scrollM;
	private JScrollPane scrollR;
	private boolean alterado = false;
	public static int wallPaper = 1;
	public static int scale = 1;
	public static final int BASE_WIDTH = 500, BASE_HEIGHT = 300;
	private static final float BASE_FONT = 12f;
	
	public MainScreen() {
		instance = this;
		ImageIcon ico = new ImageIcon(getClass().getResource("ico.png"));
		setIconImage(ico.getImage());
		background = new JLabel();
		background.setLayout(null);
		setContentPane(background);
		updateWallpaper();
		JMenuBar  mBar = new JMenuBar();
		JMenu mFil = new JMenu("File");
		JMenu mOpt = new JMenu("Options");
		options = new JMenuItem("Options");
		nw = new JMenuItem("New");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		nw.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		options.addActionListener(this);
		mOpt.add(options);
		mFil.add(nw);
		mFil.add(open);
		mFil.add(save);
		mBar.add(mFil);
		mBar.add(mOpt);
		background.setLayout(null);
		setJMenuBar(mBar);
		setContentPane(background);
		setTitle("Transmissor de Frequências");
		setSize(BASE_WIDTH * scale,BASE_HEIGHT * scale);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		txtRecieve = new JTextArea();
		txtRecieve.setEditable(false);
		txtRecieve.setLineWrap(true);
		txtRecieve.setWrapStyleWord(true);
		txtRecieve.setFont(txtRecieve.getFont().deriveFont(BASE_FONT));
		scrollR = new JScrollPane(txtRecieve);
		scrollR.setBounds(20 * scale, 120 * scale, 300 * scale, 90  * scale);
		scrollR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		txtMensagem = new JTextArea();
		txtMensagem.setLineWrap(true);
		txtMensagem.setWrapStyleWord(true);
		txtMensagem.setFont(txtMensagem.getFont().deriveFont(BASE_FONT));
		scrollM = new JScrollPane(txtMensagem);
		scrollM.setBounds(20 * scale, 20 * scale, 300 * scale, 90 * scale);
		scrollM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		txtMensagem.getDocument().addDocumentListener(new DocumentListener() {
			
		    public void insertUpdate(DocumentEvent e) {
		        alterado = true;
		    }

		    public void removeUpdate(DocumentEvent e) {
		        alterado = true;
		    }

		    public void changedUpdate(DocumentEvent e) {
		        alterado = true;
		    }
		});
		limpar.setFont(limpar.getFont().deriveFont(BASE_FONT));
		limpar.setBounds(340 * scale, 150 * scale, 120 * scale, 30 * scale);
		limpar.setFocusable(false);
		limpar.addActionListener(this);
		transmitir.setFont(transmitir.getFont().deriveFont(BASE_FONT));
		transmitir.setBounds(340 * scale, 20 * scale, 120 * scale, 30 * scale);
		transmitir.setFocusable(false);
		transmitir.addActionListener(this);
		ouvir.setFont(ouvir.getFont().deriveFont(BASE_FONT));
		ouvir.setBounds(340 * scale, 50 * scale, 120 * scale, 30 * scale);
		ouvir.setFocusable(false);
		ouvir.addActionListener(this);
		transferir.setFont(transferir.getFont().deriveFont(BASE_FONT));
		transferir.setBounds(340 * scale, 90 * scale, 50 * scale, 50 * scale);
		transferir.setFocusable(false);
		transferir.addActionListener(this);
		status.setFont(status.getFont().deriveFont(BASE_FONT));
		status.setBounds(10 * scale, 230 * scale, 470 * scale, 20 * scale);
		status.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		add(status);
		add(limpar);
		add(transferir);
		add(scrollR);
		add(ouvir);
		add(scrollM);
		add(transmitir);
		
		addWindowListener(new WindowAdapter() {

		    public void windowClosing(WindowEvent e) {

		        if(alterado) {
		            int resposta = JOptionPane.showConfirmDialog(MainScreen.this,"Deseja salvar as alterações antes de sair?","Alterações não salvas",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		            if(resposta == JOptionPane.YES_OPTION) {
		                if(salvarArquivo()) {
		                    System.exit(0);
		                }
		            } else if(resposta == JOptionPane.NO_OPTION) {
		                System.exit(0);
		            }
		        } else {

		            System.exit(0);
		        }
		    }
		});
		setVisible(true);
	}
	
	public void updateScale() {
	    setSize(BASE_WIDTH * scale,BASE_HEIGHT * scale);
	    setLocationRelativeTo(null);
	    float fontSize = 12f * scale;
	    status.setFont(status.getFont().deriveFont(fontSize));
		status.setBounds(10 * scale, 230 * scale, 470 * scale, 20 * scale);
	    limpar.setFont(limpar.getFont().deriveFont(fontSize));
	    transmitir.setFont(transmitir.getFont().deriveFont(fontSize));
	    ouvir.setFont(ouvir.getFont().deriveFont(fontSize));
	    transferir.setFont(transferir.getFont().deriveFont(fontSize));
	    txtMensagem.setFont(txtMensagem.getFont().deriveFont(fontSize));
	    txtRecieve.setFont(txtRecieve.getFont().deriveFont(fontSize));
	    scrollM.setBounds(20 * scale, 20 * scale,300 * scale,90 * scale);
	    scrollR.setBounds(20 * scale,120 * scale, 300 * scale,90 * scale);
	    transmitir.setBounds(340 * scale, 20 * scale,  120 * scale,  30 * scale);
	    ouvir.setBounds(340 * scale, 50 * scale,120 * scale, 30 * scale);
	    transferir.setBounds(340 * scale, 90 * scale, 50 * scale,  50 * scale);
	    limpar.setBounds(340 * scale,150 * scale, 120 * scale,   30 * scale);
	    updateWallpaper();
	    revalidate();
	    repaint();
	}
	
	public void setStatus(String text) {
		javax.swing.SwingUtilities.invokeLater(() -> {status.setText("Status : " + text); });
	}
	
	public void updateWallpaper() {
		ImageIcon backGround = new ImageIcon(getClass().getResource(wallPaper + ".png"));
		Image img = backGround.getImage().getScaledInstance(BASE_WIDTH * scale, BASE_HEIGHT * scale, Image.SCALE_SMOOTH);
		background.setIcon(new ImageIcon(img));
	}
	
	private boolean salvarArquivo() {

	    if(txtMensagem.getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(this,"Não é possível salvar um arquivo vazio.","Aviso",JOptionPane.WARNING_MESSAGE);
	        return false;
	    }

	    JFileChooser fileC = new JFileChooser();

	    if(fileC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

	        File file = fileC.getSelectedFile();

	        try(BufferedWriter write =
	                new BufferedWriter(new FileWriter(file))) {

	            txtMensagem.write(write);
	            alterado = false;
	            return true;

	        } catch(IOException ex) {
	            ex.printStackTrace();
	        }
	    }

	    return false;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == transmitir) {
		    String text = txtMensagem.getText();
		    if(text.trim().isEmpty()) {
		        setStatus("Erro: texto vazio");
		        JOptionPane.showMessageDialog(this,"Texto Vazio","Erro",JOptionPane.WARNING_MESSAGE
		        );
		        return;
		    }
		    setStatus("Transmitindo...");
		    new Thread(() -> {Transmissor.send(text);setStatus("Pronto");}).start();
		}
		if(e.getSource() == ouvir) {
			    setStatus("Ouvindo...");
				new Thread(() -> { Receiver.startListening(); setStatus("Pronto"); }).start();
				
		}
	
		if(e.getSource() == open) {
			JFileChooser fileC = new JFileChooser();
			int opt = fileC.showOpenDialog(this);
			if(opt == JFileChooser.APPROVE_OPTION) {
				File file = fileC.getSelectedFile();
				try(BufferedReader read = new BufferedReader(new FileReader(file))){
					txtMensagem.read(read, null);
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		if(e.getSource() == nw) {
			if(alterado) {
				int resposta = JOptionPane.showConfirmDialog(this, "Deseja salvar antes de criar um novo arquivo ?", "Alterações não salvas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if(resposta == JOptionPane.YES_OPTION) {
					JFileChooser fileC = new JFileChooser();
					if(fileC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
						File file = fileC.getSelectedFile();
						try(BufferedWriter write = new BufferedWriter(new FileWriter(file))){
							txtMensagem.write(write);
							alterado = false;
							txtMensagem.setText("");
						}catch(IOException ex) {
							ex.printStackTrace();
						}
					}
				}else if(resposta == JOptionPane.NO_OPTION){
					txtMensagem.setText("");
					alterado = false;
				}else {
					return;
				}
			}else {
				txtMensagem.setText("");
			}
		}
		
		if(e.getSource() == save) {
			if(txtMensagem.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Não é possível salvar um arquivo vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			salvarArquivo();
		}
		if(e.getSource() == transferir) {
			if(txtRecieve.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Não é possível transferir sem texto.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				String transferir = txtRecieve.getText();
				txtMensagem.setText(transferir);
				txtRecieve.setText("");
			}
		}
		if(e.getSource() == options) {
		    Options op = new Options();
		}
		if(e.getSource() == limpar) {
			if(txtRecieve.getText().trim().isEmpty()){
				JOptionPane.showMessageDialog(this, "Não é possível limpar sem texto.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				txtRecieve.setText("");
			}
		}
	}
	public static MainScreen getInstance() {
		return instance;
	}
	public void appendCharacter(char c) {
		txtRecieve.append(String.valueOf(c));
	}
}