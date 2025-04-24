package gestor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deuda {
    private String nombre;
    private double monto;
    private int año;
    private String tipoDeuda;
    private boolean pagada;
    private LocalDateTime fechaPago;

    public Deuda(String nombre, double monto, int año, String tipoDeuda) {
        assert monto > 0 : "El monto debe ser mayor que cero";
        assert año > 0 : "El año debe ser mayor que cero";
        assert tipoDeuda != null && !tipoDeuda.isEmpty() : "El tipo de deuda no puede ser nulo o vacío";
        assert nombre != null && !nombre.isEmpty() : "El nombre no puede ser nulo o vacío";

        this.nombre = nombre;
        this.monto = monto;
        this.año = año;
        this.tipoDeuda = tipoDeuda;
        this.pagada = false;
        this.fechaPago = null;
    }

    public String getNombre() { return nombre; }
    public double getMonto() { return monto; }
    public int getAño() { return año; }
    public String getTipoDeuda() { return tipoDeuda; }

    public void marcarComoPagada() {
        this.pagada = true;
        this.fechaPago = LocalDateTime.now();
    }

    public boolean estaPagada() {
        return pagada;
    }

    public String getFechaPago() {
        if (fechaPago != null) {
            return fechaPago.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        }
        return null;
    }

    @Override
    public String toString() {
        String base = "Nombre: " + nombre + ", Monto: $" + monto + ", Año: " + año + ", Tipo: " + tipoDeuda;
        if (pagada) {
            return base + " [PAGADA: " + getFechaPago() + "]";
        }
        return base;
    }
}
