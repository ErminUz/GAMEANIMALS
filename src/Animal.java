import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

abstract class Animal {
    private String specie;
    private int weight;
    private int age;
    private double healthPoints;
    private double price;
    // hämta åldern och dividera med priset - ju äldre desto lägre blir värdet.
    private ArrayList<Animal> offspring;

    //for offspring
    Animal(){
        this.healthPoints = 100;
    }

    Animal(String specie, int weight, int age, double price){
        this.specie = specie;
        this.weight = weight;
        this.age = age;
        setPrice(price, age);
        this.healthPoints = 100;
        this.offspring = new ArrayList<>();
    }

    // the older an animal gets the lower its worth gets
    private void setPrice(double price, int age){
        price = price / age;
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


    public void eat(Food food){
        int hpLimit = 100;
        if(getHealthPoints() == hpLimit){
            System.out.println("Animal is full, continue feeding and the poor thing will puke...");
        }else{
            int foodWeight = IO.promptInt("Enter food in grams: ");
            double updatedHealth = healthBuff(foodWeight, this);
            this.setHealthPoints(updatedHealth);
        }
    }

    //50 % chans att födelsen går igenom. Om det blir 1 går den igenom annars false
    private boolean birth(){
        //Random r = new Random();
        //return r.nextBoolean();
        return new Random().nextBoolean();
        //return ThreadLocalRandom.current().nextInt(0, 2) + 1 == 1;
    }

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

    public String toString(){
        String hasOffspring = hasOffspring() ? "yes" : "no";
        return "specie: " + getSpecie() + ", weight: " + getWeight() + "kg, age: " +
                getAge() + ", offspring: " + hasOffspring;
    }

}
