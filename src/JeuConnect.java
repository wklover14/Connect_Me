import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class JeuConnect extends BasicGame {
	private Grille gr, grNivPersonnalise;
	private Navigation nav;
	private Sound fondSonore;
	private Editeur e;
	private int compt;

	public JeuConnect(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO Auto-generated method stub

		switch (nav.getInterfaceActuelle()) {
		case 1:
			nav.dessinerAcceuil(g);
			break;
		case 2:
			nav.dessinerConnect(g);
			gr.dessinerGrille(g);
			gr.dessinerCarres(g);
			if (compt == 2) {
				gr.dessinerNiveauTermine(g);
			}
			if (gr.isNiveauTermine()) {
				gr.dessinerNiveauTermine(g);
				gr.setPause();
			}
			break;
		case 3:
			nav.dessinerAide(g);
			break;
		case 4:
			nav.dessinerEditeur(g);
			e.dessiner(g);
			break;
		case 5:
			nav.dessinerFelicitation(g);
			break;
		case 6:
			nav.dessinerNiveauPersonnalise(g);
			grNivPersonnalise.dessinerGrille(g);
			grNivPersonnalise.dessinerCarres(g);
			if (compt == 2) {
				grNivPersonnalise.dessinerNiveauTermine(g);
			}
			if (grNivPersonnalise.isNiveauTermine()) {
				grNivPersonnalise.dessinerNiveauTermine(g);
				grNivPersonnalise.setPause();
			}
			break;
		}
	}

	@Override
	public void init(GameContainer gc) {
		// TODO Auto-generated method stub
		gr = new Grille();
		gr.setNiveauStandart();
		e = new Editeur();
		nav = new Navigation();
		grNivPersonnalise = new Grille();

		compt = 0;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input inp = gc.getInput();

		nav.survol(inp.getMouseX(), inp.getMouseY());

		// gestion de l'editeur de niveau		
		if (nav.getInterfaceActuelle() == 4) {
			e.saisie();// receuil de tous les prametres des carres
			e.survol(inp.getMouseX(), inp.getMouseY());
			if (inp.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || inp.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				e.positionerCarre(inp.getMouseX(), inp.getMouseY());
			}
			if (e.isEditionTermine()) {
				e.enregistrerNiveau();
				nav.setInterfaceActuelle(1); // on force le retour a l'acceuil;
				e = new Editeur();// et on construit un nouvel editeur;
			}
		}

		// gestion des niveaux personnalises
		if (nav.getInterfaceActuelle() == 6) {
			grNivPersonnalise.saisieNiveau();
		}

		// GESTION DU JEU et de la navigation

		if ((inp.isMousePressed(Input.MOUSE_RIGHT_BUTTON) || inp.isMousePressed(Input.MOUSE_LEFT_BUTTON))) {
			nav.survol(inp.getMouseX(), inp.getMouseY());
			nav.clicZone();// on regarde sur quelle zone il a clique

			if (nav.getInterfaceActuelle() == 2) {// si nous sommes dans l'interface de jeu
				if (!gr.isJeuTermine() && compt == 0) {

					gr.caseClique(inp.getMouseX(), inp.getMouseY());

					if (gr.puzzleTermine())
						compt = 1;
				} else {
					nav.setInterfaceFelicitation();
					gr.relancerJeu();
				}
			}

			if (nav.getInterfaceActuelle() == 6) {// si nous sommes dans l'interface de jeu personnalise
				if (!grNivPersonnalise.isJeuTermine()) {
					grNivPersonnalise.caseClique(inp.getMouseX(), inp.getMouseY());
					if (grNivPersonnalise.puzzleTermine() && grNivPersonnalise.isNiveauSelectionne()) {
						grNivPersonnalise.setNiveauPersonnaliseFin();
						grNivPersonnalise.relancerJeuPersonnalise();
						nav.setInterfaceFelicitation();
					}
				} else {
					grNivPersonnalise.setNiveauPersonnaliseFin();
					// grNivPersonnalise.relancerJeuPersonnalise();
					nav.setInterfaceFelicitation();
				}
			}
		}

		if (nav.getInterfaceActuelle() == 2 && !gr.isJeuTermine()) {
			if (compt == 2) {
				gr.evolutionNiveaux();
				compt = 0;
			}
			if (compt == 1) {
				compt = 2;
				// gr.setNiveauTermineTrue();
			}
		}

	}

}
