package es.upsa.ssbbdd2.trabajo.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor

public class CantidadIngrediente {
    private String idIng;
    private double cantidad;
    private Unidad unidad;
}
