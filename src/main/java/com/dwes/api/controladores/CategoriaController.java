package com.dwes.api.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dwes.api.entidades.Categoria;
import com.dwes.api.entidades.Producto;
import com.dwes.api.errores.CategoriaNotFoundException;
import com.dwes.api.servicios.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista paginada de categorías")
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente")
    @ApiResponse(responseCode = "204", description = "No hay categorías disponibles")
    @ApiResponse(responseCode = "400", description = "Parámetros de solicitud incorrectos")
    public ResponseEntity<?> getAllCategorias(Pageable pageable) {
        logger.info("## getAllCategorias ##");
        Page<Categoria> page = categoriaService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una categoría por ID", description = "Devuelve una categoría específica por su ID")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class)) })
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        logger.info("## getCategoriaById ## id:({})", id);
        Categoria categoria = categoriaService.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría con ID " + id + " no encontrada"));
        return ResponseEntity.ok(categoria);
    }



    @PostMapping
    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría y la guarda en la base de datos")
    @ApiResponse(responseCode = "201", description = "Categoría creada con éxito")
    @ApiResponse(responseCode = "400", description = "Datos proporcionados para la nueva categoría son inválidos")
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria nuevaCategoria) {
        logger.info("## crearCategoria ##");
        // Realiza la validación y lógica de negocio necesaria antes de guardar la nueva categoría
        // Por ejemplo, puedes verificar si ya existe una categoría con el mismo nombre

        // Guarda la nueva categoría en la base de datos
        Categoria categoriaCreada = categoriaService.save(nuevaCategoria);

        // Devuelve una respuesta con la categoría creada y el código de estado 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría", description = "Actualiza los detalles de una categoría existente")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada para actualizar")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        logger.info("## actualizarCategoria id({}) ##", id);
        if (!categoriaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoria.setId(id); // Establece el ID del objeto a actualizar
        Categoria categoriaActualizada = categoriaService.save(categoria); // Utiliza el servicio para guardar la categoría actualizada
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una categoría", description = "Elimina una categoría existente por su ID")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada para eliminar")
    public ResponseEntity<Void> borrarCategoria(@PathVariable Long id) {
        logger.info("## borrarCategoria id:{} ##", id);
        if (!categoriaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
