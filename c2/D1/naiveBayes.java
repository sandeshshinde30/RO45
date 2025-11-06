import java.io.*;
import java.util.*;

public class naiveBayes {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        br.readLine();

        List<double[]> pass = new ArrayList<>();
        List<double[]> fail = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\s*,\\s*"); // trim spaces around commas

            if (p.length < 10) continue; // skip malformed lines

            double[] marks = new double[6];
            for (int i = 0; i < 6; i++) {
                marks[i] = Double.parseDouble(p[i + 2]);
            }

            if (p[9].equalsIgnoreCase("PASS"))
                pass.add(marks);
            else
                fail.add(marks);
        }
        br.close();

        if (pass.isEmpty() || fail.isEmpty()) {
            System.out.println("Error: No PASS or FAIL records found. Check your CSV file format.");
            return;
        }

        double[] meanPass = mean(pass);
        double[] varPass = variance(pass, meanPass);
        double[] meanFail = mean(fail);
        double[] varFail = variance(fail, meanFail);

        double priorPass = (double) pass.size() / (pass.size() + fail.size());
        double priorFail = (double) fail.size() / (pass.size() + fail.size());

        System.out.println("=== Gaussian Naive Bayes Classification ===\n");

        BufferedReader testBr = new BufferedReader(new FileReader("input.csv"));
        testBr.readLine();

        while ((line = testBr.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\s*,\\s*");
            if (p.length < 10) continue;

            String name = p[1];
            double[] test = new double[6];
            for (int i = 0; i < 6; i++) {
                test[i] = Double.parseDouble(p[i + 2]);
            }

            double probPass = Math.log(priorPass);
            double probFail = Math.log(priorFail);

            for (int i = 0; i < test.length; i++) {
                probPass += Math.log(gaussian(test[i], meanPass[i], varPass[i]));
                probFail += Math.log(gaussian(test[i], meanFail[i], varFail[i]));
            }

            String predicted = (probPass > probFail) ? "PASS" : "FAIL";
            System.out.println(name + " => Predicted: " + predicted);
        }

        testBr.close();
    }

    static double[] mean(List<double[]> data) {
        int n = data.size(), m = data.get(0).length;
        double[] mean = new double[m];
        for (double[] d : data)
            for (int i = 0; i < m; i++)
                mean[i] += d[i];
        for (int i = 0; i < m; i++)
            mean[i] /= n;
        return mean;
    }

    static double[] variance(List<double[]> data, double[] mean) {
        int n = data.size(), m = data.get(0).length;
        double[] var = new double[m];
        for (double[] d : data)
            for (int i = 0; i < m; i++)
                var[i] += Math.pow(d[i] - mean[i], 2);
        for (int i = 0; i < m; i++)
            var[i] /= n;
        return var;
    }

    static double gaussian(double x, double mean, double var) {
        if (var == 0) var = 1e-6;
        return (1.0 / Math.sqrt(2 * Math.PI * var)) *
               Math.exp(-Math.pow(x - mean, 2) / (2 * var));
    }
}
