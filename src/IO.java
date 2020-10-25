import java.util.Scanner;

public class IO {

    static public Scanner s = new Scanner(System.in);

    private static void clear(){
        System.out.println("\n".repeat(50));
    }

    public static void prompt(String string){
        System.out.println(string);
    }

    // vet inte om denna fungerar
    public static void promptObjInfo(Object obj){
        System.out.println(obj.toString());
    }

    public static String stringPrompt(String question){
        prompt(question);
        return s.next();
    }

    public static int promptInt(String question){
        prompt(question);
        return s.nextInt();
    }

    public static double promptDbl(String question){
        prompt(question);
        return s.nextDouble();
    }

    public static int startMenu(String gameName, String ...options){
        System.out.println("-".repeat(50));
        System.out.println(gameName);
        System.out.println("-".repeat(50));

        int i = 1;
        for(String option : options){
            System.out.println(i + ". " + option);
            i++;
        }

        int choice = 0;
        try{
            choice = s.nextInt();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        return choice < 1 || choice > options.length ? startMenu(gameName, options) : choice;
    }

    public static int gameOptions(String gameName, String gameStats, String ...gameOptions){
        clear();
        System.out.println("-".repeat(50));
        System.out.println(gameName);
        System.out.println(gameStats);
        System.out.println("-".repeat(50));
        //System.out.println(player.getName() + "s turn");

        int i = 1;
        for(String option : gameOptions){
            System.out.println(i + ". " + option);
            i++;
        }
        System.out.println("-".repeat(50));

        int choice = 0;
        try{
            choice = s.nextInt();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        return choice < 1 || choice > gameOptions.length ? gameOptions(gameName, gameStats, gameOptions) : choice;
    }

}
