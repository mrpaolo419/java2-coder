package tienda.dto;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriaDTO {

    @Schema(description = "Id of the category", example = "1")
    private Long id;

    @Schema(description = "Name of the category", example = "Bread")
    private String nombre;

    @Schema(description = "Products in this category")
    private Set<Long> productoIds;

    // Constructor sin argumentos
    public CategoriaDTO() {
    }

    // Constructor con todos los argumentos
    public CategoriaDTO(Long id, String nombre, Set<Long> productoIds) {
        this.id = id;
        this.nombre = nombre;
        this.productoIds = productoIds;
    }

    // Getters y Setters
    public Set<Long> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(Set<Long> productoIds) {
        this.productoIds = productoIds;
    }
}

