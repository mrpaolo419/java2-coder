package tienda.service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;
import tienda.dto.ProductoDTO;
import tienda.mapper.ProductoMapper;

import tienda.model.Producto;

import tienda.repository.ProductoRepository;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FakeStoreApiService  {

    
    private final String apiUrl = "https://fakestoreapi.com/products";

    @Autowired
    private final ProductoRepository productoRepository;
    @Autowired
    private final ProductoMapper productoMapper;
    @Autowired
    private RestTemplate restTemplate;

    public FakeStoreApiService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

public List<ProductoDTO> getAllProductos() {
    // Obtener los productos de la base de datos
    List<ProductoDTO> productosDB = productoRepository.findAll().stream()
        .map(productoMapper::toDTOProducto)
        .collect(Collectors.toList());

    // Obtener los productos de la API externa
    Producto[] productosAPI = restTemplate.getForObject(apiUrl, Producto[].class);

    // Si la API devuelve datos, mapeamos y añadimos a la lista
    if (productosAPI != null) {
        for (Producto producto : productosAPI) {
            productosDB.add(productoMapper.toDTOProducto(producto));
        }
    }

    // Retornar la lista combinada
    return productosDB;
}


public ProductoDTO getProductoById(Long id) {
    Optional<Producto> optionalProducto = productoRepository.findById(id);

    if (optionalProducto.isPresent()) {
        // Si el producto está en la base de datos, lo convierte a DTO
        return productoMapper.toDTOProducto(optionalProducto.get());
    } else {
        // Si el producto no está en la base de datos, buscar en la API externa
        ProductoDTO productoDTO = restTemplate.getForObject(apiUrl + "/{id}", ProductoDTO.class, id);
        if (productoDTO != null) {
            return productoDTO;
        } else {
            throw new RuntimeException("Producto no encontrado ni en la base de datos ni en la API externa");
        }
    }
}


@Transactional
public ProductoDTO saveProductoFromApi(Long id) {
    ProductoDTO productoDTO = restTemplate.getForObject(apiUrl + "/{id}", ProductoDTO.class, id);

    if (productoDTO != null) {
        Producto producto = productoMapper.toEntity(productoDTO);
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTOProducto(savedProducto);
    } else {
        throw new RuntimeException("Producto no encontrado en la API con ID: " + id);
    }
}

public ProductoDTO saveProductoDTO(ProductoDTO productoDTO) {
    // Crear un nuevo producto a partir del DTO
    Producto producto = productoMapper.toEntity(productoDTO);

    
    
    // Guarda el producto en la base de datos
    Producto savedProducto = productoRepository.save(producto);

    // Retorna el DTO del producto guardado
    return productoMapper.toDTOProducto(savedProducto);
}

public void deleteProducto(Long id) {
    if (productoRepository.existsById(id)) {
        productoRepository.deleteById(id);

        // Eliminar el cliente en la API externa
        restTemplate.delete(apiUrl + "/{id}", id);
    } else {
        throw new RuntimeException("Cliente no encontrado con ID: " + id);
    }
}

public ProductoDTO updateProductoDTO(Long id, ProductoDTO productoDTO) {
    return productoRepository.findById(id)
        .map(producto -> {
            producto.setTitle(productoDTO.getTitle());
            producto.setPrice(productoDTO.getPrice());
            producto.setDescription(productoDTO.getDescription());
            producto.setImage(productoDTO.getImage());
            // Guarda el producto actualizado y conviértelo a DTO
            Producto updatedProducto = productoRepository.save(producto);
            return productoMapper.toDTOProducto(updatedProducto);
        })
        .orElse(null);
}



}