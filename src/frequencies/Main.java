package frequencies;

public class Main {
	
	/*
	 * Classe princpal da aplicação responsável por 
	 * inicializar as configurações salvas e abrir a interface principal
	 */
	
	//ponto de entrada do programa
	public static void main(String[] args) {
		int theme = Config.loadTheme(); //carrega o tema do arquivo de configurações
		int wallpaper = Config.loadWallpaper(); //carrega o wallpaper //
		int mic = Config.loadMic(); //carrega o microfone selecionado //
		int scale = Config.loadScale(); //carrega a escala da janela //
		Options.applyTheme(theme); //aplica o tema
		MainScreen.wallPaper = wallpaper; //define o wallpaper da tela principal
		Options.micIndex = mic; //define o microfone selecionado
		MainScreen.scale = scale; //define a escala da janela
		MainScreen mainscreen = new MainScreen(); //cria e exibe a tela pricipaç
	}
}