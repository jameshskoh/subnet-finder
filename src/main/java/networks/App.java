package networks;

import graph.Graph;
import graph.GraphLoader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        Graph g = new Graph();

        String inputFileName = "data/facebook_ucsd.edge";
        String outputFileName = "data/ucsd_6000_main.edge";

        int truncateSize = 6000;

        try {
            GraphLoader.loadGraph(g, inputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Map<Integer, Set<Integer>> neighborSets = g.exportGraph();

        Map<Integer, Set<Integer>> newNeighborSets = SubsetGenerator.truncate(neighborSets, truncateSize);

        List<Set<Integer>> subnets = SubnetFinder.findSubnets(newNeighborSets);
        SubnetFinder.sortSubnetByDecreasingSize(subnets);

        for (int i = 0; i < subnets.size(); i++) {
            String msg = String.format("Set:\t%d Size:\t%d", i, subnets.get(i).size());
            System.out.println(msg);
        }

        System.out.println("Picking the first subset");

        Map<Integer, Set<Integer>> subsetNeighborSets = SubnetFinder.findSubnetNeighborSets(
                newNeighborSets, subnets, 0);

        Map<Integer, Set<Integer>> relabeledSubsetNeighborSets = NodeRelabeler.relabel(subsetNeighborSets);

        EdgeWriter.write(relabeledSubsetNeighborSets, outputFileName, 0);
    }
}
