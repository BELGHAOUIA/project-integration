package springboot.Entity.Enum;

public enum TypeTicket {
	perte ("Perte"),
	probelm_technique ("Probleme Technique"),
	probelm_mecanique ("Probleme Mecanique");

	private final String name;

	TypeTicket(String name) {this.name = name;}

	public String getName() {return name;}
}
