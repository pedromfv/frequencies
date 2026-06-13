package frequencies;

public class CharTable {
	
	private static final char[][] SYMBOLS = {
			{'A', 'B', 'C', 'D', 'E', 'F'},
			{'G', 'H', 'I', 'J', 'K', 'L'},
			{'M', 'N', 'O', 'P', 'Q', 'R'},
			{'S', 'T', 'U', 'V', 'W', 'X'},
			{'Y', 'Z', ',', '.', '?', '!'},
			{'0', '1', '2', '3', '4', '5'},
			{'6', '7', '8', '9', ';', '['},
			{']', '´', '`', '~', '^', '{'},
			{'}', '/', '@', '#', '$', '%'},
			{'¨', '&', '*', '(', ')', '-'},
			{'_', '+', '=', '|', 'Á', 'É'},
			{'Ó', 'Ú', 'Í', 'Ã', 'Õ', 'Â'},
			{'Ê', 'Ô', 'Î', 'Û', 'À', 'Ñ'},
			{' ', '§', 'Ï', 'Ö', 'Ü', 'Ç'}
	};
	
	private static final int freq_initial_line = 600;
	private static final int freq_initial_column = 3500;
	private static final int GAP_LINE = 200;
	private static final int GAP_COLUMN = 500;
	
	public static int[] obtainFrequencies(char caractere) {
		caractere = Character.toUpperCase(caractere);
		for(int line = 0; line < SYMBOLS.length; line++) {
			for(int column = 0; column < SYMBOLS[line].length; column++) {
				if(SYMBOLS[line][column] == caractere) {
					int f1 = freq_initial_line + (line * GAP_LINE);
					int f2 = freq_initial_column + (column * GAP_COLUMN);
					return new int[] {f1, f2};
				}
			}
		}
		return null;
	}
	
	public static char obtainCharacter(int f1, int f2) {
		
		int line = (f1 - freq_initial_line) / GAP_LINE;
		int column = (f2 - freq_initial_column) / GAP_COLUMN;
		if(line >= 0 && line < SYMBOLS.length && column >= 0 && column < SYMBOLS[0].length) {
			return SYMBOLS[line][column];
		}
		return '\0';
	}
}