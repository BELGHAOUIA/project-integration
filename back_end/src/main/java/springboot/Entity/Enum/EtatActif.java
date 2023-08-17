package springboot.Entity.Enum;

public enum EtatActif {
	NORMALE ("Normale"),
	EN_PANNE ("En panne"),
	EN_COURS_DE_REPARATION ("En Cours De Reparation");

	private final String name;

	EtatActif(String name) {this.name = name;}

	public String getName() {return name;}
}
