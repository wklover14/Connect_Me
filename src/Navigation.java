import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class Navigation {
	// cette classe contient les methodes et attributs qui decriront cmt on se
	// deplace dans l'application
	private int interfaceActuelle = 1;// 1 pour la page d'acceuil, 2 pour une page de jeu, 3 pour la page d'aide,
										// 4 l'editeur, 5 pour l'interface felicitation 6 pour
										// jouer au niveau cree

	private int zoneSurvole = -1;

	public Font font1 = new Font("Comic", Font.BOLD, 50);
	public Font font2 = new Font("Arial", Font.BOLD, 30);
	public Font font3 = new Font("Comic", Font.BOLD, 15);

	public TrueTypeFont titre1 = new TrueTypeFont(font1, true);
	public TrueTypeFont titre2 = new TrueTypeFont(font2, true);
	public TrueTypeFont titre3 = new TrueTypeFont(font3, true);

	public Color vertPrefere = new Color(100, 132, 127);

	public Navigation() {
		// rien a faire pour le moment
	}

	public int getInterfaceActuelle() {
		return interfaceActuelle;
	}

	public void setInterfaceFelicitation() {
		interfaceActuelle = 5;
	}
	
	public void setInterfaceActuelle(int interfaceActuelle) {
		this.interfaceActuelle = interfaceActuelle;
	}

	// METHODES POUR DESSINER LES DIFFERENTES INTERFACES POSSIBLES DANS LE JEU (
	// acceuil, aide jeu...) CE SONT TOUTES DES METHODES GRAPHIQUES

	public void dessinerFond(Graphics g) {
		Color fond = new Color(236, 193, 156, 100);
		
		try {
			g.drawImage(new Image("images/background.jpg"), 0, 0);
			g.setColor(fond);
			g.fillRect(0, 300, 640, 180);
		} catch (SlickException e) {
			System.out.println("merde");
		}
	}

	public void decorationZoneSurvole(Graphics g) throws SlickException {// cette methode permet de mettre en
		// surbrillance une zone de l'acceuil
		g.setColor(Color.white);
		switch (zoneSurvole) {
		case 2:// zone 1 est celle de Start
			g.fillRect(0, 300, 640, 40);
			break;
		case 3:// zone 2 est celle de Help
			g.fillRect(0, 340, 640, 40);
			break;
		case 4:// zone 3 est celle de Editeur de niveau
			g.fillRect(0, 380, 640, 50);
			break;
		case 0:
			// ici c'est le bouton de retour à l'acceuil
			break;
		case 6:// la zone 6 est celle des niveaux crees
			g.fillRect(0, 430, 640, 50);
			break;
		}
	}

	public void dessinerRetourAcceuil(Graphics g) throws SlickException {// dessine le bouton de retour a la page
																			// d'acceuil
		Image img = new Image("images/backVert.png");
		g.drawImage(img, 600, 5);
	}

	public void colorerRetourAcceuil(Graphics g) {// colore le boutton d'acceuil lorsqu'on le
													// survole
		if (zoneSurvole == 0) {
			Image img;
			try {
					img = new Image("images/backBlanc.png");
					g.drawImage(img, 600, 5);
			} catch (Exception e) {
				System.out.println("Askip probleme de chargement de l'image backBlanc.png");
			}
			// g.drawImage(img, 600, 5);
		}
	}

	public void dessinerAcceuil(Graphics g) throws SlickException {
		this.dessinerFond(g);

		this.decorationZoneSurvole(g);
		titre1.drawString((640 - titre1.getWidth("Connect Me")) / 2, 130, "Connect Me", vertPrefere);
		titre2.drawString((640 - titre2.getWidth("Start")) / 2, 300, "Start", vertPrefere);
		titre2.drawString((640 - titre2.getWidth("Help")) / 2, 300 + titre2.getHeight("Start") + 10, "Help",
				vertPrefere);
		titre2.drawString((640 - titre2.getWidth("Create your level")) / 2,
				300 + titre2.getHeight("Start") + titre2.getHeight("Help") + 20, "Create your level", vertPrefere);
		titre2.drawString((640 - titre2.getWidth("Play your level")) / 2,
				300 + titre2.getHeight("Start") + titre2.getHeight("Help") + titre2.getHeight("Create your level") + 30,
				"Play your level", vertPrefere);
	}

	public void dessinerAide(Graphics g) throws SlickException {
		this.dessinerFond(g);
		this.dessinerRetourAcceuil(g);
		this.colorerRetourAcceuil(g);
		
		g.drawImage(new Image("images/texte1Aide.png"),40,40, Color.white);
		//titre3.drawString((640- titre3.getWidth("Pour jouer il faut déplacer les "))/2 , 80 , "Pour jouer il faut déplacer les ", Color.white);
	}

	public void dessinerFelicitation(Graphics g) throws SlickException {
		this.dessinerFond(g);
		this.dessinerRetourAcceuil(g);
		this.colorerRetourAcceuil(g);
		titre1.drawString((640 - titre1.getWidth("FELICITATIONS")) / 2, 100, "FELICITATIONS", vertPrefere);
		titre1.drawString((640 - titre1.getWidth("JEU TERMINE")) / 2, (100 + titre1.getHeight("FELICITATIONS") + 15),
				"JEU TERMINE", vertPrefere);

//		titre3.drawString(15, 250, "Vous avez l'honneur de faire partie des élus", vertPrefere);
	}

	public void dessinerConnect(Graphics g) throws SlickException {// dessine l'arriere plan de la grille lorsqu'on joue
		this.dessinerFond(g);
		titre2.drawString(250, 20, "Connect Me", vertPrefere);
		titre3.drawString(10, 600, "Niveau : ", Color.white);
		titre3.drawString(100, 600, "Tentatives : ", Color.white);
		this.dessinerRetourAcceuil(g);
		this.colorerRetourAcceuil(g);
	}

	public void dessinerEditeur(Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		this.dessinerFond(g);
		this.dessinerRetourAcceuil(g);
		this.colorerRetourAcceuil(g);

	}

	public void dessinerNiveauPersonnalise(Graphics g) throws SlickException {
		dessinerFond(g);
		dessinerRetourAcceuil(g);
		colorerRetourAcceuil(g);
		
	}
	// METHODES POUR DEFINIR L'INTERFACE ET SWITCHER
	// CES METHODES SONT DESTINES AUX UPDATE...

	public void survol(int x, int y) {
		// verifie si x et y correspondent au coordonnees dans une zone qui a du sens;
		// 2 pour le lien vers le jeu, 3 pour l'aide, 4 pour l'editeur, 6 pour jeupersonnalise, 0 pour le boutton de retour

		zoneSurvole = y <= 340 && y >= 300 ? 2
				: y <= 380 && y > 340 ? 3
						: y <= 430 && y > 380 ? 4 : y <= 480 && y > 430 ? 6 : y <= 50 && y >= 0 && x >= 600 ? 0 : -1;

	}

	public void clicZone() {// change l'interface
		switch (zoneSurvole) {
		case 2:
			if (interfaceActuelle == 1)// on est a la page d'acceuil et on passe au jeux proprement dit
				interfaceActuelle = 2;
			break;
		case 3:
			if (interfaceActuelle == 1)// on est a la page d'acceuil et on passe a l'interface d'aide
				interfaceActuelle = 3;
			break;
		case 4:
			if (interfaceActuelle == 1)// on est a la page d'acceuil et on passe a l'interface de l'editeur
				interfaceActuelle = 4;
			break;
		case 0: //Il s'agit de la zone de retour donc on rentre a l'acceuil
			interfaceActuelle = 1;
			break;
		case 6: // on va vers les niveaux personnalisés
			if(interfaceActuelle == 1)
				interfaceActuelle = 6;
			break;
		}

	}
}
