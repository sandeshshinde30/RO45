import java.io.*;
import java.util.*;

public class linearRegression {
    public static void main(String[] args) {
        String file = "input.csv";
        List<Double> X = new ArrayList<>(); 
        List<Double> Y = new ArrayList<>(); 

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    double x = Double.parseDouble(parts[3]); 
                    double y = Double.parseDouble(parts[8]); 
                    X.add(x);
                    Y.add(y);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int n = X.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += X.get(i);
            sumY += Y.get(i);
            sumXY += X.get(i) * Y.get(i);
            sumX2 += X.get(i) * X.get(i);
        }

        
        double b = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double a = (sumY - b * sumX) / n;

        System.out.println("=== Linear Regression Model ===");
        System.out.println("Equation: Y = " + String.format("%.2f", a) + " + " + String.format("%.2f", b) + "X");

        
        double newX = 65;
        double predictedY = a + b * newX;
        System.out.println("\nPredicted Total Marks for Course1 = " + newX + " → " + String.format("%.2f", predictedY));

        
        System.out.println("\n--- Intermediate Calculations ---");
        System.out.println("SumX = " + sumX);
        System.out.println("SumY = " + sumY);
        System.out.println("SumXY = " + sumXY);
        System.out.println("SumX^2 = " + sumX2);
        System.out.println("n = " + n);
    }
}
