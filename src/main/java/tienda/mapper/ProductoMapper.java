package tienda.mapper;



import org.springframework.stereotype.Component;
import tienda.dto.ProductoDTO;
import tienda.model.Producto;


@Component
public class ProductoMapper {

    private final CategoriaMapper categoriaMapper;

    public ProductoMapper(CategoriaMapper categoriaMapper) {
        this.categoriaMapper = categoriaMapper;
    }

    public ProductoDTO toDTOProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("La entidad Producto no puede ser nula");
        }

        // Convierte la categoría a DTO si existe
        String categoriaNombre = (producto.getCategory() != null) ? categoriaMapper.toDTOCategoria(producto.getCategory()).getNombre() : null;

        return ProductoDTO.builder()
                .id(producto.getId())
                .title(producto.getTitle())
                .price(producto.getPrice())
                .description(producto.getDescription())
                .image(producto.getImage())
                .category(categoriaNombre)
                .build();
    }

    public Producto toEntity(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            throw new IllegalArgumentException("El productoDTO no puede ser nulo");
        }

        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setTitle(productoDTO.getTitle());
        producto.setPrice(productoDTO.getPrice());
        producto.setDescription(productoDTO.getDescription());
        producto.setImage(productoDTO.getImage());

       

        return producto;
    }
}
