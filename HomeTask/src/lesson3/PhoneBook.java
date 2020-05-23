package lesson3;

import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    Map<Long, String> stringMap;

    public PhoneBook() {
        this.stringMap = new HashMap<>();
    }

    public void add(long number, String surname) {
        stringMap.put(number, surname);
    }

    public void get(String surname) {
        for (Map.Entry<Long, String> entry : stringMap.entrySet()) {
            if (entry.getValue().contains(surname)) {
                System.out.printf("Contact: %s. Telephone number: ", entry.getValue());
                System.out.println(String.valueOf(entry.getKey()).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3"));
            }
        }

    }
}
