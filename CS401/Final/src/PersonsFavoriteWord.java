public class PersonsFavoriteWord { //3-7 problems in this class
	private String name;
	private FavoriteWord word;

	public PersonsFavoriteWord(String name, FavoriteWord word){
		setWord(word);
		setName(name);
	}
	
	public void setWord(FavoriteWord newWord) {
		this.word = newWord;
	}
	
	public FavoriteWord getWord() {
		return word;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name+"'s favorite word is " + word + ".";
	}
}
