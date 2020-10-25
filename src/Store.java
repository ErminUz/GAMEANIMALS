import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Store {
    /*
    private static ArrayList<Animal> animalStock = new ArrayList<>();
    private static ArrayList<Animal> foodStock = new ArrayList<>();
    */
    public static ArrayList<Animal> animalStock = FileManagement.getAnimals();
    public static ArrayList<Food> foodStock = FileManagement.getFoods();

    //public static ArrayList<Animal> animalStock;
    //private static ArrayList<Food> foodStock;

    public Store(){
        run();
    }

    private void run(){
        FileManagement.createAnimal();
        FileManagement.createFood();
        animalStock = FileManagement.getAnimals();
        foodStock = FileManagement.getFoods();
    }

    public static void clearEmptyStock(){
        for(int i = 0; i < foodStock.size(); i++){
            if(foodStock.get(i).removeFromStock()){
                foodStock.remove(foodStock.get(i));
            }
        }
    }

    private static ArrayList<Food> keepFoodListUpdated(){
        ArrayList<Food> updated = new ArrayList<>();
        for(Food food : foodStock){
            if(!food.removeFromStock()){
                updated.add(food);
            }else{
                foodStock.remove(food);
            }
        }

        return updated;
    }

    private static void listAnimals(){
        int i = 0;
        for(Animal animal : animalStock){
            System.out.println((i+1) + animal.toString());
            i++;
        }
    }

    private static void listAnimalPrices(){

        int i = 1;
        for(Animal animal : animalStock){
            IO.prompt(i + ". (" + animal.getClass().getSimpleName() + ") "
                    + animal.getSpecie() + ", price: " + animal.getPrice() + "kr");
                   // + "\nEats: " + animal.whatIEat());
            i++;
        }
    }

    private static void listFoods(){
        /*
        int i = 0;
        for(Food food : foodStock){
            System.out.println((i+1) + food.toString());
            i++;
        }
        */
    }

    private static ArrayList<Food>  removeEmptyStock(){
        ArrayList<Food> update = new ArrayList<>();
        for(Food food : foodStock){
            if(food.removeFromStock()){
                foodStock.remove(food);
            }else{
                update.add(food);
            }
        }
        /*
        ArrayList<Food> updated = foodStock;
        for(Food food : foodStock){
            if(food.removeFromStock()){
                foodStock.remove(food);
            }else{
                updated.add(food);
            }
        }
        foodStock = updated;
        */
        return update;
    }

    private static void listFoodPrices(){
        /*
        int i = 1;
        for(Food food : foodStock){
            if(!food.removeFromStock()){
                System.out.println(i + ". " + food.getFood()
                        + ", price: " + food.getPricekg() + "kr/kg"
                        + " (stock: " + food.getWeight() + "kg)");
            }else{
                foodStock.remove(food);
            }
        }

         */
        /*
        ArrayList<Food> foodList = keepFoodListUpdated();
        int i = 1;
        for(Food food : foodList){
            IO.prompt(i + ". " + food.getFood() + ", price: " + food.getPricekg() + "kr" +
                    " (stock: " + food.getWeight() + "kg)");
            i++;
        }
        */
        //removeEmptyStock();
        clearEmptyStock();
        int i = 1;
        for(Food food : foodStock){
            System.out.println(i + ". " + food.getFood() + ", price: " + food.getPricekg() + "kr" +
                    " (stock: " + food.getWeight() + "kg)");
            i++;
            /*
            if(!food.removeFromStock()){
                System.out.println(i + ". " + food.getFood() + ", price: " + food.getPricekg() + "kr" +
                        " (stock: " + food.getWeight() + "kg)");
                i++;
            }
            */
        }
    }

    /*
    private static boolean isAnimal(Object obj){
        return obj instanceof Animal;
    }

    private static boolean isFood(Object obj){
        return obj instanceof Food;
    }
    */

    //metoden ska för varje köp om player har pengar nog att fortsätta handla
    //alltså om något i affären finns som player har råd med
    private static int canAffordAnything(Player player){
        //boolean affordSomething = true;
        //kan ha en counter istället. Om counter slutar på mer än 0 betyder det att det finns något player har råd med

        int affordables = 0;
        for(Animal animal : animalStock){
            if(player.getMoney() >= animal.getPrice()){
                affordables++;
            }
        }

        return affordables;
        /*
        boolean condition = false;
        for(Animal animal : animalStock){
            if(player.getMoney() >= animal.getPrice()){
                condition = true;
                break;
            }
        }
        return condition;
        */
    }


    private static void printAnimalsIncludingFood(){
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for(Animal animal : animalStock){
            for(String food : animal.getPreferableFoods()){
                sb.append(food).append(" ");
            }
            System.out.println(i + ". " + animal.getSpecie() + ", price: " + animal.getPrice() +
                    "\neats: " + sb.toString());

            i++;
        }
    }

    // denna metod ska kanske ändras till att ta en list med spelare och kanske en lista med objekt
    // som görs om till antingen food eller animal
    // eller så har den bara parametrarna player och animal, dessa byts ut i game klassen istället
    public static void buy(Player player){
        // här i början kan jag också testa om player ens har pengar och kan använda sig av buy
        // och kolla om det finns djur i affären till salu
        IO.prompt("In store: \nAnimals: ");
        listAnimalPrices();
        //printAnimalsIncludingFood();
        IO.prompt("\nFoods:");
        listFoodPrices();
        String choice = IO.stringPrompt("Select from animals(input: animal) or food(input: food)?");
        String selectOrRandom = IO.stringPrompt("Manually select or auto: ");
        if(choice.toLowerCase().equals("animal") && selectOrRandom.toLowerCase().equals("manually")){
            boolean canBuy = true;
            //listAnimalPrices();
            //int i = 0;
            while(canAffordAnything(player) > 0){
                // behövs ändå en kontroll ifall player har pengar
                // finns inge pengar går vi ut ur while
                if(player.getMoney() <= 0){
                    canBuy = false;
                }else{
                    //int i = 0;
                    IO.prompt(String.valueOf("Your balance: " + player.getMoney()) + "kr");
                    listAnimalPrices();
                    int animalChoice = IO.promptInt("Choose an animal") - 1;
                    Animal chosenAnimal = animalStock.get(animalChoice);
                    if(player.getMoney() >= chosenAnimal.getPrice()){
                        player.addAnimal(chosenAnimal);
                        animalStock.remove(chosenAnimal);
                        double balance = player.getMoney() - chosenAnimal.getPrice();
                        player.setBalance(balance);
                        System.out.println("bought");
                        System.out.println(player.getMoney());
                    }else{
                        IO.prompt("Can't afford anything more");
                    }
                }
                //i++; //- tror denna ska vara i if satsen inne i else
            }
            IO.prompt("Can't afford anymore animals");
        }else if(choice.toLowerCase().equals("animal") && selectOrRandom.toLowerCase().equals("auto")){
            // innuti här ska spelaren samla på sig så mycket djur den kan och sålänge den har råd
            Collections.shuffle(animalStock); // tror man får ha en ny referens typ här, en ny arrayList

            // måste hantera bättre om du inte har pengar, denna säger inte åt användaren tydligt
            /*
            int i = 0;
            while(canAffordAnything(player) > 0){
                //player.getAnimals().add(animalStock.get(i));
                player.addAnimal(animalStock.get(i));
                animalStock.remove(animalStock.get(i));
                double balance = player.getMoney() - animalStock.get(i).getPrice();
                player.setBalance(balance);
                i++;
            }
            */
            int i = 0;
            boolean buy = true;
            while(buy){
                if(canAffordAnything(player) > 0 && !animalStock.isEmpty()){
                    player.addAnimal(animalStock.get(i));
                    double balance = player.getMoney() - animalStock.get(i).getPrice();
                    player.setBalance(balance);
                    animalStock.remove(animalStock.get(i));
                }else{
                    System.out.println("Can't afford anything");
                    buy = false;
                }
            }

            System.out.println("Animal/s owned: ");
            for(Animal animal : player.getAnimals()){
                System.out.println(animal.getSpecie());
            }
        }else if(choice.toLowerCase().equals("food") && selectOrRandom.toLowerCase().equals("manually")){
            boolean canBuy = true;
            //listAnimalPrices();
            int i = 0;
            while(canBuy){
                // behövs ändå en kontroll ifall player har pengar
                // finns inge pengar går vi ut ur while
                if(player.getMoney() == 0 || foodStock.isEmpty()){
                    System.out.println("No money or food is out"); // för nu, men ändra så att man vet mer exakt varför
                    canBuy = false;
                }else{
                    //kan kanske skapa en metod för att visa vad som är affordable
                    listFoodPrices();
                    int foodChoice = IO.promptInt("Choose a food") - 1;
                    //int amountGrams = IO.promptInt("Enter amount you wish to buy in grams");
                    Food chosenFood = foodStock.get(foodChoice);
                    String subClass = chosenFood.getClass().getSimpleName();
                    String food = chosenFood.getFood();
                    int priceKg = chosenFood.getPricekg();
                    int priceFoodPerGrams = chosenFood.getPricekg() / 1000;
                    int maxAmountBuy = howMuchCanBeBought(player, chosenFood);
                    IO.prompt("You afford up to following amount: " + maxAmountBuy + "g of " + chosenFood.getFood() + "s");
                    int amountGrams = IO.promptInt("Enter amount you wish to buy in grams");
                    int amountAfford = (int)(priceFoodPerGrams * player.getMoney());
                    if(amountGrams <= maxAmountBuy){
                        IO.prompt("You've bought " + amountGrams + "g of " + chosenFood.getFood() + "s");
                        int cost = amountGrams * priceFoodPerGrams;
                        double balance = player.getMoney() - cost;
                        player.setBalance(balance);
                        Food foodToAdd = addFoodToPlayerFoodList(subClass, food, priceKg, (amountGrams / 1000));
                        player.updateFoodList(foodToAdd);
                        //player.addFood(chosenFood, (amountGrams / 1000));
                        chosenFood.setWeight(chosenFood.getWeight() - (amountGrams / 1000));
                        //foodStock.remove(chosenFood); fel
                        if(chosenFood.getWeight() >= 0){
                            foodStock.remove(chosenFood);
                        }
                    }
                }
            }
            //IO.prompt("That's all you can buy");
        }else if(choice.toLowerCase().equals("food") && selectOrRandom.toLowerCase().equals("auto")){
            // buy as long as possible; as long as player can afford it. How do deal with amount...?
            // player can choose a amount and the rest is randomized; type of food being bought.
            int amountGrams = IO.promptInt("Enter amount you wish to buy in grams");
            // now buy set amount x type food as long as there's food and player can afford
            // while?
            /*
            for(Food food : keepFoodListUpdated()){
                while(canAfford(player, amountGrams, food) && !foodStock.isEmpty()){
                    player.addFood(food, (amountGrams / 1000));
                    int price = amountGrams * (food.getPricekg() / 1000);
                    player.setBalance(player.getMoney() - price);
                    food.setWeight(food.getWeight() - amountGrams);
                }
            }
            */
            boolean run = true;

            //ArrayList<Food> updatedList = keepFoodListUpdated();
            //ArrayList<Food> updatedList = removeIfEmpty();
            while(run){
                for(Food food : foodStock){
                    Food foodToAdd = addFoodToPlayerFoodList(food.getClass().getSimpleName(), food.getFood(), food.getPricekg(), (amountGrams / 1000));
                    if(player.getMoney() > (food.getPricekg() / 1000.0) * amountGrams && food.getWeight() > 0){
                        double price = (food.getPricekg() / 1000.0) * amountGrams;
                        double balance = player.getMoney() - price;
                        player.setBalance(balance);
                        String classFoodName = food.getClass().getSimpleName();

                        //Food foodToAdd =
                         //       addFoodToPlayerFoodList(classFoodName, food.getFood(), food.getPricekg(), (amountGrams / 1000));
                        // player måste nog ha en metod för att uppdatera food listan korrekt
                        // ändra här till en metod updateFoodList
                        // den ser till så att endast ett objekt av samma slag visas,
                        // inte tex flera bananas med samma vikter eller olika
                        player.updateFoodList(foodToAdd);
                        //player.addFood(foodToAdd);
                        food.setWeight(food.getWeight() - (amountGrams / 1000));
                    }else{
                        run = false;
                    }
                }

            }
            IO.prompt("Bought all you can afford, your balance: " + player.getMoney());
            //skriva ut vad spelaren köpt eller iaf vad spelaren har nu
            // jag hade kunnat ha en lista som lägger till allt som handlas för att
            // kunna skriva ut vad man köpt
            IO.prompt("Food list: ");
            ArrayList<Food> foods = player.getFood();
            for(Food food : foods){
                System.out.println(food.toString());
            }

            System.out.println("\n(updated food stock: )");
            //ArrayList<Food> updated = keepFoodListUpdated();
            //updatedFoodList();
            //ArrayList<Food> stock = removeIfEmpty();
            for(Food food : foodStock){
                System.out.println(food.getFood() + ", weight: " + food.getWeight() + "kg");
            }
        }
        // här kan nog hela inventory skrivas
    }

    private static Food addFoodToPlayerFoodList(String className, String foodName, int pricekg, int weight){
        switch(className.toLowerCase()){
            case "carbs":
                return new Carbs(foodName, pricekg, weight);
            case "fat":
                return new Fat(foodName, pricekg, weight);
            case "protein":
                return new Protein(foodName, pricekg, weight);

        }
        return null;
    }

    private static boolean canAfford(Player player, int amount, Food food){
        int afford = 0;
        int pricePerGram = food.getPricekg() / 1000;
        int price = amount * pricePerGram;
        double balance = player.getMoney();
        return balance >= price && food.getWeight() != 0;
    }

    private static int howMuchCanBeBought(Player player, Food food){
        int foodQuantityInGrams = food.getWeight() * 1000;
        int foodPricePerGrams = food.getPricekg() / 1000;
        double balance = player.getMoney();
        int amountGramsCanBuy = (int) (player.getMoney() / foodPricePerGrams);
        if(amountGramsCanBuy > foodQuantityInGrams){
            amountGramsCanBuy = foodQuantityInGrams;
        }

        return amountGramsCanBuy;
    }

    static double assessValue(Animal animal){
        return animal.getPrice() * animal.getHealthPoints();
    }

    /*
    private static void stock(){
        animalStock = FileManagement.getAnimals();
        foodStock = FileManagement.getFoods();
    }
    */

    static ArrayList<Animal> getAnimalStock(){
        return animalStock;
    }

    static ArrayList<Food> getFoodStock(){
        return foodStock;
    }

    static void clearAnimalStock(){
        animalStock.clear();
    }

    static void clearFoodStock(){
        foodStock.clear();
    }


}
