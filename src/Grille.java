import java.awt.Font;
import java.io.*;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Grille {

	// Une instance de la classe grille gere soit les niveaux standarts soit les
	// niveaux aleatoires, jamais les deux!!!!!

	private Carre[][] carres = new Carre[5][5];
	private int niveau, tentatives;
	private boolean jeuTermine, niveauTermine, niveauSelectionne = false;
	private int[] caseSelectionnee = new int[2];// qui contient les coordonnées de la case courante
	private ArrayList<int[]> destinationValide = new ArrayList<int[]>();

	public Font font1 = new Font("Arial", Font.BOLD, 50);
	public Font font2 = new Font("Arial", Font.BOLD, 30);
	public Font font3 = new Font("Comic", Font.BOLD, 15);

	public TrueTypeFont titre1 = new TrueTypeFont(font1, true);
	public TrueTypeFont titre2 = new TrueTypeFont(font2, true);
	public TrueTypeFont titre3 = new TrueTypeFont(font3, true);

	public Color vertPrefere = new Color(100, 132, 127);

	// METHODES GLOBALES

	public Grille() {
		jeuTermine = false;
		niveauTermine = false;
	}

	public boolean isJeuTermine() {
		return jeuTermine;
	}

	public boolean isNiveauTermine() {
		return niveauTermine;
	}

	public boolean isNiveauSelectionne() {
		return niveauSelectionne;
	}

	public void setNiveauSelectionne(boolean niveauSelectionne) {
		this.niveauSelectionne = niveauSelectionne;
	}

	public void setNiveauTermineTrue() {
		niveauTermine = true;
	}

	public void dessinerGrille(Graphics g) throws SlickException {
		int numPixelX = 70, numPixelY = 70;

		Color c = new Color(100, 132, 127, 150);// 100,128,192
		titre3.drawString(75, 600, "" + niveau, Color.white);
		titre3.drawString(200, 600, ""+tentatives,Color.white);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				g.setColor(c);
				g.fillRect(numPixelX, numPixelY, 100, 100);
				g.setColor(Color.black);
				g.drawRect(numPixelX, numPixelY, 100, 100);// tracage des bordures
				numPixelX += 100;
			}
			numPixelX = 70;
			numPixelY += 100;
		}

	}

	public void dessinerCarres(Graphics g) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (carres[i][j] != null) {
					carres[i][j].dessiner(g);
				}
			}
		}
	}

	public int[] quelleCase(int x, int y) {
		int tab[] = new int[2];
		if (x >= 70 && x < 570 && y < 570 && y >= 70) { // on verifie que les coordonée correspondent au case de la
														// grille
			tab[0] = (y - 70) / 100;
			tab[1] = (x - 70) / 100;
			// System.out.println(tab[0] + " : " + tab[1]);
		} else {
			tab = null;
		}
		return tab;
	}

	public void caseClique(int x, int y) throws SlickException {
		int[] tab = new int[2];
		tab = quelleCase(x, y);
		if (tab != null) {
			if (carres[tab[0]][tab[1]] != null) {// la case cible contient une piece qu'on va deplacer ou tourner
				destinationValide.clear();
				caseSelectionnee = tab;
				
				switch (carres[tab[0]][tab[1]].getType()) {// on regarde le type de la case
				case 0:
					break;// ce pion ne peut pas bouger donc on ne fait rien
				case 1:
					destinationValide = carres[tab[0]][tab[1]].mouvHorizontale(this);
					break;// les pions qui bougent horizontalement
				case 2:
					destinationValide = carres[tab[0]][tab[1]].mouvVerticale(this);
					break;
				case 3:
					destinationValide = carres[tab[0]][tab[1]].mouvTranslation(this);
					break;
				case 4:
					carres[tab[0]][tab[1]].mouvRotation(); // ici on modifie directement la forme
					tentatives++;
					break;
				case 5: 
					destinationValide=carres[tab[0]][tab[1]].mouvTotale(this);
					break;
				}
			} else {// la case cible est une case vide du tableau, on verifie alors si on deplace la
					// piece ou si on ne fait rien
				if (caseSelectionnee[0] != -1) {// a ce niveau dans tab il y'a la case destination et caseSelectionne il
												// y'a la case precedente
					if (!destinationValide.isEmpty()) {
						for (int[] t : destinationValide) {
							if (t[0] == tab[0] && t[1] == tab[1]) {
								// on cree une nouvelle carre aux bonnes coordonnée et on met a null l'ancienne
								// case
								carres[tab[0]][tab[1]] = new Carre(tab[0], tab[1],
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getType(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getHaut(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getGauche(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getBas(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getDroite());
								carres[caseSelectionnee[0]][caseSelectionnee[1]] = null;
							}
						}
					}
					caseSelectionnee[0] = -1;
					caseSelectionnee[1] = -1;
					tentatives++;
					destinationValide.clear();
				}

			}
		}
		// if(this.puzzleTermine())
		// this.niveauTermine=true;
	}

	public boolean puzzleTermine() throws SlickException {
		boolean fin = true;
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++) {
				if (carres[i][j] != null) {
					if (this.carreExiste(i + 1, j)) {// case du dessous contient un carre
						if (carres[i][j].getBas() != carres[i + 1][j].getHaut())
							fin = false;
					} else { // la case du dessous ne contient pas un carre
						if (this.caseExiste(i + 1, j))
							if (carres[i][j].getBas() != 0)
								fin = false;
					}
					if (this.carreExiste(i - 1, j)) {// case du dessus
						if (carres[i][j].getHaut() != carres[i - 1][j].getBas())
							fin = false;
					} else { // la case du dessus ne contient pas un carre
						if (this.caseExiste(i - 1, j))
							if (carres[i][j].getHaut() != 0)
								fin = false;
					}
					if (this.carreExiste(i, j - 1)) {// case de gauche
						if (carres[i][j].getGauche() != carres[i][j - 1].getDroite())
							fin = false;
					} else { // la case de gauche ne contient pas un carre
						if (this.caseExiste(i, j - 1))
							if (carres[i][j].getGauche() != 0)
								fin = false;
					}
					if (this.carreExiste(i, j + 1)) {// case de droite
						if (carres[i][j].getDroite() != carres[i][j + 1].getGauche())
							fin = false;
					} else { // la case du droite ne contient pas un carre
						if (this.caseExiste(i, j + 1))
							if (carres[i][j].getDroite() != 0)
								fin = false;
					}

				}
			}

		return fin;
	}

	// METHODES SPECIFIQUES A LA GESTION DES NIVEAUX STANDARTS OU PREDEFINIES

	public void setNiveauStandart() {
		
		carres = Niveau.setNiveau1() ;
		caseSelectionnee[0] = -1;
		caseSelectionnee[1] = -1;
		tentatives=0;
		niveau = 1;
	}

	public void relancerJeu() throws SlickException {
		jeuTermine = false;
		niveauTermine = false;
		tentatives=0;
		niveau = 1;
		carres = Niveau.setNiveau1();
		caseSelectionnee[0] = -1;
		caseSelectionnee[1] = -1;
	}

	public void evolutionNiveaux() throws SlickException {
		// le niveau au debut est 1
		if (this.puzzleTermine() && !this.jeuTermine) {// si un niveau est termine
			//System.out.println("Niveau " + this.niveau + " terminé");
			niveauTermine = true;
			tentatives=0;
			niveau++;
			switch (niveau) {
			case 2:
				carres = Niveau.setNiveau2();
				break;
			case 3:
				carres=Niveau.setNiveau3();
				break;
			case 4:
				carres=Niveau.setNiveau4();
				break;
			case 5:
				carres=Niveau.setNiveau5();
				break;
			case 6:
				carres=Niveau.setNiveau6();
				break;
			case 7:
				carres=Niveau.setNiveau7();
				break;
			case 8:
				carres=Niveau.setNiveau8();
				break;
			case 9:
				jeuTermine=true;
				break;
			}
			caseSelectionnee[0] = -1;
			caseSelectionnee[1] = -1;
		}
	}

	public void dessinerNiveauTermine(Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 00, 640, 100);
		titre2.drawString((640 - titre2.getWidth("NIVEAU TERMINE")) / 2, 00 + (100 - titre2.getHeight()) / 2,
				"NIVEAU TERMINE", vertPrefere);
		System.out.println("Dessin effectue");
		niveauTermine = false;
	}

	public void setPause() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// METHODES POUR GERER LES NIVEAUX ALEATOIRES
	/*
	 * public void setNiveauAleatoire() { carres = Niveaux.getNiveauAleatoire(); }
	 * 
	 * public void evolutionNiveauAleatoire() throws SlickException { if
	 * (this.puzzleTermine()) { jeuTermine = true;
	 * System.out.println("Niveau aléatoire terminé"); this.relancerJeuAleatoire();
	 * } }
	 * 
	 * public void relancerJeuAleatoire() throws SlickException { jeuTermine =
	 * false; niveau = 1; carres = Niveaux.getNiveauAleatoire(); caseSelectionnee[0]
	 * = -1; caseSelectionnee[1] = -1; }
	 */
	// FIN DES METHODES DE JEU ALEATOIRES
	// QUELQUES METHODES TRES PRATIQUES
	public boolean estLibre(int x, int y) {
		if (x >= 0 && x < 5 && y >= 0 && y < 5) {
			return carres[x][y] == null;
		} else {
			return false;
		}
	}

	public boolean carreExiste(int x, int y) { // cette methode renvoi vrai si un carre se trouve dans la case x,y
		boolean c = x >= 0 && x < 5 && y >= 0 && y < 5; // est ce que la case appartient a la grille
		if (c)
			return carres[x][y] != null;
		else
			return false;
	}

	public boolean caseExiste(int x, int y) {
		return x >= 0 && x < 5 && y >= 0 && y < 5;
	}

	// METHODES POUR L'EDITION DE NOUVEAU NIVEAU

	public void setNouvelleConfiguration(ArrayList<Carre> carres) {
		int i = 0, j = 0;
		for (int k = 0; k < carres.size(); k++) {
			if (j == 5) {
				j = 0;
				i++;
			}
			this.carres[i][j] = carres.get(k);
			j++;
		}
		caseSelectionnee[0] = -1;
		caseSelectionnee[1] = -1;
	}

	public void clicCaseNouvelEditeur(int x, int y) {
		int[] tab = new int[2];
		tab = quelleCase(x, y);
		if (tab != null) {
			if (carres[tab[0]][tab[1]] != null) {// la case cible contient une piece qu'on va deplacer ou tourner
				destinationValide.clear();
				caseSelectionnee = tab;
				// dans ce cas tous les pions peuvent se deplacer librement sur le plateau
				destinationValide = carres[tab[0]][tab[1]].mouvTranslation(this);

			} else {// la case cible est une case vide du tableau, on verifie alors si on deplace la
					// piece ou si on ne fait rien
				if (caseSelectionnee[0] != -1) {// a ce niveau dans tab il y'a la case destination et caseSelectionne il
												// y'a la case precedente
					if (!destinationValide.isEmpty()) {
						for (int[] t : destinationValide) {
							if (t[0] == tab[0] && t[1] == tab[1]) {
								// on cree une nouvelle carre aux bonnes coordonnée et on met a null l'ancienne
								// case
								carres[tab[0]][tab[1]] = new Carre(tab[0], tab[1],
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getType(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getHaut(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getGauche(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getBas(),
										carres[caseSelectionnee[0]][caseSelectionnee[1]].getDroite());
								carres[caseSelectionnee[0]][caseSelectionnee[1]] = null;
							}
						}
					}
					caseSelectionnee[0] = -1;
					caseSelectionnee[1] = -1;
					destinationValide.clear();
				}

			}
		}
	}

	public Niveau getNouveauNiveau() {// cette fonction retourne l'etat actuel de la grille comme unn niveau
		ArrayList<CarreEnregistre> c = new ArrayList<CarreEnregistre>();
		CarreEnregistre cr;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (carres[i][j] != null) {
					cr = new CarreEnregistre(carres[i][j]);// construction d'un carre enregistrable à partir du carre
															// connu
					c.add(cr);
				}
			}
		}

		Niveau n = new Niveau(c);
		return n;
	}

	// METHODDES POUR L'UTILISATION D'UN NIVEAU PERSONNALISE
	
	//Cette methode permet de recuperer un fichier 
	public ArrayList<Niveau> getNiveaux(){
		File fichier = new File("Niveau.niv");
		ArrayList<Niveau> niveaux = new ArrayList<Niveau>();
		
		if (!fichier.exists()) {
			try {
				fichier.createNewFile();
			} catch (IOException e) {
				System.out.println("Impossible de creer le fichier de niveaux");
				System.exit(0);
			}
		}
		// receuil des niveaux
		try {
			ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(fichier));
			Object lu = flotLecture.readObject();
			if (lu instanceof ArrayList) {
				niveaux = (ArrayList<Niveau>) lu;
				System.out.println("recuperation effectue");
			}
			flotLecture.close();
		} catch (Exception e) {
			System.out.println("Impossible de lire le fichier");
		}
		
		return niveaux;
	}
	

	public ArrayList<String> ListeNiveaux() {
		ArrayList<String> liste = new ArrayList<String>();
		for (Niveau n : this.getNiveaux()) {
			liste.add(n.getNomNiveau());
//				System.out.println(n.getNomNiveau());
		}
		return liste;
	}

	public void saisieNiveau() {
		if (!niveauSelectionne) {
			int i=0;
			String reponse = "", message = "Entrez le numero de votre niveau :";
			for(String s:this.ListeNiveaux()){
				message+=i+" "+s+"||";
				i++;
			}
			while(true) {
				try {
					reponse = javax.swing.JOptionPane.showInputDialog(message, reponse);
					niveau = Integer.parseInt(reponse);
					if(niveau>=i || niveau<0)
						throw new IllegalArgumentException();
					break;
				} catch (NumberFormatException e) {
					reponse=" format incorrecte ";
				} catch (IllegalArgumentException e) {
					reponse="niveau non existant";
				}
			}
			niveauSelectionne=true;
			this.setNiveauPersonnalise();
		}
	}
	
	public void setNiveauPersonnalise() {
		ArrayList<Niveau> niveaux=this.getNiveaux();
		Niveau niv = niveaux.get(niveau);
		carres=niv.setNiveauPersonnalise();
	}
	
	public void relancerJeuPersonnalise() throws SlickException {
		jeuTermine = false;
		niveauTermine = false;
		niveauSelectionne=false;
		caseSelectionnee[0] = -1;
		caseSelectionnee[1] = -1;
	}
	
	public void setNiveauPersonnaliseFin() {
		jeuTermine = true;
		niveauTermine = true;
	}
}
