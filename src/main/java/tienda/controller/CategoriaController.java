package tienda.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import tienda.dto.CategoriaDTO;
import tienda.service.CategoriaServices;

import tienda.utils.ApiMss;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;


@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaServices categoriaService;

    @GetMapping(path = "/all")
    @Operation(summary = "Obtener todas las categorías", description = "Retorna todas las categorías")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categories not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                    @ExampleObject(name = "CategoriesNotFound", value = "{\"message\": \"Categories not found\"}", description = "Categorías no encontradas")
            }))})
    public ResponseEntity<?> getAllCategorias() {
        try {
            List<CategoriaDTO> categorias = categoriaService.getAllCategorias();
            return ResponseEntity.ok().body(new ApiMss("Lista de categorías", categorias));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMss("NO HAY CATEGORÍAS", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una categoría por su id", description = "Retorna la categoría asociada al id proporcionado")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "CategoryNotFound", value = "{\"message\": \"Category not found\"}", description = "Categoría no encontrada")
        }))})
    public ResponseEntity<?> getCategoriaById(@PathVariable("id") Long id){
        try {
            Optional<CategoriaDTO> categoria = categoriaService.getCategoriaById(id);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMss("Categoría no encontrada", e.getMessage()));
        }
    }

    @PostMapping("/createCategory")
    @Operation(summary = "Crear una categoría", description = "Retorna la categoría creada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Category not created", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "CategoryNotCreated", value = "{\"message\": \"Category not created\"}", description = "Categoría no creada")
        }))})
    public ResponseEntity<CategoriaDTO> createCategory(@RequestBody CategoriaDTO categoriaDTO){
        CategoriaDTO createdCategory = categoriaService.saveCategoria(categoriaDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra una categoría", description = "Retorna mensaje de categoría eliminada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Category not deleted", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "CategoryNotDeleted", value = "{\"message\": \"Category not deleted\"}", description = "Categoría no borrada")
        }))})
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
        try {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.ok().body(new ApiMss("Categoría Eliminada", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMss("Error: No se pudo eliminar la Categoría", null));
        }
    }

    @PatchMapping("/{id}/nombre")
    @Operation(summary = "Modifica el nombre de una categoría asociada a un ID", description = "Exige el nuevo nombre que hay que modificar.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Name not modified", content = @Content(schema = @Schema(implementation = ApiResponse.class), examples = {
                @ExampleObject(name = "NameNotModified", value = "{\"message\": \"Name not modified\"}", description = "Nombre no modificado")
        }))})
    public ResponseEntity<?> updateNombre(@PathVariable Long id, @RequestParam String nuevoNombre) {
        try {
            categoriaService.updateNombreCategoria(id, nuevoNombre);
            return ResponseEntity.ok().body(new ApiMss("Nombre de la categoría actualizado", id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada con id: " + id);
        }
    }




}
