import java.io.*;
import java.util.*;

public class correlation {
    public static void main(String[] args) {
        String file = "correlation.csv";
        List<Double> salaryList = new ArrayList<>();
        List<Double> expList = new ArrayList<>();

        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String salaryText = parts[2].trim().toLowerCase();
                double salaryValue = 0;

                if (salaryText.equals("low"))
                    salaryValue = 10000;
                else if (salaryText.equals("medium"))
                    salaryValue = 32000;
                else if (salaryText.equals("high"))
                    salaryValue = 55000;

                double expValue = Double.parseDouble(parts[3].trim());

                salaryList.add(salaryValue);
                expList.add(expValue);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int n = salaryList.size();

      
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;

        for (int i = 0; i < n; i++) {
            double x = salaryList.get(i);
            double y = expList.get(i);

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
            sumY2 += y * y;
        }

      
        double numerator = (n * sumXY) - (sumX * sumY);
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        double r = numerator / denominator;

     
        System.out.println("=== Correlation Calculation ===");
        System.out.println("Number of Records (n): " + n);
        System.out.println("ΣX  (Sum of Salary): " + sumX);
        System.out.println("ΣY  (Sum of Exp): " + sumY);
        System.out.println("ΣXY (Sum of Salary*Exp): " + sumXY);
        System.out.println("ΣX² (Sum of Salary²): " + sumX2);
        System.out.println("ΣY² (Sum of Exp²): " + sumY2);
        System.out.println();
        System.out.println("Numerator: " + numerator);
        System.out.println("Denominator: " + denominator);
        System.out.println("-----------------------------------");
        System.out.printf("Pearson Correlation (r): %.4f\n", r);

      
        if (r > 0.7)
            System.out.println("Strong Positive Correlation ");
        else if (r > 0.3)
            System.out.println("Moderate Positive Correlation ");
        else if (r > 0)
            System.out.println("Weak Positive Correlation ");
        else if (r < 0)
            System.out.println("Negative Correlation ");
        else
            System.out.println("No Correlation ");
    }
}
