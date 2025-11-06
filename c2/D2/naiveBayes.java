import java.io.*;
import java.util.*;

public class naiveBayes {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        String line = br.readLine(); 
        List<String[]> data = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] row = line.split(",");
            
            row[0] = categorizeTemp(Double.parseDouble(row[0]));
            row[1] = categorizeHumidity(Double.parseDouble(row[1]));
            data.add(row);
        }
        br.close();

      
        int yesCount = 0, noCount = 0;
        for (String[] row : data) {
            if (row[4].equalsIgnoreCase("yes")) yesCount++;
            else noCount++;
        }
        int total = data.size();

      
        Map<String, Integer> yesMap = new HashMap<>();
        Map<String, Integer> noMap = new HashMap<>();

        for (String[] row : data) {
            String label = row[4];
            for (int i = 0; i < 4; i++) {
                String key = i + "_" + row[i];
                if (label.equalsIgnoreCase("yes"))
                    yesMap.put(key, yesMap.getOrDefault(key, 0) + 1);
                else
                    noMap.put(key, noMap.getOrDefault(key, 0) + 1);
            }
        }

        
        double testTemp = 70.0;
        double testHumidity = 65.0;
        String[] test = {
            categorizeTemp(testTemp),
            categorizeHumidity(testHumidity),
            "rainy",
            "false"
        };

        double pYes = (double) yesCount / total;
        double pNo = (double) noCount / total;

        for (int i = 0; i < 4; i++) {
            String key = i + "_" + test[i];
            pYes *= (yesMap.getOrDefault(key, 0) + 1.0) / (yesCount + 1.0);
            pNo  *= (noMap.getOrDefault(key, 0) + 1.0) / (noCount + 1.0);
        }

        String result = (pYes > pNo) ? "yes" : "no";
        System.out.println("P(Yes) = " + pYes);
        System.out.println("P(No)  = " + pNo);
        System.out.println("Predicted Play = " + result.toUpperCase());
    }

    static String categorizeTemp(double temp) {
        if (temp < 65) return "Low";
        else if (temp <= 80) return "Medium";
        else return "High";
    }

    static String categorizeHumidity(double hum) {
        if (hum < 60) return "Low";
        else if (hum <= 75) return "Medium";
        else return "High";
    }
}
