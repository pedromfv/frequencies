package frequencies;

public class FrequencyDetector {
	
	private static final int[] ROWS = {
			600, 800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 2600,
			2800, 3000, 3200
	};
	
	private static final int[] COLUMNS = {
			3500, 4000, 4500, 5000, 5500, 6000
	};
	
	private static final double LIMIAR = 1000;
	
	public static char detect(byte[] buffer) {
		int bestRow = 0;
		double secondRowPower = 0;
		int bestColumn = 0;
		double secondColumnPower = 0;
		double maxRowPower = 0;
		double maxColumnPower = 0;
		
		for(int row : ROWS) {
			double power = Goertzel.detect(buffer, row);
			if(power > maxRowPower) {
				secondRowPower = maxRowPower;
				maxRowPower = power;
				bestRow = row;
			}else if(power > secondRowPower) {
				secondRowPower = power;
			}
		}
		for(int column : COLUMNS) {
			double power = Goertzel.detect(buffer, column);
			if(power > maxColumnPower) {
				secondColumnPower = maxColumnPower;
				maxColumnPower = power;
				bestColumn = column;
			}else if(power > secondColumnPower) {
				secondColumnPower = power;
			}
		}
		if(secondRowPower == 0 || secondColumnPower == 0) {
			return '\0';
		}
		double rowRatio = maxRowPower / secondRowPower;
		double columnRatio = maxColumnPower / secondColumnPower;
		if(rowRatio < 5.0 || columnRatio < 5.0) {
			return '\0';
		}
		System.out.printf("Row=%d Power=%.0f | Col=%d Power=%.0f%n", bestRow, maxRowPower,bestColumn,maxColumnPower);
		
		return CharTable.obtainCharacter(bestRow, bestColumn);
	}
}