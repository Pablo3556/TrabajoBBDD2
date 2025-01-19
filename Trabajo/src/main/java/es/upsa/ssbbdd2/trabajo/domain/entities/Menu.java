package es.upsa.ssbbdd2.trabajo.domain.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Menu{
    private String idM;
    private String idP;
    private String nombreM;
    private double precio;
    @JsonbTransient
    private LocalDate fechaDesde;
    @JsonbTransient
    private LocalDate fechaHasta;

    private List<Plato> platos;
}
