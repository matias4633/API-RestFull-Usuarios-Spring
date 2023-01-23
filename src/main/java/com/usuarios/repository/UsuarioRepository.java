package com.usuarios.repository;

import com.usuarios.model.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel,Long> {
    ArrayList<UsuarioModel> findByPrioridad(Integer prioridad);
}
