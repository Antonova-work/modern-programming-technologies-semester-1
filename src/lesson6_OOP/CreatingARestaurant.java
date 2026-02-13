package lesson6_OOP;

public class CreatingARestaurant {
    public static void main(String[] args) {
        Restaurant myRestaurant = new Restaurant();
        myRestaurant.name = "Пицца-Парк";
        myRestaurant.rating = 5.0;
        myRestaurant.showInfo();
    }
}

class Restaurant {
    String name;
    double rating;

    void showInfo (){
        System.out.println("Ресторан: " + name + ", рейтинг: " + rating);
    }
}
