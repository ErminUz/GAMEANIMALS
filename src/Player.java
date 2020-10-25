import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String name;
    private double money;
    private ArrayList<Animal> animals;
    private ArrayList<Food> foods;

    public Player(String name){
        this.name = name;
        this.money = 1000;
        this.animals = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    // kan/borde inte denna vara i game metoden?
    public static void setPlayer(int amount, ArrayList<Player> playerList){
        int counter = 0;
        while(counter < amount){
            String name = IO.stringPrompt("Enter player " + (counter + 1) + " name: ");
            playerList.add(new Player(name));
            counter++;
        }
    }

    private int foodAlreadyStored(Food foodToAdd){
        for(Food food : foods){
            if(food.getFood().equals(foodToAdd.getFood())){
                return foods.indexOf(food);
            }
        }
        return -1;
    }
    /*
    private Food retrieveFoodObject(Food foodToAdd){
        if(foodAlreadyStored(foodToAdd)){

        }
    }

     */

    public void updateFoodList(Food addFood){
        //if(foods.contains(addFood))
        if(foodAlreadyStored(addFood) >= 0){
            Food foodToAddTo = foods.get(foodAlreadyStored(addFood));
            //int index = foods.indexOf(addFood);
            //Food sameFood = foods.get(index);
            //sameFood.setWeight(addFood.getWeight());
            int newWeight = foodToAddTo.getWeight() + addFood.getWeight();
            foodToAddTo.setWeight(newWeight);
        }else{
            foods.add(addFood);
        }
    }

    /*
    public ArrayList<Food> properFoodList(){
        ArrayList<Food> updated = new ArrayList<>();
        for(Food food : foods){
            if(food instanceof Carbs){
                Carbs carb;
                for(int i = 1; i < foods.size(); i++){
                    if(food.getFood().equals(foods.get(i).getFood())){

                    }
                }
            }
        }
    }
    */

    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void removeAnimal(Animal animal){
        animals.remove(animal);
    }

    public void addFood(Food food){
        foods.add(food);
    }

    public void addFood(Food food, int weight){
        food.setWeight(weight);
        foods.add(food);
    }

    public void removeFood(Food food){
        foods.remove(food);
    }

    public String getName(){
        return name;
    }

    public void setBalance(double balance){
        this.money = balance;
        if(this.money <= 0){
            this.money = 0;
        }
    }

    public double getMoney(){
        money =  Math.round(money * 100.0) / 100.0;
        return money;
    }

    // denna metod kan jag ta bort då jag kan kalla istället getmetoden för respektive lista följt av size()
    private int getAnimalCount(){
        return this.getAnimals().size();
    }

    public ArrayList<Animal> getAnimals(){
        if(this.animals.isEmpty()){
            //System.out.println("You have no animals");
            return this.animals; // kanske inte behövs...
        }

        return this.animals;
    }

    //bör finnas en metod eller skrivas i getFood:
    // just nu kommer man lägga till flera objekt av samma typ.
    // kolla då i listan om objekten är lika med varandra då sätts e ihop till ett objekt.
    // så att det är ett sammansatt värde, och inte tex flera bananas med olika vikter eller samma: utan en.
    public ArrayList<Food> getFood(){
        if(this.foods.isEmpty()){
            //System.out.println("You have no food");
            return this.foods; // kanske inte behövs
        }

        return this.foods;
    }

    private String animalsList(){
        StringBuilder sb = new StringBuilder();
        if(getAnimalCount() == 0){
            sb.append("\n(Player ").append(getName()).append("): You currently don't own any animals, buy some in the store");
        }else{
            sb.append("Player: " + getName() + "s animals:");
            int i = 0;
            for(Animal animal : this.getAnimals()){
                sb.append(i+1).append(animal.getSpecie()).append(", health: ").append(animal.getHealthPoints()).append("\n");
                i++;
            }
        }

        return sb.toString();
    }

    private String foodList(){
        StringBuilder sb = new StringBuilder();
        if(getFood().size() == 0){
            sb.append("(Player ").append(getName()).append("): You currently don't own any food, buy some in the store");
        }else{
            sb.append("Player: ").append(getName()).append("s food:\n");
            int i = 0;
            for(Food food : this.getFood()){
                sb.append(i+1).append(". ").append(food.getFood()).append(", weight: ").append(food.getWeight()).append("kg").append("\n");
                i++;
            }
        }

        return sb.toString();
    }

    private boolean birth(){
        return new Random().nextBoolean();
    }

    /*
    public String dead(Animal animal){
        String msg = "";
        if(animal.getHealthPoints() == 0){
            this.animals.remove(animal);
            msg += animal.getSpecie();
        }
        return msg;
    }
    */

    private boolean dead(Animal animal){
        if(animal.getHealthPoints() == 0){
            //this.animals.remove(animal);
            return true;
        }
        return false;
    }

    public String announceDead(){
        StringBuilder sb = new StringBuilder();
        //sb.append(this.getName()).append(", dead animals: ");
        for(int i = 0; i < this.getAnimals().size(); i++){
            Animal animal = this.getAnimals().get(i);
            sb.append(this.getName()).append(", dead animals: ");
            if(dead(animal)){
                this.getAnimals().remove(animal);
                sb.append(" ").append(animal.getSpecie()).append(" ");
            }
        }

        return sb.toString();
    }

    public void breed(Animal male, Animal female){
        if(male instanceof Bird && female instanceof Bird){
            if(birth()){
                System.out.println("Birth went well");
                Bird offspring = new Bird(male.getSpecie());
                male.getOffspring().add(offspring);
                female.getOffspring().add(offspring);
                animals.add(offspring);
                //male.offspring.add(offspring);
                //female.offspring.add(offspring);
            }else{
                System.out.println("Miscarriage...");
            }
        }else if(male instanceof Fish && female instanceof Fish){
            if(birth()){
                System.out.println("Birth went well");
                Fish offspring = new Fish(male.getSpecie());
                male.getOffspring().add(offspring);
                female.getOffspring().add(offspring);
                animals.add(offspring);
                //male.offspring.add(offspring);
                //female.offspring.add(offspring);
            }else{
                System.out.println("Miscarriage...");
            }
        }else if(male instanceof Mammal && female instanceof Mammal){
            if(birth()){
                System.out.println("Birth went well");
                Mammal offspring = new Mammal(male.getSpecie());
                male.getOffspring().add(offspring);
                female.getOffspring().add(offspring);
                animals.add(offspring);
                //male.offspring.add(offspring);
                //female.offspring.add(offspring);
            }else{
                System.out.println("Miscarriage...");
            }
        }
    }

    public void sell(Animal animal){
        double balanceComingIn = this.getMoney();
        double animalPrice = Store.assessValue(animal);
        this.setBalance(this.getMoney() + animalPrice);
        this.animals.remove(animal);
        Store.animalStock.add(animal); // om listan i store är public annars för private följande
        System.out.println("(updated balance: " + this.getMoney() + ")"
                + "\n(from: " + balanceComingIn + ")");
        //Store.getAnimalStock().add(animal);
    }

    public String toString(){
        return "name: " + this.name + ", balance: " + this.getMoney() +
                animalsList() +
                "\n" + foodList();
    }

    /*
    public void printAnimalsIncludeTheirFood(Player player){
        int i = 1;
        for(Animal animal : player.getAnimals()){
            System.out.println(i + ". " + animal.getSpecie() + animal.whatIEat());
            i++;
        }
    }
    */
}
