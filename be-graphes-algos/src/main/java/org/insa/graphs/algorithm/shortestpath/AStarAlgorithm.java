package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;


public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    Node destination = data.getDestination();
    
    //Redefinition de init
    protected void init(Graph graph,Label[] labels) {
    	for (Node node: graph.getNodes()) {
        	labels[node.getId()]=new LabelStar(node,node.getPoint().distanceTo(destination.getPoint()));
        //cf classe point pour avoir le cout a vol d'oiseau !
    	}
    }

}
