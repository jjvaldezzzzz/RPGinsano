class Player {
    private String name;
    private String password;
    private LinkedList<Character> characters = new LinkedList<>();
    
    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public void addCharacter(Character character) {
        characters.add(character);
    }
    
    public LinkedList<Character> getCharacters() {
        return characters;
    }
    
    public String getName() {
        return name;
    }
    
}