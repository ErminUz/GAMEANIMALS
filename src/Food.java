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

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    public int calcPrice(int weightGrams){
        int pricePerGrams = this.getPricekg() / 1000;
        return weightGrams * pricePerGrams;

    }

    public boolean removeFromStock(){
        return this.getWeight() <= 0;
    }

    public String toString(){
        return "food: " + this.food + ", price/kg: " + this.pricekg + "kr" + ", stock: " + this.weight + "kg";
    }
}
