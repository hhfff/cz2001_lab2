import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;

public class RandomGraph {
    public static Graph getRandomGraph(int randomDegree,int numberOfNode){
        Graph graph = new SingleGraph("Random");
        Generator gen = new RandomGenerator(randomDegree);
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<numberOfNode; i++)
            gen.nextEvents();
        gen.end();
        return graph;
    }

}
