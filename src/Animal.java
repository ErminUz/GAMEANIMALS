import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

abstract class Animal implements Serializable {
    private String specie;
    private int weight;
    private int age;
    private double healthPoints;
    private double price;
    // hämta åldern och dividera med priset - ju äldre desto lägre blir värdet.
    private ArrayList<Animal> offspring;
    private List<String> preferableFoods;

    //denna sätts till false i konstruktorn, djur är friska från början och de föds friska
    private boolean sick;


    /*
    ett djur kan äta 1-3 sorters mat man har. Jag har 3 sorters klasser: carbs, fat och protein. Jag hade kunnat lägga in
    en siffra 1-3 som indikerar på hur många sorter djuret kan äta. Tex en häst hade fått 2 vilka är carbs och fat. Men då
    uppstår problemet att jag måste lägga till detta i textfilen, strängar för vilka sorter dem kan äta.
    Och då behövs nog inte siffran
    private ArrayList<String> preferedFood;

    */

    //for offspring
    Animal(String specie){
        this.specie = specie;
        this.healthPoints = 100;
        setPrice(price, age);
        this.offspring = new ArrayList<>();
        setWhatAnimalEats();
        this.age = 1;
    }

    Animal(String specie, int weight, int age, double price){
        this.specie = specie;
        this.weight = weight;
        this.age = age;
        setPrice(price, age);
        this.healthPoints = 100;
        this.offspring = new ArrayList<>();
        setWhatAnimalEats();
    }

    // the older an animal gets the lower its worth gets
    private void setPrice(double price, int age){
        price = price / age;
        price = Math.round(price * 100.0) / 100.0;
        this.setPrice(price);
    }

    /*
    private int calcHPBuff(int weight){
        String num = String.valueOf(weight);
        boolean decimal = num.contains(",");
        if(decimal){
            int numLe
        }
        int numLength = String.valueOf(weight).length();
        if(numLength == 1){

        }else if(numLength == 2){

        }
    }
    */

    private void increaseHealth(){

    }

    private void setWhatAnimalEats(){
        HashMap<String, List<String>> whatAnimalsEat = FileManagement.getWhatAnimalsEatStorage();
        if(whatAnimalsEat.containsKey(this.getSpecie())){
            this.preferableFoods = whatAnimalsEat.get(this.getSpecie());
        }

        /*
        HashMap<String, List<String>> whatAnimalsEat = FileManagement.getWhatAnimalsEatStorage();
        for(String specie : whatAnimalsEat.keySet()){
            for(Animal animal : FileManagement.getAnimals()){
                if(animal.getSpecie().equals(specie)){
                    this.preferableFoods = whatAnimalsEat.get(specie);
                }
            }
        }
        */
    }


    // method checks if given int has decimal or not
    // will break down the weight into either 1 or 2 elements in an array
    // this array of int/s serves as to how many times healthbuff will be applied
    // example: weight given is 5kg food. 10 % healthbuff per kg = 5 times healthbuff.
    // example decimal: weight given is 5.3kg food. 10 % healthbuff per kg = 5 times healthbuff + 1 time 3 % healthbuff
    // for now method only works for weights that are whole numbers and numbers with one decimal point

    private double healthBuff(double foodWeight, Animal animal){
        double health = animal.getHealthPoints();
        BigDecimal bd = new BigDecimal(String.valueOf(foodWeight));
        int intValue = bd.intValue();
        double decimalValue = Double.parseDouble(String.valueOf(bd.subtract(new BigDecimal(intValue))));
        if(decimalValue == 0){
            for(int i = 0; i < intValue; i++){
                health += health * 0.1;
            }
        }else{
            for(int i = 0; i < intValue; i++){
                health += health * 0.1;
            }
            decimalValue /= 10;
            health += health * (health * decimalValue);
        }

        return health;
    }

    private boolean canEat(Food food){
        return this.getPreferableFoods().contains(food.getFood());
    }



