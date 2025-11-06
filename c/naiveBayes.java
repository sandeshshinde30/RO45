import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class naiveBayes {
    public static void main(String[] args) {
        String file = "naiveBayes.csv";
        ArrayList<String[]> data = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine(); 
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int total = data.size();
        int yesCount = 0;
        int noCount = 0;

        for (String[] row : data) {
            if (row[4].equalsIgnoreCase("Yes"))
                yesCount++;
            else
                noCount++;
        }

        double pYes = (double) yesCount / total;
        double pNo = (double) noCount / total;

        
        String outlook = "Sunny";
        String temp = "Cool";
        String humidity = "Normal";
        String wind = "Weak";

        double pOutlookYes = probability(data, 0, outlook, "Yes");
        double pTempYes = probability(data, 1, temp, "Yes");
        double pHumidityYes = probability(data, 2, humidity, "Yes");
        double pWindYes = probability(data, 3, wind, "Yes");

        double pOutlookNo = probability(data, 0, outlook, "No");
        double pTempNo = probability(data, 1, temp, "No");
        double pHumidityNo = probability(data, 2, humidity, "No");
        double pWindNo = probability(data, 3, wind, "No");

        double yesProb = pYes * pOutlookYes * pTempYes * pHumidityYes * pWindYes;
        double noProb = pNo * pOutlookNo * pTempNo * pHumidityNo * pWindNo;

        System.out.println("P(Yes) = " + yesProb);
        System.out.println("P(No)  = " + noProb);

        if (yesProb > noProb)
            System.out.println("Predicted: PlayTennis = Yes");
        else
            System.out.println("Predicted: PlayTennis = No");
    }

    public static double probability(ArrayList<String[]> data, int col, String value, String label) {
        int count = 0;
        int labelCount = 0;
        for (String[] row : data) {
            if (row[4].equalsIgnoreCase(label)) {
                labelCount++;
                if (row[col].equalsIgnoreCase(value))
                    count++;
            }
        }
        if (labelCount == 0)
            return 0;
        return (double) count / labelCount;
    }
}
