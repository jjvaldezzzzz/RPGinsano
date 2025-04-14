class Character {
    private String name;
    private int level = 1;
    private int maxHealth = 100;
    private int currentHealth = 100;
    private int maxMana = 50;
    private int currentMana = 50;
    private int baseDamage = 15;
    
    public Character(String name) {
        this.name = name;
    }
    
    public void setCombatStats(int health, int baseDmg) {
        this.maxHealth = health;
        this.currentHealth = health;
        this.baseDamage = baseDmg;
    }
    
    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;
    }
    
    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) currentHealth = maxHealth;
        currentMana -= 20;
    }
    
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    public void levelUp() {
        level++;
        maxHealth += 20;
        currentHealth = maxHealth;
        maxMana += 10;
        currentMana = maxMana;
        baseDamage += 2;
    }
    
    public void attack(Character target) {
        int damage = baseDamage + (level * 2);
        System.out.println(name + " ataca " + target.getName() + " causando " + damage + " de dano!");
        target.takeDamage(damage);
    }
    
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getCurrentHealth() { return currentHealth; }
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }
}