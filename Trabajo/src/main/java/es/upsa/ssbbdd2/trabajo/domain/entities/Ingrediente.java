package es.upsa.ssbbdd2.trabajo.domain.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")

public class Ingrediente {
    private String id;
    private String nombre;
}
