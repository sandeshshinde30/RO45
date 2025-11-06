import java.io.*;
import java.util.*;

public class apriori {
    public static void main(String[] args) {
        String file = "apriori.csv";
        List<List<String>> transactions = new ArrayList<>();
        int minSupport = 2; 

       
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                List<String> items = new ArrayList<>();
                for (String p : parts) {
                    if (!p.trim().isEmpty())
                        items.add(p.trim().toLowerCase());
                }
                transactions.add(items);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

      
        Set<String> allItems = new HashSet<>();
        for (List<String> t : transactions) {
            allItems.addAll(t);
        }

        System.out.println("=== L1 Frequent Itemsets ===");
        Map<Set<String>, Integer> freq1 = new HashMap<>();

        for (String item : allItems) {
            int count = 0;
            for (List<String> t : transactions) {
                if (t.contains(item))
                    count++;
            }
            if (count >= minSupport) {
                Set<String> s = new HashSet<>();
                s.add(item);
                freq1.put(s, count);
                System.out.println(s + " : " + count);
            }
        }

    
        System.out.println("\n=== L2 Frequent Itemsets ===");
        List<Set<String>> l1 = new ArrayList<>(freq1.keySet());
        Map<Set<String>, Integer> freq2 = new HashMap<>();

        for (int i = 0; i < l1.size(); i++) {
            for (int j = i + 1; j < l1.size(); j++) {
                Set<String> combined = new HashSet<>();
                combined.addAll(l1.get(i));
                combined.addAll(l1.get(j));

                if (combined.size() == 2) {
                    int count = 0;
                    for (List<String> t : transactions) {
                        if (t.containsAll(combined))
                            count++;
                    }
                    if (count >= minSupport) {
                        freq2.put(combined, count);
                        System.out.println(combined + " : " + count);
                    }
                }
            }
        }


        System.out.println("\n=== L3 Frequent Itemsets ===");
        List<Set<String>> l2 = new ArrayList<>(freq2.keySet());

        for (int i = 0; i < l2.size(); i++) {
            for (int j = i + 1; j < l2.size(); j++) {
                Set<String> combined = new HashSet<>();
                combined.addAll(l2.get(i));
                combined.addAll(l2.get(j));

                if (combined.size() == 3) {
                    int count = 0;
                    for (List<String> t : transactions) {
                        if (t.containsAll(combined))
                            count++;
                    }
                    if (count >= minSupport) {
                        System.out.println(combined + " : " + count);
                    }
                }
            }
        }

        
        System.out.println("\n=== Association Rules (from L2) ===");
        for (Set<String> itemset : freq2.keySet()) {
            List<String> items = new ArrayList<>(itemset);
            if (items.size() == 2) {
                String A = items.get(0);
                String B = items.get(1);
                int ABcount = freq2.get(itemset);
                int Acount = 0, Bcount = 0;

                for (List<String> t : transactions) {
                    if (t.contains(A)) Acount++;
                    if (t.contains(B)) Bcount++;
                }

                double confAB = (double) ABcount / Acount;
                double confBA = (double) ABcount / Bcount;

                System.out.printf("Rule: %s -> %s  (Conf: %.2f)\n", A, B, confAB);
                System.out.printf("Rule: %s -> %s  (Conf: %.2f)\n", B, A, confBA);
            }
        }
    }
}
