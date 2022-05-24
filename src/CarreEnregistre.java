import java.io.Serializable;

//l'existence de cette classe se justifie par le fait que la classe Carre n'est pas serializable a cause de sa propriete image
//cette classe reprend donc toutes les prorietes de Carre sans l'image et sera enregistre, 
//a la lecture du fichier on convertira ces carreEnregistre en cCarre et on travaillera 


public class CarreEnregistre implements Serializable {
	private int x, y;// coordonnées su coin superieur gauche
	private int type;// 0 pour les carrés qui ne bouge pas, 1 pour ceux qui bouge horizontalement,2
						// pour verticalement,3 pour les deux precdentes,4 pour ceux rotent et 5 pour
						// ceux qui ont toutes les possibilités
	private int[][] broche = new int[4][1];
	public CarreEnregistre() {
		
	}
	public CarreEnregistre(Carre c) {
		this.x=c.getX();
		this.y=c.getY();
		this.type=c.getType();
		this.broche[0][0]=c.getHaut();
		this.broche[1][0]=c.getGauche();
		this.broche[2][0]=c.getBas();
		this.broche[3][0]=c.getDroite();
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int[][] getBroche() {
		return broche;
	}
	public void setBroche(int[][] broche) {
		this.broche = broche;
	}
}
