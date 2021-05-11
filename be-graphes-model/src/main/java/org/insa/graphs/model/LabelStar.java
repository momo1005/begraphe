package org.insa.graphs.model;

public class LabelStar extends Label {
	private double cost_estimated;
	private double cost_total; //on creer pas de variable on va juste faire le calcul de la somme

	public LabelStar(Node node) {
		super(node);
		//this.cost_total = this.cost_estimated+this.getCost();
		// TODO Auto-generated constructor stub
	}
	
	public void setCost_Estimated(double cout) {
        this.cost_estimated=cout;
    }
	
	public double getCost_Estimated() {
        return this.cost_estimated;
    }

	public double getCost_total() {
		return cost_total;
	}

	public void setCost_total(double cost_total) {
		this.cost_total = cost_total;
	}

}
