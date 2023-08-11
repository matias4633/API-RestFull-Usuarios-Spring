package com.usuarios.repository;

import com.usuarios.model.UsuarioModel;
import com.usuarios.wrapper.UsuarioWrapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel,Long>, JpaSpecificationExecutor<UsuarioModel> {
    ArrayList<UsuarioModel> findByPrioridad(Integer prioridad);
    List<UsuarioModel> findAll(Specification<UsuarioModel> condicioness);

}
