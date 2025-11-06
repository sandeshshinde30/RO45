import java.io.*;
import java.util.*;

public class kMeans {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        String header = br.readLine(); // skip header

        List<double[]> data = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            double salary = Double.parseDouble(parts[3].trim());
            double exp = Double.parseDouble(parts[4].trim());
            data.add(new double[]{salary, exp});
        }
        br.close();

        int k = 2;
        List<double[]> centroids = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < k; i++) centroids.add(data.get(r.nextInt(data.size())));

        boolean changed;
        int[] labels = new int[data.size()];
        do {
            changed = false;
            for (int i = 0; i < data.size(); i++) {
                double minDist = Double.MAX_VALUE;
                int cluster = 0;
                for (int j = 0; j < k; j++) {
                    double d = distance(data.get(i), centroids.get(j));
                    if (d < minDist) { minDist = d; cluster = j; }
                }
                if (labels[i] != cluster) { labels[i] = cluster; changed = true; }
            }

            List<double[]> newCentroids = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                double[] mean = new double[2];
                int count = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (labels[i] == j) {
                        mean[0] += data.get(i)[0];
                        mean[1] += data.get(i)[1];
                        count++;
                    }
                }
                if (count > 0) {
                    mean[0] /= count;
                    mean[1] /= count;
                } else {
                    mean = data.get(r.nextInt(data.size()));
                }
                newCentroids.add(mean);
            }
            centroids = newCentroids;
        } while (changed);

        System.out.println("=== Final Clusters ===");
        for (int i = 0; i < data.size(); i++)
            System.out.println(Arrays.toString(data.get(i)) + " => Cluster " + labels[i]);
    }

    static double distance(double[] a, double[] b) {
        double s = 0;
        for (int i = 0; i < a.length; i++)
            s += Math.pow(a[i] - b[i], 2);
        return Math.sqrt(s);
    }
}
