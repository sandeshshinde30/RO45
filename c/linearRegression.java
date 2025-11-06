import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class linearRegression {
    public static void main(String[] args) {
        String line = "";
        String file = "linearRegression.csv";

        double[] x = new double[100];
        double[] y = new double[100];
        int n = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                x[n] = Double.parseDouble(data[0]);
                y[n] = Double.parseDouble(data[1]);
                n++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        for (int i = 0; i < n; i++) {
            sumX = sumX + x[i];
            sumY = sumY + y[i];
            sumXY = sumXY + (x[i] * y[i]);
            sumX2 = sumX2 + (x[i] * x[i]);
        }

        double meanX = sumX / n;
        double meanY = sumY / n;

        double numerator = 0;
        double denominator = 0;

        for (int i = 0; i < n; i++) {
            numerator = numerator + ((x[i] - meanX) * (y[i] - meanY));
            denominator = denominator + ((x[i] - meanX) * (x[i] - meanX));
        }

        double slope = numerator / denominator;
        double intercept = meanY - (slope * meanX);

        System.out.println("Linear Regression Equation: y = " + slope + "x + " + intercept);

        double predictX = 9; 
        double predictY = (slope * predictX) + intercept;

        System.out.println("Predicted Marks for " + predictX + " Study Hours = " + predictY);
    }
}
