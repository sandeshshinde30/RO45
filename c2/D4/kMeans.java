import java.io.*;
import java.util.*;

public class kMeans {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input.csv"));
        br.readLine(); // skip header

        List<double[]> data = new ArrayList<>();
        List<String> names = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");

            String name = parts[0];
            names.add(name);

            double salary = 0;
            String sal = parts[2].trim().toLowerCase();
            if (sal.equals("low")) salary = 12000;
            else if (sal.equals("medium")) salary = 32000;
            else if (sal.equals("high")) salary = 55000;
            else salary = Double.parseDouble(sal);

            double exp = 0;
            String expStr = parts[3].trim().toLowerCase();
            if (expStr.equals("low")) exp = 1;
            else if (expStr.equals("medium")) exp = 3;
            else if (expStr.equals("high")) exp = 5;
            else exp = Double.parseDouble(parts[3]);

            data.add(new double[]{salary, exp});
        }
        br.close();

        int k = 2;
        Random rand = new Random();
        List<double[]> centroids = new ArrayList<>();
        for (int i = 0; i < k; i++)
            centroids.add(data.get(rand.nextInt(data.size())).clone());

        Map<Integer, List<Integer>> clusters = new HashMap<>();
        boolean changed = true;
        while (changed) {
            clusters.clear();
            for (int i = 0; i < k; i++) clusters.put(i, new ArrayList<>());

            for (int idx = 0; idx < data.size(); idx++) {
                double[] point = data.get(idx);
                int closest = 0;
                double minDist = distance(point, centroids.get(0));
                for (int i = 1; i < k; i++) {
                    double dist = distance(point, centroids.get(i));
                    if (dist < minDist) {
                        minDist = dist;
                        closest = i;
                    }
                }
                clusters.get(closest).add(idx);
            }

            changed = false;
            for (int i = 0; i < k; i++) {
                if (clusters.get(i).isEmpty()) continue;
                double[] newCentroid = mean(clusters.get(i), data);
                if (!Arrays.equals(newCentroid, centroids.get(i))) {
                    centroids.set(i, newCentroid);
                    changed = true;
                }
            }
        }

        System.out.println("=== Clusters ===");
        for (int i = 0; i < k; i++) {
            System.out.println("Cluster " + i + ":");
            for (int idx : clusters.get(i)) {
                System.out.println(names.get(idx) + " -> " + Arrays.toString(data.get(idx)));
            }
        }
    }

    static double distance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) sum += Math.pow(a[i] - b[i], 2);
        return Math.sqrt(sum);
    }

    static double[] mean(List<Integer> indices, List<double[]> data) {
        int dim = data.get(0).length;
        double[] m = new double[dim];
        for (int idx : indices) {
            double[] p = data.get(idx);
            for (int i = 0; i < dim; i++) m[i] += p[i];
        }
        for (int i = 0; i < dim; i++) m[i] /= indices.size();
        return m;
    }
}
