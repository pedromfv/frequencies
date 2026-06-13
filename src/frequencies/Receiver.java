package frequencies;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.SwingUtilities;

public class Receiver {
	
	private static final float SAMPLE_RATE = 44100;
	private static char lastChar = '\0';
	private static char candidate = '\0';
	private static int candCount = 0;
	
	public static void startListening() {
		MainScreen.getInstance().setStatus("Recebendo Mensagem...");
		try {

            AudioFormat format =new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
            Mixer.Info[] mixers = AudioSystem.getMixerInfo();
            int totalMics = 0;
            for (Mixer.Info info : mixers) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (isCaptureDevice(mixer)) {
                    totalMics++;
                }
            }
            if (totalMics == 0) {
                throw new RuntimeException("Nenhum microfone encontrado.");
            }
            if (Options.micIndex < 0 || Options.micIndex >= totalMics) {
                Options.micIndex = 0;

                Config.save(Options.currentTheme, MainScreen.wallPaper, Options.micIndex, MainScreen.scale);
            }
            System.out.println("Dispositivos encontrados:");
            int printIndex = 0;
            for (Mixer.Info info : mixers) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (isCaptureDevice(mixer)) {
                    System.out.println("[" + printIndex + "] "+ info.getName());
                    printIndex++;
                }
            }
            TargetDataLine mic = null;

            int micCounter = 0;

            for (Mixer.Info info : mixers) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (isCaptureDevice(mixer)) {
                    if (micCounter == Options.micIndex) {
                        mic = (TargetDataLine) mixer.getLine(
                        new javax.sound.sampled.DataLine.Info(TargetDataLine.class,format));
                        System.out.println("Usando: " + info.getName());
                        break;
                    }

                    micCounter++;
                }
            }

            if (mic == null) {
                throw new RuntimeException("Falha ao abrir o microfone selecionado.");
            }
            mic.open(format);
            mic.start();

            byte[] buffer = new byte[2048];

            System.out.println("Ouvindo...");

            while (true) {
                mic.read(buffer, 0, buffer.length);
                char c = FrequencyDetector.detect(buffer);
                if (c == '\0') {
                    candidate = '\0';
                    candCount = 0;
                    lastChar = '\0';
                    continue;
                }
                if (c == candidate) {
                    candCount++;
                } else {
                    candidate = c;
                    candCount = 1;
                }

                if (candCount >= 2 && candidate != lastChar) {

                    if (candidate == '§') {
                        System.out.println("Fim da mensagem");
                        MainScreen.getInstance().setStatus("Mensagem recebida");
                        mic.stop();
                        mic.close();
                        return;
                    }
                    char detectedChar = candidate;
                    System.out.printf("Detectado: %c%n",detectedChar);
                    SwingUtilities.invokeLater(() ->MainScreen.getInstance().appendCharacter(detectedChar));
                    lastChar = detectedChar;
                    
                }
            }
         
        } catch (Exception e) {
            MainScreen.getInstance().setStatus("Erro no Microfone");
            e.printStackTrace();
        }
    }
	private static boolean isCaptureDevice(Mixer mixer) {

	    for (javax.sound.sampled.Line.Info info : mixer.getTargetLineInfo()) {

	        if (info instanceof javax.sound.sampled.DataLine.Info dataInfo) {

	            if (TargetDataLine.class.isAssignableFrom(
	                    dataInfo.getLineClass())) {

	                return true;
	            }
	        }
	    }
	    return false;
	}
}