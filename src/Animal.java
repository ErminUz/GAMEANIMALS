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


    private void increaseHealth(){

    }

    private void setWhatAnimalEats(){
        HashMap<String, List<String>> whatAnimalsEat = FileManagement.getWhatAnimalsEatStorage();
        if(whatAnimalsEat.containsKey(this.getSpecie())){
            this.preferableFoods = whatAnimalsEat.get(this.getSpecie());
        }

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


    }

    //50 % chans att födelsen går igenom. Om det blir 1 går den igenom annars false
    static boolean birth(){
        //Random r = new Random();
        //return r.nextBoolean();
        return new Random().nextBoolean();
        //return ThreadLocalRandom.current().nextInt(0, 2) + 1 == 1;
    }


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



}
