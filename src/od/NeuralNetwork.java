package od;

import java.util.List;
import java.util.Random;

public class NeuralNetwork {

    private final int layers;
    private Matrix[] neurons, weights, biases;
    private final double learningRate = 0.001;

    public NeuralNetwork(int... sizes) {
        this.layers = sizes.length;

        this.neurons = new Matrix[layers];
        for (int i = 0; i < layers; i++) {
            neurons[i] = new Matrix(sizes[i], 1);
        }

        this.weights = new Matrix[layers - 1];
        for (int i = 0; i < layers - 1; i++) {
            weights[i] = new Matrix(sizes[i + 1], sizes[i]);
        }

        this.biases = new Matrix[layers - 1];
        for (int i = 0; i < layers - 1; i++) {
            biases[i] = new Matrix(sizes[i + 1], 1);
        }
    }

    public List<Double> predict(double[] Input) {
        feedForward(Input);
        return neurons[layers - 1].toArray();
    }

    public void train(double[][] Input, double[][] Target, long epochs) {
        int lastTime = 0;
        for (int i = 0; i < epochs; i++) {
            int sample = new Random().nextInt(Input.length);
            this.train(Input[sample], Target[sample]);

            int now = (int) (i * 100 / (double) epochs);
            if (now > lastTime) {

                System.out.printf("%2d ... ", now);
                lastTime = now;
                if (now % 10 == 0) {
                    System.out.println(" ");
                }
            }
        }
    }

    public void feedForward(double[] Input) {
        neurons[0] = Matrix.fromArray(Input);
        for (int i = 0; i < layers - 1; i++) {
            Matrix matrix = Matrix.multiply(weights[i], neurons[i]);
            matrix.add(biases[i]);
            matrix.sigmoid();
            neurons[i + 1] = matrix;
        }
    }

    public void propagateBack(double[] Target) {
        Matrix target = Matrix.fromArray(Target);
        Matrix error = Matrix.subtract(target, neurons[layers - 1]);

        for (int i = layers - 1; i > 0; i--) {
            if (i < layers - 1) {
                Matrix transWeights = Matrix.transpose(weights[i]);
                error = Matrix.multiply(transWeights, error);
            }
            Matrix gradient = neurons[i].dSigmoid();
            gradient.multiply(error);
            gradient.multiply(learningRate);

            Matrix transNeurons = Matrix.transpose(neurons[i - 1]);
            Matrix weightsDelta = Matrix.multiply(gradient, transNeurons);

            weights[i - 1].add(weightsDelta);
            biases[i - 1].add(gradient);

        }
    }

    private void train(double[] Input, double[] Target) {
        feedForward(Input);
        propagateBack(Target);
    }
}
