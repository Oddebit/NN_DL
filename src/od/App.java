package od;

import java.util.List;
import java.util.Random;

public class App {

    private double[][] input = {
            {1, 2, 3},
            {-1, 0, 1},
            {21, 22, 23},
            {5, 6, 7},
            {-10, -9, -8},

            {5, 7, 6},
            {1, 1, 1},
            {-5, -6, -7},
            {10, 11, 10},
            {8, 2, 3},
            {2, 4, 6},
            {6, 9, 7},
            {-1, 2, -3},
    };
    private double[][] target = {
            {1}, {1}, {1}, {1}, {1},
            {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}
    };
    private final int elements = 3;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        NeuralNetwork nn = new NeuralNetwork(elements, 25, 1);

        nn.train(input, target, 1_000_000);
        double[][] test = {
                //TRUE
                {0, 1, 2},
                {6, 7, 8},
                {19, 20, 21},
                //FALSE
                {1, 3, 2},
                {4, 5, 4},
                {8, 7, 6},
                {47, 49, 48},
                {26, 26, 26}
        };

        System.out.println(" ");
        List<Double> output;
        for (double[] row : test) {
            output = nn.predict(row);

            for (double prob : output) {
                String answer = 0.1 > prob ? "false" : prob > 0.9 ? "true" : "unclear";
                System.out.printf("%-8s (%5.2f%%)", answer, 100 * prob);
            }
            System.out.println(" ");
        }
    }

    public void generateTraining(int elements, int samples, int start, int scope) {
        input = new double[2 * samples][elements];
        target = new double[2 * samples][1];

        Random random = new Random();
        for (int i = 0; i < samples; i++) {
            double[] toGenerate = input[i];
            double value = start + random.nextInt(scope);
            for (int j = 0; j < elements; j++) {
                toGenerate[j] = value + j;
            }
            target[i][0] = 1;
        }
        for (int i = samples; i < 2 * samples; i++) {
            double[] toGenerate = input[i];
            double[] temp = new double[elements];
            for (int j = 0; j < elements; j++) {
                double value = start + random.nextInt(scope);
                toGenerate[j] = value;
            }
            target[i][0] = 0;
        }
        System.out.println("Training is generated.\n");
    }
}