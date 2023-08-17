package springboot.Entity.Enum;

public enum Status {
    NEW ("En Attent"),
    OPEN ("Ouvert"),
    WORKING ("En Cours"),
    HOLD ("En Pause"),
    CLOSED ("Fermer");

    private final String name;

    Status(String name) {this.name = name;}

    public String getName() {return name;}
}
