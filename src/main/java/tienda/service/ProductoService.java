package tienda.service;




import java.util.List;

import tienda.dto.ProductoDTO;

public interface ProductoService {

   
    ProductoDTO getProductoById(Long id);

    

    
    ProductoDTO saveProductoDTO(ProductoDTO productoDTO);

    
    List<ProductoDTO> getAllProductos();

    ProductoDTO saveProductoDTOApi(ProductoDTO productoDTO);

    // Eliminar un producto por su ID
    void deleteProducto(Long id);
}

