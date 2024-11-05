import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Получить список слов

// Вытащить оттуда рандомное слово и зашифровать его
// Ввод букв с консоли
public class Main {

    public static final int LIVES_COUNT = 4;
    public static File file = new File("package/", "file.txt");
    public static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        questionToPlayer();
    }

    private static void questionToPlayer() {
        System.out.println("Вы хотите начать игру?\nД - ДА\nН - НЕТ");
        System.out.println();
        char answerOfPlayer = inputLetterFromConcole();
        if (answerOfPlayer == 'Д') {
            String[] words = getDataFromFile(file);
            startGame(words);
        } else if (answerOfPlayer == 'Н') {
            sc.close();
            System.out.println("До свидания");
        } else {
            System.out.println("Введите Д или Н");
            questionToPlayer();
        }
    }



    private static void startGame(String[] arrayOfWords) {
        int tries = 0;
        String randomWord = arrayOfWords[(int) (Math.random() * arrayOfWords.length)].toUpperCase();
        StringBuilder maskedWord = new StringBuilder(randomWord.replaceAll(".", "*"));
        List<Character> usedLetters = new ArrayList();
        char letter;
        while (tries < LIVES_COUNT) {
            System.out.println(maskedWord);
            System.out.println();
            if (!usedLetters.isEmpty()) {
                System.out.print("Вы уже ввели: ");
                usedLetters.stream()
                        .map(str -> str + " ")
                        .forEach(System.out::print);
                System.out.println();
            }
            letter = inputLetterFromConcole();
            if (usedLetters.contains(letter)) {
                System.out.println("Вы уже вводили эту букву");
                continue;
            }
            if (randomWord.contains(String.valueOf(letter))) {
                for (int i = 0; i < randomWord.length(); i++) {
                    if (randomWord.charAt(i) == letter) {
                        maskedWord.setCharAt(i, letter);
                    }
                }
                if (!String.valueOf(maskedWord).contains("*")) {
                    System.out.println("Поздравляем, вы отгадали слово");
                    questionToPlayer();
                    break;
                }
            } else {
                System.out.println();
                hangMan(tries);
                tries++;
                if (tries == LIVES_COUNT) {
                    System.out.println("Ты проиграл. Было загадано " + randomWord);
                    questionToPlayer();
                    break;
                }
            }
            usedLetters.add(letter);
        }
    }

    private static String[] getDataFromFile(File file) {
        String[] words;
        StringBuilder result = new StringBuilder();
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            int a = reader.read();
            while (a > 0) {
                result.append((char) a);
                a = reader.read();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return words = result.toString().split("\n");
    }

    private static char inputLetterFromConcole() {
        char letter;
        System.out.println("Введите русскую букву");
        letter = sc.next().toUpperCase().charAt(0);
        if (!Character.toString(letter).matches("[А-ЯЁ]")) {
            do {
                System.out.println("Ошибка: введите русскую букву");
                letter = sc.next().toUpperCase().charAt(0);
            } while (!Character.toString(letter).matches("[А-ЯЁ]"));
        }
        return letter;
    }

    private static void hangMan(int tries) {
        String[] hangsArr = { "" +
                "|\n" + "|\n" + "|\n" + "|\n",
                "____\n" + "|\n" + "|\n" + "|\n" + "|\n",
                "____\n" + "|  |\n" + "|\n" + "|\n" + "|\n",
                "____\n" + "|  |\n" + "|  O\n" + "| /|\\\n" + "| /|\\\n"
        };
        System.out.println(hangsArr[tries]);
    }
}