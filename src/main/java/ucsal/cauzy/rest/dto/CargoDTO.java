package ucsal.cauzy.rest.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

public class CargoDTO {
    private Integer idCargo;
    private String nomeCargo;

    // Getters e Setters
    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }
}
