import java.io.*;
import java.util.*;

public class correlation {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        br.readLine(); // skip header

        List<Double> age = new ArrayList<>();
        List<Double> salary = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            age.add(Double.parseDouble(parts[0].trim()));
            salary.add(Double.parseDouble(parts[1].replace("k","").trim()));
        }
        br.close();

        int n = age.size();
        double meanAge = age.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        double meanSalary = salary.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

        double numerator = 0, denomAge = 0, denomSalary = 0;
        for (int i = 0; i < n; i++) {
            double da = age.get(i) - meanAge;
            double ds = salary.get(i) - meanSalary;
            numerator += da * ds;
            denomAge += da * da;
            denomSalary += ds * ds;
        }

        double correlation = numerator / Math.sqrt(denomAge * denomSalary);
        System.out.printf("Correlation between Age and Salary: %.4f\n", correlation);
    }
}
