import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    //
    private static Path whatAnimalEats = Paths.get("/Users/macbook/IdeaProjects/GAMEANIMALS/resource/animalEats");

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

    //
    private static ArrayList<String> animalSpecies = new ArrayList<>();

    // antingen har jag en arraylist av typen arraylist string array, eller bara arraylist av typen string array
    private static ArrayList<String[]> whatAnimalEatsLists = new ArrayList<>();
    private static HashMap<String, List<String>> whatAnimalsEat = new HashMap<>();
    //private static ArrayList<HashMap>

    // följande två metoder kan förkortas till endast en genom att ha en tredje parameter
    // en string som ger ett körsätt om den är food eller animal
    private static void clearAttrAnimalList(){
        attributeValuesAnimal.clear();
    }

    private static void clearAttrFoodList(){
        attributeValuesFood.clear();
    }

    private static void addSpecieOnlyOnce(String specie){
        if(animalSpecies.contains(specie)){
        }else{
            animalSpecies.add(specie);
        }
    }

    public static void readAnimalsIn(){
        String info = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(textAnimals)));
            while((info = br.readLine()) != null){
                String[] s = info.split(" ");
                attributeValuesAnimal.add(s);
                //attributeValuesAnimal.add(info.split(" "));
                String specie = s[1];
                addSpecieOnlyOnce(specie);
                //animalSpecies.add(specie);
                //setWhatAnimalEats(); // tror den bör vara här. nej nog inte här utan utanför while
                //readInWhatAnimalEats(); //tror den kan var här...
            }
            setWhatAnimalEats();
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

    private static void readInAnimalsWhileRunning(){
        clearAttrAnimalList();
        String info = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(textAddAnimals)));
            while((info = br.readLine()) != null){
                attributeValuesAnimal.add(info.split(" "));
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void createAnimalWhileRunning(){
        readInAnimalsWhileRunning();
        String[] animalSpecie = getAnimalClassFromList(attributeValuesAnimal);
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

    public static void createAnimal(){
        readAnimalsIn();
        String[] animalSpecie = getAnimalClassFromList(attributeValuesAnimal);
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
                    int weight = generateWeight(weightRange); // generateWeight(attr[3]);
                    // jag kanske kan deklarera age på samma sätt som weight
                    int age = Integer.parseInt(attr[4]);
                    double price = Double.parseDouble(attr[5]);
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
                    double price = Double.parseDouble(attr[5]);
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
                    double price = Double.parseDouble(attr[5]);
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

    public static void readFoodsIn(){
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

    private static void readFoodsInWhileRunning(){
        clearAttrFoodList();
        String info = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(textAddFoods)));
            while((info = br.readLine()) != null){
                attributeValuesAnimal.add(info.split(" "));
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void createFoodWhileRunning(){
        readFoodsInWhileRunning();
        String[] food = getFoodClassFromList(attributeValuesFood);
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

    public static void createFood(){
        readFoodsIn();
        String[] food = getFoodClassFromList(attributeValuesFood);
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

    /*
    private static void loadAddedAnimals(Path file, ArrayList<String[]> addingTo){
        String info = " ";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(file)));
            while((info = br.readLine()) != null){
                addingTo.add(info.split(" "));;
            }

            createAnimalWhileRunning();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void loadAddedFood(Path file, ArrayList<String[]> addingTo){
        String info = " ";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(textFoods)));
            while((info = br.readLine()) != null){
                attributeValuesFood.add(info.split(" "));
            }

            createFoodWhileRunning(attributeValuesFood);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    */

    //vart ska denna köras ifrån?
    public static void setWhatAnimalEats(){
        readInWhatAnimalEats();
    }

    //tror inte denna behöver return type...?
    private static void readInWhatAnimalEats(){
        String info = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(whatAnimalEats)));
            while((info = br.readLine()) != null){
                //ArrayList<String[]> createdList = new ArrayList<>();
                //createdList.add(info.split(" "));
                //whatAnimalEatsLists.add(createdList);
                whatAnimalEatsLists.add(info.split(" "));
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        transferToHashMap();
    }

    private static void transferToHashMap(){
        for(String[] s : whatAnimalEatsLists){
            for(String specie : animalSpecies){
                if(s[0].equals(specie)){
                    String key = specie;
                    StringBuilder value = new StringBuilder();
                    for(int i = 1; i < s.length; i++){
                        value.append(s[i]).append(" ");
                    }

                    String[] sArr = value.toString().split(" ");
                    List<String> values = Arrays.asList(sArr);
                    whatAnimalsEat.put(key, values);
                }
            }
        }
    }

    public static ArrayList<Animal> getAnimals(){
        return animals;
    }

    public static ArrayList<Food> getFoods(){
        return foods;
    }

    public static HashMap<String, List<String>> getWhatAnimalsEatStorage(){
        return whatAnimalsEat;
    }

    public static ArrayList<String[]> getAttributeValuesAnimal(){
        return attributeValuesAnimal;
    }

    public static void printClassesAnimalsAdded(){
        String[] classes = getAnimalClassFromList(attributeValuesAnimal);
        for(String animalClass : classes){
            System.out.println(animalClass);
        }
    }

    public static void refresh(){
        animals.clear();
        foods.clear();
        attributeValuesAnimal.clear();
        attributeValuesFood.clear();
        whatAnimalEatsLists.clear();
        whatAnimalsEat.clear();
    }



    public static void save(Path file){

    }
}
