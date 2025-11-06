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
            String[] parts = line.split(",");
            // discretize temperature and humidity
            parts[1] = discretizeTemp(Double.parseDouble(parts[1].trim()));
            parts[2] = discretizeHumidity(Double.parseDouble(parts[2].trim()));
            data.add(parts);
        }
        br.close();

        double entropyDataset = entropy(data);
        System.out.printf("Entropy of Dataset: %.4f%n%n", entropyDataset);

        String[] attributes = {"Outlook", "Temperature", "Humidity", "Windy"};

        for (int i = 0; i < 4; i++) {
            double ig = infoGain(data, i, entropyDataset);
            double gini = giniIndex(data, i);
            System.out.printf("Attribute: %s%n  → Information Gain: %.4f%n  → Gini Index: %.4f%n%n",
                    attributes[i], ig, gini);
        }
    }

    static String discretizeTemp(double temp) {
        if (temp < 70) return "Low";
        else if (temp <= 80) return "Medium";
        else return "High";
    }

    static String discretizeHumidity(double hum) {
        if (hum < 75) return "Low";
        else if (hum <= 85) return "Medium";
        else return "High";
    }

    static double entropy(List<String[]> data) {
        Map<String, Integer> count = new HashMap<>();
        for (String[] row : data) {
            String label = row[4].trim();
            count.put(label, count.getOrDefault(label, 0) + 1);
        }
        double entropy = 0;
        int total = data.size();
        for (int c : count.values()) {
            double p = (double) c / total;
            entropy -= p * Math.log(p) / Math.log(2);
        }
        return entropy;
    }

    static double infoGain(List<String[]> data, int attrIndex, double entropyDataset) {
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            String key = row[attrIndex].trim();
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(row);
        }
        double weightedEntropy = 0;
        int total = data.size();
        for (List<String[]> subset : subsets.values()) {
            weightedEntropy += ((double) subset.size() / total) * entropy(subset);
        }
        return entropyDataset - weightedEntropy;
    }

    static double giniIndex(List<String[]> data, int attrIndex) {
        Map<String, List<String[]>> subsets = new HashMap<>();
        for (String[] row : data) {
            String key = row[attrIndex].trim();
            subsets.putIfAbsent(key, new ArrayList<>());
            subsets.get(key).add(row);
        }
        int total = data.size();
        double gini = 0;
        for (List<String[]> subset : subsets.values()) {
            Map<String, Integer> count = new HashMap<>();
            for (String[] row : subset) {
                String label = row[4].trim();
                count.put(label, count.getOrDefault(label, 0) + 1);
            }
            double g = 1;
            int subsetSize = subset.size();
            for (int c : count.values()) {
                double p = (double) c / subsetSize;
                g -= p * p;
            }
            gini += ((double) subsetSize / total) * g;
        }
        return gini;
    }
}
