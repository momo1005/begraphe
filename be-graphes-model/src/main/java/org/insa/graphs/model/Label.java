package org.insa.graphs.model;

//on a besoin d'implementer comparable car on met un compareto
//On met un compare to pour pouvoir utiliser binaryHeap
//on fait ça sur les label car dans node le compare tout est sur les id = inutile
//compareto sur les cout ici !
public class Label implements Comparable<Label> {
	//sommet associé à ce label
	private int sommet_courant;
	
	//node associé
	private Node node;
	
	//booléen, vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme.
	private boolean marqued;
	
	//valeur courante du plus court chemin depuis l'origine vers le sommet.
	private double cost;
	
	//correspond au sommet précédent sur le chemin correspondant au plus court chemin courant.
	private Arc father;
	
	//constructeur
	public Label(Node node) {
		this.node=node;
        this.sommet_courant=node.getId();
        this.marqued=false;
        this.cost=Double.POSITIVE_INFINITY;
        this.father=null;
    }
	
	public Node getNode() {
        return this.node;
    }
	
	public double getCost() {
        return this.cost;
    }
	
	public double getCost_total() {
        return this.cost;
    }
	
	public int getSommet_Courant() {
        return this.sommet_courant;
    }
	
	public boolean getMarqued() {
        return this.marqued;
    }
	
	public Arc getFather() {
        return this.father;
    }
	
	public void setCost(double cout) {
        this.cost=cout;
    }
	
	public void setSommet_courant(int sommet) {
        this.sommet_courant=sommet;
    }
	
	public void setMarqued(boolean marque) {
        this.marqued=marque;
    }
	
	public void setFather(Arc pere) {
        this.father=pere;
    }
	
	//pas de cout estimer avec dijstra normal
	public double getCost_Estimated() {
        return 0;
    }
	
	/**
     * Compare the cost of this label with the cost of the given label.
     * 
     * @param other lable to compare this label with.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
	//-1 si other plus grand
	//0 si égaux
	//1 si le notre (this) est plus grand
	//ATTENTION BESOIN DE DETAILLER COMPARE TO CAR 
	//En cas d'égalité, on considèrera en premier le sommet ayant le plus petit coût estimé à la destination.
    public int compareTo(Label other) {
    	if(this.getCost_total()<other.getCost_total()) {
    		return -1;
    	} else if (this.getCost_total()>other.getCost_total()) {
    		return 1;
    	} else { //egalité on regarde le sommet ayant le plus petit cout estimed
    		if(this.getCost_Estimated()==other.getCost_Estimated()) {
    			//soit on a deux label soit les cout estimé des labels star sont égaux => egalité
    			return 0;
    		} else if (this.getCost_Estimated()<other.getCost_Estimated()) {
    			return -1;
    		} else {
    			return 1;
    		}
    	}
    }
	
}
