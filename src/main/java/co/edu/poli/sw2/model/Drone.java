package co.edu.poli.sw2.model;

public class Drone {

    private int id;
    private String senal;
    private String modelo;
    private String fabricante;
    private double peso;


    public Drone() {
    }


    public Drone(int id, String senal, String modelo, String fabricante, double peso) {
        this.id = id;
        this.senal = senal;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.peso = peso;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getSenal() {
        return senal;
    }


    public void setSenal(String senal) {
        this.senal = senal;
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


    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", senal='" + senal + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", peso=" + peso +
                '}';
    }
}