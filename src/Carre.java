import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Carre {
	private int x, y;// coordonnées su coin superieur gauche
	private int type;// 0 pour les carrés qui ne bouge pas, 1 pour ceux qui bouge horizontalement,2
						// pour verticalement,3 pour les deux precdentes,4 pour ceux rotent et 5 pour
						// ceux qui ont toutes les possibilités
	private int[][] broche = new int[4][1];// [0][0] correspond au nombre de broches du coté haut, [1][0] au nombre de
											// broches du coté gauche,[2][0] pour le bas et [3][0] pour la droite
	private Image img;
	private Image imgBroche;
	
	public Carre() {
		//rr
	}
	public Carre(int x, int y, int type, int nbreHaut, int nbreGauche, int nbreBas, int nbreDroit) {
			try {
//				imgBroche = new Image("images/Broche.png");
			}
			catch (Exception e) {
				//System.out.println("ouiiii");
			}
	
		if (x >= 0 && x < 5 && y < 5 && y >= 0 && type < 6 && type >= 0 && nbreHaut >= 0 && nbreHaut <= 4
				&& nbreGauche >= 0 && nbreGauche <= 4 && nbreBas >= 0 && nbreBas <= 4 && nbreDroit >= 0
				&& nbreDroit <= 4) {
			this.x = x;
			this.y = y;
			this.setType(type);
			this.broche[0][0] = nbreHaut;
			this.broche[1][0] = nbreGauche;
			this.broche[2][0] = nbreBas;
			this.broche[3][0] = nbreDroit;
			try {
				imgBroche = new Image("images/Broche.png");
				switch (type) {
				case 0:
					img = new Image("images/CarreRouge.png");
					break;
				case 1:
					img = new Image("images/CarreViolet.png");
					break;
				case 2:
					img = new Image("images/CarreJaune.png");
					break;
				case 3:
					img = new Image("images/CarreVert.png");
					break;
				case 4:
					img = new Image("images/CarreBleu.png");
					break;
				case 5:
					img = new Image("images/CarreOrange.png");
					break;
				}
			} catch (SlickException e) {
				System.out.println("Probleme de chargement de l'image du carre");
			}

		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getHaut() {
		return broche[0][0];
	}

	public int getGauche() {
		return broche[1][0];
	}

	public int getBas() {
		return broche[2][0];
	}

	public int getDroite() {
		return broche[3][0];
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void dessiner(Graphics g) {
		int nbreBroche = 0;
		int coordx = y * 100 + 80, coordy = x * 100 + 80;// represente les coordonées du coin superieur gauche du
															// rectangle dans la grille

		g.drawImage(img, coordx, coordy); // affichage des carres sans les broches
		for (int i = 0; i < 4; i++) {
			nbreBroche = broche[i][0];
			int haut = i == 0 ? 1 : 0;// la variable haut prend 1 si on est sur le coté haut et zero sinon
			int bas = i == 2 ? 1 : 0;// mm procede qu'avant
			int gauche = i == 1 ? 1 : 0;
			int droit = i == 3 ? 1 : 0;
			int vertical = i % 2 == 0 ? 1 : 0;// si on est sur une verticale, soit haut et bas
			int horizontale = i % 2 != 0 ? 1 : 0;// soit gauche et droite

			switch (nbreBroche) {
			case 0:
				break;
			case 1:
				g.drawImage(imgBroche, coordx + 35 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 35 * horizontale); // sur une verticale la coordonnée suivant x
																			// est constante
				break;
			case 2:
				g.drawImage(imgBroche, coordx + 25 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 25 * horizontale);// sur une horizontale, la coordonnée suivant y
																			// est constante
				g.drawImage(imgBroche, coordx + 45 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 45 * horizontale);
				break;
			case 3:
				g.drawImage(imgBroche, coordx + 15 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 15 * horizontale);
				g.drawImage(imgBroche, coordx + 35 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 35 * horizontale);
				g.drawImage(imgBroche, coordx + 55 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 55 * horizontale);
				break;
			case 4:
				g.drawImage(imgBroche, coordx + 10 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 10 * horizontale);
				g.drawImage(imgBroche, coordx + 27 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 27 * horizontale);
				g.drawImage(imgBroche, coordx + 43 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 43 * horizontale);
				g.drawImage(imgBroche, coordx + 60 * vertical - 10 * gauche + 80 * droit,
						coordy - 9 * haut + 80 * bas + 60 * horizontale);
				break;
			}

		}

	}

	public ArrayList<int[]> mouvHorizontale(Grille g) {
		ArrayList<int[]> tab = new ArrayList<int[]>();

		for (int i = 0; i < 5; i++) {
			if (g.estLibre(this.x, i)) {
				int[] t = new int[2];
				t[0] = this.x;
				t[1] = i;
				tab.add(t);
			}
		}

		return tab;
	}

	public ArrayList<int[]> mouvVerticale(Grille g) {
		ArrayList<int[]> tab = new ArrayList<int[]>();

		for (int i = 0; i < 5; i++) {
			if (g.estLibre(i, this.y)) {
				int[] t = new int[2];
				t[0] = i;
				t[1] = this.y;
				tab.add(t);
			}
		}
		return tab;
	}

	public ArrayList<int[]> mouvTranslation(Grille g) {
		ArrayList<int[]> tab = new ArrayList<int[]>();
		ArrayList<int[]> t1 = new ArrayList<int[]>();
		ArrayList<int[]> t2 = new ArrayList<int[]>();

		t1 = this.mouvHorizontale(g);
		t2 = this.mouvVerticale(g);
		for (int[] t : t1) {
			tab.add(t);
		}
		for (int[] p : t2) {
			tab.add(p);
		}

		return tab;
	}

	public void mouvRotation() {// cette fonction se contente de modifier les broches de chaque coté
		int temp = broche[3][0];
		for (int i = 3; i > 0; i--) {
			broche[i][0] = broche[i - 1][0];
		}
		broche[0][0] = temp;
//		for (int i = 0; i < 4; i++) {
//			System.out.print(broche[i][0] + " : ");
//		}
//		System.out.println("");

	}
	
	public ArrayList<int[]> mouvTotale(Grille g){
		ArrayList<int[]> tab = new ArrayList<int[]>();
		tab=this.mouvTranslation(g);
		this.mouvRotation();
		return tab;
	}
	
	
	//CE CONSTRUCTEUR A ETE AJOUTE DANS LE BUT DE POUVOIR RECONSTITUER UN CARRE A PARTIR D'UN CARRE ENREGISTRE 
	public Carre(CarreEnregistre c) {
		try {
			imgBroche = new Image("images/Broche.png");
		} catch (Exception e) {
			//e1.printStackTrace();
		}
		
		this.x = c.getX();
		this.y = c.getY();
		this.setType(c.getType());
		this.broche=c.getBroche();
		
		try {
			switch (type) {
			case 0:
				img = new Image("images/CarreRouge.png");
				break;
			case 1:
				img = new Image("images/CarreViolet.png");
				break;
			case 2:
				img = new Image("images/CarreJaune.png");
				break;
			case 3:
				img = new Image("images/CarreVert.png");
				break;
			case 4:
				img = new Image("images/CarreBleu.png");
				break;
			case 5:
				img = new Image("images/CarreOrange.png");
				break;
			}
		} catch (SlickException e) {
			System.out.println("Probleme de chargement de l'image du carre");
		}

	
	}
	

}
