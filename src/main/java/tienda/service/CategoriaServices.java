package tienda.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.dto.CategoriaDTO;

import tienda.mapper.CategoriaMapper;
import tienda.model.Categoria;

import tienda.repository.CategoriaRepository;


@Service
public class CategoriaServices {
    @Autowired
    private final CategoriaRepository categoriaRepository;
    @Autowired
    private final CategoriaMapper categoriaMapper;

    public CategoriaServices(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }


    public List<CategoriaDTO> getAllCategorias() {
        // Validar si el repositorio está vacío
        if (categoriaRepository.findAll().isEmpty()) {
            throw new RuntimeException("No se encontraron categorías");
        }
    
        // Convertir las entidades a DTO y retornarlas como lista
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDTOCategoria) // Asegúrate de usar el método correcto en el mapper
                .collect(Collectors.toList());
    }
    


    public Optional<CategoriaDTO> getCategoriaById(Long id) {
        return categoriaRepository.findAll()
            .stream()
            .findFirst()
            .map(categoriaMapper::toDTOCategoria);
    }

    public CategoriaDTO saveCategoria(CategoriaDTO categoriaDTO) {
    // Convertir el DTO en una entidad Categoria
    Categoria categoria = categoriaMapper.toEntity(categoriaDTO);

    // Guardar la categoría en el repositorio
    Categoria savedCategoria = categoriaRepository.save(categoria);

    // Convertir la categoría guardada a DTO y devolverla
    return categoriaMapper.toDTOCategoria(savedCategoria);
}

public void deleteCategoria(Long id) {
    if (categoriaRepository.existsById(id)) {
        categoriaRepository.deleteById(id);
    } else {
        throw new RuntimeException("El producto no existe");
    }
}

public CategoriaDTO updateNombreCategoria(Long id, String nuevoNombre) {
    Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró la categoría con ID: " + id));

    // Validar el nuevo nombre (opcional)
    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
    }

    // Actualizar el nombre de la categoría
    categoria.setName(nuevoNombre);

    // Guardar los cambios en la base de datos
    Categoria categoriaActualizada = categoriaRepository.save(categoria);

    // Retornar la categoría actualizada como DTO
    return categoriaMapper.toDTOCategoria(categoriaActualizada);
}

}

