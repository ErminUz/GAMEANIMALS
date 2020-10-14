abstract class Food {
    private String food;
    private int pricekg;
    private int weight;

    public Food(String food, int pricekg, int weight){
        this.food = food;
        this.pricekg = pricekg;
        this.weight = weight;
    }

    public String getFood(){
        return food;
    }

    public int getPricekg(){
        return pricekg;
    }

    public int getWeight(){
        return weight;
    }

    public String toString(){
        return "food: " + this.food + ", price/kg: " + this.pricekg + "kr" + ", stock: " + this.weight + "kg";
    }
}
