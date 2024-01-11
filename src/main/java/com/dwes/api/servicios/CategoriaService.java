package com.dwes.api.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.dwes.api.entidades.Categoria;
import com.dwes.api.entidades.Producto;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Page<Categoria> findAll(Pageable pageable);

    Optional<Categoria> findById(Long categoriaId);

    Categoria save(Categoria categoria);

    void deleteById(Long categoriaId);


    boolean existsById(Long id);
}
