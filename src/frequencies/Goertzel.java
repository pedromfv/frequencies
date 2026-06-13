package frequencies;

public class Goertzel {
	
	private static final double SAMPLE_RATE = 44100.0;
	
	public static double detect(byte[] samples, double targetFreq) {
		int n = samples.length;
		double k = Math.round((n * targetFreq) / (SAMPLE_RATE));
		double omega = (2.0 * Math.PI * k) / n;
		double coef = 2.0 * Math.cos(omega);
		double q0 = 0;
		double q1 = 0;
		double q2 = 0;
		
		for(int i = 0;i < n; i++) {
			q0 = coef * q1 - q2 + samples[i];
			q2 = q1;
			q1 = q0;
		}
		double power = q1 * q1 + q2 * q2 - coef * q1 * q2;
		return power;
	}
}