package model;

/**
 * Created by Илья on 25.05.2015.
 */
public class Material {
    int id;
    String type;
    double density;
    double ash;
    double melting_point;
    String description;

    public Material(String type, double density, double ash, double melting_point, String description) {
        this.type = type;
        this.density = density;
        this.ash = ash;
        this.melting_point = melting_point;
        this.description = description;
    }

    public Material(int id, String type, double density, double ash, double melting_point, String description) {
        this.id = id;
        this.type = type;
        this.density = density;
        this.ash = ash;
        this.melting_point = melting_point;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getAsh() {
        return ash;
    }

    public void setAsh(double ash) {
        this.ash = ash;
    }

    public double getMelting_point() {
        return melting_point;
    }

    public void setMelting_point(double melting_point) {
        this.melting_point = melting_point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
