package tienda.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")  // Nombre de la columna en la tabla
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Producto> productos;



        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    
        public List<Producto> getProductos() { return productos; }
        public void setProductos(List<Producto> productos) { this.productos = productos; }
}

