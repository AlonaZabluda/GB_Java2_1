package lesson3;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        String[] words = {"cat", "hamster", "parrot", "dog", "dog", "cat", "horse",
                "fish", "hamster", "hamster", "cat", "ladybug", "hamster"};

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            if(!map.containsKey(s))
                map.put(s, 1);
            else
                map.put(s, map.get(s) + 1);
        }
        Set<String> unicWords = new HashSet<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.printf("Word %s repeats: %d times.\n", entry.getKey(), entry.getValue());
            if(entry.getValue() == 1)
                unicWords.add(entry.getKey());
        }
        System.out.println(unicWords);


        PhoneBook book = new PhoneBook();
        book.add(546638912, "Ivanov");
        book.add(546112345, "Popov");
        book.add(537987614, "Drozdov");
        book.add(546765109, "Popov");
        book.add(537230983, "Ivanov");

        book.get("Ivanov");

    }
}

