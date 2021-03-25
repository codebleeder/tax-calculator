import products.Product;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            String inputFilePath = args[0];
            String outputFilePath = args[1];

            run(inputFilePath, outputFilePath);

        } else {
            throw new IOException("Please provide two file path arguments");
        }

        System.exit(0);
    }
    private static void run(String inputFilePath, String outputFilePath){

    }

}
