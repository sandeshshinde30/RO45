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
            String[] parts = line.split(",");
            // discretize temperature and humidity
            parts[1] = discretizeTemp(Double.parseDouble(parts[1].trim()));
            parts[2] = discretizeHumidity(Double.parseDouble(parts[2].trim()));
            data.add(parts);
        }
        br.close();

        // Count of each class (yes/no)
        Map<String, Integer> classCount = new HashMap<>();
        for (String[] row : data) {
            String label = row[4].trim();
            classCount.put(label, classCount.getOrDefault(label, 0) + 1);
        }

        // Count of attribute values per class
        Map<String, Map<String, Integer>> attrCount = new HashMap<>();
        for (String[] row : data) {
            String label = row[4].trim();
            for (int i = 0; i < 4; i++) { // Outlook, Temperature, Humidity, Windy
                String key = i + "_" + row[i].trim() + "_" + label;
                attrCount.putIfAbsent(key, new HashMap<>());
                attrCount.get(key).put(label, attrCount.get(key).getOrDefault(label, 0) + 1);
            }
        }

        // Example test data
        String[] test = {"sunny", "Medium", "High", "FALSE"}; // you can change this
        Map<String, Double> probs = new HashMap<>();
        int total = data.size();

        for (String label : classCount.keySet()) {
            double p = (double) classCount.get(label) / total; // prior
            for (int i = 0; i < 4; i++) {
                String key = i + "_" + test[i] + "_" + label;
                double count = attrCount.containsKey(key) ? attrCount.get(key).get(label) : 1; // Laplace smoothing
                p *= count / classCount.get(label);
            }
            probs.put(label, p);
        }

        String predicted = probs.get("yes") > probs.get("no") ? "yes" : "no";
        System.out.println("Predicted PlayTennis: " + predicted);
        System.out.println("Probabilities: " + probs);
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
}
