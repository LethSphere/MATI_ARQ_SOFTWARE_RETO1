package co.edu.uniandes.matchengine.apihandler.dto;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class OrdenDTO {
    private String id;
    private String producto;
    private int cantidad;
    private String tipo; // "COMPRA" o "VENTA"
    private Map<String, Long> timestamps = new HashMap<>();

    public OrdenDTO(String id, String producto, int cantidad, String tipo) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.tipo = tipo;
        registrarHito("apihandler_recepcion");
    }

    public void registrarHito(String nombrePaso) {
        this.timestamps.put(nombrePaso, System.currentTimeMillis());
    }

    // Getters y Setters...
    public String getTipo() { return tipo; }
    public String getId() { return id; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public Map<String, Long> getTimestamps() { return timestamps; }
}