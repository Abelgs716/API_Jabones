package com.dwes.api.servicios.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import com.dwes.api.entidades.Categoria;

import com.dwes.api.repositorios.CategoriaRepository;
import com.dwes.api.servicios.CategoriaService;

import java.util.Optional;
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public Optional<Categoria> findById(Long categoriaId) {
        return categoriaRepository.findById(categoriaId);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteById(Long categoriaId) {
        categoriaRepository.deleteById(categoriaId);
    }

  

    @Override
    public boolean existsById(Long id) {
        return categoriaRepository.existsById(id);
    }
}