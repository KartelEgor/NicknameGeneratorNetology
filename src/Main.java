import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger threeLetters = new AtomicInteger(0);
    public static AtomicInteger fourLetters = new AtomicInteger(0);
    public static AtomicInteger fiveLetters = new AtomicInteger(0);
    public static List<Thread> threadList = new ArrayList<>();

    public static void main(String[] args) {

        String[] texts = randomWords();

        Thread thread1 = new Thread(() -> {
            for (String word : texts) {
                if (isPalindrome(word)) {
                    if (word.length() == 3) threeLetters.getAndIncrement();
                    else if (word.length() == 4) fourLetters.getAndIncrement();
                    else fiveLetters.getAndIncrement();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String word : texts) {
                if (isSameLetters(word)) {
                    if (word.length() == 3) threeLetters.getAndIncrement();
                    else if (word.length() == 4) fourLetters.getAndIncrement();
                    else fiveLetters.getAndIncrement();
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String word : texts) {
                if (isRightLetter(word)) {
                    if (word.length() == 3) threeLetters.getAndIncrement();
                    else if (word.length() == 4) fourLetters.getAndIncrement();
                    else fiveLetters.getAndIncrement();
                }
            }
        });

        threadList.add(thread1);
        threadList.add(thread2);
        threadList.add(thread3);
        threadList.forEach(Thread::start);

        try {
            for (Thread thread : threadList) thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Красивых слов с длиной 3: " + threeLetters.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + fourLetters.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + fiveLetters.get() + " шт");
    }

    //--------------------------------------------------------------------------------------------------------------------------
    public static boolean isPalindrome(String word) {
        return word
                .contentEquals(new StringBuilder(word)
                        .reverse());
    }

    public static boolean isSameLetters(String word) {
        char[] cArray = word.toCharArray();
        for (int i = 1; i < cArray.length; i++) {
            if (cArray[0] != cArray[i]) return false;
        }
        return true;
    }

    public static boolean isRightLetter(String word) {
        char[] cArray = word.toCharArray();
        for (int i = 0; i < cArray.length - 1; i++) {
            if (word.codePointAt(i) > word.codePointAt(i + 1)) return false;
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static String[] randomWords() {
        Random random = new Random();
        String[] texts = new String[10_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }
}
