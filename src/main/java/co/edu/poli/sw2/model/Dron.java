package co.edu.poli.sw2.model;

public class Dron {

    private int id;
    private String serial;
    private String modelo;
    private String fabricante;
    private double peso;
    private int pilotoId;
    private int misionId;

    public Dron() {
    }

    public Dron(int id, String serial, String modelo, String fabricante,
                 double peso, int pilotoId, int misionId) {

        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.peso = peso;
        this.pilotoId = pilotoId;
        this.misionId = misionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(int pilotoId) {
        this.pilotoId = pilotoId;
    }

    public int getMisionId() {
        return misionId;
    }

    public void setMisionId(int misionId) {
        this.misionId = misionId;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", peso=" + peso +
                ", pilotoId=" + pilotoId +
                ", misionId=" + misionId +
                '}';
    }
}