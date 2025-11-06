import java.io.*;
import java.util.*;

public class naiveBayes {
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

        // Only count class labels: yes/no
        Map<String, Integer> classCount = new HashMap<>();
        for (String[] row : data) {
            String label = row[4].trim(); // deposit column
            classCount.put(label, classCount.getOrDefault(label, 0) + 1);
        }

        // Count of attribute values per class (ignore unique accno)
        Map<String, Map<String, Integer>> attrCount = new HashMap<>();
        for (String[] row : data) {
            String label = row[4].trim();
            // Use only relevant categorical attributes: Cust, bankname, location
            for (int i = 0; i < row.length; i++) {
                if (i == 1) continue; // skip accno
                if (i == 4) continue; // skip class itself
                String key = i + "_" + row[i].trim() + "_" + label;
                attrCount.putIfAbsent(key, new HashMap<>());
                attrCount.get(key).put(label, attrCount.get(key).getOrDefault(label, 0) + 1);
            }
        }

        // Test input
        String[] test = {"male", "sbi", "mumbai"}; // Cust, bankname, location
        Map<String, Double> probs = new HashMap<>();
        int total = data.size();

        for (String label : classCount.keySet()) {
            double p = (double) classCount.get(label) / total; // prior
            for (int i = 0; i < test.length; i++) {
                int colIndex = (i == 0) ? 0 : (i == 1 ? 2 : 3); // map test array to correct CSV index
                String key = colIndex + "_" + test[i] + "_" + label;
                double count = attrCount.containsKey(key) ? attrCount.get(key).get(label) : 1; // Laplace smoothing
                p *= count / classCount.get(label);
            }
            probs.put(label, p);
        }

        String predicted = probs.get("yes") > probs.get("no") ? "yes" : "no";
        System.out.println("Predicted deposit: " + predicted);
        System.out.println("Probabilities: " + probs);
    }
}
