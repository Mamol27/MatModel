package model;

/**
 * Created by Илья on 26.05.2015.
 */
public class Ecotmm {
    int id;
    double consistency;
    double temperatury_coef;
    double temp_reduction;
    double flow_index_material;
    double heat_transfer;

    public Ecotmm(int id, double consistency, double temperatury_coef, double temp_reduction, double flow_index_material, double heat_transfer) {
        this.id = id;
        this.consistency = consistency;
        this.temperatury_coef = temperatury_coef;
        this.temp_reduction = temp_reduction;
        this.flow_index_material = flow_index_material;
        this.heat_transfer = heat_transfer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getConsistency() {
        return consistency;
    }

    public void setConsistency(double consistency) {
        this.consistency = consistency;
    }

    public double getTemperatury_coef() {
        return temperatury_coef;
    }

    public void setTemperatury_coef(double temperatury_coef) {
        this.temperatury_coef = temperatury_coef;
    }

    public double getTemp_reduction() {
        return temp_reduction;
    }

    public void setTemp_reduction(double temp_reduction) {
        this.temp_reduction = temp_reduction;
    }

    public double getFlow_index_material() {
        return flow_index_material;
    }

    public void setFlow_index_material(double flow_index_material) {
        this.flow_index_material = flow_index_material;
    }

    public double getHeat_transfer() {
        return heat_transfer;
    }

    public void setHeat_transfer(double heat_transfer) {
        this.heat_transfer = heat_transfer;
    }
}
