package tienda.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import tienda.dto.ProductoDTO;
import tienda.mapper.ProductoMapper;
import tienda.service.FakeStoreApiService;
import tienda.utils.ApiMss;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class TiendaController {

    private final FakeStoreApiService productoService;

    @Autowired
    public TiendaController(FakeStoreApiService productoService) {
        this.productoService = productoService;
    }

    @Autowired
    public ProductoMapper productoMapper;

    @PostMapping("/import/{id}")
    @Operation(summary = "Importa los productos desde una API externa", description = "Inyecta en el modelo los datos de la API y los transforma en la entidad asociada.")
    public ResponseEntity<ProductoDTO> importProductoFromApi(@PathVariable Long id) {
        try {
            ProductoDTO productoDTO = productoService.saveProductoFromApi(id);
            return new ResponseEntity<>(productoDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/createProducto")
    @Operation(summary = "Crear un producto asociado a una tienda", description = "Crea un producto y lo asocia a una tienda. Retorna un estado HTTP si se crea.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not created", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "ProductNotCreated", value = "{\"message\": \"Product not created\"}", description = "Producto no creado")
        }))
    })
    public ResponseEntity<?> addProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO createdProducto = productoService.saveProductoDTO(productoDTO);
        return new ResponseEntity<>(createdProducto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Obtener todos los productos", description = "Retorna la lista de productos creados y de la API")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                    @ExampleObject(name = "ProductsNotFound", value = "{\"message\": \"Products not found\"}", description = "Productos no encontrados")
            }))
    })
    public ResponseEntity<?> getAllProductos() {
        try {
            List<ProductoDTO> productos = productoService.getAllProductos();
            return ResponseEntity.ok().body(new ApiMss("Lista de Productos", productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMss("NO HAY PRODUCTOS", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por su id", description = "Retorna el producto asociado a la id proporcionada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "ProductNotFound", value = "{\"message\": \"Product not found\"}", description = "Producto no encontrado")
        }))
    })
    public ResponseEntity<?> getProductoById(@PathVariable Long id){
        try {
            productoService.getProductoById(id);
            return ResponseEntity.ok().body(new ApiMss("Producto:", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: Producto no encontrado " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un producto", description = "Borra un producto asociado a la id proporcionada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not deleted", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "ProductNotDeleted", value = "{\"message\": \"Product not deleted\"}", description = "Producto no borrado")
        }))
    })
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.ok().body(new ApiMss("Producto Eliminado", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMss("Error: No se pudo eliminar el producto", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar producto por su id", description = "Retorna el producto con sus campos modificados")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ProductoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not modified", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "ProductNotModified", value = "{\"message\": \"Product not modified\"}", description = "Producto no modificado")
        }))
    })
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){
        ProductoDTO updatedProducto = productoService.updateProductoDTO(id, productoDTO);
        return ResponseEntity.ok().body(updatedProducto);
    }

}
