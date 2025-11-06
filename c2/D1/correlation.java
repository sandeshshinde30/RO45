import java.io.*;
import java.util.*;

public class correlation {
    public static void main(String[] args) {
        String fileName = "input.csv"; 
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    firstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                rows.add(values);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        
        int[] numericCols = {2, 3, 4, 5, 6, 7, 8, 9}; 
        

        int n = rows.size();
        int m = numericCols.length;

        double[][] data = new double[n][m];

        for (int i = 0; i < n; i++) {
            String[] row = rows.get(i);
            for (int j = 0; j < m; j++) {
                int colIndex = numericCols[j];
                String val = row[colIndex].trim();

               
                if (val.equalsIgnoreCase("PASS")) {
                    data[i][j] = 1;
                } else if (val.equalsIgnoreCase("FAIL")) {
                    data[i][j] = 0;
                } else {
                    try {
                        data[i][j] = Double.parseDouble(val);
                    } catch (NumberFormatException e) {
                        data[i][j] = 0;
                    }
                }
            }
        }

      
        String[] colNames = {"Course1", "Course2", "Course3", "Course4", "Course5", "Course6", "Total", "Result"};

      
        System.out.println("\n=== Correlation Matrix ===");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                double corr = correlation(data, i, j);
                System.out.printf("%8.3f ", corr);
            }
            System.out.println(" <-- " + colNames[i]);
        }

       
        System.out.println("\n=== Correlation with Result ===");
        int resultCol = m - 1;
        for (int i = 0; i < m - 1; i++) {
            double corr = correlation(data, i, resultCol);
            System.out.printf("%s vs Result: %.3f\n", colNames[i], corr);
        }
    }

   
    private static double correlation(double[][] data, int col1, int col2) {
        int n = data.length;
        double mean1 = 0, mean2 = 0;

        for (int i = 0; i < n; i++) {
            mean1 += data[i][col1];
            mean2 += data[i][col2];
        }
        mean1 /= n;
        mean2 /= n;

        double num = 0, den1 = 0, den2 = 0;
        for (int i = 0; i < n; i++) {
            double x = data[i][col1] - mean1;
            double y = data[i][col2] - mean2;
            num += x * y;
            den1 += x * x;
            den2 += y * y;
        }

        double denom = Math.sqrt(den1 * den2);
        if (denom == 0) return 0;
        return num / denom;
    }
}