    public void eat(Food food, double amount) {
        int originalFoodWeight = food.getWeight();
        if(this.getHealthPoints() >= 100){
            System.out.println("Animal has full health");
        }else{
            if(canEat(food)){
                double updateHealthAmount = healthBuff(amount, this);
                this.setHealthPoints((this.healthPoints) + updateHealthAmount);
                //int newFoodStockWeight = (int) ((food.getWeight() * 1000) - amount) / 1000;
                int newFoodStockWeight = (int) (food.getWeight() - amount);
                food.setWeight(newFoodStockWeight);
            }else{
                System.out.println("Can't feed the animal with that");
            }
        }
        System.out.println("(updated food weight: " + food.getWeight() + "kg)\n(from: " + originalFoodWeight + "kg)");

        /*
        // kolla om djuret har maten i sin lista i hashmap
        if(canEat(food)){ // && this.getHealthPoints() < 100
            //int foodWeight = IO.promptInt("Enter food in grams: ");
            double updatedHealth = healthBuff(amount, this);
            this.setHealthPoints(updatedHealth);
            int newFoodStock = (int) ((food.getWeight() * 1000) - amount) / 1000;
            food.setWeight(newFoodStock);
            System.out.println("eating");
        }else{
            System.out.println("animal doesnt eat that or has already full hp");
        }
        */
    }

    //50 % chans att födelsen går igenom. Om det blir 1 går den igenom annars false
    static boolean birth(){
        //Random r = new Random();
        //return r.nextBoolean();
        return new Random().nextBoolean();
        //return ThreadLocalRandom.current().nextInt(0, 2) + 1 == 1;
    }


    //denna metod kanske bör istället vara i player klassen, och kallas breed.
    /*
    public void mate(Animal male, Animal female){
        if(male instanceof Bird && female instanceof Bird){
            if(birth()){
                System.out.println("Birth went well");
                Bird offspring = new Bird();
                male.offspring.add(offspring);
                female.offspring.add(offspring);
            }else{
                System.out.println("Miscarriage...");
            }
        }else if(male instanceof Fish && female instanceof Fish){
            if(birth()){
                System.out.println("Birth went well");
                Fish offspring = new Fish();
                male.offspring.add(offspring);
                female.offspring.add(offspring);
            }else{
                System.out.println("Miscarriage...");
            }
        }else if(male instanceof Mammal && female instanceof Mammal){
            if(birth()){
                System.out.println("Birth went well");
                Mammal offspring = new Mammal();
                male.offspring.add(offspring);
                female.offspring.add(offspring);
            }
        }
    }

     */


    public void setSpecie(String specie){
        this.specie = specie;
    }

    public String getSpecie(){
        return specie;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    public void setHealthPoints(double health){
        this.healthPoints = health;
        this.healthPoints = Math.round(this.healthPoints * 10) / 10.0;
        if(this.healthPoints <= 0.0){
            this.healthPoints = 0.0;
        }else if(this.healthPoints > 100.0){
            this.healthPoints = 100.0;
        }
    }

    public double getHealthPoints(){
        return healthPoints;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public ArrayList<Animal> getOffspring(){
        return offspring;
    }

    public boolean hasOffspring(){
        return !offspring.isEmpty();
    }

    public List<String> getPreferableFoods(){
        return preferableFoods;
    }

    public boolean isSick(){
        return sick;
    }

    public String toString(){
        String hasOffspring = hasOffspring() ? "yes" : "no";
        return "specie: " + getSpecie() + ", weight: " + getWeight() + "kg, age: " +
                getAge() + ", offspring: " + hasOffspring;
    }

    public void sick(){

    }

    /*
    public String whatIEat(){
        StringBuilder sb = new StringBuilder();
        //sb.append(this.getSpecie());
        for(String food : preferableFoods){
            sb.append(food).append(" ");
        }

        return sb.toString();
    }
    */


}
