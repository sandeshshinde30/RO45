import java.io.*;
import java.util.*;

public class linearRegression {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        br.readLine(); // skip header

        List<Double> exp = new ArrayList<>();
        List<Double> salary = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            
            // Salary: use numeric if available, else map low/medium/high
            double s;
            try {
                s = Double.parseDouble(parts[2].trim());
            } catch (Exception e) {
                s = mapSalary(parts[3].trim());
            }
            
            // Experience is numeric (column 4 if salary numeric, else column 5)
            double e = Double.parseDouble(parts.length > 5 ? parts[4].trim() : parts[4].trim());
            
            salary.add(s);
            exp.add(e);
        }
        br.close();

        int n = exp.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        for (int i = 0; i < n; i++) {
            sumX += exp.get(i);
            sumY += salary.get(i);
            sumXY += exp.get(i) * salary.get(i);
            sumX2 += exp.get(i) * exp.get(i);
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        System.out.printf("Linear Regression Equation: Salary = %.2f + %.2f * Experience\n", intercept, slope);

        double testExp = 2;
        double predicted = intercept + slope * testExp;
        System.out.printf("Predicted salary for experience %.0f years = ₹%.2f\n", testExp, predicted);
    }

    static double mapSalary(String s) {
        switch (s.toLowerCase()) {
            case "low": return 15000;
            case "medium": return 32000;
            case "high": return 55000;
            default: return 0;
        }
    }
}
