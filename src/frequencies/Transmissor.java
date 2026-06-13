package frequencies;

public class Transmissor {
	
	private static final int TONE_LENGTH = 200;
	private static final int SILENCE_LENGTH = 30;
	
	public static void send(String message) {
		message = message.toUpperCase();
		
		for(int i = 0; i < message.length(); i++) {
			char caractere = message.charAt(i);
			int[] frequencies = CharTable.obtainFrequencies(caractere);
			if(frequencies == null) {
				System.out.printf("O caracter %c não é suportado.", caractere);
				continue;
			}
			int f1 = frequencies[0];
			int f2 = frequencies[1];
			
			System.out.printf("\n%s -> %d Hz + %d Hz", caractere, f1, f2);
			AudioGenerator.playDMTF(f1, f2, TONE_LENGTH);
			sleep(SILENCE_LENGTH);	
		}
		int[] eof = CharTable.obtainFrequencies('§');
		if(eof != null) {
			AudioGenerator.playDMTF(eof[0], eof[1], TONE_LENGTH);
		}
		
	}
	
	private static void sleep(int ms) {
		try {
			Thread.sleep(ms);
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}