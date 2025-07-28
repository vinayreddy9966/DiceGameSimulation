package game;

import java.util.*;

public class DiceGame {

    private static final Random rand = new Random();

    public static int rollDie() {
        return rand.nextInt(6) + 1;
    }

    public static int playGame(int numDice) {
        List<Integer> dice = rollDice(numDice);
        int totalScore = 0;

        while (!dice.isEmpty()) {
            if (dice.contains(3)) {
                dice.removeIf(d -> d == 3);
            } else {
                int min = Collections.min(dice);
                totalScore += min;
                dice.remove((Integer) min);
            }

            if (!dice.isEmpty()) {
                dice = rollDice(dice.size());
            }
        }

        return totalScore;
    }

    private static List<Integer> rollDice(int num) {
        List<Integer> dice = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            dice.add(rollDie());
        }
        return dice;
    }

    public static Map<Integer, Integer> simulateGames(int numSimulations, int numDice) {
        Map<Integer, Integer> results = new TreeMap<>();
        for (int i = 0; i < numSimulations; i++) {
            int score = playGame(numDice);
            results.put(score, results.getOrDefault(score, 0) + 1);
        }
        return results;
    }
}
