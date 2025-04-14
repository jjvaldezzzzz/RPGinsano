import java.util.Scanner;
import java.util.InputMismatchException;

class Battle {
    private Queue<Character> turnQueue = new Queue<>();
    private Stack<Character> defeatedStack = new Stack<>();
    private int currentTurn = 1;
    private boolean isPvP;
    private Scanner scanner = new Scanner(System.in);
    
    public Battle(boolean isPvP) {
        this.isPvP = isPvP;
    }
    
    public void addParticipant(Character character) {
        turnQueue.enqueue(character);
    }
    
    public void startBattle() {
        System.out.println("\n=== Batalha Iniciada ===");
        System.out.println("Modo: " + (isPvP ? "Player vs Player" : "Player vs Environment"));
        
        while (getAliveParticipantsCount() > 1) {
            executeTurn();
        }
        
        endBattle();
    }
    
    private void executeTurn() {
        Character current = turnQueue.dequeue();
        if (!current.isAlive()) return;
        
        System.out.println("\n--- Turno " + currentTurn + " ---");
        System.out.println("Vez de: " + current.getName());
        System.out.println("HP: " + current.getCurrentHealth() + "/" + current.getMaxHealth());
        System.out.println("MP: " + current.getCurrentMana() + "/" + current.getMaxMana());
        
        if (!isPvP && (current.getName().equals("P.I"))) {
            monsterAI(current);
        } else {
            playerTurn(current);
        }
        
        currentTurn++;
    }
    
    private void playerTurn(Character player) {
        try {
            Character target = findAliveTarget(player);
            if (target == null) {
                System.out.println("Nenhum alvo disponível!");
                return;
            }
            
            System.out.println("\n1. Atacar (" + target.getName() + ")");
            System.out.println("2. Curar (30 HP, 20 MP)");
            System.out.print("Escolha sua ação: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    player.attack(target);
                    if (!target.isAlive()) {
                        System.out.println(target.getName() + " foi derrotado!");
                        defeatedStack.push(target);
                    }
                    break;
                case 2:
                    if (player.getCurrentMana() >= 20) {
                        player.heal(30);
                        System.out.println(player.getName() + " se curou em 30 pontos de vida!");
                    } else {
                        System.out.println("Mana insuficiente! Atacando...");
                        player.attack(target);
                    }
                    break;
                default:
                    System.out.println("Opção inválida! Atacando...");
                    player.attack(target);
            }
            
            if (player.isAlive()) {
                turnQueue.enqueue(player);
            } else {
                defeatedStack.push(player);
            }
        } catch (InputMismatchException e) {
            System.out.println("Por favor, digite um número válido!");
            scanner.nextLine();
            playerTurn(player);
        }
    }
    
    private void monsterAI(Character monster) {
        Character target = findAliveTarget(monster);
        if (target == null) return;
        
        if (monster.getName().equals("P.I")) {
            if (Math.random() > 0.9) {
                System.out.println(monster.getName() + " errou o ataque!");
            } else {
                monster.attack(target);
            }
        } else { 
            if (currentTurn % 2 == 0) {
                monster.attack(target);
            } else {
                System.out.println(monster.getName() + " está se preparando para atacar!");
            }
        }
        
        if (!target.isAlive()) {
            System.out.println(target.getName() + " foi derrotado!");
            defeatedStack.push(target);
        }
        
        if (monster.isAlive()) {
            turnQueue.enqueue(monster);
        } else {
            defeatedStack.push(monster);
        }
    }
    
    private Character findAliveTarget(Character attacker) {
        LinkedList<Character> queueList = turnQueue.toLinkedList();
        for (int i = 0; i < queueList.size(); i++) {
            Character c = queueList.get(i);
            if (c.isAlive() && c != attacker) {
                return c;
            }
        }
        return null;
    }
    
    private int getAliveParticipantsCount() {
        int count = 0;
        LinkedList<Character> queueList = turnQueue.toLinkedList();
        for (int i = 0; i < queueList.size(); i++) {
            Character c = queueList.get(i);
            if (c.isAlive()) count++;
        }
        return count;
    }
    
    private void endBattle() {
        System.out.println("\n=== Batalha Concluída ===");
        
        Character winner = null;
        LinkedList<Character> queueList = turnQueue.toLinkedList();
        for (int i = 0; i < queueList.size(); i++) {
            Character c = queueList.get(i);
            if (c.isAlive()) {
                winner = c;
                break;
            }
        }
        
        if (winner != null) {
            System.out.println("Vencedor: " + winner.getName() + "!");
            winner.levelUp();
            System.out.println(winner.getName() + " subiu para o nível " + winner.getLevel() + "!");
        } else {
            System.out.println("Todos foram derrotados!");
        }
        
        System.out.println("\nRanking:");
        System.out.println("1. " + winner.getName() + " (Vencedor)");
        
        int position = 2;
        while (!defeatedStack.isEmpty()) {
            Character defeated = defeatedStack.pop();
            System.out.println(position + ". " + defeated.getName());
            position++;
        }
    }
}