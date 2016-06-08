
public class TestGrille {

	public static void main (String[] argv ){
		int[][] grille = new int [9][9];
		printGrille(grille);
	}

	private static void printGrille(int[][] grille) {
		for (int i = 0; i<9; i++){
			System.out.println("||-|-|-||-|-|-||-|-|-||");
			if (i%3 == 0){
				System.out.println("||-|-|-||-|-|-||-|-|-||");
			}
			for (int j=0 ; j<9 ; j++){
				if (j%3 ==0){
					System.out.print("||"+grille[i][j]);
				}
				else {
					System.out.print("|"+grille[i][j]);
				}
				if (j==8){
					System.out.println("||");
				}
			}
		}
	}
}
