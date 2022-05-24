import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Editeur {
	private ArrayList<Carre> carres;
	private int nbreCarre, type, haut, gauche, bas, droit;
	private boolean nbreCarreConnu, carreCree, editionTermine, validation;
	private Grille gr = new Grille();
	private Niveau niveau = new Niveau();
	private String nomNiveau;

	public Font font1 = new Font("Arial", Font.BOLD, 50);
	public Font font2 = new Font("Arial", Font.BOLD, 30);
	public Font font3 = new Font("Comic", Font.BOLD, 15);

	public TrueTypeFont titre1 = new TrueTypeFont(font1, true);
	public TrueTypeFont titre2 = new TrueTypeFont(font2, true);
	public TrueTypeFont titre3 = new TrueTypeFont(font3, true);

	public Color vertPrefere = new Color(100, 132, 127);

	public Editeur() {
		carres = new ArrayList<Carre>();
		nbreCarre = -1;
		type = -1;
		haut = -1;
		gauche = -1;
		bas = -1;
		droit = -1;
		nbreCarreConnu = false;
		carreCree = false;
	}

	// Methodes pour le render

	public void dessiner(Graphics g) {

		if (isEditionTermine()) {
			titre2.drawString((640 - titre2.getWidth("Votre Niveau a bien été crée")) / 2, 30,
					"Votre Niveau a bien ete cree", vertPrefere);
		} else {
			titre2.drawString((640 - titre2.getWidth("Creation de votre Niveau")) / 2, 30, "Creation de votre Niveau",
					vertPrefere);
			if (!isNbreCarreConnu()) {
				titre2.drawString(0, 100, "Entrez le nombre carrés [5-19] :", vertPrefere);
			} else {

				if (isCarreCree()) {// il a finit de configurer toutes les pieces il passe donc au positionnement
					try {
						gr.dessinerGrille(g);
						gr.dessinerCarres(g);
						if (validation) {
							g.setColor(Color.white);
							g.fillRect(0, 600, 640, 20);
						}
						titre3.drawString((640 - titre3.getWidth("OK")) / 2, 600, "OK", vertPrefere);
					} catch (SlickException e) {
						// e.printStackTrace();
					}
				}
			}
		}

	}

	// cette methode gere l'entree des parametres via des boite de dialogues

	public void saisie() {
		String reponse = "Mon super niveau", message = "Entrez le nom du niveau";
		if (!isNbreCarreConnu()) {
			while (true) {// saisie du nom du niveau
				try {
					reponse = javax.swing.JOptionPane.showInputDialog(message, reponse);
					nomNiveau = reponse;
					break;
				} catch (NumberFormatException e) {
					// e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// rien
				} catch (NullPointerException e) {
					// s'il clique sur annuler alors on ferme l'application
					System.exit(0);
				}
			}
			reponse = "02";
			message = "Entrez le nombre de carres [2-19]";
			while (true) {// saisie du nombre de carre
				try {
					reponse = javax.swing.JOptionPane.showInputDialog(message, reponse);
					String[] valeurs = reponse.split(":");
					setNbreCarre(Integer.parseInt(valeurs[0]));// le set peut generer l'exception de domaines tandis que
																// le parseInt l'exception de type
					break;
				} catch (NumberFormatException e) {
					// e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// rien
				} catch (NullPointerException e) {
					// s'il clique sur annuler alors on ferme l'application
					System.exit(0);
				}
			}
			nbreCarreConnu = true;
		} else {
			if (!isCarreCree()) {
				int i = 0, j = 0;
				Carre nouveauCarre;
				for (int k = 1; k <= nbreCarre; k++) {// saisie de tous les carres en une fois
					reponse = "TT:HH:GG:BB:DD";
					message = "Configurez le carre numero " + k + " sous la forme type:haut:gauche:bas:droite";
					while (true) {// saisie des coordonnees d'un carre
						try {
							reponse = javax.swing.JOptionPane.showInputDialog(message, reponse);
							String[] valeurs = reponse.split(":");
							setType(Integer.parseInt(valeurs[0]));
							setHaut(Integer.parseInt(valeurs[1]));
							setGauche(Integer.parseInt(valeurs[2]));
							setBas(Integer.parseInt(valeurs[3]));
							setDroit(Integer.parseInt(valeurs[4]));
							break;
						} catch (IllegalArgumentException e) {
							// rien
						} catch (NullPointerException e) {
							// s'il clique sur annuler alors on ferme l'application
							System.exit(0);
						}

					}
					if (j == 5) {
						j = 0;
						i++;
					}
					nouveauCarre = new Carre(i, j, type, haut, gauche, bas, droit);// creation du nouveau carre
					carres.add(nouveauCarre);
					System.out.println("ajout du carre numero " + k + " effectue");
					j++;
				}
				carreCree = true;
				gr.setNouvelleConfiguration(carres);// transfert des nouvelles configuration vers la grille
			} else {
				// ici on va configurer la décoration de la zone de fin
			}
		}
	}

	// cette fonction permet de gerer la position des pieces sur la grille
	public void positionerCarre(int x, int y) {
		if (isCarreCree() && isNbreCarreConnu() && !isEditionTermine()) {
			if (y <= 640 && y >= 600) { // il clique sur le bouton ok pour valider le niveau
				System.out.println("positionnement termine");
				editionTermine = true;
				niveau = gr.getNouveauNiveau();
				niveau.setNomNiveau(nomNiveau);

			} else {// continuer a positionner les pieces sur la grille
				gr.clicCaseNouvelEditeur(x, y);
			}
		}
	}

	// cette methode gere le survol de la zone de validation
	public void survol(int x, int y) {
		if (isCarreCree() && isNbreCarreConnu() && !isEditionTermine() && y <= 630 && y >= 600) {
			validation = true;
		} else {
			validation = false;
		}
	}

	public void enregistrerNiveau() {
		File fichier = new File("Niveau.niv");
		ArrayList<Niveau> c = new ArrayList<Niveau>();

		// si le fichier n'existe pas alors on le cree

		if (!fichier.exists()) {
			try {
				fichier.createNewFile();
			} catch (IOException e) {
				System.out.println("Impossible de creer le fichier de niveaux");
				System.exit(0);
			}
		}

		// on garde le contnu du fichier dans c
		if (fichier.canRead()) {
			
			try {
				ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(fichier));
				Object lu = flotLecture.readObject();// on recupere tous les objets du fichiers sous forme de tableau
				System.out.println("qsjjbqsfbjsbfj");
				if (lu instanceof ArrayList) {
					c = (ArrayList<Niveau>)lu;
					System.out.println("recuperation effectue");
				}
				flotLecture.close();
			} catch (Exception e) {
				System.out.println("erreur de lecture du fichier");
				System.exit(0);
			}
		}

		// on recree le fichier

		try {
			fichier.delete();
			fichier.createNewFile();
		} catch (IOException e1) {
			System.out.println("echec lors de la creation du fichier");
			System.exit(0);
		}

		// on ajoute le nouveau niveau dan l'arraylist

		c.add(niveau);

		// on enregistre
		try {
			ObjectOutputStream flotEcriture = new ObjectOutputStream(new FileOutputStream(fichier));
			flotEcriture.writeObject(c);
			flotEcriture.close();
			System.out.println("Enregistrement effectue");
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("Erreur d'ecriture");
		}

	}

	// Setter et Getter

	public void setType(int type) throws IllegalArgumentException {
		if (type >= 0 && type <= 4)
			this.type = type;
		else
			throw new IllegalArgumentException();
	}

	public void setNbreCarre(int nbreCarre) throws IllegalArgumentException {
		if (nbreCarre >= 2 && nbreCarre <= 19)
			this.nbreCarre = nbreCarre;
		else
			throw new IllegalArgumentException("nombre de carres incorrecte");
	}

	public void setHaut(int haut) throws IllegalArgumentException {
		if (haut >= 0 && haut <= 4)
			this.haut = haut;
		else
			throw new IllegalArgumentException("nombre de broches du haut incorrecte");
	}

	public void setGauche(int gauche) throws IllegalArgumentException {
		if (gauche >= 0 && gauche <= 4)
			this.gauche = gauche;
		else
			throw new IllegalArgumentException("nombre de broches de gauche incorrecte");
	}

	public void setBas(int bas) throws IllegalArgumentException {
		if (bas >= 0 && bas <= 4)
			this.bas = bas;
		else
			throw new IllegalArgumentException("nombre de broches du bas incorrecte");
	}

	public void setDroit(int droit) throws IllegalArgumentException {
		if (droit >= 0 && droit <= 4)
			this.droit = droit;
		else
			throw new IllegalArgumentException("nombre de broches de droite incorrecte");
	}

	private boolean isCarreCree() {
		return carreCree;
	}

	private boolean isNbreCarreConnu() {
		return nbreCarreConnu;
	}

	public boolean isEditionTermine() {
		return editionTermine;
	}

	public void setEditionTermine(boolean editionTermine) {
		this.editionTermine = editionTermine;
	}
}
