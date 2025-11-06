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
            data.add(line.split(","));
        }
        br.close();

        System.out.printf("Entropy of Dataset: %.4f\n\n", entropy(data));

        String[] attributes = {"Cust", "bankname", "location"};
        int[] colIndex = {0, 2, 3}; // CSV indexes of these attributes

        for (int i = 0; i < attributes.length; i++) {
            double gain = infoGain(data, colIndex[i]);
            double gini = giniIndex(data, colIndex[i]);
            System.out.printf("Attribute: %s\n  → Information Gain: %.4f\n  → Gini Index: %.4f\n\n", 
                              attributes[i], gain, gini);
        }
    }

    static double entropy(List<String[]> data) {
        Map<String, Integer> count = new HashMap<>();
        for (String[] row : data) count.put(row[4], count.getOrDefault(row[4], 0) + 1);
        double ent = 0;
        int total = data.size();
        for (int c : count.values()) {
            double p = (double)c / total;
            ent -= p * (Math.log(p)/Math.log(2));
        }
        return ent;
    }

    static double infoGain(List<String[]> data, int col) {
        double totalEntropy = entropy(data);
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            subsets.putIfAbsent(row[col], new ArrayList<>());
            subsets.get(row[col]).add(row);
        }
        double subsetEntropy = 0;
        int total = data.size();
        for (List<String[]> subset : subsets.values()) {
            subsetEntropy += ((double)subset.size()/total) * entropy(subset);
        }
        return totalEntropy - subsetEntropy;
    }

    static double giniIndex(List<String[]> data, int col) {
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            subsets.putIfAbsent(row[col], new ArrayList<>());
            subsets.get(row[col]).add(row);
        }
        int total = data.size();
        double gini = 0;
        for (List<String[]> subset : subsets.values()) {
            int subTotal = subset.size();
            Map<String, Integer> count = new HashMap<>();
            for (String[] row : subset) count.put(row[4], count.getOrDefault(row[4], 0) + 1);
            double sum = 0;
            for (int c : count.values()) {
                double p = (double)c / subTotal;
                sum += p * p;
            }
            gini += ((double)subTotal / total) * (1 - sum);
        }
        return gini;
    }
}
