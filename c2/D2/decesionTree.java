import java.io.*;
import java.util.*;

public class decesionTree {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        String line = br.readLine(); 
        String[] header = line.split(",");
        List<String[]> data = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            data.add(line.split(","));
        }
        br.close();

        
        int targetIndex = header.length - 1;

        double totalEntropy = entropy(data, targetIndex);
        System.out.println("=== Decision Tree Metrics ===");
        System.out.println("Entropy of Dataset: " + String.format("%.4f", totalEntropy));
        System.out.println();

        for (int i = 0; i < targetIndex; i++) {
            double infoGain = totalEntropy - conditionalEntropy(data, i, targetIndex);
            double gini = giniIndex(data, i, targetIndex);

            System.out.println("Attribute: " + header[i]);
            System.out.println("  → Information Gain: " + String.format("%.4f", infoGain));
            System.out.println("  → Gini Index: " + String.format("%.4f", gini));
            System.out.println();
        }
    }

    static double entropy(List<String[]> data, int targetIndex) {
        Map<String, Integer> freq = new HashMap<>();
        for (String[] row : data) {
            String value = row[targetIndex].trim();
            freq.put(value, freq.getOrDefault(value, 0) + 1);
        }
        double entropy = 0.0;
        int total = data.size();
        for (int count : freq.values()) {
            double p = (double) count / total;
            entropy -= p * (Math.log(p) / Math.log(2));
        }
        return entropy;
    }

 
    static double conditionalEntropy(List<String[]> data, int attrIndex, int targetIndex) {
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            String key = row[attrIndex].trim();
            subsets.computeIfAbsent(key, k -> new ArrayList<>()).add(row);
        }

        double condEntropy = 0.0;
        int total = data.size();
        for (List<String[]> subset : subsets.values()) {
            double weight = (double) subset.size() / total;
            condEntropy += weight * entropy(subset, targetIndex);
        }
        return condEntropy;
    }


    static double giniIndex(List<String[]> data, int attrIndex, int targetIndex) {
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            String key = row[attrIndex].trim();
            subsets.computeIfAbsent(key, k -> new ArrayList<>()).add(row);
        }

        double gini = 0.0;
        int total = data.size();

        for (List<String[]> subset : subsets.values()) {
            Map<String, Integer> freq = new HashMap<>();
            for (String[] row : subset) {
                String label = row[targetIndex].trim();
                freq.put(label, freq.getOrDefault(label, 0) + 1);
            }

            double size = subset.size();
            double giniSubset = 1.0;
            for (int count : freq.values()) {
                double p = count / size;
                giniSubset -= p * p;
            }
            gini += (size / total) * giniSubset;
        }

        return gini;
    }
}
