package com.usuarios.service;

import com.usuarios.model.UsuarioModel;
import com.usuarios.repository.UsuarioRepository;
import com.usuarios.wrapper.UsuarioWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired //Instancia automaticamente la variable y en caso que la necesite la inyecta.
    UsuarioRepository usuarioRepository;

    public ArrayList<UsuarioModel> obtenerUsuarios(){
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
    }

    public ArrayList<UsuarioModel> obtenerUsuarios(Specification<UsuarioModel> condicioness){
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll(condicioness);
    }
    public UsuarioModel guardarUsuario(UsuarioModel usuarioModel){
        return (UsuarioModel) usuarioRepository.save(usuarioModel);
    }

    public Optional<UsuarioModel> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public ArrayList<UsuarioModel> buscarPorPrioridad(Integer prioridad){
        return usuarioRepository.findByPrioridad(prioridad);
    }

    public boolean eliminarUsuario(Long id){
        try {
            usuarioRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
