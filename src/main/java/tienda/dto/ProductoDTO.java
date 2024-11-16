package tienda.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoDTO {

    @Schema(description = "Identificador único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Pan Integral")
    private String title;

    @Schema(description = "Precio del producto", example = "10.5")
    private Double price;

    @Schema(description = "Descripción detallada del producto", example = "Pan elaborado con harina integral")
    private String description;

    @Schema(description = "URL de la imagen del producto", example = "https://example.com/images/pan_integral.jpg")
    private String image;

    @Schema(description = "Categoría del producto", example = "Alimentos")
    private String category;

    public ProductoDTO() {
    }

    // Constructor
    public ProductoDTO(Long id, String title, Double price, String description, String image, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
        
        
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
