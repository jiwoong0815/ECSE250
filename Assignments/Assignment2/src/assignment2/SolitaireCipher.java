package assignment2;

public class SolitaireCipher {
	public Deck key;

	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}

	/*
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		/**** ADD CODE HERE ****/

		int[] keystream = new int[size];

		for (int i = 0; i < size; i++){
			keystream[i] = key.generateNextKeystreamValue();
		}
		return keystream;

	}

	/*
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		/**** ADD CODE HERE ****/

		String textWithLetter = "";

		for (int i = 0; i < msg.length(); i++){
			char character = msg.charAt(i);
			if (Character.isLetter(character)){
				textWithLetter += character;
			}
		}
		textWithLetter=textWithLetter.toUpperCase();

		int[] keystream = getKeystream(textWithLetter.length());

		String encoded = "";

		for (int i = 0 ; i < textWithLetter.length(); i++){
			char character = textWithLetter.charAt(i);
			int shiftingNum = ((character - 'A') + keystream[i]) % 26;
			char shiftedChar = (char) ('A' + shiftingNum);
			encoded += shiftedChar;
		}
		return encoded;

	}

	/*
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {
		/**** ADD CODE HERE ****/
		int[] keystream = getKeystream(msg.length());
		String decoded = "";

		for (int i = 0; i < msg.length(); i++){
			char character = msg.charAt(i);
			int shiftingNum = ((character - 'A') - keystream[i])%26;

			if (shiftingNum < 0){
				shiftingNum = shiftingNum + 26;
			}

			char shiftedChar = (char) ('A' + shiftingNum);
			decoded += shiftedChar;
		}

		return decoded;
	}

}

