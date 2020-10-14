import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class FileManagement {
    // filen som läses vid start och gör objekt av djuren ur textformatet
    private static Path textAnimals = Paths.get("/Users/macbook/IdeaProjects/GAMEANIMALS/resource/Animals");

    // filen som läses vid start och gör objekt av djuren ur textformatet
    private static Path textFoods = Paths.get("/Users/macbook/IdeaProjects/GAMEANIMALS/resource/Foods");

    // filen som läses när metoden add animal väljs. Förmodat att man skrivit till fil innan man kallar metod
    private static Path textAddAnimals;

    // filen som läses när metoden add food väljs. Förmodat att man skrivit till fil innan man kallar metod
    private static Path textAddFoods;

    // förmodligen för om jag klarar funktionen spara gaming session för att återuppta senare
    private static Path storage;

    // när man lägger till extra objekt under körning av program kan de lagras i en lista med string arrays
    // för att sedan göras om till objekt utifrån värdena i arrayn.
    // när man väljer att skapa objekt igen kan man kanske frågas om man vill välja från och med vilka objekt
    // man ska börja loopa igenom och skapa objekt utav
    // behöver nog också två listor av objekttypen
    private static ArrayList<Animal> animals = new ArrayList<>();
    private static ArrayList<Food> foods = new ArrayList<>();
    private static ArrayList<String[]> attributeValuesAnimal = new ArrayList<>();
    private static ArrayList<String[]> attributeValuesFood = new ArrayList<>();
    //private static ArrayList<Game> gameSessions = new ArrayList<>();

    // följande två metoder kan förkortas till endast en genom att ha en tredje parameter
    // en string som ger ett körsätt om den är food eller animal
    public static void readAnimalsIn(){
        String info = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(textAnimals)));
            while((info = br.readLine()) != null){
                attributeValuesAnimal.add(info.split(" "));
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static int generateWeight(String weightRange){
        int lengthNum = weightRange.length();
        int generatedWeight = 0;
        if(lengthNum == 2){
            int min = Integer.parseInt(weightRange.substring(0,1));
            int max = Integer.parseInt(weightRange.substring(1));
            generatedWeight = ThreadLocalRandom.current().nextInt((min-1), max) + 1;
        }else if(lengthNum == 3){
            int min = Integer.parseInt(weightRange.substring(0,1));
            int max = Integer.parseInt(weightRange.substring(1));
            generatedWeight = ThreadLocalRandom.current().nextInt((min-1), max) + 1;
        }else if(lengthNum ==4){
            int min = Integer.parseInt(weightRange.substring(0,2));
            int max = Integer.parseInt(weightRange.substring(2));
            generatedWeight = ThreadLocalRandom.current().nextInt((min-1), max) + 1;
        }

        return generatedWeight;
    }

    public static void createAnimalWhileRunning(ArrayList<String[]> attributesList){
        String[] animalSpecie = getAnimalClassFromList(attributesList);
        int i = 0;
        for(String animal : animalSpecie){
            if(animal.equals("Mammal")){
                String[] attr = attributeValuesAnimal.get(i);
                int createObjAmount = Integer.parseInt(attr[2]);
                int counter = 0;
                while(counter < createObjAmount){
                    // ha kanske en prompt som ber dig döpa djuret
                    String weightRange = attr[3];
                    String specie = attr[1];
                    int weight = generateWeight(weightRange);
                    // jag kanske kan deklrarera age på samma sätt som weight
                    int age = Integer.parseInt(attr[4]);
                    int price = Integer.parseInt(attr[5]);
                    Mammal mammal = new Mammal(specie, weight, age, price);
                    animals.add(mammal);
                    counter++;
                }
            }else if(animal.equals("Fish")){
                String[] attr = attributeValuesAnimal.get(i);
                int createObjAmount = Integer.parseInt(attr[2]);
                int counter = 0;
                while(counter < createObjAmount){
                    // ha kanske en prompt som ber dig döpa djuret
                    String weightRange = attr[3];
                    String specie = attr[1];
                    int weight = generateWeight(weightRange);
                    int age = Integer.parseInt(attr[4]);
                    int price = Integer.parseInt(attr[5]);
                    Fish fish = new Fish(specie, weight, age, price);
                    animals.add(fish);
                    counter++;
                }
            }else if(animal.equals("Bird")){
                String[] attr = attributeValuesAnimal.get(i);
                int createObjAmount = Integer.parseInt(attr[2]);
                int counter = 0;
                while(counter < createObjAmount){
                    // ha kanske en prompt som ber dig döpa djuret
                    String weightRange = attr[3];
                    String specie = attr[1];
                    int weight = generateWeight(weightRange);
                    int age = Integer.parseInt(attr[4]);
                    int price = Integer.parseInt(attr[5]);
                    Bird bird = new Bird(specie, weight, age, price);
                    animals.add(bird);
                    counter++;
                }
            }
            i++;
        }

    }

    public static void createAnimal(ArrayList<String[]> attributesList){
        readAnimalsIn();
        String[] animalSpecie = getAnimalClassFromList(attributesList);
        int i = 0;
        for(String animal : animalSpecie){
            if(animal.equals("Mammal")){
                String[] attr = attributeValuesAnimal.get(i);
                int createObjAmount = Integer.parseInt(attr[2]);
                int counter = 0;
                while(counter < createObjAmount){
                    // ha kanske en prompt som ber dig döpa djuret
                    String weightRange = attr[3];
                    String specie = attr[1];
                    int weight = generateWeight(weightRange);
                    // jag kanske kan deklrarera age på samma sätt som weight
                    int age = Integer.parseInt(attr[4]);
                    int price = Integer.parseInt(attr[5]);
                    Mammal mammal = new Mammal(specie, weight, age, price);
                    animals.add(mammal);
                    counter++;
                }
            }else if(animal.equals("Fish")){
                String[] attr = attributeValuesAnimal.get(i);
                int createObjAmount = Integer.parseInt(attr[2]);
                int counter = 0;
                while(counter < createObjAmount){
                    // ha kanske en prompt som ber dig döpa djuret
                    String weightRange = attr[3];
                    String specie = attr[1];
                    int weight = generateWeight(weightRange);
                    int age = Integer.parseInt(attr[4]);
                    int price = Integer.parseInt(attr[5]);
                    Fish fish = new Fish(specie, weight, age, price);
                    animals.add(fish);
                    counter++;
                }
            }else if(animal.equals("Bird")){
                String[] attr = attributeValuesAnimal.get(i);
                int createObjAmount = Integer.parseInt(attr[2]);
                int counter = 0;
                while(counter < createObjAmount){
                    // ha kanske en prompt som ber dig döpa djuret
                    String weightRange = attr[3];
                    String specie = attr[1];
                    int weight = generateWeight(weightRange);
                    int age = Integer.parseInt(attr[4]);
                    int price = Integer.parseInt(attr[5]);
                    Bird bird = new Bird(specie, weight, age, price);
                    animals.add(bird);
                    counter++;
                }
            }
            i++;
        }
    }

    private static String[] getAnimalClassFromList(ArrayList<String[]> list){
        StringBuilder sb = new StringBuilder();
        String[] eachLineAnimalArr;
        for(String[] animal : list){
            sb.append(animal[0]).append(" ");
        }

        eachLineAnimalArr = sb.toString().split(" ");
        return eachLineAnimalArr;
    }

    private static void readFoodsIn(){
        String info = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(textFoods)));
            while((info = br.readLine()) != null){
                attributeValuesFood.add(info.split(" "));
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void createFoodWhileRunning(ArrayList<String[]> attributesList){
        String[] food = getFoodClassFromList(attributesList);
        int i = 0;
        for(String x : food){
            if(x.equals("Carbs")){
                String[] attr = attributeValuesFood.get(i);
                String name = attr[1];
                int pricekg = Integer.parseInt(attr[3]);
                int weight = Integer.parseInt(attr[2]);
                Carbs carb = new Carbs(name, pricekg, weight);
                foods.add(carb);
                i++;
            }else if(x.equals("Fat")){
                String[] attr = attributeValuesFood.get(i);
                String name = attr[1];
                int pricekg = Integer.parseInt(attr[3]);
                int weight = Integer.parseInt(attr[2]);
                Fat fat = new Fat(name, pricekg, weight);
                foods.add(fat);
                i++;
            }else if(x.equals("Protein")){
                String[] attr = attributeValuesFood.get(i);
                String name = attr[1];
                int pricekg = Integer.parseInt(attr[3]);
                int weight = Integer.parseInt(attr[2]);
                Protein protein = new Protein(name, pricekg, weight);
                foods.add(protein);
                i++;
            }
        }
    }

    public static void createFood(Path file, ArrayList<String[]> attributesList){
        readFoodsIn();
        String[] food = getFoodClassFromList(attributesList);
        int i = 0;
        for(String x : food){
            if(x.equals("Carbs")){
                String[] attr = attributeValuesFood.get(i);
                String name = attr[1];
                int pricekg = Integer.parseInt(attr[3]);
                int weight = Integer.parseInt(attr[2]);
                Carbs carb = new Carbs(name, pricekg, weight);
                foods.add(carb);
                i++;
            }else if(x.equals("Fat")){
                String[] attr = attributeValuesFood.get(i);
                String name = attr[1];
                int pricekg = Integer.parseInt(attr[3]);
                int weight = Integer.parseInt(attr[2]);
                Fat fat = new Fat(name, pricekg, weight);
                foods.add(fat);
                i++;
            }else if(x.equals("Protein")){
                String[] attr = attributeValuesFood.get(i);
                String name = attr[1];
                int pricekg = Integer.parseInt(attr[3]);
                int weight = Integer.parseInt(attr[2]);
                Protein protein = new Protein(name, pricekg, weight);
                foods.add(protein);
                i++;
            }
        }
    }

    private static String[] getFoodClassFromList(ArrayList<String[]> list){
        StringBuilder sb = new StringBuilder();
        String[] eachLineFoodArr;
        for(String[] food : list){
            sb.append(food[0]).append(" ");
        }

        eachLineFoodArr = sb.toString().split(" ");
        return eachLineFoodArr;
    }

    private static void loadAddedAnimals(Path file, ArrayList<String[]> addingTo){
        String info = " ";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(file)));
            while((info = br.readLine()) != null){
                addingTo.add(info.split(" "));;
            }

            createAnimalWhileRunning(addingTo);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }



    public static void loadAddedFood(Path file, ArrayList<String[]> addingTo){
        String info = " ";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(file)));
            while((info = br.readLine()) != null){
                addingTo.add(info.split(" "));
            }

            createFoodWhileRunning(addingTo);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Animal> getAnimals(){
        return animals;
    }

    public static ArrayList<Food> getFoods(){
        return foods;
    }

    public static void save(Path file){

    }
}
