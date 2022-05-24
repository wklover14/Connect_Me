import java.io.Serializable;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Niveau implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Carre> carres;
	private ArrayList<CarreEnregistre> carresEnregistre;
	private String nomNiveau;

	public Niveau() {
		// rien rien pour le moment
	}

	public Niveau(ArrayList<CarreEnregistre> c) {
		this.carresEnregistre = c;
	}

	public String getNomNiveau() {
		return nomNiveau;
	}

	public void setNomNiveau(String nomNiveau) {
		this.nomNiveau = nomNiveau;
	}
	public static Carre[][] setNiveau1(){
		Carre[][] carres = new Carre[5][5];
		carres[1][0] = new Carre(1, 0, 1, 0, 0, 0, 1);
		carres[1][3] = new Carre(1, 3, 0, 0, 1, 0, 0);
		return carres;
	}
	
	public static Carre[][] setNiveau2(){
		Carre[][] carres = new Carre[5][5];
		carres[0][2] = new Carre(0, 2, 2, 0, 2, 0, 0);
		carres[2][1] = new Carre(2, 1, 0, 0, 0, 0, 2);
		return carres;
	}

	public static Carre[][] setNiveau3() {
		Carre[][] carres = new Carre[5][5];
		carres[1][3] = new Carre(1, 3, 3, 0, 0, 3, 0);
		carres[2][1] = new Carre(2, 1, 0, 3, 0, 0, 0);
		return carres;
	}
	
	public static Carre[][] setNiveau4() {
		Carre[][] carres = new Carre[5][5];
		carres[2][2] = new Carre(2, 2, 4, 0, 0, 2, 1);
		carres[0][2] = new Carre(0, 2, 2, 2, 0, 0, 0);
		carres[2][0] = new Carre(2, 0, 1, 0, 1, 0, 0);
		return carres;
	}
	
	public static Carre[][] setNiveau5() {
		Carre[][] carres = new Carre[5][5];
		carres[1][0] = new Carre(1, 0, 3, 0, 0, 2, 1);
		carres[1][3] = new Carre(1, 3, 4, 0, 3, 1, 0);
		carres[0][3] = new Carre(0, 3, 0, 0, 0, 3, 0);
		carres[1][1] = new Carre(1, 1, 3, 2, 2, 0, 0);
		carres[2][1] = new Carre(2, 1, 4, 0, 0, 2, 0);
		return carres;
	}
	
	public static Carre[][] setNiveau6() {
		Carre[][] carres = new Carre[5][5];
		carres[0][3] = new Carre(0, 3, 0, 0, 0, 3, 0);
		carres[0][2] = new Carre(0, 2, 3, 2, 2, 0, 0);
		carres[1][3] = new Carre(1, 3, 4, 0, 0, 3, 1);
		carres[2][1] = new Carre(2, 1, 4, 2, 0, 0, 0);
		carres[3][3] = new Carre(3, 3, 3, 0, 0, 2, 1);
		return carres;
	}

	public static Carre[][] setNiveau7()  {
		Carre[][] carres = new Carre[5][5];
		carres[0][0] = new Carre(0, 0, 1, 0, 0, 1, 0);
		carres[1][1] = new Carre(1, 1, 4, 3, 3, 0, 0);
		carres[1][3] = new Carre(1, 3, 0, 1, 4, 2, 3);
		carres[2][2] = new Carre(2, 2, 4, 3, 2, 3, 2);
		carres[2][3] = new Carre(2, 3, 4, 2, 1, 0, 2);
		carres[4][4] = new Carre(4, 4, 3, 0, 3, 3, 4);
		carres[4][1] = new Carre(4, 1, 2, 3, 0, 0, 2);
		carres[2][4] = new Carre(2, 4, 3, 3, 0, 0, 0);
		carres[3][4] = new Carre(3, 4, 1, 1, 0, 0, 0);
		carres[4][0] = new Carre(4, 0, 3, 0, 3, 0, 0);
		return carres;
	}
	
	public static Carre[][] setNiveau8() {
		Carre[][] carres = new Carre[5][5];
		carres[0][0] = new Carre(0, 0, 5, 2, 1, 3, 1);
		carres[1][0] = new Carre(1, 0, 1, 0, 0, 3, 0);
		carres[0][3] = new Carre(0, 3, 2, 1, 2, 0, 3);
		carres[1][3] = new Carre(1, 3, 2, 0, 1, 1, 3);
		carres[1][4] = new Carre(1, 4, 2, 0, 3, 2, 0);
		carres[2][2] = new Carre(2, 2, 4, 3, 0, 2, 1);
		carres[3][1] = new Carre(3, 1, 0, 0, 0, 0, 4);
		carres[3][2] = new Carre(3, 2, 4, 3, 3, 2, 4);
		carres[3][4] = new Carre(3, 4, 4, 2, 1, 0, 2);
		carres[4][0] = new Carre(4, 0, 1, 1, 3, 0, 0);
		carres[4][1] = new Carre(4, 1, 3, 3, 0, 0, 2);
		return carres;
	}
	
	

	public static Carre[][] getNiveauAleatoire() {
		Carre[][] carres = new Carre[5][5];
		int nbreCarres = (int) (Math.random() * 10) + 4;
		int nbVerticale = 0, nbHorizontale = 0;

		// on s'assure que le nbre de broche horizontale et verticale est paire et non
		// nul
		nbVerticale = 0;
		nbHorizontale = 0;
		carres = new Carre[5][5];

		for (int k = 0; k < nbreCarres; k++) {
			int i = (int) (Math.random() * 5);
			int j = (int) (Math.random() * 5);
			if (carres[i][j] == null) {

				int nbHaut = (int) (Math.random() * 5);
				nbVerticale += nbHaut;

				int nbGauche = (int) (Math.random() * 5);
				nbHorizontale += nbGauche;

				int nbBas = (int) (Math.random() * 5);
				nbVerticale += nbBas;

				int nbDroite = (int) (Math.random() * 5);
				nbHorizontale += nbDroite;

				carres[i][j] = new Carre(i, j, (int) (Math.random() * 5), nbHaut, nbGauche, nbBas, nbDroite);
			}
		}

		return carres;
	}

	
	
	//cette methode retourne le niveau 
	
	public Carre[][] setNiveauPersonnalise(){
		Carre[][] carresP= new Carre[5][5];
		for(CarreEnregistre c:carresEnregistre) {
			carresP[c.getX()][c.getY()]=new Carre(c);
		}
		
		return carresP;
	}

}
