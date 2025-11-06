import java.io.*;
import java.util.*;

public class decesionTree {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        br.readLine(); // skip header

        List<String[]> data = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] row = line.split(",");
            // convert Age range to midpoint
            row[0] = String.valueOf(midPoint(row[0].trim()));
            data.add(row);
        }
        br.close();

        String[] attributes = {"Age", "Income", "Studying", "CreditRate"};

        System.out.printf("Entropy of Dataset: %.4f\n\n", entropy(data));

        for (int i = 0; i < attributes.length; i++) {
            double ig = informationGain(data, i);
            double gini = giniIndex(data, i);
            System.out.printf("Attribute: %s\n  → Information Gain: %.4f\n  → Gini Index: %.4f\n\n",
                    attributes[i], ig, gini);
        }
    }

    static double midPoint(String range) {
        String[] parts = range.split("-");
        if (parts.length == 2) {
            double low = Double.parseDouble(parts[0]);
            double high = Double.parseDouble(parts[1]);
            return (low + high) / 2.0;
        } else {
            return Double.parseDouble(range); // if already numeric
        }
    }

    static double entropy(List<String[]> data) {
        int yes = 0, no = 0;
        for (String[] row : data) {
            if (row[4].trim().equals("yes")) yes++;
            else no++;
        }
        double pYes = (double) yes / data.size();
        double pNo = (double) no / data.size();
        return -(pYes > 0 ? pYes * Math.log(pYes)/Math.log(2) : 0)
               -(pNo > 0 ? pNo * Math.log(pNo)/Math.log(2) : 0);
    }

    static double informationGain(List<String[]> data, int attr) {
        double totalEntropy = entropy(data);
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            String key = row[attr].trim();
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(row);
        }
        double subsetEntropy = 0;
        for (List<String[]> subset : subsets.values()) {
            subsetEntropy += ((double) subset.size() / data.size()) * entropy(subset);
        }
        return totalEntropy - subsetEntropy;
    }

    static double giniIndex(List<String[]> data, int attr) {
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            String key = row[attr].trim();
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(row);
        }
        double gini = 0;
        for (List<String[]> subset : subsets.values()) {
            int yes = 0, no = 0;
            for (String[] row : subset) {
                if (row[4].trim().equals("yes")) yes++;
                else no++;
            }
            double pYes = (double) yes / subset.size();
            double pNo = (double) no / subset.size();
            gini += ((double) subset.size() / data.size()) * (1 - (pYes*pYes + pNo*pNo));
        }
        return gini;
    }
}
