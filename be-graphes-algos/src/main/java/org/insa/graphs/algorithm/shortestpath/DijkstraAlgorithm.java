package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
//import org.insa.graphs.algorithm.AbstractInputData;
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
    
    final ShortestPathData data = getInputData();


    @Override
	public ShortestPathSolution doRun() {        
        //Argument car necessaire a la fonction setlabels et doRun 
        Graph graph = data.getGraph();
        //Pas de soucis avec labelStar car label star herite de label
        //donc on peut mettre des label star dans un tableau de label
        Label[] labels = new Label[graph.size()];
        
        //Nombre de noeud dans le graph
        //final int nbNodes = graph.size();
    	this.init(graph,labels);

        //On créer et associe les labels à chaque noeud
    	//dans label on a la node
    	//et sommet_courant = id de la node = indice du tableau
    	//les label sont rangé selon leurs id -> a l'indice de l'id boom on a le label associé
    	//id du node < nombre de node (normalemenrt c'est forcément vrai)
    	
        //On met a jour pour l'origine - Le sommet est marqué avec un cout de 0
        labels[data.getOrigin().getId()].setMarqued(true);
        labels[data.getOrigin().getId()].setCost(0);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());

       
        //On creer un tas binaire sur les labels/etiquettes du code
        //Car on va trier en fonction du min
        BinaryHeap<Label> tas_binaire = new BinaryHeap<Label>();
        tas_binaire.insert(labels[data.getOrigin().getId()]);
        
        
        Label x;
        while((labels[data.getDestination().getId()].getMarqued()!=true)&&(!tas_binaire.isEmpty())) {
        	//On enleve le min du tas
        	//x=tas_binaire.findMin();
        	//tas_binaire.remove(x);
        	//ne marche plus je sais pas pourquoi :
            x=tas_binaire.deleteMin();
        	
            //On met a jour marqued de x à true
            x.setMarqued(true);
            //Notify node has been marqued
            notifyNodeMarked(x.getNode());
            
            //System.out.println("Cout du noeud n° "+x.getSommet_Courant() +" qui viens d'etre marqué ="+x.getCost()+" cout estimer = "+x.getCost_Estimated()+ " et cout totale = "+x.getCost_total());
            
            //on regarde les successeur du noeud associé au label x
            //Attention ici on a des arcs le succeseur ça va etre successeur[i].getDestination()
            List<Arc> sucesseurs = x.getNode().getSuccessors(); 
            int nb_successeur =0;
            for (Arc arc: sucesseurs) {
            	nb_successeur=nb_successeur+1;
            	if(data.isAllowed(arc) ) {
            		
            		int indice = arc.getDestination().getId(); //id du node succeseur
            		
                	//si le succeseur n'est pas marqué
                	if (!labels[indice].getMarqued()) {
                		double old_cost=labels[indice].getCost();
                		
                		//PAS UTILE
//                		if(data.getMode().equals(AbstractInputData.Mode.LENGTH)) { //calcul du cout enfonction de la longueur
//                			new_cost=labels[x.getSommet_Courant()].getCost()+arc.getLength();
//                		}else { //on calcul le cout en fonction du temps
//                			new_cost=labels[x.getSommet_Courant()].getCost()+arc.getMinimumTravelTime();
//                		}
                		
                		//getCost(arc) sait si on est en mode temps ou length et prend le cout qui correspond.
                		double new_cost=labels[x.getSommet_Courant()].getCost()+data.getCost(arc);
                		
                		double maj_cost=Double.min(old_cost,new_cost); //nouvelle valeur de cost
                		
                		//notify if node reached for the first time
                		if (Double.isInfinite(old_cost) && Double.isFinite(new_cost)) {
                            notifyNodeReached(arc.getDestination());
                        }
                		
                		if(old_cost != maj_cost) {
                			//ATTENTION :
                			//old_cost=infini -> il etait pas dans le tas : pas de soucis
                			//old-cost!=infini alors il etait deja dans le tas 
                			//Dans ce cas la on utilise remove pour ne pas le mettre 2 fois !
                			//On remove l'ancienne valeur et on insert la nouvelle(=label mis a jour)
                			if(old_cost!=Double.POSITIVE_INFINITY) { //"UPDATE" p90
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
            	}
            }
        	//System.out.println("Nombre de sucesseur explorés = "+nb_successeur +" et nombre de successeur du node = "+x.getNode().getNumberOfSuccessors());
        	System.out.println("tas valide ?"+tas_binaire.isValid());
        	
//            if(labels[data.getDestination().getId()].getMarqued()==true) {
//            	System.out.println("Destination trouvé");
//            }
         }
        
        ShortestPathSolution solution = null;
        
     // Destination has no predecessor, the solution is infeasible...
        if (labels[data.getDestination().getId()].getFather() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels[data.getDestination().getId()].getFather();
            while (arc != null) {
                arcs.add(arc);
                arc = labels[arc.getOrigin().getId()].getFather();
            }

            // Reverse the path...
            Collections.reverse(arcs);
            
            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL,new Path(graph,arcs));
        }

        return solution;
    }
    
    //INITIALISATION DES LABELS

    protected void init(Graph graph,Label[] labels) {
    	for (Node node: graph.getNodes()) {
        	labels[node.getId()]=new Label(node);
        }
    }

}
