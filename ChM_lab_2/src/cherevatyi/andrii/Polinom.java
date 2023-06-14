package cherevatyi.andrii;

import java.util.Map;

import static java.lang.Math.*;

public class Polinom {
    private final Map<Integer, Double> coffs;

    public Polinom(Map<Integer, Double> coffs) {
        this.coffs = coffs;
    }

    public double[] doBisectionMethod(double[] interval, double accuracy) {
        double numberOfIterations = 0;
        double startOfInterval = interval[0];
        double endOfInterval = interval[1];
        double value;
        double[] functionValues = findValuesOfInterval(new double[]{startOfInterval, endOfInterval});
        if (functionValues[0] * functionValues[1] > 0) {
            throw new IllegalArgumentException("Знак функції не змінюється на заданому проміжку");
        }
        while (abs(endOfInterval - startOfInterval) > accuracy) {
            double c = (startOfInterval + endOfInterval) / 2;
            double[] firstInterval = new double[]{startOfInterval, c};
            double[] secondInterval = new double[]{c, endOfInterval};
            double[] valuesOfFirstInterval = findValuesOfInterval(firstInterval);
            if (valuesOfFirstInterval[0] * valuesOfFirstInterval[1] < 0) {
                startOfInterval = firstInterval[0];
                endOfInterval = firstInterval[1];
            } else {
                startOfInterval = secondInterval[0];
                endOfInterval = secondInterval[1];
            }
            numberOfIterations++;
        }
        value = (startOfInterval + endOfInterval) / 2;
        return new double[]{value, numberOfIterations};
    }



    public double[] doChordMethod(double[] interval, double accuracy) {
        double numberOfIterations = 0;
        double startOfInterval = interval[0];
        double endOfInterval = interval[1];
        double prevValue = 0;
        double value = Double.MAX_VALUE;
        double[] functionValues = findValuesOfInterval(new double[]{startOfInterval, endOfInterval});
        if (functionValues[0] * functionValues[1] > 0) {
            throw new IllegalArgumentException("Знак функції не змінюється на заданому проміжку");
        }
        while (abs(value - prevValue) > accuracy) {
            prevValue = value;
            value = startOfInterval - ((functionValues[0] * (startOfInterval
                    - endOfInterval)) / (functionValues[0] - functionValues[1]));
            double[] firstInterval = new double[]{startOfInterval, value};
            double[] secondInterval = new double[]{value, endOfInterval};
            double[] valuesOfFirstInterval = findValuesOfInterval(firstInterval);
            if (valuesOfFirstInterval[0] * valuesOfFirstInterval[1] < 0) {
                startOfInterval = firstInterval[0];
                endOfInterval = firstInterval[1];
            } else {
                startOfInterval = secondInterval[0];
                endOfInterval = secondInterval[1];
            }
            numberOfIterations++;
        }
        return new double[]{value, numberOfIterations};
    }

    public double[] doNewtonMethod(double[] interval, double accuracy) {
        double numberOfIterations = 0;
        double startOfInterval = interval[0];
        double endOfInterval = interval[1];
        double prevValue = 0;
        double value = (startOfInterval + endOfInterval) / 2;
        double[] functionValues = findValuesOfInterval(new double[]{startOfInterval, endOfInterval});
        if (functionValues[0] * functionValues[1] > 0) {
            throw new IllegalArgumentException("Знак функції не змінюється на заданому проміжку");
        }
        while (abs(value - prevValue) > accuracy) {
            prevValue = value;
            value = prevValue - ((findValueOfFunctionInPoint(prevValue)) / (findDerivativeInPoint(prevValue)));
            numberOfIterations++;
        }
        return new double[]{value, numberOfIterations};
    }

    private double[] findValuesOfInterval(double[] interval) {
        double[] values = new double[2];
        for (int i = coffs.size() - 1; i >= 0; i--) {
            if (coffs.containsKey(i)) {
                values[0] += coffs.get(i) * pow(interval[0], i);
                values[1] += coffs.get(i) * pow(interval[1], i);
            }
        }
        return values;
    }

    private double findValueOfFunctionInPoint(double point) {
        double value = 0;
        for (int i = coffs.size() - 1; i >= 0; i--) {
            if (coffs.containsKey(i)) {
                value += coffs.get(i) * pow(point, i);
            }
        }
        return value;
    }

    private double findDerivativeInPoint(double point) {
        double value = 0;
        for (int i = coffs.size() - 1; i >= 0; i--) {
            if (coffs.containsKey(i)) {
                if (i == 0) {
                    continue;
                }
                value += i * coffs.get(i) * pow(point, i - 1);
            }
        }
        return value;
    }
}


