import java.io.*;
import java.util.*;

public class hierarchical {
    static class Point {
        String name;
        double salary;
        double exp;

        Point(String name, double salary, double exp) {
            this.name = name;
            this.salary = salary;
            this.exp = exp;
        }

        @Override
        public String toString() {
            return name + "(S:" + salary + ",E:" + exp + ")";
        }
    }

    public static void main(String[] args) {
        String file = "hierarchical.csv";
        List<Point> points = readCSV(file);

        System.out.println("=== HIERARCHICAL CLUSTERING (AGGLOMERATIVE) ===");
        System.out.println("Total Points: " + points.size());
        System.out.println();

        
        List<List<Point>> clusters = new ArrayList<>();
        for (Point p : points) {
            List<Point> cluster = new ArrayList<>();
            cluster.add(p);
            clusters.add(cluster);
        }

        
        while (clusters.size() > 1) {
            double minDist = Double.MAX_VALUE;
            int c1 = -1, c2 = -1;

            
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    double d = clusterDistance(clusters.get(i), clusters.get(j));
                    if (d < minDist) {
                        minDist = d;
                        c1 = i;
                        c2 = j;
                    }
                }
            }

            
            List<Point> merged = new ArrayList<>(clusters.get(c1));
            merged.addAll(clusters.get(c2));

            System.out.println("Merging Cluster " + (c1 + 1) + " and Cluster " + (c2 + 1)
                    + " (Distance = " + String.format("%.2f", minDist) + ")");
            System.out.print("Resulting Cluster: ");
            for (Point p : merged)
                System.out.print(p.name + " ");
            System.out.println("\n");

            clusters.remove(c2); 
            clusters.remove(c1);
            clusters.add(merged);
        }

        System.out.println("=== FINAL CLUSTER ===");
        for (Point p : clusters.get(0)) {
            System.out.println(p);
        }
    }


    static List<Point> readCSV(String file) {
        List<Point> points = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                double salary = Double.parseDouble(parts[2].trim());
                double exp = Double.parseDouble(parts[3].trim());
                points.add(new Point(name, salary, exp));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }


    static double distance(Point p1, Point p2) {
        double d1 = p1.salary - p2.salary;
        double d2 = p1.exp - p2.exp;
        return Math.sqrt(d1 * d1 + d2 * d2);
    }

    
    static double clusterDistance(List<Point> c1, List<Point> c2) {
        double min = Double.MAX_VALUE;
        for (Point p1 : c1) {
            for (Point p2 : c2) {
                double d = distance(p1, p2);
                if (d < min)
                    min = d;
            }
        }
        return min;
    }
}
