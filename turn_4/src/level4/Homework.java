package level4;

public class Homework {
    public static void main(String[] args) {
        MyJson json = new MyJson();
        if (json.load("turn_4\\src\\level4\\json1.txt")) {
            json.showInfo();
        }
    }
}
