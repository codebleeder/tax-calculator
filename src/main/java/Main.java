import factory.Factory;
import factory.ProductWithQuantity;
import factory.ReceiptPrinter;
import models.InputData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            String inputFilePath = args[0];
            String outputFilePath = args[1];

            run(inputFilePath, outputFilePath);

        } else {
            throw new IOException("Please provide two file path arguments");
        }

        //System.exit(0);
    }

    public static void run(String inputFilePath, String outputFilePath){
        BufferedReader bufferedReader = null;

        try {
            if (!outputFilePath.endsWith(".txt")) {
                throw new IOException("output file path does not contain .txt extension");
            }
            PrintStream printStream = new PrintStream(new File(outputFilePath));
            System.setOut(printStream);

            bufferedReader = new BufferedReader(new FileReader(inputFilePath));
            String curLine;
            List<ProductWithQuantity> inventory = new ArrayList<>();

            while ((curLine = bufferedReader.readLine()) != null) {
                InputData inputData = parseInputData(curLine);
                inventory.add(
                        new ProductWithQuantity(
                                Factory.createProductAndApplyTaxes(inputData.getName(), inputData.getBaseCost()),
                                inputData.getQuantity()
                                ));
            }
            ReceiptPrinter printer = new ReceiptPrinter(inventory);
            printer.generateReceipt();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static InputData parseInputData(String curLine) {
        String regex = "(?<quantity>\\d+) (?<name>.+) at (?<baseCost>\\d+(\\.\\d+)?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(curLine);
        if (matcher.find()){
            double baseCost = Double.parseDouble(matcher.group("baseCost"));
            String name = matcher.group("name").trim();
            long quantity = Long.parseLong(matcher.group("quantity"));
            return new InputData(quantity, name, baseCost);
        }

        return null;
    }

}
