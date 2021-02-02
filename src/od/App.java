package od;

import java.util.List;

public class App {

    public static void main(String[] args) {

        double[][] input = {
                {1, 2, 3},
                {2, 3, 4},
                {3, 4, 5},
                {5, 6, 7},
                {8, 9, 10},
                {-2, -1, 0},
                {-1, 0, 1},
                {23, 24, 25},

                {5, 9, 3},
                {10, 9, 8},
                {3, 1, 7},
                {5, 7, 6},
                {8, 4, 10},
                {23, 21, 22},
                {-1, -2, -3},
                {2, 4, 6},

        };

        double[][] target = {
                {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1},
                {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0},
        };

        NeuralNetwork nn = new NeuralNetwork(3, 25, 50, 50, 25, 1);

        List<Double> output;

        nn.train(input, target, 300_000);

        double [][] test = {
                //TRUE
                {0, 1, 2},
                {6, 7, 8},
                {21, 22, 23},
                //FALSE
                {1, 3, 2},
                {4, 5, 4},
                {8, 7, 6},
                {67, 69, 68},
                {256, 258, 260}
        };

        System.out.println(" ");
        for(double[] row : test) {
            output = nn.predict(row);

            for (double prob : output) {
                String answer = 0.1 > prob ? "false" : prob > 0.9 ? "true" : "unclear";
                System.out.printf("%-8s (%5.2f%%)", answer, 100 * prob);
            }
            System.out.println(" ");
        }
    }
}