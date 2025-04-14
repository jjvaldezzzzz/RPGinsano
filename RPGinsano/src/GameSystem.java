import java.util.Scanner;
import java.util.InputMismatchException;

class GameSystem {
    private Scanner scanner = new Scanner(System.in);
    private Player currentPlayer;
    private Character player1Character;
    private Character player2Character;
    
    public void start() {
        System.out.println("=== RPG Battle System ===");
        loginMenu();
        mainMenu();
    }
    
    private void loginMenu() {
        while (true) {
            try {
                System.out.println("\n1. Login");
                System.out.println("2. Cadastrar");
                System.out.print("Escolha: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                if (choice == 1 || choice == 2) {
                    System.out.print("Nome do jogador: ");
                    String name = scanner.nextLine();
                    System.out.print("Senha: ");
                    String password = scanner.nextLine();
                    
                    currentPlayer = new Player(name, password);
                    System.out.println("Operação realizada com sucesso!");
                    break;
                } else {
                    System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, digite um número válido!");
                scanner.nextLine();
            }
        }
    }
    
    private void mainMenu() {
        while (true) {
            try {
                System.out.println("\n=== Menu Principal ===");
                System.out.println("1. Criar Personagem");
                System.out.println("2. Selecionar Personagem (Player 1)");
                System.out.println("3. Iniciar Batalha PvE");
                System.out.println("4. Iniciar Batalha PvP");
                System.out.println("5. Sair");
                System.out.print("Escolha: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1: createCharacter(); break;
                    case 2: selectCharacter(true); break;
                    case 3: startPvEBattle(); break;
                    case 4: startPvPBattle(); break;
                    case 5: System.exit(0);
                    default: 
                        System.out.println("Opção inválida! Tente novamente.");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, digite um número válido!");
                scanner.nextLine();
            }
        }
    }
    
    private void createCharacter() {
        try {
            System.out.print("Nome do personagem: ");
            String name = scanner.nextLine();
            
            Character newCharacter = new Character(name);
            currentPlayer.addCharacter(newCharacter);
            System.out.println("Personagem criado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar personagem: " + e.getMessage());
        }
    }
    
    private void selectCharacter(boolean isPlayer1) {
        if (currentPlayer.getCharacters().isEmpty()) {
            System.out.println("Nenhum personagem disponível. Crie um primeiro!");
            return;
        }
        
        try {
            System.out.println("Seus personagens:");
            LinkedList<Character> characters = currentPlayer.getCharacters();
            for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);
            System.out.println((i + 1) + ". " + character.getName() + " (Nível " + character.getLevel() + ")");
            }
            
            System.out.print("Escolha o personagem " + (isPlayer1 ? "Player 1" : "Player 2") + ": ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice > 0 && choice <= currentPlayer.getCharacters().size()) {
                Character selected = currentPlayer.getCharacters().get(choice - 1);
                
                if (isPlayer1) {
                    player1Character = selected;
                    System.out.println("Personagem Player 1 selecionado: " + selected.getName());
                } else {
                    
                    if (player1Character != null && selected.getName().equals(player1Character.getName())) {
                        System.out.println("Este personagem já foi selecionado pelo Player 1!");
                        return;
                    }
                    player2Character = selected;
                    System.out.println("Personagem Player 2 selecionado: " + selected.getName());
                }
            } else {
                System.out.println("Escolha inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Por favor, digite um número válido!");
            scanner.nextLine();
        }
    }
    
    private void startPvEBattle() {
        if (player1Character == null) {
            System.out.println("Selecione um personagem para o Player 1 primeiro!");
            return;
        }
        Character monster1 = new Character("P.I");
        monster1.setCombatStats(80, 25);
        
        Battle battle = new Battle(false); 
        battle.addParticipant(player1Character);
        battle.addParticipant(monster1);
        battle.startBattle();
    }
    
    private void startPvPBattle() {
        if (player1Character == null) {
            System.out.println("Selecione um personagem para o Player 1 primeiro!");
            selectCharacter(true);
        }
        
        if (player2Character == null) {
            System.out.println("Selecione um personagem para o Player 2:");
            selectCharacter(false);
            
            if (player2Character == null) {
                return; 
            }
        }
        
        Battle battle = new Battle(true); 
        battle.addParticipant(player1Character);
        battle.addParticipant(player2Character);
        
        battle.startBattle();
    }
}