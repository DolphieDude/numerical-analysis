package cherevatyi.andrii;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        double epsilonAccuracy;
        System.out.println("Введіть точність, до якої буде обраховуватися результат:");
        System.out.println("1 : \u03B5 = 0.001, 2 : \u03B5 = 0.0001, 3 : \u03B5 = 0.000001");
        Scanner scan = new Scanner(System.in);
        int var = scan.nextInt();
        if (var == 1) epsilonAccuracy = 0.001;
        else if(var == 2) epsilonAccuracy = 0.0001;
        else epsilonAccuracy = 0.000001;

        Map<Integer, Double> polCoffs = new HashMap<>();
        polCoffs.put(5, .0);
        polCoffs.put(4, -1.);
        polCoffs.put(3, 3.);
        polCoffs.put(2, .0);
        polCoffs.put(1, -2.);
        polCoffs.put(0, 5.);
        Polinom polinom = new Polinom(polCoffs);

        System.out.println("Заданий поліном: 0x\u2075 - 1x\u2074 + 3x\u00B3 + 0x\u00B2 - 2x + 5");

        System.out.println("\nЗначення кореня на проміжку [-2; 0]");
        double[] bisectionValues = polinom.doBisectionMethod(new double[]{-2., 0.}, epsilonAccuracy);
        System.out.printf("Метод бісекції: x = %.7s, кількість ітерацій: %.0f\n", formatNumber(bisectionValues[0]), bisectionValues[1]);
        double[] chordValues = polinom.doChordMethod(new double[]{-2., 0.}, epsilonAccuracy);
        System.out.printf("Метод хорд: x = %.7s, кількість ітерацій: %.0f\n",formatNumber(chordValues[0]), chordValues[1]);
        double[] newtonValues = polinom.doNewtonMethod(new double[]{-2., 0.}, epsilonAccuracy);
        System.out.printf("Метод Ньотона: x = %.7s, кількість ітерацій: %.0f\n\n",formatNumber(newtonValues[0]), newtonValues[1]);

        System.out.println("\nЗначення кореня на проміжку [0; 3]");
        bisectionValues = polinom.doBisectionMethod(new double[]{0., 3.}, epsilonAccuracy);
        System.out.printf("Метод бісекції: x = %.7s, кількість ітерацій: %.0f\n", formatNumber(bisectionValues[0]), bisectionValues[1]);
        chordValues = polinom.doChordMethod(new double[]{0., 3.}, epsilonAccuracy);
        System.out.printf("Метод хорд: x = %.7s, кількість ітерацій: %.0f\n",formatNumber(chordValues[0]), chordValues[1]);
        newtonValues = polinom.doNewtonMethod(new double[]{0., 3.}, epsilonAccuracy);
        System.out.printf("Метод Ньотона: x = %.7s, кількість ітерацій: %.0f\n\n",formatNumber(newtonValues[0]), newtonValues[1]);
    }

    public static StringBuilder formatNumber(double number) {
        LinkedList<Character> charList = new LinkedList<>();
        char[] charArr;
        charArr = String.valueOf(number).toCharArray();
        int length;
        if (charArr.length > 6) {
            length = 6;
        } else {
            length = charArr.length;
        }
        for (int i = 0; i < length; i++) {
            charList.add(charArr[i]);
        }
        for (int k = 0; k < length; k++) {
            if (Character.isDigit(charArr[k])) {
                if (charArr.length > length) {
                    if (charArr[k] == '0' && k == length - 1) {
                        charList.remove(k);
                    }
                } else if (charArr[k] == '0' && k == length - 1) {
                    charList.remove(k);
                }
            }
        }
        if (charList.getLast().equals('.')) {
            charList.removeLast();
        }
        if (number > 0) {
            for (; charList.size() < 6; ) {
                charList.addFirst('0');
            }
        } else {
            for (; charList.size() < 6; ) {
                charList.add(1, '0');
            }
        }

        Object[] outputArr = charList.toArray();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < outputArr.length; i++) {
            output.append(outputArr[i]);
        }
        return output;
    }
}
