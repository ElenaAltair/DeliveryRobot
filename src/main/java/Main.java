import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
Задача 1. Робот-доставщик
Описание
Представьте, что перед вашей командой поставили задачу разработать программное обеспечение
для робота-доставщика.
Инструкции для робота содержат команды:

R — поверни направо;
L — поверни налево;
F — двигайся вперёд.

Напишите многопоточную программу, которая в каждом потоке:

генерирует текст generateRoute("RLRFR", 100);
считает количество команд поворота направо (буквы 'R');
выводит на экран результат.
Количество потоков равно количеству генерируемых маршрутов и равно 1000.
 */
public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {


        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String str = generateRoute("RLRFR", 100);
                int count = str.length() - str.replace("R", "").length();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            }).start();
        }


        System.out.print("Самое частое количество повторений ");
        Map.Entry<Integer, Integer> maxEntry = sizeToFreq.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null);
        System.out.print(maxEntry.getKey());
        System.out.print(" (встретилось ");
        System.out.print(maxEntry.getValue());
        System.out.print(" раз) ");
        System.out.println();
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> kv : sizeToFreq.entrySet()) {
            System.out.println("- " + kv.getKey() + " (" + kv.getValue() + " раз)");
        }
    }


    //В процессе построения карты маршрутов вам поручили проанализировать
    // разнообразие существующих путей.
    // Для генерации маршрутов вы используете функцию:
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
