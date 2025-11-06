import java.io.*;
import java.util.*;

public class kMeans {
    static class Point {
        double salary;
        double exp;
        int cluster;

        Point(double salary, double exp) {
            this.salary = salary;
            this.exp = exp;
        }

        public String toString() {
            return "(Salary=" + salary + ", Exp=" + exp + ")";
        }
    }

    public static void main(String[] args) {
        String file = "kMeans.csv";
        List<Point> points = new ArrayList<>();

        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String salaryText = parts[2].trim().toLowerCase();
                double salaryValue = 0;

                
                if (salaryText.equals("low")) salaryValue = 10000;
                else if (salaryText.equals("medium")) salaryValue = 32000;
                else if (salaryText.equals("high")) salaryValue = 55000;

                double expValue = Double.parseDouble(parts[3].trim());
                points.add(new Point(salaryValue, expValue));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        int k = 2; 
        Random rand = new Random();

        
        List<Point> centroids = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int randomIndex = rand.nextInt(points.size());
            centroids.add(new Point(points.get(randomIndex).salary, points.get(randomIndex).exp));
        }

        System.out.println("Initial Centroids:");
        for (int i = 0; i < k; i++) {
            System.out.println("Centroid " + i + ": " + centroids.get(i));
        }

        boolean changed = true;
        int iterations = 0;

        
        while (changed && iterations < 10) {
            changed = false;
            iterations++;

            
            for (Point p : points) {
                double minDist = Double.MAX_VALUE;
                int closestCluster = -1;
                for (int i = 0; i < k; i++) {
                    double dist = Math.sqrt(Math.pow(p.salary - centroids.get(i).salary, 2)
                            + Math.pow(p.exp - centroids.get(i).exp, 2));
                    if (dist < minDist) {
                        minDist = dist;
                        closestCluster = i;
                    }
                }
                if (p.cluster != closestCluster) {
                    p.cluster = closestCluster;
                    changed = true;
                }
            }

            
            System.out.println("\nIteration " + iterations + " - Cluster Assignments:");
            for (int i = 0; i < k; i++) {
                System.out.print("Cluster " + i + ": ");
                for (Point p : points) {
                    if (p.cluster == i)
                        System.out.print(p + " ");
                }
                System.out.println();
            }

            
            for (int i = 0; i < k; i++) {
                double sumSalary = 0;
                double sumExp = 0;
                int count = 0;

                for (Point p : points) {
                    if (p.cluster == i) {
                        sumSalary += p.salary;
                        sumExp += p.exp;
                        count++;
                    }
                }
                if (count > 0) {
                    centroids.get(i).salary = sumSalary / count;
                    centroids.get(i).exp = sumExp / count;
                }
            }

            System.out.println("\nUpdated Centroids:");
            for (int i = 0; i < k; i++) {
                System.out.println("Centroid " + i + ": " + centroids.get(i));
            }
        }

        
        System.out.println("\n=== Final Cluster Results ===");
        for (int i = 0; i < k; i++) {
            System.out.println("Cluster " + i + ":");
            for (Point p : points) {
                if (p.cluster == i)
                    System.out.println(p);
            }
            System.out.println();
        }
    }
}
