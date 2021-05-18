package org.insa.graphs.model;

public class LabelStar extends Label {
	private double cost_estimated;

	public LabelStar(Node node, double cout_estime) {
		super(node);
		this.cost_estimated = cout_estime;
		// TODO Auto-generated constructor stub
	}
	
//	public void setCost_Estimated(double cout) {
//        this.cost_estimated=cout;
//    }
	
	public double getCost_Estimated() {
		return this.cost_estimated;
    }

	//On redefinit getCost_total
	public double getCost_total() {
		return super.getCost()+this.cost_estimated;
	}
	
	//on a utilisé la siouxe solution consistant a definir getTotalCost dans Label
	//L'utilisé dans compareTo
	//Et redéfinir getTotalCost dans label*
	//Du coup label* contuinue d'implementer l'interface Comparable<label> mais avec le bon coutTotale
//	public int compareTo(LabelStar other) {
//        return Double.compare(getCost_total(), other.getCost_total());
//    }
}
