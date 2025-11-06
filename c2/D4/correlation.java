import java.io.*;
import java.util.*;

public class correlation {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        br.readLine(); // skip header

        List<Double> salary = new ArrayList<>();
        List<Double> exp = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            double s = mapSalary(parts[2].trim());
            double e = mapExp(parts[3].trim());
            salary.add(s);
            exp.add(e);
        }
        br.close();

        double meanS = mean(salary);
        double meanE = mean(exp);
        double sumSE = 0, sumS2 = 0, sumE2 = 0;
        for (int i = 0; i < salary.size(); i++) {
            double ds = salary.get(i) - meanS;
            double de = exp.get(i) - meanE;
            sumSE += ds * de;
            sumS2 += ds * ds;
            sumE2 += de * de;
        }

        double correlation = sumSE / Math.sqrt(sumS2 * sumE2);
        System.out.printf("Correlation between Salary and Experience: %.4f\n", correlation);
    }

    static double mean(List<Double> list) {
        double sum = 0;
        for (double v : list) sum += v;
        return sum / list.size();
    }

    static double mapSalary(String s) {
        switch (s.toLowerCase()) {
            case "low": return 15000;
            case "medium": return 32000;
            case "high": return 55000;
            default: 
                try { return Double.parseDouble(s); } 
                catch (Exception e) { return 0; }
        }
    }

    static double mapExp(String s) {
        switch (s.toLowerCase()) {
            case "low": return 2;
            case "medium": return 3;
            case "high": return 5;
            default:
                try { return Double.parseDouble(s); } 
                catch (Exception e) { return 0; }
        }
    }
}
