package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;


public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    LabelStar[] labels = new LabelStar[graph.size()];
    Node destination = data.getDestination();
    
    //Redefinition de setlabels
    private void setlabels(Graph graph,LabelStar[] labels) {
    	for (Node node: graph.getNodes()) {
        	labels[node.getId()]=new LabelStar(node,node.getPoint().distanceTo(destination.getPoint())); //je sais pas ou on trouve le cout a vol d'oiseau
        //cf classe point pour avoir le cout a vol d'oiseau !
    	}
    }

}
