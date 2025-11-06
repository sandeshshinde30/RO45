import java.io.*;
import java.util.*;

public class naiveBayes {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        String line = br.readLine(); // skip header

        List<String[]> data = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            data.add(line.split(","));
        }
        br.close();

        // Count classes
        int yesCount = 0, noCount = 0;
        for (String[] row : data) {
            if (row[4].trim().equals("yes")) yesCount++;
            else noCount++;
        }

        int total = data.size();
        double priorYes = (double) yesCount / total;
        double priorNo = (double) noCount / total;

        // Test input
        String[] test = {"20-40", "high", "yes", "fair"};

        // Calculate probability for yes
        double probYes = priorYes;
        for (int i = 0; i < 4; i++) {
            int count = 0;
            for (String[] row : data) {
                if (row[4].trim().equals("yes") && row[i].trim().equals(test[i])) count++;
            }
            // Laplace smoothing
            probYes *= (count + 1.0) / (yesCount + getDistinctValues(data, i));
        }

        // Calculate probability for no
        double probNo = priorNo;
        for (int i = 0; i < 4; i++) {
            int count = 0;
            for (String[] row : data) {
                if (row[4].trim().equals("no") && row[i].trim().equals(test[i])) count++;
            }
            probNo *= (count + 1.0) / (noCount + getDistinctValues(data, i));
        }

        String predicted = probYes > probNo ? "yes" : "no";
        System.out.println("Predicted: " + predicted);
        System.out.println("Probability yes: " + probYes);
        System.out.println("Probability no: " + probNo);
    }

    static int getDistinctValues(List<String[]> data, int col) {
        Set<String> set = new HashSet<>();
        for (String[] row : data) set.add(row[col].trim());
        return set.size();
    }
}
