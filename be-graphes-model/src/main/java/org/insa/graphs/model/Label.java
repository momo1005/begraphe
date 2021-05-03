package org.insa.graphs.model;

public class Label {
	//sommet associé à ce label
	private int sommet_courant;
	
	//booléen, vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme.
	private boolean marqued;
	
	//valeur courante du plus court chemin depuis l'origine vers le sommet.
	private int cost;
	
	//correspond au sommet précédent sur le chemin correspondant au plus court chemin courant.
	private Arc father;
	
	//constructeur
	public Label(int sommet,boolean marque,int cout,Arc pere) {
        this.sommet_courant=sommet;
        this.marqued=marque;
        this.cost=cout;
        this.father=pere;
    }
	
	public int getCost() {
        return this.cost;
    }
}