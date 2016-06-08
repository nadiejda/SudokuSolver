import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TestPosition {
	public static void main (String[] argv ){
		// initialiser une grille
		int[][] grille = initGrille();
		// initialiser la recherche
		HashMap<Position, Set<Integer>> absentsParPosition = new HashMap<Position,Set<Integer>>();
		HashMap<Position, Set<Integer>> absentsParPosition2 = new HashMap<Position,Set<Integer>>();
		Position p = new Position(3,3);
		Position p1 = new Position(2,2);
		Position p2 = new Position(3,3);
		Position p3 = new Position(2,2);
		Set<Integer> absents = new HashSet<Integer>();
		Set<Integer> absents2 = new HashSet<Integer>();
		absents.add(2);
		absents.add(3);
		absents2.add(3);
		absents2.add(2);
		absentsParPosition.put(p1,absents);
		absentsParPosition.put(p, absents);
		absentsParPosition2.put(p2, absents2);
		absentsParPosition2.put(p3, absents2);
		System.out.println(absentsParPosition.get(new Position(3,3)));
		System.out.println(absentsParPosition.get(p));
		System.out.println(new Position(3,3).equals(new Position(3,3)));
		System.out.println(absentsParPosition.equals(absentsParPosition2));
		
	}
	
	private static int[][] initGrille(){
		return new int [9][9];
		//TODO Remplir la grille avec des valeurs
		// TODO Afficher la grille
	}	
	
	
}
