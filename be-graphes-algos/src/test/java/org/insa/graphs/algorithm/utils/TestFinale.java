package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.shortestpath.*;

import org.insa.graphs.model.*;
import org.insa.graphs.model.io.*;
import org.junit.Assume;
import org.junit.Test;

import org.insa.graphs.model.io.BinaryGraphReader; 
import org.insa.graphs.model.io.GraphReader; 
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.PathReader;

import java.util.Random;

public class TestFinale {
	
	private static Graph carre;
	private static Graph hautegaronne;
	private static Graph guyane;
	
	//Lien des cartes sur mon ordi
	final static String linkcarre = "C:\\Users\\morga\\Documents\\GitHub\\begraphe\\maps_tf\\carre.mapgr";
	final static String linkhautegaronne = "C:\\Users\\morga\\Documents\\GitHub\\begraphe\\maps_tf\\haute-garonne.mapgr";
	final static String linkguyane = "C:\\Users\\morga\\Documents\\GitHub\\begraphe\\maps_tf\\guyane.mapgr";
	
	static List<Node> nodescarre;
	static List<Node> nodeshg;
	static List<Node> nodesguyane;
	
	//Option "all road" "only cars" etc...
	static List<ArcInspector> Filtres = ArcInspectorFactory.getAllFilters() ;
	

    
    //UN=Un noeud
    //D,B,A = Dijktra, Bellman,Astar
    //T,L=Time lenght
    //I=Infaisable
	//R=Random , RC= Random Cars, C = Carre
	//AB, AC , BC = Inegalité triangualaire
	
    public static ShortestPathSolution UNDT,UNDL,UNBT,UNBL,UNAT,UNAL;
    public static ShortestPathSolution IDT,IDL,IBT,IBL,IAT,IAL;
	public static ShortestPathSolution RDT,RDL,RBT,RBL,RAT,RAL, RCDT,RCDL,RCBT,RCBL,RCAT,RCAL, CDT,CDL,CBT,CBL,CAT,CAL;
	public static ShortestPathSolution ABDL,BCDL,ACDL2,ABAL,BCAL,ACAL2,ABDT,BCDT,ACDT2,ABAT,BCAT,ACAT2;
	
	static Path time_unnoeud_hg, lenght_unnoeud_hg;
	
