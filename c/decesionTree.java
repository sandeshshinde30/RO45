import java.io.*;
import java.util.*;

public class decesionTree {
    static class Data {
        Map<String, String> features = new HashMap<>();
        String label;
    }

    public static void main(String[] args) {
        String file = "decesionTree.csv";
        List<Data> dataset = readCSV(file);
        Set<String> features = dataset.get(0).features.keySet();

        System.out.println("=== Decision Tree Calculation ===");
        double baseEntropy = entropy(dataset);
        double baseGini = gini(dataset);

        System.out.println("Base Entropy: " + String.format("%.4f", baseEntropy));
        System.out.println("Base Gini: " + String.format("%.4f", baseGini));
        System.out.println();

        for (String feature : features) {
            double gain = infoGain(dataset, feature, baseEntropy);
            double giniGain = giniGain(dataset, feature, baseGini);
            System.out.printf("Feature: %-12s | Info Gain: %.4f | Gini Gain: %.4f\n", feature, gain, giniGain);
        }
    }

    
    public static List<Data> readCSV(String file) {
        List<Data> dataset = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String headerLine = br.readLine();
            String[] headers = headerLine.split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Data d = new Data();
                for (int i = 0; i < parts.length - 1; i++) {
                    d.features.put(headers[i].trim(), parts[i].trim());
                }
                d.label = parts[parts.length - 1].trim();
                dataset.add(d);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    
    public static double entropy(List<Data> data) {
        Map<String, Integer> labelCount = new HashMap<>();
        for (Data d : data)
            labelCount.put(d.label, labelCount.getOrDefault(d.label, 0) + 1);

        double entropy = 0.0;
        int total = data.size();
        for (int count : labelCount.values()) {
            double p = (double) count / total;
            entropy -= p * (Math.log(p) / Math.log(2));
        }
        return entropy;
    }

    
    public static double gini(List<Data> data) {
        Map<String, Integer> labelCount = new HashMap<>();
        for (Data d : data)
            labelCount.put(d.label, labelCount.getOrDefault(d.label, 0) + 1);

        double gini = 1.0;
        int total = data.size();
        for (int count : labelCount.values()) {
            double p = (double) count / total;
            gini -= p * p;
        }
        return gini;
    }

    
    public static double infoGain(List<Data> dataset, String feature, double baseEntropy) {
        Map<String, List<Data>> subsets = new HashMap<>();
        for (Data d : dataset) {
            String key = d.features.get(feature);
            subsets.computeIfAbsent(key, k -> new ArrayList<>()).add(d);
        }

        double weightedEntropy = 0.0;
        for (List<Data> subset : subsets.values()) {
            double p = (double) subset.size() / dataset.size();
            weightedEntropy += p * entropy(subset);
        }

        return baseEntropy - weightedEntropy;
    }

    
    public static double giniGain(List<Data> dataset, String feature, double baseGini) {
        Map<String, List<Data>> subsets = new HashMap<>();
        for (Data d : dataset) {
            String key = d.features.get(feature);
            subsets.computeIfAbsent(key, k -> new ArrayList<>()).add(d);
        }

        double weightedGini = 0.0;
        for (List<Data> subset : subsets.values()) {
            double p = (double) subset.size() / dataset.size();
            weightedGini += p * gini(subset);
        }

        return baseGini - weightedGini;
    }
}
