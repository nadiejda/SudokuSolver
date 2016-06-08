import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class Main {
	protected List<Grille> listeGrillesChoisies;
	protected Grille grilleCourante; 
	
	public Main(){
		this.grilleCourante = new Grille(NiveauGrille.DEMONIAQUE);
		this.listeGrillesChoisies = new ArrayList<Grille>();
	}
	
	public static void main (String[] argv ){
		long dateDebut = System.currentTimeMillis();
		// initialiser le jeu
		Main jeu = new Main();
		// initialiser la recherche
		rechercheGrilleAbsents(jeu.grilleCourante);
		do{
			HashMap<Position,Set<Integer>> absentsParPosition= Grille.cloneHashMap(jeu.grilleCourante.absentsParPosition);
			// Afficher la grille
			jeu.grilleCourante.printGrille();
			remplirGrilleAvecAbsentsUniques(jeu.grilleCourante);
			remplirGrilleAvecPositionsUniques(jeu.grilleCourante);
			// Si une position ne présente pas d'absents possibles 
			if(jeu.grilleCourante.pasDAbsentsAUnePosition()){
				do{
				jeu.grilleCourante.printGrille();
				// Revenir en arrière sur un choix
				jeu.grilleCourante = jeu.listeGrillesChoisies.get(jeu.listeGrillesChoisies.size()-1).clone();
				jeu.listeGrillesChoisies.remove(jeu.listeGrillesChoisies.size()-1);
				Choix choixAInterdire = jeu.grilleCourante.absentChoisiParPosition;
				// Retirer l'absent choisi de la liste d'absentsParPosition
				jeu.grilleCourante.absentsParPosition.get(choixAInterdire.position).remove(choixAInterdire.absentChoisi);
				// Si c'était le dernier choix possible recommencer
				}
				while (jeu.grilleCourante.pasDAbsentsAUnePosition());
				// Enfin faire un nouveau choix
				choisirAbsent(jeu.grilleCourante);
				// Mémoriser la nouvelle grille et le nouveau choix
				jeu.listeGrillesChoisies.add(jeu.grilleCourante.clone());
				// Insérer la valeur choisie dans la grille
				Position positionChoix = jeu.grilleCourante.absentChoisiParPosition.position;
				jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum]=(int) jeu.grilleCourante.absentChoisiParPosition.absentChoisi;
				// Retirer l'absent des absentsParPosition et propager l'information dans la grille choisie
				jeu.grilleCourante.absentsParPosition.remove(positionChoix);
				retirerAbsentLigne(jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum],positionChoix.rownum,jeu.grilleCourante.absentsParPosition);
				retirerAbsentColonne(jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum],positionChoix.colnum,jeu.grilleCourante.absentsParPosition);
				retirerAbsentCarre(jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum],positionChoix,jeu.grilleCourante.absentsParPosition);
			}
			// Si les absentsParPosition ne changent pas
			if (jeu.grilleCourante.absentsParPosition.equals(absentsParPosition)){
				jeu.grilleCourante.printGrille();
				// faire un choix de valeur
				choisirAbsent(jeu.grilleCourante);
				// Mémoriser une copie de la grille et le choix
				jeu.listeGrillesChoisies.add(jeu.grilleCourante.clone());
				// Insérer la valeur choisie dans la grille
				Position positionChoix = jeu.grilleCourante.absentChoisiParPosition.position;
				jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum]=(int) jeu.grilleCourante.absentChoisiParPosition.absentChoisi;
				// Retirer l'absent des absentsParPosition et propager l'information dans la grille choisie
				jeu.grilleCourante.absentsParPosition.remove(positionChoix);
				retirerAbsentLigne(jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum],positionChoix.rownum,jeu.grilleCourante.absentsParPosition);
				retirerAbsentColonne(jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum],positionChoix.colnum,jeu.grilleCourante.absentsParPosition);
				retirerAbsentCarre(jeu.grilleCourante.grille[positionChoix.rownum][positionChoix.colnum],positionChoix,jeu.grilleCourante.absentsParPosition);
			}
		}
		while(!jeu.grilleCourante.absentsParPosition.isEmpty());
		
		
		// Afficher la grille
		System.out.println("Grille remplie !");
		long dateFin = System.currentTimeMillis();
		System.out.println("Durée d'exécution (ms) :"+(dateFin - dateDebut));
		jeu.grilleCourante.printGrille();
	}

	private static void choisirAbsent(Grille grille) {
		// Choisir un absent à une position
		for (int i= 0 ; i<9; i++){
			for (int j=0 ; j<9 ; j++){
				if(grille.grille[i][j]==0){
					// Mémoriser le choix 
					grille.absentChoisiParPosition= new Choix(new Position(i,j),(Integer) grille.absentsParPosition.get(new Position(i,j)).toArray()[0]);
					// Sortir de la fonction
					return ;	
				}
			}
		}
		// La grille est remplie , la grille n'est pas modifiée	
	}

	private static void rechercheGrilleAbsents(Grille grille) {
		for (int i=0 ; i<9 ; i++){
			for (int j=0 ; j<9 ; j++){
				if(grille.grille[i][j]==0){
					Position position = new Position(i,j);
					Set<Integer> absentsIJ = new HashSet<Integer>() ;
					intersectionAbsents(position,grille.grille,absentsIJ);
					grille.absentsParPosition.put(position, absentsIJ);
				}
			}
		}
	}

	private static void intersectionAbsents(Position position, int[][] grille, Set<Integer> absentsIJ) {
		// Initialiser la liste avec tous les numéros
		initAbsents(absentsIJ);
		// Retirer les numéros présents à la même ligne
		intersectionAbsentsParLigne(grille, position.rownum,absentsIJ);
		// Retirer les numéros présents à la même colonne
		intersectionAbsentsParColonne(grille, position.colnum,absentsIJ);
		// Retirer les numéros présents au même carré
		intersectionAbsentsParCarre(grille, position, absentsIJ);
	}
	
	private static void intersectionAbsentsParLigne(int[][] grille, int rownum, Set<Integer> absents) {
		// Parcourir la ligne 
		for (int colnum =0 ; colnum <9 ; colnum ++){
			// Si le numéro est différent de 0 le retirer de la liste des absents
			int valeur =grille[rownum][colnum];
			if (valeur!= 0){
				absents.remove(valeur);
			}
		}	
	}
	
	private static void intersectionAbsentsParColonne(int[][] grille, int colnum, Set<Integer> absents) {
		// Parcourir la colonne 
		for (int rownum =0 ; rownum <9 ; rownum ++){
			// Si le numéro est différent de 0 le retirer de la liste des absents
			if (grille[rownum][colnum]!= 0){
				absents.remove(grille[rownum][colnum]);
			}
		}
	}
	
	private static void intersectionAbsentsParCarre(int[][] grille, Position position, Set<Integer> absents) {
		// Parcourir le carré
		for (int i =0 ; i<3 ; i++){
			for (int j=0 ; j<3 ; j++){
				int rownum = position.rownum - position.rownum %3 +i;
				int colnum = position.colnum - position.colnum %3 +j ;
				// Si le numéro est différent de 0 le retirer de la liste des absents
				if (grille[rownum][colnum]!= 0){
					absents.remove(grille[rownum][colnum]);
				}
			}
		}
	}

	private static void initAbsents(Set<Integer> absents) {
		absents.add(1);
		absents.add(2);
		absents.add(3);
		absents.add(4);
		absents.add(5);
		absents.add(6);
		absents.add(7);
		absents.add(8);
		absents.add(9);
	}

	private static void remplirGrilleAvecAbsentsUniques(Grille grille) {
		// Parcourir positions
		for (int i =0 ; i<9 ; i++){
			for (int j =0 ; j<9 ; j++){
				// Si les absents à cette position sont uniques, l'intégrer à la position dans la grille
				try{
					if (grille.absentsParPosition.get(new Position(i,j)).size()==1){
						grille.grille[i][j]=(int) grille.absentsParPosition.get(new Position(i,j)).toArray()[0];
						// Propager l'information aux absents de la ligne, du carré, et de la colonne
						grille.absentsParPosition.remove(new Position(i,j));
						retirerAbsentLigne(grille.grille[i][j],i,grille.absentsParPosition);
						retirerAbsentColonne(grille.grille[i][j],j,grille.absentsParPosition);
						retirerAbsentCarre(grille.grille[i][j],new Position(i,j),grille.absentsParPosition);
					}		
				}
				catch(NullPointerException e){
				}
			}
		}
	}
	
	private static void retirerAbsentLigne(int present, int rownum, HashMap<Position, Set<Integer>> absentsParPosition) {
		// Parcourir les absents à la même ligne
		for (int colnum =0; colnum <9 ; colnum ++){
			// Retirer le présent
			try{
				absentsParPosition.get(new Position(rownum, colnum)).remove(present);
			}
			catch(NullPointerException e){
			}
		}
	}	
	
	private static void retirerAbsentColonne(int present, int colnum, HashMap<Position, Set<Integer>> absentsParPosition) {
		// Parcourir les absents à la même colonne
		for (int rownum =0; rownum <9 ; rownum ++){
			// Retirer le présent
			try{
				absentsParPosition.get(new Position(rownum, colnum)).remove(present);
			}
			catch(NullPointerException e){
			}
		}
	}	

	private static void retirerAbsentCarre(int present, Position position, HashMap<Position, Set<Integer>> absentsParPosition) {
		// Parcourir le carré
		for (int i =0 ; i<3 ; i++){
			for (int j=0 ; j<3 ; j++){
				int rownum = position.rownum - position.rownum %3 +i;
				int colnum = position.colnum - position.colnum %3 +j ;
				// Retirer le présent
				try{
					absentsParPosition.get(new Position(rownum, colnum)).remove(present);
				}
				catch(NullPointerException e){
				}
			}
		}
	}	

	private static void remplirGrilleAvecPositionsUniques(Grille grille) {
		remplirLignesAvecPositionsUniques(grille.grille, grille.absentsParPosition);
		remplirColonnesAvecPositionsUniques(grille.grille, grille.absentsParPosition);
		remplirCarresAvecPositionsUniques(grille.grille, grille.absentsParPosition);
	}


	private static void remplirLignesAvecPositionsUniques(int[][] grille,
			HashMap<Position, Set<Integer>> absentsParPosition) {
		//  Pour chaque ligne, parcourir les absents et vérifier s'ils sont absents à plusieurs positions
		for (int i= 0; i<9 ; i++){
			for (int absent=1; absent<10 ; absent++){
				boolean absentAUnePositionUnique = false;
				Position positionUniqueAbsent = null;
				for (int j = 0 ; j<9; j++){
					try{
						if (absentsParPosition.get(new Position(i,j)).contains(absent)){
							if (absentAUnePositionUnique){
								//Si on a déjà vu l'absent à une autre position de la ligne, sortir de la boucle
								absentAUnePositionUnique = false;
								break;
							}
							else {
								absentAUnePositionUnique = true;
								positionUniqueAbsent = new Position(i,j);
							}
						}	
					}
					catch(NullPointerException e){
					}
					
				}
				if (absentAUnePositionUnique){
					// Si un absent n'a qu'un seul choix de position, l'intégrer à la position dans la grille
					grille[positionUniqueAbsent.rownum][positionUniqueAbsent.colnum]=absent;
					// Propager l'information aux absents de la ligne, du carré, et de la colonne
					absentsParPosition.remove(positionUniqueAbsent);
					retirerAbsentLigne(absent,positionUniqueAbsent.rownum,absentsParPosition);
					retirerAbsentColonne(absent,positionUniqueAbsent.colnum,absentsParPosition);
					retirerAbsentCarre(absent,positionUniqueAbsent,absentsParPosition);
				}
			}
		}
	}
	
	private static void remplirColonnesAvecPositionsUniques(int[][] grille,
			HashMap<Position, Set<Integer>> absentsParPosition) {
		//  Pour chaque colonne, parcourir les absents et vérifier s'ils sont absents à plusieurs positions
		for (int j= 0; j<9 ; j++){
			for (int absent=1; absent<10 ; absent++){
				boolean absentAUnePositionUnique = false;
				Position positionUniqueAbsent = null;
				for (int i = 0 ; i<9; i++){
					try{
						if (absentsParPosition.get(new Position(i,j)).contains(absent)){
							if (absentAUnePositionUnique){
								//Si on a déjà vu l'absent à une autre position de la ligne, sortir de la boucle
								absentAUnePositionUnique = false;
								break;
							}
							else {
								absentAUnePositionUnique = true;
								positionUniqueAbsent = new Position(i,j);
							}
						}		
					}
					catch(NullPointerException e){
					}

				}
				if (absentAUnePositionUnique){
					// Si un absent n'a qu'un seul choix de position, l'intégrer à la position dans la grille
					grille[positionUniqueAbsent.rownum][positionUniqueAbsent.colnum]=absent;
					// Propager l'information aux absents de la ligne, du carré, et de la colonne
					absentsParPosition.remove(positionUniqueAbsent);
					retirerAbsentLigne(absent,positionUniqueAbsent.rownum,absentsParPosition);
					retirerAbsentColonne(absent,positionUniqueAbsent.colnum,absentsParPosition);
					retirerAbsentCarre(absent,positionUniqueAbsent,absentsParPosition);
				}
			}
		}
	}
	
	private static void remplirCarresAvecPositionsUniques(int[][] grille,
			HashMap<Position, Set<Integer>> absentsParPosition) {
		//  Pour chaque carre, parcourir les absents et vérifier s'ils sont absents à plusieurs positions
		for (int i =0 ; i<3 ; i++){
			for (int j=0 ; j<3 ; j++){
				int rownum = i*3;
				int colnum = j*3 ;
				for (int absent=1; absent<10 ; absent++){
					boolean absentAUnePositionUnique = false;
					Position positionUniqueAbsent = null;
					boolean finParcoursCarre = false;
					// Parcourir le carré
					for (int i2 =0 ; i2<3 ; i2++){
						if (finParcoursCarre){
							break;
						}
						for (int j2=0 ; j2<3 ; j2++){
							try{
								if (absentsParPosition.get(new Position(rownum+i2,colnum+j2)).contains(absent)){
									if (absentAUnePositionUnique){
										//Si on a déjà vu l'absent à une autre position de la ligne, sortir de la boucle
										absentAUnePositionUnique = false;
										finParcoursCarre = true;
										break;
									}
									else {
										absentAUnePositionUnique = true;
										positionUniqueAbsent = new Position(rownum+i2,colnum+j2);
									}
								}
							}
							catch(NullPointerException e){
							}
						}
					}
					if (absentAUnePositionUnique){
						// Si un absent n'a qu'un seul choix de position, l'intégrer à la position dans la grille
						grille[positionUniqueAbsent.rownum][positionUniqueAbsent.colnum]=absent;
						// Propager l'information aux absents de la ligne, du carré, et de la colonne
						absentsParPosition.remove(positionUniqueAbsent);
						retirerAbsentLigne(absent,positionUniqueAbsent.rownum,absentsParPosition);
						retirerAbsentColonne(absent,positionUniqueAbsent.colnum,absentsParPosition);
						retirerAbsentCarre(absent,positionUniqueAbsent,absentsParPosition);
					}
				}
			}
		}
	}
	
}