	public static void init() throws IOException {
		//Reader
		try {
			final GraphReader readercarre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(linkcarre))));
			final GraphReader readerhg = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(linkhautegaronne))));
			final GraphReader readerguyane = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(linkguyane))));

			carre = readercarre.read();
			hautegaronne = readerhg.read();
			guyane = readerguyane.read();
			
		} catch (Exception e) {
			System.out.println("[error][lecture des graphes]");
		}
		
	    //on recuperer les nodes des graphes
		nodescarre = carre.getNodes();
	    nodeshg = hautegaronne.getNodes();
	    nodesguyane = guyane.getNodes();
	    
	    //
	    // 			UN SEUL NOEUD
	    //		ORIGINE = DESTINATION
	    //
	    
	    //constructeur
	    Random random = new Random();
	    
	    //nextInt = Returns a pseudorandom, uniformly distributed int value between 0 (inclusive)
	    //and the specified value (exclusive), drawn from this random number generator's sequence.  
	    Node toutseulhg = nodeshg.get(random.nextInt(nodeshg.size()));
	    ShortestPathData time_unnoeud_hg = new ShortestPathData(hautegaronne, toutseulhg, toutseulhg, Filtres.get(2));
		ShortestPathData lenght_unnoeud_hg = new ShortestPathData(hautegaronne, toutseulhg, toutseulhg, Filtres.get(0));
	    
	    DijkstraAlgorithm AUNDT = new DijkstraAlgorithm(time_unnoeud_hg);
	    DijkstraAlgorithm AUNDL = new DijkstraAlgorithm(lenght_unnoeud_hg);
	    BellmanFordAlgorithm AUNBT = new BellmanFordAlgorithm(time_unnoeud_hg);
	    BellmanFordAlgorithm AUNBL = new BellmanFordAlgorithm(lenght_unnoeud_hg);
	    AStarAlgorithm AUNAT = new AStarAlgorithm(time_unnoeud_hg);
	    AStarAlgorithm AUNAL = new AStarAlgorithm(lenght_unnoeud_hg);

	    
        UNDT = AUNDT.doRun();
        UNDL = AUNDL.doRun();
        UNBT = AUNBT.doRun();
        UNBL = AUNBL.doRun();
        UNAT = AUNAT.doRun();
        UNAL = AUNAL.doRun();


        
        //
        // 		INFAISAIBLE
        //
        
        //Carte de la Guyanne
        Node X1=nodesguyane.get(11846);
        Node X2=nodesguyane.get(5618);
        
        ShortestPathData time_infaisable = new ShortestPathData(guyane, X1, X2, Filtres.get(2));
    	ShortestPathData lenght_infaisable = new ShortestPathData(guyane, X1, X2, Filtres.get(0));
        

        DijkstraAlgorithm AIDT = new DijkstraAlgorithm(time_infaisable);
        DijkstraAlgorithm AIDL = new DijkstraAlgorithm(lenght_infaisable);
        BellmanFordAlgorithm AIBT = new BellmanFordAlgorithm(time_infaisable);
        BellmanFordAlgorithm AIBL = new BellmanFordAlgorithm(lenght_infaisable);
        AStarAlgorithm AIAT = new AStarAlgorithm(time_infaisable);
        AStarAlgorithm AIAL = new AStarAlgorithm(lenght_infaisable);

        IDT = AIDT.doRun();
        IDL = AIDL.doRun();
        IBT = AIBT.doRun();
        IBL = AIBL.doRun();
        IAT = AIAT.doRun();
        IAL = AIAL.doRun();
        
        //
        // 		RANDOM
        //

        Random random1 = new Random();
        Random random2 = new Random();
        
		Node Y1=nodeshg.get(random1.nextInt(nodeshg.size()));
        Node Y2=nodeshg.get(random2.nextInt(nodeshg.size()));
        
        ShortestPathData time_random = new ShortestPathData(hautegaronne, Y1, Y2, Filtres.get(2));
    	ShortestPathData lenght_random = new ShortestPathData(hautegaronne, Y1, Y2, Filtres.get(0));
    	ShortestPathData time_random_car = new ShortestPathData(hautegaronne, Y1, Y2, Filtres.get(3));
    	ShortestPathData lenght_random_car = new ShortestPathData(hautegaronne, Y1, Y2, Filtres.get(1));
    	
	    DijkstraAlgorithm ARDT = new DijkstraAlgorithm(time_random);
	    DijkstraAlgorithm ARDL = new DijkstraAlgorithm(lenght_random);
	    BellmanFordAlgorithm ARBT = new BellmanFordAlgorithm(time_random);
	    BellmanFordAlgorithm ARBL = new BellmanFordAlgorithm(lenght_random);
	    AStarAlgorithm ARAT = new AStarAlgorithm(time_random);
	    AStarAlgorithm ARAL = new AStarAlgorithm(lenght_random);
	    
	    DijkstraAlgorithm ARCDT = new DijkstraAlgorithm(time_random_car);
	    DijkstraAlgorithm ARCDL = new DijkstraAlgorithm(lenght_random_car);
	    BellmanFordAlgorithm ARCBT = new BellmanFordAlgorithm(time_random_car);
	    BellmanFordAlgorithm ARCBL = new BellmanFordAlgorithm(lenght_random_car);
	    AStarAlgorithm ARCAT = new AStarAlgorithm(time_random_car);
	    AStarAlgorithm ARCAL = new AStarAlgorithm(lenght_random_car);
    	
	    RDT = ARDT.doRun();
        RDL = ARDL.doRun();
        RBT = ARBT.doRun();
        RBL = ARBL.doRun();
        RAT = ARAT.doRun();
        RAL = ARAL.doRun();
        
        RCDT = ARCDT.doRun();
        RCDL = ARCDL.doRun();
        RCBT = ARCBT.doRun();
        RCBL = ARCBL.doRun();
        RCAT = ARCAT.doRun();
        RCAL = ARCAL.doRun();
        
        //Carte carre - Meme test
        
        Node Z1=nodescarre.get(random1.nextInt(nodescarre.size()));
        Node Z2=nodescarre.get(random2.nextInt(nodescarre.size()));
        
        ShortestPathData time_random_carre = new ShortestPathData(carre, Z1, Z2, Filtres.get(2));
    	ShortestPathData lenght_random_carre = new ShortestPathData(carre, Z1, Z2, Filtres.get(0));

    	
	    DijkstraAlgorithm ACDT = new DijkstraAlgorithm(time_random_carre);
	    DijkstraAlgorithm ACDL = new DijkstraAlgorithm(lenght_random_carre);
	    BellmanFordAlgorithm ACBT = new BellmanFordAlgorithm(time_random_carre);
	    BellmanFordAlgorithm ACBL = new BellmanFordAlgorithm(lenght_random_carre);
	    AStarAlgorithm ACAT = new AStarAlgorithm(time_random_carre);
	    AStarAlgorithm ACAL = new AStarAlgorithm(lenght_random_carre);
    	
	    CDT = ACDT.doRun();
        CDL = ACDL.doRun();
        CBT = ACBT.doRun();
        CBL = ACBL.doRun();
        CAT = ACAT.doRun();
        CAL = ACAL.doRun();
        
        //
        // 		INEGALITE TRIANGULAIRE
        //        
        Random randomA = new Random();
        Random randomB = new Random();
        Random randomC = new Random();
        
        Node A=nodeshg.get(randomA.nextInt(nodeshg.size()));
        Node B=nodeshg.get(randomB.nextInt(nodeshg.size()));
        Node C=nodeshg.get(randomC.nextInt(nodeshg.size()));
        
        ShortestPathData time_triangle_AB = new ShortestPathData(hautegaronne, A, B, Filtres.get(2));
    	ShortestPathData time_triangle_BC = new ShortestPathData(hautegaronne, B, C, Filtres.get(2));
    	ShortestPathData time_triangle_AC = new ShortestPathData(hautegaronne, A, C, Filtres.get(2));

    	ShortestPathData lenght_triangle_AB = new ShortestPathData(hautegaronne, A, B, Filtres.get(0));
    	ShortestPathData lenght_triangle_BC = new ShortestPathData(hautegaronne, B, C, Filtres.get(0));
    	ShortestPathData lenght_triangle_AC = new ShortestPathData(hautegaronne, A, C, Filtres.get(0));

    	
    	DijkstraAlgorithm AABDL = new DijkstraAlgorithm(lenght_triangle_AB);
	    DijkstraAlgorithm ABCDL = new DijkstraAlgorithm(lenght_triangle_BC);
	    DijkstraAlgorithm AACDL = new DijkstraAlgorithm(lenght_triangle_AC);
	    AStarAlgorithm AABAL = new AStarAlgorithm(lenght_triangle_AB);
	    AStarAlgorithm ABCAL = new AStarAlgorithm(lenght_triangle_BC);
	    AStarAlgorithm AACAL = new AStarAlgorithm(lenght_triangle_AC);
	    
	    DijkstraAlgorithm AABDT = new DijkstraAlgorithm(time_triangle_AB);
	    DijkstraAlgorithm ABCDT = new DijkstraAlgorithm(time_triangle_BC);
	    DijkstraAlgorithm AACDT = new DijkstraAlgorithm(time_triangle_AC);
	    AStarAlgorithm AABAT = new AStarAlgorithm(time_triangle_AB);
	    AStarAlgorithm ABCAT = new AStarAlgorithm(time_triangle_BC);
	    AStarAlgorithm AACAT = new AStarAlgorithm(time_triangle_AC);
	    
	    ABDL=AABDL.doRun();
	    BCDL= ABCDL.doRun();
	    ACDL2= AACDL.doRun();
	    ABAL= AABAL.doRun();
	    BCAL= ABCAL.doRun();
	    ACAL2= AACAL.doRun();
	    
	    ABDT=AABDT.doRun();
	    BCDT= ABCDT.doRun();
	    ACDT2= AACDT.doRun();
	    ABAT= AABAT.doRun();
	    BCAT= ABCAT.doRun();
	    ACAT2= AACAT.doRun();
	    
	}
	
    //Test Pour un neoud seul
	@Test
	public void TestUnSeulNoeud() throws IOException {
		init(); //beosin d'initialiser une seule fois 
		
		//Dijkstra et Belman
		assertEquals(UNDT.getStatus(), UNBT.getStatus() );	
		assertEquals(UNDL.getStatus(), UNBL.getStatus() );
		
		//Astar et Bellman
		assertEquals(UNAT.getStatus(), UNBT.getStatus() );	
		assertEquals(UNAL.getStatus(), UNBL.getStatus() );

	}
	
    //Test Infaisable
	@Test
	public void TestInfaisable() throws IOException {
		assertEquals(IDT.getStatus(), Status.INFEASIBLE);
		assertEquals(IDL.getStatus(), Status.INFEASIBLE);
		assertEquals(IAT.getStatus(), Status.INFEASIBLE);
		assertEquals(IAL.getStatus(), Status.INFEASIBLE);
		
	}
	
    //Test RANDOM
	@Test
	public void TestRANDOM() throws IOException {
		//Random toute route
		
		//Dijkstra et Bellman
		if((RDL.getPath()!=null)&&(RBL.getPath()!=null)) {
			assertEquals(RDL.getPath().getLength(), RBL.getPath().getLength(), 0.01 );	
		} else {
			assertEquals(RDL.getStatus(), Status.INFEASIBLE);
			assertEquals(RBL.getStatus(), Status.INFEASIBLE);
		}
		if((RDT.getPath()!=null)&&(RBT.getPath()!=null)) {
			assertEquals(RDT.getPath().getMinimumTravelTime(), RBT.getPath().getMinimumTravelTime(), 0.01);
		} else {
			assertEquals(RDT.getStatus(), Status.INFEASIBLE);
			assertEquals(RBT.getStatus(), Status.INFEASIBLE);
		}
		
		//Astar et Bellman
		
		if((RAL.getPath()!=null)&&(RBL.getPath()!=null)) {
			assertEquals(RAL.getPath().getLength(), RBL.getPath().getLength(), 0.01 );	
		} else {
			assertEquals(RAL.getStatus(), Status.INFEASIBLE);
			assertEquals(RBL.getStatus(), Status.INFEASIBLE);
		}
		if((RAT.getPath()!=null)&&(RBT.getPath()!=null)) {
			assertEquals(RAT.getPath().getMinimumTravelTime(), RBT.getPath().getMinimumTravelTime(), 0.01);
		} else {
			assertEquals(RAT.getStatus(), Status.INFEASIBLE);
			assertEquals(RBT.getStatus(), Status.INFEASIBLE);
		}
		

		//Random Route pour voiture
		//Dijkstra et Bellman
		if((RCDL.getPath()!=null)&&(RCBL.getPath()!=null)) {
			assertEquals(RCDL.getPath().getLength(), RCBL.getPath().getLength(), 0.01 );	
		} else {
			assertEquals(RCDL.getStatus(), Status.INFEASIBLE);
			assertEquals(RCBL.getStatus(), Status.INFEASIBLE);
		}
		if((RCDT.getPath()!=null)&&(RCBT.getPath()!=null)) {
			assertEquals(RCDT.getPath().getMinimumTravelTime(), RCBT.getPath().getMinimumTravelTime(), 0.01);
		} else {
			assertEquals(RCDT.getStatus(), Status.INFEASIBLE);
			assertEquals(RCBT.getStatus(), Status.INFEASIBLE);
		}
				
		//Astar et Bellman pour voiture
		if((RCAL.getPath()!=null)&&(RCBL.getPath()!=null)) {
			assertEquals(RCAL.getPath().getLength(), RCBL.getPath().getLength(), 0.01 );	
		} else {
			assertEquals(RCAL.getStatus(), Status.INFEASIBLE);
			assertEquals(RCBL.getStatus(), Status.INFEASIBLE);
		}
		if((RCAT.getPath()!=null)&&(RCBT.getPath()!=null)) {
			assertEquals(RCAT.getPath().getMinimumTravelTime(), RCBT.getPath().getMinimumTravelTime(), 0.01);
		} else {
			assertEquals(RCAT.getStatus(), Status.INFEASIBLE);
			assertEquals(RCBT.getStatus(), Status.INFEASIBLE);
		}
				
		//Random toute route - CARRE
		//Dijkstra et Bellman
		if((CDL.getPath()!=null)&&(CBL.getPath()!=null)) {
			assertEquals(CDL.getPath().getLength(), CBL.getPath().getLength(), 0.01 );	
		} else {
			assertEquals(CDL.getStatus(), Status.INFEASIBLE);
			assertEquals(CBL.getStatus(), Status.INFEASIBLE);
		}
		if((CDT.getPath()!=null)&&(CBT.getPath()!=null)) {
			assertEquals(CDT.getPath().getMinimumTravelTime(), CBT.getPath().getMinimumTravelTime(), 0.01);
		} else {
			assertEquals(CDT.getStatus(), Status.INFEASIBLE);
			assertEquals(CBT.getStatus(), Status.INFEASIBLE);
		}
				
		//Astar et Bellman
		if((CAL.getPath()!=null)&&(CBL.getPath()!=null)) {
			assertEquals(CAL.getPath().getLength(), CBL.getPath().getLength(), 0.01 );	
		} else {
			assertEquals(CAL.getStatus(), Status.INFEASIBLE);
			assertEquals(CBL.getStatus(), Status.INFEASIBLE);
		}
		if((CAT.getPath()!=null)&&(CBT.getPath()!=null)) {
			assertEquals(CAT.getPath().getMinimumTravelTime(), CBT.getPath().getMinimumTravelTime(), 0.01);
		} else {
			assertEquals(CAT.getStatus(), Status.INFEASIBLE);
			assertEquals(CBT.getStatus(), Status.INFEASIBLE);
		}
		
	}
	
	//Test Sans oracle 1 - Principe de l'Inegalité triangulaire
	// Le PCC entre A et C est forcement plus petit que le PPC entre A et B + PCC entre B et C
	//Ceci est une condition necessaire mais pas suffisante
	//En effet si AC < AB + BC cela ne garanti pas l'optimalité de AC en tant que pcc
	@Test
	public void TestSansOracle() throws IOException {
		//Inégalité triangulaire

		//Dijsktra - Length
		if((ABDL.getPath()!=null)&&(ACDL2.getPath()!=null)&&(BCDL.getPath()!=null)) {
			assertTrue(ACDL2.getPath().getLength()<=ABDL.getPath().getLength()+BCDL.getPath().getLength());
		}
		
		//Dijsktra - Time
		if((ABDT.getPath()!=null)&&(ACDT2.getPath()!=null)&&(BCDT.getPath()!=null)) {
			assertTrue(ACDT2.getPath().getLength()<=ABDT.getPath().getLength()+BCDT.getPath().getLength());
		}
			
		//ASTAR - Length
		if((ABAL.getPath()!=null)&&(ACAL2.getPath()!=null)&&(BCAL.getPath()!=null)) {
			assertTrue(ACAL2.getPath().getLength()<=ABAL.getPath().getLength()+BCAL.getPath().getLength());
		}
				
		//ASTAR - Time
		if((ABAT.getPath()!=null)&&(ACAT2.getPath()!=null)&&(BCAT.getPath()!=null)) {
			assertTrue(ACAT2.getPath().getLength()<=ABAT.getPath().getLength()+BCAT.getPath().getLength());
		}
				
	}
	
	//Autre Test sans oracle
	
	//2
	//D'après le cours nous savons que :
	//"Le sous chemin de plus court chemin sont des plus court chemin"
	//C'est une condition d'optimalité elle est nécessaire mais pas suffisante.
	//Ayant le même degré de verification (necessaire mais pas suffisante) que le test d'inegalité triangulaire je ne vais pas le coder
	
	//Conclusion
	//Sans oracle nous pouvons verifier des conditions necessaire.
	//Mais je ne trouve pas de test sur les conditions suffisante.
	//Cela ne nous permet pas de verifier que notre algortihme retourne une soltion optimale
	
	//Rappel :
	//Condition necessaire : "Q est une condition nécessaire pour avoir P si dès que P est vraie, alors nécessairement, forcément, obligatoirement Q est vraie."
	//Condition suffisante : "Q est une condition suffisante pour avoir P s'il suffit que Q soit vraie pour que P soit vraie."
		
	
}
