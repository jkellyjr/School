public class Driver { //about 0-2 problems in this class
	public static void main(String[] args) {
		
		PersonsFavoriteWord faveWord = new PersonsFavoriteWord();
		
		FavoriteWord word = new FavoriteWord();
		
		word.setWord("cat");
		
		faveWord.setWord(word);
		
		faveWord.setName("Albert");
		
		System.out.println(faveWord);
 }
}