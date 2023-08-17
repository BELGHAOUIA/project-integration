package springboot.Entity.Enum;

public enum Priority {
    low ("Bas"),
    medium ("Moyen"),
    high ("Haute"),
    critical ("Critique");

    private final String name;

    Priority(String name) {this.name = name;}

    public String getName() {return name;}
}
