package es.upsa.ssbbdd2.trabajo.domain.entities;

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
public class MenuImport {
    private String nombre;
    private LocalDate desde;
    private LocalDate hasta;
    private List<String> platos;

}