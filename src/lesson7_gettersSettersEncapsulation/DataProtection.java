package lesson7_gettersSettersEncapsulation;

public class DataProtection {
    public static void main(String[] args) {
        Restaurant myRestaurant = new Restaurant("Пицца-Парк");
        System.out.println("Текущее имя ресорана: " + myRestaurant.getName());
        myRestaurant.setRating(4.5);
        System.out.println("Текущий рейтинг ресорана: " + myRestaurant.getRating());
    }

}

class Restaurant {
    private String name;
    private double rating;

    public Restaurant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRating(double newRating) {
        if (newRating < 0.0) {
            rating = 0.0;
        } else if (newRating > 5.0) {
            rating = 5.0;
        } else {
            rating = newRating;
        }
    }

    public double getRating() {
        return rating;
    }
}