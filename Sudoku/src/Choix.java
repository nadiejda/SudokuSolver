
public class Choix {
	protected Position position ;
	protected Integer absentChoisi ;
	
	public Choix(Position position , Integer absentChoisi){
		this.position = position ;
		this.absentChoisi = absentChoisi;
	}

	@Override
	protected Choix clone() {
		Choix clone = new Choix(this.position.clone(),this.absentChoisi);
		return clone;
	}
}
