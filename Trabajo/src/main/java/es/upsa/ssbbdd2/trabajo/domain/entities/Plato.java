package es.upsa.ssbbdd2.trabajo.domain.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")

public class Plato {
    private String id;
    private String nombre;
    @JsonbTransient
    private String descripcion;
    @JsonbTransient
    private double precio;
    private Tipo tipo;
    private List<Ingrediente> ingredientes;
}
