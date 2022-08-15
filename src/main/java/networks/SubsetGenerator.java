package networks;

import graph.Graph;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class SubsetGenerator {
    public static Map<Integer, Set<Integer>> truncate(
            Map<Integer, Set<Integer>> neighborSets, int size) {
        Map<Integer, Set<Integer>> newNeighborSets = new HashMap<>();

        boolean[][] edgeExists = new boolean[size][size];
        boolean[] nodeExists = new boolean[size];

        for (int node : neighborSets.keySet()) {
            for (int neighbor : neighborSets.get(node)) {
                if (node < size && neighbor < size) {
                    nodeExists[node] = true;
                    nodeExists[neighbor] = true;
                    edgeExists[node][neighbor] = true;
                    edgeExists[neighbor][node] = true;
                }
            }
        }

        Map<Integer, Integer> remap = new HashMap<>();
        int newIndex = 0;
        for (int oldIndex = 0; oldIndex < size; oldIndex++) {
            if (nodeExists[oldIndex]) {
                newNeighborSets.put(newIndex, new HashSet<>());
                remap.put(oldIndex, newIndex);
                newIndex++;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (edgeExists[i][j]) {
                    int ii = remap.get(i);
                    int jj = remap.get(j);

                    Set<Integer> newNeighborSet1 = newNeighborSets.get(ii);
                    newNeighborSet1.add(jj);

                    Set<Integer> newNeighborSet2 = newNeighborSets.get(jj);
                    newNeighborSet2.add(ii);
                }
            }
        }

        return newNeighborSets;
    }
}
