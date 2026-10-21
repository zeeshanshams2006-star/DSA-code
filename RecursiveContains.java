import java.util.ArrayList;
public class RecursiveContains {
    public static boolean containsV2(ArrayList<Integer> list,int value,int startIndex) {
        if (list.get(startIndex) == value) {
            return true;
        }
        else if (startIndex == list.size() - 1) {
            return false;
        }
        else {
            return containsV2(list, value, startIndex + 1);
        }
    }
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(74);
        numbers.add(36);
        numbers.add(95);
        numbers.add(75);
        numbers.add(29);
        numbers.add(47);
        System.out.println(numbers);
        System.out.println(containsV2(numbers, 29, 4)); // true
        System.out.println(containsV2(numbers, 95, 0)); // true
        System.out.println(containsV2(numbers, 100, 0)); // false
    }
}