import java.io.*;
import java.util.*;

public class apriori {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("......................... Apriori Algorithm .........................\n");
        String fileName = "input.csv"; // CSV file name
        System.out.print("Enter minimum support (%): ");
        int minSupportPercent = sc.nextInt();
        System.out.print("Enter minimum confidence (%): ");
        int minConfidence = sc.nextInt();

        // Read transactions
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<Set<String>> transactions = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] items = line.split(",");
            Set<String> t = new HashSet<>();
            for (String item : items) t.add(item.trim());
            transactions.add(t);
        }
        br.close();

        int minSupportCount = (int) Math.ceil(transactions.size() * minSupportPercent / 100.0);

        Map<Set<String>, Integer> allFrequentItemsets = new LinkedHashMap<>();
        Map<Set<String>, Integer> currentFrequent = new LinkedHashMap<>();

        // Generate 1-itemsets
        Map<String, Integer> itemCount = new HashMap<>();
        for (Set<String> t : transactions) {
            for (String item : t) itemCount.put(item, itemCount.getOrDefault(item, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : itemCount.entrySet()) {
            if (entry.getValue() >= minSupportCount) {
                Set<String> s = new HashSet<>();
                s.add(entry.getKey());
                currentFrequent.put(s, entry.getValue());
            }
        }
        allFrequentItemsets.putAll(currentFrequent);
        int k = 2;

        // Generate k-itemsets
        while (!currentFrequent.isEmpty()) {
            Map<Set<String>, Integer> candidates = new LinkedHashMap<>();
            List<Set<String>> prev = new ArrayList<>(currentFrequent.keySet());

            for (int i = 0; i < prev.size(); i++) {
                for (int j = i + 1; j < prev.size(); j++) {
                    Set<String> union = new HashSet<>(prev.get(i));
                    union.addAll(prev.get(j));
                    if (union.size() == k) {
                        int count = 0;
                        for (Set<String> t : transactions)
                            if (t.containsAll(union)) count++;
                        if (count >= minSupportCount) candidates.put(union, count);
                    }
                }
            }

            if (!candidates.isEmpty()) allFrequentItemsets.putAll(candidates);
            currentFrequent = candidates;
            k++;
        }

        // Print frequent itemsets
        System.out.println("\n..... Frequent Itemsets (min support " + minSupportPercent + "%) .....\n");
        for (Map.Entry<Set<String>, Integer> entry : allFrequentItemsets.entrySet()) {
            double support = entry.getValue() * 100.0 / transactions.size();
            System.out.printf("%-30s %d\t%.2f%%\n", entry.getKey(), entry.getValue(), support);
        }

        // Generate association rules
        System.out.println("\n........... Generating Association Rules (Confidence >= " + minConfidence + "%) ...........\n");
        for (Set<String> itemset : allFrequentItemsets.keySet()) {
            if (itemset.size() < 2) continue;
            List<Set<String>> subsets = getSubsets(itemset);
            int itemsetCount = allFrequentItemsets.get(itemset);
            for (Set<String> subset : subsets) {
                if (subset.isEmpty() || subset.size() == itemset.size()) continue;
                Set<String> remaining = new HashSet<>(itemset);
                remaining.removeAll(subset);
                if (remaining.isEmpty()) continue;
                int subsetCount = allFrequentItemsets.getOrDefault(subset, 0);
                double confidence = subsetCount == 0 ? 0 : (itemsetCount * 100.0 / subsetCount);
                System.out.printf("Rule: %s --> %s\nConfidence = %.2f%% --> %s\n\n", subset, remaining, confidence,
                        confidence >= minConfidence ? "Valid" : "Invalid");
            }
        }
    }

   // Generate all subsets of a set using recursion
static List<Set<String>> getSubsets(Set<String> original) {
    List<Set<String>> subsets = new ArrayList<>();
    List<String> list = new ArrayList<>(original);
    generateSubsets(list, 0, new HashSet<>(), subsets);
    return subsets;
}

// Recursive helper method
static void generateSubsets(List<String> list, int index, Set<String> current, List<Set<String>> subsets) {
    if (index == list.size()) {
        subsets.add(new HashSet<>(current));
        return;
    }
    // Include the current element
    current.add(list.get(index));
    generateSubsets(list, index + 1, current, subsets);

    // Exclude the current element
    current.remove(list.get(index));
    generateSubsets(list, index + 1, current, subsets);
}
}
