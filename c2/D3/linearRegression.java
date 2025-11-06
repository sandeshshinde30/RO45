import java.io.*;
import java.util.*;

public class linearRegression {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        String line = br.readLine(); // skip header

        List<Double> X = new ArrayList<>(); // Age
        List<Double> Y = new ArrayList<>(); // Salary

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            double age = Double.parseDouble(parts[0]);
            double salary = Double.parseDouble(parts[1].replace("k", "")) * 1000;
            X.add(age);
            Y.add(salary);
        }

        int n = X.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += X.get(i);
            sumY += Y.get(i);
            sumXY += X.get(i) * Y.get(i);
            sumX2 += X.get(i) * X.get(i);
        }

        double meanX = sumX / n;
        double meanY = sumY / n;

        double b = (sumXY - n * meanX * meanY) / (sumX2 - n * meanX * meanX);
        double a = meanY - b * meanX;

        System.out.printf("Linear Regression Equation: Salary = %.2f + %.2f * Age%n", a, b);

        double testAge = 26;
        double predictedSalary = a + b * testAge;
        System.out.printf("Predicted salary for age %.0f = ₹%.2f%n", testAge, predictedSalary);
    }
}
