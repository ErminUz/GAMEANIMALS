import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Game implements Serializable {
    private Store store;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> playersEnd = new ArrayList<>();
    private int rounds;

    public Game(){
        play();
    }

    /*
    public Game(int rounds, ArrayList<Player> players, Store store){
        this.rounds = rounds;
        this.players = players;
        this.store = store;
    }
    */

    /*
    private void saveSession(int rounds, ArrayList<Player> players, Store store){
        Game saveGame = new Game(rounds, players, store);
        String nameSession = IO.stringPrompt("Name saving file: ");
        Session session = new Session(saveGame, nameSession);
        FileManagement.save(session);
    }

    private void loadSession(){
        ArrayList<Session> sessions = FileManagement.load();
        System.out.println("SAVED");
        int i = 1;
        for(Session session : sessions){
            System.out.println(i + ". game: " + session.getName() + ", saved: " + session.getDate());
            i++;
        }
        int choice = IO.promptInt("Pick a saved game file to load") - 1;
        Session chosenSession = sessions.get(choice);
        Game game = chosenSession.getGame();
        int rounds = game.getRounds();
        ArrayList<Player> playerList = game.getPlayers();
        Store store = game.getStore();
    }
    */

    public Store getStore(){
        return store;
    }

    private void printFoods(Player player){
        int i = 1;
        for(Food food : player.getFood()){
            System.out.println(i + ". " + food.getFood() + " " + food.getWeight() + "kg");
            i++;
        }
    }

    private void printAnimals(Player player){
        int i = 1;
        for(Animal animal : player.getAnimals()){
            System.out.println(i + ". " + animal.getSpecie());
            i++;
        }
    }

    public void breed(Player player){
        Animal male, female;
        String classMale, classFemale;

        int animalOne, animalTwo;
        printAnimals(player);
        animalOne = IO.promptInt("First animal") - 1;
        male = player.getAnimals().get(animalOne);
        printAnimals(player);
        animalTwo = IO.promptInt("Second animal") - 1;
        female = player.getAnimals().get(animalTwo);
        classMale = male.getClass().toString();
        classFemale = female.getClass().toString();

        if(!classMale.equals(classFemale)) {
            breed(player);
        }

        player.breed(male, female);
    }

    public static void playerAnimalListIncludingFood(Player player){
        int i = 1;
        for(Animal animal : player.getAnimals()){
            StringBuilder sb = new StringBuilder();
            int j = 0;
            for(String food : animal.getPreferableFoods()){
                if(j == animal.getPreferableFoods().size() - 1){
                    sb.append(food);
                }else{
                    sb.append(food).append(", ");
                    j++;
                }
                //sb.append(" ").append(food);
                //sb.append(food).append(" ");
            }

            System.out.println(i + ". " + animal.getSpecie() + " (eats: " + sb.toString() + ")");
            i++;
        }
    }

    public void feed(Player player){
        //printAnimals(player);
        playerAnimalListIncludingFood(player);
        int animalChoice = IO.promptInt("Feed one of your animals") - 1;
        Animal chosenAnimal = player.getAnimals().get(animalChoice);
        // här kan man skriva ut vad valt djur äter
        printFoods(player);
        int foodChoice = IO.promptInt("Pick food") - 1;
        // hantering av mängd bör ske här i player klassen. fixa det nog
        Food chosenFood = player.getFood().get(foodChoice);
        double amount = IO.promptDbl("Enter amount in kg to feed:");
        chosenAnimal.eat(chosenFood, amount);
        // måste uppdate mängden food kvar i players list

    }

    public void sell(Player player){
        String choice = IO.stringPrompt("Sell one(input: 1) or x amount(input: x)?" +
                                      "\n(animals: " + player.getAnimals().size() + ")").toLowerCase();
        Animal animal;
        switch(choice){
            case "1":
                printAnimals(player);
                int animalChoice = IO.promptInt("Pick an animal to sell") - 1;
                //animal = player.getAnimals().get(animalChoice);
                player.sell(player.getAnimals().get(animalChoice));
                break;
            case "x":
                //lägg till så att du inte kan välja mer än hur mkt djur du faktiskt har
                int amount = IO.promptInt("How many to sell?(you have " + player.getAnimals().size() + " animals)");
                int counter = 0;
                while(counter < amount){
                    printAnimals(player);
                    animalChoice = IO.promptInt("Pick an animal to sell") - 1;
                    //animal = player.getAnimals().get(animalChoice);
                    player.sell(player.getAnimals().get(animalChoice));
                    counter++;
                }
                break;
        }
    }

    private void startMenu(){
        int choice = IO.startMenu("Store Simulator 2020",
                "Help",
                         "Play",
                         "Load game - FUNKAR EJ",
                         "Exit");
        switch(choice){
            case 1:
                help();
                break;
            case 2:
                run();
                break;
            case 3:
                //loadSession();
                break;
            case 4:
                break;
        }
    }

    private void help(){
        System.out.println("Store Simulator 2020\nInstructions: ");
        System.out.println("You select amount of rounds and amount of players\n" +
                "You can buy animals or food each round, or you can sell animals or breed them\n" +
                "Goal is to survive the longest by having money or animals left");
        startMenu();

    }

    private void play(){
        startMenu();
    }

    private void listStoreAnimals(){
        for(Animal animal : Store.getAnimalStock()){
            System.out.println(animal.toString());
        }
    }

    private void listStoreFoods(){
        for(Food food : Store.getFoodStock()){
            System.out.println(food.toString());
        }
    }

    private void run(){
        String deathNote = "";
        String roundStats = "";
        refresh();
        new Store();
        int playerAmount = IO.promptInt("Enter player amount: ");
        setPlayer(playerAmount);
        int playerCount = getPlayerCount();
        int rounds = setRounds("Enter rounds(5-30): ");
        int counter = 1;
        int roundCounter = 1;
        boolean quit = false;
        while(counter <= rounds){
            System.out.println("entering while loop");
            String lastRound = deathNote + "\n" + roundStats;
            //int choice = IO.gameOptions(lastRound,"Store Simulator 2020", roundCounter, setGameStatsEachRound(),
                    //"Buy max amount animals/foods",
                                 //"Feed animal",
                                 //"Breed",
                                 //"Sell animal/s",
                                 //"Store(Strunta i denna , denna fanns bara för att jag skulle testa rundornas gång)",
                                 //"Save - FUNKAR EJ",
                                 //"Exit");
            //roundCounter++;
            for(Player player : this.getPlayers()){
                String dead = illness(player);
                System.out.println(player.getName() + "s turn");
                int choice = IO.gameOptions(dead, player, lastRound,"Store Simulator 2020", roundCounter, setGameStatsEachRound(),
                        "Buy max amount animals/foods",
                        "Feed animal",
                        "Breed",
                        "Sell animal/s",
                        "Store(Strunta i denna , denna fanns bara för att jag skulle testa rundornas gång)",
                        "Save - FUNKAR EJ",
                        "Exit");
                System.out.println("COUNTER: " + counter);
                switch(choice){
                    case 1:
                        System.out.println("(" + player.getName() + "s turn)");
                        Store.buy(player);
                        break;
                    case 2:
                        System.out.println("(" + player.getName() + "s turn)");
                        feed(player);
                        break;
                    case 3:
                        System.out.println("(" + player.getName() + "s turn)");
                        breed(player);
                        break;
                    case 4:
                        System.out.println("(" + player.getName() + "s turn)");
                        sell(player);
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        quit = true;
                        break;
                }
            }
            healthDecrement(getPlayers());
            //deathNote = deathInfo(getPlayers());
            roundStats = nextRound();
            roundCounter++;
            counter++;
        }
        /*
        if(quit){
            startMenu();
        }
        */
        // använd här metoden för gameEnd ? när spelet är slut ska vissa saker göras
        gameEnd();
        startMenu();
    }

    private String illness(Player player){
        if(!player.getAnimals().isEmpty()){
            for(int i = 0; i < player.getAnimals().size(); i++){
                Animal animal = player.getAnimals().get(i);
                double r = Math.round((new Random().nextDouble() * 10) / 10);
                System.out.println("random num " + r);
                if(r >= 0.0 && r <= 0.2){
                    double vetCost = vetFee(animal);
                    String decision = IO.stringPrompt("Animal: " + animal.getSpecie() + " is sick" +
                            "\nDo you want to give medical(input: med) attention or end it(input: end)?").toLowerCase();
                    switch(decision){
                        case "med":
                            if(player.getMoney() < vetCost){
                                System.out.println("You can't afford treatment");
                                //om man inte kan betala veterinärskostnad då dör djuret och tas bort
                                String specie = animal.getSpecie();
                                player.getAnimals().remove(animal);
                                return specie;

                            }else if(vet()){
                                System.out.println("Animal was treated");
                                animal.setHealthPoints(100);
                                player.setBalance(player.getMoney() - vetCost);
                                return null;
                            }else{
                                System.out.println("Animal dead");
                                String specie = animal.getSpecie();
                                player.getAnimals().remove(animal);
                                return specie;
                            }
                            //komma tillbaka till spelet
                            //break;
                        case "end":
                            System.out.println("You chose to make it sleepy");
                            String specie = animal.getSpecie();
                            player.getAnimals().remove(animal);
                            return specie;
                            //komma tillbaka till spelet
                    }
                }
                return null;
            }
        }
        return null;
    }

    private boolean vet(){
        return new Random().nextBoolean();
    }

    private double vetFee(Animal animal){
        double cost = 0.0;
        if(animal instanceof Mammal){
           cost = 100.0;
        }else if(animal instanceof Bird){
            cost = 50.0;
        }else if(animal instanceof Fish){
            cost = 25.0;
        }

        return cost;
    }

    private void healthDecrement(ArrayList<Player> players){
        for(Player player : players){
            if(!player.getAnimals().isEmpty()){
                for(int i = 0; i < player.getAnimals().size(); i++){
                    Animal animal = player.getAnimals().get(i);
                    double rand = new Random().nextInt(21) + 10;
                    double newHealth = animal.getHealthPoints() - rand;
                    animal.setHealthPoints(newHealth);
                    i++;
                }
            }
        }
    }

    private String deathInfo(ArrayList<Player> list){
        String msg = "";
        for(Player player : list){
            msg = player.announceDead();
        }

        return msg;
    }

    private Player returnWinner(){

        return playersEnd.stream().max(Comparator.comparing(Player::getMoney)).orElse(players.get(0));
        /*
        double winner = 0;
        for(int i = 0; i < players.size()-1; i++) {
            for(int j = 1; j < players.size(); j++) {

            }
        }
        */
    }

    //newgame metod som tar bort allt som lagrats från föregående spelomgång
    /*
    Om man spelar en runda då har ett visst antal och typer av objekt lagrats, och spelare också.
    När man sen kör ett nytt spel, kör spelet på samma objekt från föregående match plus de nya.
    Jag måste alltså rensa alla listor som tillhör en match när run metoden i game körs
    */
    private void refresh(){
        FileManagement.refresh();
        players.clear();
    }


    private void gameEnd(){
        playersEnd.addAll(players);
        System.out.println(playersEnd);
        for(Player player : playersEnd){
            if(!player.getAnimals().isEmpty()){
                for(int i = 0; i < player.getAnimals().size(); i++){
                    player.sell(player.getAnimals().get(i));
                }
            }
        }
        IO.prompt("End results: ");
        for(Player player : playersEnd){
            System.out.println(player.getMoney());
        }

        Player winner = returnWinner();
        IO.prompt("Winner: " + winner.getName() + ", balance: " + winner.getMoney());
        endStats();
        //här om man vill nytt spel eller avsluta?
    }

    private void endStats(){
        int i = 1;
        IO.prompt("End game stats: ");
        for(Player player : playersEnd){
            IO.prompt(i + ". " + "Player " + player.getName() + ", balance: " + player.getMoney());
        }
    }

    private String nextRound(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            if(player.getAnimals().isEmpty() && player.getMoney() == 0){
                playersEnd.add(player);
                players.remove(player);
                System.out.println(player.getName() + " you're out");
            }
        }

        for(int i = 0; i < players.size(); i++){
            Player player = players.get(i);
            sb.append(player.getName());
            if(player.getAnimals().isEmpty() && player.getMoney() == 0){
                sb.append(" - PLAYER OUT");
                players.remove(player);
            }else{
                sb.append(" - PLAYER IN");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /*
    Se till att varje spelare tydligt i början av varje runda får information om vilka djur hen äger,
    vilken mat hen äger och hur mycket pengar hen har,
     samt hur mycket djurs hälsa har försämrats sedan förra omgången
     */
    private String setGameStatsEachRound(){
        StringBuilder sb = new StringBuilder();

        for(Player player : this.getPlayers()){
            sb.append(player.toString()).append("\n").append("-".repeat(50)).append("\n");
        }
        return sb.toString();
    }

    private int setRounds(String question){
        int rounds = IO.promptInt(question);
        this.rounds = rounds;
        return rounds;
    }

    private void setPlayer(int amount){
        int counter = 0;
        while(counter < amount){
            String name = IO.stringPrompt("Enter player " + (counter + 1) + " name: ");
            Player player = new Player(name);
            players.add(player);
            counter++;
        }
    }

    public int getPlayerCount(){
        return players.size();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public int getRounds(){
        return rounds;
    }
}
