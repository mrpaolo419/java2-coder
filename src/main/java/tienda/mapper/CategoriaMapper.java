package tienda.mapper;


import org.springframework.stereotype.Component;
import tienda.dto.CategoriaDTO;
import tienda.model.Categoria;
import tienda.model.Producto;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class CategoriaMapper {
    public CategoriaDTO toDTOCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La entidad Categoria no puede ser nula");
        }
        // Recopilar los IDs de los productos asociados a esta categoría
        Set<Long> productoIds = categoria.getProductos().stream()
                .map(Producto::getId)
                .collect(Collectors.toSet());
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getName())
                
                .productoIds(productoIds)
                .build();
    }

    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            throw new IllegalArgumentException("El categoriaDTO no puede ser nulo");
        }

        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        categoria.setName(categoriaDTO.getNombre());
        

        return categoria;
    }
}

