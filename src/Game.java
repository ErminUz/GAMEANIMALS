import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Game {
    private Store store;
    private ArrayList<Player> players = new ArrayList<>();
    // kanske lägga till i denna spelarna som varit med i en spelomgång, om den infon behövs...?
    private ArrayList<Player> playerListGameEnd = new ArrayList<>();
    private int rounds;

    public Game(){

    }


    /*
    public Store setStore(String storeNameQuestion){
        String
    }
    */

    /*
    a) Köpa max så många djur som hen har pengar till(varje typ av djur har ett ursprungspris, oavsett kön).
    b) Köpa max så mycket mat som hen har pengar till(mat köps i kg och har kilopris)
    c) Mata sina djur(vilken slags mat måste anges för varje djur man vill mate)
    d) Försöka få ett par djur att para sig, då skapas i 50% av fallen nya djur man äger(om djuren är av samma slag och olika
      olika kön, olika slags djur kan inte para sig). Om parningen lyckas kan spelaren döpa det/de nya djuret/djuren(olika
      slags djur kan ha olika många ungar/parning). Könet på djuren som skapas vid parning slumpas(50%/50%)
    e) Sälja ett/flera djur(priset är ursprungspriset gånger hälsovärdet)
    */

    // jag måste hålla koll på vilken spelares tur det är
    //metoden heter run eller session kanske?

    /*
    private void playerList(){
        int i = 1;
        for(Player player : this.getPlayers()){
            System.out.println(i + ". " + player.getName());
            i++;
        }
    }
    */

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
        int animalOne, animalTwo;
        printAnimals(player);
        animalOne = IO.promptInt("First animal") - 1;
        male = player.getAnimals().get(animalOne);
        printAnimals(player);
        animalTwo = IO.promptInt("Second animal") - 1;
        female = player.getAnimals().get(animalTwo);
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
        printAnimals(player);
        int animalChoice = IO.promptInt("Pick an animal to sell") - 1;
        player.sell(player.getAnimals().get(animalChoice));
    }

    private void startMenu(){
        int choice = IO.startMenu("Store Simulator 2020",
                "Help",
                         "Play",
                         "Load game",
                         "Exit");
        switch(choice){
            case 1:
                help();
                break;
            case 2:
                run();
                break;
            case 3:
                // load()
            case 4:
                break;
        }
    }

    private void help(){
        System.out.println("Store Simulator 2020\nInstructions: ");

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

    private void store(){
        //new Store();
        String choice = IO.stringPrompt("List animal or food in store");
        switch(choice.toLowerCase()){
            case "animal":
                listStoreAnimals();
                break;
            case "food":
                listStoreFoods();
                break;
        }
    }

    private void run(){
        refresh();
        new Store();
        int playerAmount = IO.promptInt("Enter player amount: ");
        setPlayer(playerAmount);
        int playerCount = getPlayerCount();
        int rounds = setRounds("Enter rounds(5-30): ");
        int counter = 0;
        while(counter < rounds){
            // options och metoder att lägga till ev
            // save
            // saved
            // exit
            int choice = IO.gameOptions("Store Simulator 2020", setGameStatsEachRound(),
                    "Buy max amount animals/foods", // 1
                                 "Feed animal", // 2
                                 "Breed", // 3
                                 "Sell animal/s", // 4
                                 "Store", // 5
                                 "Save", // 6
                                 "Exit"); //
            for(Player player : this.getPlayers()){
                System.out.println(player.getName() + "s turn");
                switch(choice){
                    case 1:
                        Store.buy(player);
                        break;
                    case 2:
                        feed(player);
                        break;
                    case 3:
                        breed(player);
                        break;
                    case 4:
                        sell(player);
                        break;
                    case 5:
                        //store(); ha detta i ettan istället då jag inte kan lösa det nu..

                    case 6:
                        //save();
                        //break;
                    case 7:
                        //exit här kanske ngn erase metod måste finnas, och sen tillbaka till startmenu
                        break;
                }
            }
            counter++;
            //Store.clearEmptyStock();
        }
        // använd här metoden för gameEnd ? när spelet är slut ska vissa saker göras
        gameEnd();
        startMenu();
    }

    private Player returnWinner(){
        return players.stream().max(Comparator.comparing(Player::getMoney)).get();
    }

    //newgame metod som tar bort allt som lagrats från föregående spelomgång
    /*
    Om man spelar en runda då har ett visst antal och typer av objekt lagrats, och spelare också.
    När man sen kör ett nytt spel, kör spelet på samma objekt från föregående match plus de nya.
    Jag måste alltså rensa alla listor som tillhör en match när run metoden i game körs
    */
    private void refresh(){
        // jag tror det räcker med att rensa i FileManagement klassen ist
        FileManagement.refresh();
        //Store.clearAnimalStock();
        //Store.clearFoodStock();
        players.clear();
    }


    private void gameEnd(){
        for(Player player : players){
            if(!player.getAnimals().isEmpty()){
                for(Animal animal : player.getAnimals()){
                    player.sell(animal);
                }
            }
        }
        IO.prompt("End results: ");
        for(Player player : players){
            //IO.prompt(String.valueOf(player.getMoney()));
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
        for(Player player : players){
            IO.prompt(i + ". " + "Player " + player.getName() + ", balance: " + player.getMoney());
        }
    }

    //denna metod kanske behövs varje rond för att se vilka spelare som ska fortsätta
    private void nextRound(){

    }

    /*
    Se till att varje spelare tydligt i början av varje runda får information om vilka djur hen äger,
    vilken mat hen äger och hur mycket pengar hen har,
     samt hur mycket djurs hälsa har försämrats sedan förra omgången
     */
    private String setGameStatsEachRound(){
        StringBuilder sb = new StringBuilder();

        for(Player player : this.getPlayers()){
            sb.append(player.toString()).append("\n");
        }
        return sb.toString();
    }

    private void setAnimals(){
        FileManagement.createAnimal();
    }

    private void setFoods(){
        FileManagement.createFood();
    }

    /*
    private void setPlayers(String playersQuestion){
        int playerAmount = IO.promptInt(playersQuestion);
        Player.setPlayer(playerAmount, this.getPlayers());
    }
    */


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

    public static void addPlayers(){

    }

    public static void openStore(){

    }

    public int getPlayerCount(){
        return players.size();
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    //**
    //**
    //**
    //Metoder för att kolla så att programmet funkar
    private static void printStoreAnimals(){
        for(Animal animal : Store.getAnimalStock()){
            System.out.println(animal.toString());
        }
    }

    private static void printStoreFood(){
        for(Food food : FileManagement.getFoods()){
            System.out.println(food.toString());
        }
    }

    // test för att se ifall player skapandet fungerar som det ska
    private void createAndDisplayPlayer(){
        int amount = IO.promptInt("How many players are to be created?");
        setPlayer(amount);
        for(Player player : players){
            System.out.println(player.toString());
        }
    }

    public static void main(String[] args){
        //FileManagement.readAnimalsIn();

        //for(String[] attr : FileManagement.getAttributeValuesAnimal()){
          //  System.out.println(Arrays.toString(attr));
        //}
        Game g = new Game();
        g.play();
        //FileManagement.createAnimal();
        //printStoreAnimals();



        //FileManagement.printClassesAnimalsAdded();
        //FileManagement.createFood();

        //System.out.println(FileManagement.getAnimals().isEmpty());
        //printStoreAnimals();
        /*
        ArrayList<Animal> animalList = FileManagement.getAnimals();
        for(Animal animal : animalList){
            System.out.println(animal.toString());
        }
        */
        //System.out.println("-".repeat(50));
        //printStoreFood();
    }


}
