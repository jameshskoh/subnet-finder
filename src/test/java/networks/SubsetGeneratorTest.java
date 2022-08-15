package networks;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SubsetGeneratorTest {
    @Test
    void truncate_shouldReturnReducedSets() {
        Map<Integer, Set<Integer>> neighborSets = new HashMap<>();
        neighborSets.put(0, Stream.of(2, 3).collect(Collectors.toSet()));
        neighborSets.put(1, Stream.of(3, 4).collect(Collectors.toSet()));
        neighborSets.put(2, Stream.of(0, 3).collect(Collectors.toSet()));
        neighborSets.put(3, Stream.of(0, 3, 4).collect(Collectors.toSet()));
        neighborSets.put(4, Stream.of(2, 4).collect(Collectors.toSet()));

        Map<Integer, Set<Integer>> newNeighborSets = new HashMap<>();
        newNeighborSets.put(0, Stream.of(1).collect(Collectors.toSet()));
        newNeighborSets.put(1, Stream.of(0).collect(Collectors.toSet()));

        Map<Integer, Set<Integer>> myNewNeighborSets = SubsetGenerator.truncate(neighborSets, 3);

        assertEquals(newNeighborSets.size(), myNewNeighborSets.size());

        for (int node : newNeighborSets.keySet()) {
            assertEquals(newNeighborSets.get(node).size(), myNewNeighborSets.get(node).size());

            for (int neighbor : newNeighborSets.get(node)) {
                assertTrue(myNewNeighborSets.get(node).contains(neighbor));
            }
        }
    }
}
