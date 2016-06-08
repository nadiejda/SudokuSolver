import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.Map.Entry;

public class Grille {

	protected int[][] grille;
	protected HashMap<Position, Set<Integer>> absentsParPosition;
	protected Choix absentChoisiParPosition;
	
	public Grille(){
		this.grille = new int [9][9];
		this.absentsParPosition = new HashMap<Position,Set<Integer>>();
		this.absentChoisiParPosition = null;
	}
	
	public Grille(NiveauGrille niveau){
		if(niveau == NiveauGrille.DEMONIAQUE){
			this.grille = initGrilleDemoniaque2();	
		}
		else if (niveau == NiveauGrille.FACILE){
			this.grille = initGrille();
		}
		this.absentsParPosition = new HashMap<Position,Set<Integer>>();
		this.absentChoisiParPosition = null ;
	}
	
	public void setGrille(int[][] grille){
		this.grille = grille;
	}
	
	public void setAbsentsParPosition(HashMap<Position, Set<Integer>> absentsParPosition){
		this.absentsParPosition = absentsParPosition;
	}
	
	public void setAbsentChoisiParPosition(Choix choix){
		this.absentChoisiParPosition = choix;
	}
	
	@Override
	protected Grille clone() {
		Grille clone = new Grille();
		clone.setGrille(cloneGrille(this.grille));
		clone.setAbsentsParPosition(cloneHashMap(this.absentsParPosition));
		clone.setAbsentChoisiParPosition(this.absentChoisiParPosition);
		return clone;
	}
	
	private static int[][] cloneGrille(int[][] grille) {
		int[][]grilleClone = new int [9][9] ;
		for(int i=0; i <grille.length ; i++){
			for (int j= 0 ; j< grille.length ; j++){
				grilleClone[i][j]= (grille)[i][j];
			}
		}
		return grilleClone;
	}

	public static HashMap<Position, Set<Integer>> cloneHashMap(HashMap<Position,Set<Integer>> original) {
		HashMap<Position, Set<Integer>> clone = new HashMap<Position, Set<Integer>>();
		Iterator<Entry<Position, Set<Integer>>> i = original.entrySet().iterator();
		while (i.hasNext()){
			Entry<Position,Set<Integer>> e = (Entry<Position, Set<Integer>>) i.next();
			Iterator<Integer> is = e.getValue().iterator();
			Set<Integer> cloneSet= new HashSet<Integer>();
			while (is.hasNext()){
				cloneSet.add((Integer) is.next());
			}
			clone.put(e.getKey(), cloneSet);
		}
		return clone;
	}
	
	public int[][] initGrille(){
		this.grille = new int [9][9];
		// Remplir la grille avec des valeurs
		// Grille facile :
		grille[0][0] = 8;
		grille[0][7] = 9;
		grille[0][8] = 3;
		grille[1][1] = 9;
		grille[1][3] = 7;
		grille[1][4] = 6;
		grille[1][5] = 4;
		grille[1][6] = 5;
		grille[1][8] = 1;
		grille[2][3] = 9;
		grille[2][5] = 8;
		grille[3][0] = 7;
		grille[3][2] = 6;
		grille[3][3] = 3;
		grille[3][4] = 4;
		grille[3][6] = 1;
		grille[3][8] = 9;
		grille[4][2] = 9;
		grille[4][6] = 3;
		grille[5][0] = 3;
		grille[5][2] = 2;
		grille[5][4] = 5;
		grille[5][5] = 9;
		grille[5][6] = 4;
		grille[5][8] = 8;
		grille[6][3] = 8;
		grille[6][5] = 5;
		grille[7][0] = 9;
		grille[7][2] = 8;
		grille[7][3] = 4;
		grille[7][4] = 1;
		grille[7][5] = 6;
		grille[7][7] = 3;
		grille[8][0] = 5;
		grille[8][1] = 7;
		grille[8][8] = 6;
		
		return grille;
		
	}	
	
	public int[][] initGrilleDemoniaque(){
		this.grille = new int [9][9];
		// Remplir la grille avec des valeurs
		// Grille facile :
		grille[0][0] = 5;
		grille[0][3] = 9;
		grille[0][5] = 6;
		grille[0][8] = 2;
		grille[1][0] = 6;
		grille[1][1] = 8;
		grille[1][3] = 2;
		grille[1][5] = 7;
		grille[1][7] = 3;
		grille[1][8] = 4;
		grille[2][4] = 3;
		grille[3][1] = 5;
		grille[3][3] = 6;
		grille[3][4] = 9;
		grille[3][5] = 1;
		grille[3][7] = 4;
		grille[5][1] = 3;
		grille[5][3] = 7;
		grille[5][4] = 5;
		grille[5][5] = 4;
		grille[5][7] = 9;
		grille[6][4] = 6;
		grille[7][0] = 4;
		grille[7][1] = 6;
		grille[7][3] = 8;
		grille[7][5] = 3;
		grille[7][7] = 5;
		grille[7][8] = 9;
		grille[8][0] = 8;
		grille[8][3] = 4;
		grille[8][5] = 9;
		grille[8][8] = 6;
		
		return grille;
	}
	public int[][] initGrilleDemoniaque2(){
		this.grille = new int [9][9];
		// Remplir la grille avec des valeurs
		// Grille facile :
		grille[0][5] = 9;
		grille[0][7] = 2;
		grille[0][8] = 3;
		grille[1][4] = 7;
		grille[1][6] = 5;
		grille[1][8] = 9;
		grille[2][2] = 9;
		grille[2][6] = 4;
		grille[2][7] = 6;
		grille[3][3] = 4;
		grille[3][4] = 5;
		grille[3][8] = 2;
		grille[4][1] = 3;
		grille[4][3] = 9;
		grille[4][5] = 1;
		grille[4][7] = 4;
		grille[5][0] = 4;
		grille[5][4] = 3;
		grille[5][5] = 8;
		grille[6][1] = 6;
		grille[6][2] = 4;
		grille[6][6] = 3;
		grille[7][0] = 5;
		grille[7][2] = 8;
		grille[7][4] = 2;
		grille[8][0] = 1;
		grille[8][1] = 9;
		grille[8][3] = 7;
		return grille;
	}
	
	public void printGrille() {
		for (int i = 0; i<9; i++){
			System.out.println("||-|-|-||-|-|-||-|-|-||");
			if (i%3 == 0){
				System.out.println("||-|-|-||-|-|-||-|-|-||");
			}
			for (int j=0 ; j<9 ; j++){
				if (j%3 ==0){
					System.out.print("||"+this.grille[i][j]);
				}
				else {
					System.out.print("|"+this.grille[i][j]);
				}
				if (j==8){
					System.out.print("||");
				}
			}
			System.out.print("absents par position :");
			for (int j = 0; j<9 ; j++){
				try {
					System.out.print( this.absentsParPosition.get(new Position(i,j)));
				}
				catch( NullPointerException e){
				}
			}
			System.out.println("");
		}
		System.out.println("||-|-|-||-|-|-||-|-|-||");
		System.out.println("||-|-|-||-|-|-||-|-|-||");
	}

	public boolean pasDAbsentsAUnePosition() {
		// Parcourir les AbsentsParPosition
		for (Set<Integer> s : this.absentsParPosition.values()){
			// Si une liste d'absents est vide
			if(s.isEmpty()){
				return true;
			}
		}
		// Sinon
		return false;
	}

}
