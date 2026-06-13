package frequencies;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class AudioGenerator {
	
	private static final float SAMPLE_RATE = 44100;
	
	public static void playDMTF(double f1, double f2, int time) {
		 try {
	         byte[] buffer = new byte[(int)(SAMPLE_RATE * time / 1000)];
	         for(int i = 0; i < buffer.length; i++) {
	         double t = i / SAMPLE_RATE;
	         double sample =Math.sin(2 * Math.PI * f1 * t) +Math.sin(2 * Math.PI * f2 * t);
             buffer[i] = (byte)(sample * 63);
	        }
             AudioFormat format =new AudioFormat(SAMPLE_RATE, 8,1,true,false);

	            SourceDataLine line =AudioSystem.getSourceDataLine(format);

	            line.open(format);
	            line.start();

	            line.write(buffer, 0, buffer.length);

	            line.drain();
	            line.close();

	        }
	        catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
	}