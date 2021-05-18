package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
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
    		
    		//Prise en compte du temps ou de la distance
    		if(data.getMode().equals(AbstractInputData.Mode.LENGTH)) { //calcul du cout enfonction de la longueur
    			labels[node.getId()]=new LabelStar(node,node.getPoint().distanceTo(destination.getPoint()));
    	        //cf classe point pour avoir le cout (distance a vol d'oiseau !
    			
    		}else { //on calcul le cout en fonction du temps
    			//t=d/v;
    			//on prend la vitesse max du graphe pour minimiser le temps 
    			labels[node.getId()]=new LabelStar(node,node.getPoint().distanceTo(destination.getPoint())/(graph.getGraphInformation().getMaximumSpeed()*1000/3600));
    		}
    		
    		
    		
        	
    	}
    }

}

//
