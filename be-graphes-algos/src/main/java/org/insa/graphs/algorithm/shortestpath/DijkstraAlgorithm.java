package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Label;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    

    @Override
    protected ShortestPathSolution doRun() {        
     // On récupere le graphe
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        
        //Nombre de noeud dans le graph
        final int nbNodes = graph.size();
        
    	//Associer un label à chaque noeud
    	//Les noeuds des graphes sont numérotés de 0 à N-1.
    	Label[] labels = new Label[nbNodes];

        //On créer et associe les labels à chaque noeud
    	//dans label on a la node
    	//et sommet_courant = id de la node = indice du tableau
    	//les label sont rangé selon leurs id -> a l'indice de l'id boom on a le label associé
    	//id du node < nombre de node (normalemenrt c'est forcément vrai)
        for (Node node: graph.getNodes()) {
        	labels[node.getId()]=new Label(node);
        }
        
        //On met a jour pour l'origine - Le sommet est marqué avec un cout de 0
        labels[data.getOrigin().getId()].setMarqued(true);
        labels[data.getOrigin().getId()].setCost(0);

        
        //On creer un tas binaire sur les labels/etiquettes du code
        //Car on va trier en fonction du min
        //or si on met les noeud on ne va pas avoir 
        BinaryHeap<Label> tas_binaire = new BinaryHeap<Label>();
        tas_binaire.insert(labels[data.getOrigin().getId()]);
        
        
        boolean pas_fini = true;
        for(Label lab : labels) {
        	if (lab.getMarqued()==false) {
        		pas_fini=false;
        	}
        }
    
        Label x;
        while(pas_fini==true) {
        	//On enleve le min du tas
            x=tas_binaire.deleteMin();
            //On met a jour marqued de x à true
            x.setMarqued(true);
            
            //on regarde les successeur du noeud associé au label x
            //Attention ici on a des arcs le succeseur ça va etre successeur[i].getDestination()
            List<Arc> sucesseurs = x.getNode().getSuccessors(); 
            
            for (Arc arc: sucesseurs) {
            	int indice = arc.getDestination().getId(); //id du node succeseur
            	//si le succeseur n'est pas marqué
            	if (!labels[indice].getMarqued()) {
            		double old_cost=labels[indice].getCost();
            		double new_cost=labels[x.getSommet_Courant()].getCost()+data.getCost(arc);
            		double maj_cost=Double.min(old_cost,new_cost); //nouvelle valeur de cost
            		if(old_cost != maj_cost) {
            			
            			//ATTENTION :
            			//old_cost=infini -> il etait pas dans le tas : pas de soucis
            			//old-cost!=infini alors il etait deja dans le tas 
            			//Dans ce cas la on utilise remove pour ne pas le mettre 2 fois !
            			//On remove l'ancienne valeur et on insert la nouvelle(=label mis a jour)
            			if(old_cost!=Double.POSITIVE_INFINITY) {
            				//deja dans le tas
            				//on le supprime maintenant avant de modifier le label car j'ai peur que le indexof() detecte plus le label sinon
            				tas_binaire.remove(labels[indice]);	
            			}
            			
            			//on met a jour le cout 
            			labels[indice].setCost(maj_cost);
            			//placer le label associé au noeud dans le tas
            	        tas_binaire.insert(labels[indice]);
            	        //on met a jour le pere
            	        labels[indice].setFather(arc); //on met des arc donc la l'arc entre x et son sucesseur
            		}
            		
            	}
            	
            	//on met a jour notre variable
            	for(Label lab : labels) {
                	if (lab.getMarqued()==false) {
                		pas_fini=false;
                	}
                }
            	
            }
            
         }
        
        
        ShortestPathSolution solution = null;
        
     
        
        
        // TODO:
        return solution;
    }

}
