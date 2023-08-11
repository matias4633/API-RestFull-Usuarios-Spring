package com.usuarios.controller;

import com.usuarios.model.UsuarioModel;
import com.usuarios.service.UsuarioService;
import com.usuarios.wrapper.UsuarioWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllador {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    EntityManager entityManager;

    @GetMapping()
    public ArrayList<UsuarioModel> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }
    @PostMapping()
    public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuarioModel){
        return usuarioService.guardarUsuario(usuarioModel);
    }
    @GetMapping(path = "/{id}") //Asi obtengo el parametro de la url.
    public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id){
        return usuarioService.buscarPorId(id);
    }
    @GetMapping("/query") //Saco el dato de /usuarios/query?prioridad=3
    public ArrayList<UsuarioModel> obtenerUsuariosPorPrioridad(@RequestParam("prioridad") Integer prioridad){
        return usuarioService.buscarPorPrioridad(prioridad);
    }

    @PostMapping("/json")
    public ArrayList<UsuarioModel> obtenerUsuariosSpecifications(@RequestParam String nombre , @RequestParam String email){
        Specification<UsuarioModel> condiciciones = (root , query , builder)->{
            Predicate nombreCond =  builder.like(root.get("nombre"),"%"+nombre+"%");
            Predicate emaulCond = builder.like(root.get("email"),"%"+email+"%");
            Predicate prioridad = builder.equal(root.get("prioridad") , 5);
            Predicate condO = builder.or(nombreCond, emaulCond);
            Predicate CondY = builder.and(condO,prioridad);
            return CondY;
        };
       return  usuarioService.obtenerUsuarios(condiciciones);
    }

    @PostMapping("/json/criteria")
    public List<UsuarioWrapper> obtenerUsuariosCriteria(@RequestParam String nombre , @RequestParam String email){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsuarioWrapper> query = builder.createQuery(UsuarioWrapper.class);
        Root<UsuarioModel> root = query.from(UsuarioModel.class);
        Predicate nombreCond =  builder.like(root.get("nombre"),"%"+nombre+"%");
        Predicate emaulCond = builder.like(root.get("email"),"%"+email+"%");
        Predicate prioridad = builder.equal(root.get("prioridad") , 5);
        query.where(builder.and(builder.or(nombreCond,emaulCond),prioridad));
        query.select(
                builder.construct(UsuarioWrapper.class , root.get("nombre"), root.get("email"))
        );

        return entityManager.createQuery(query).getResultList();
    }

    @DeleteMapping(path = "/{id}") //Response al Metodo DELETE
    public String borrarUsuarioPorId(@PathVariable("id") Long id){
        boolean ok=usuarioService.eliminarUsuario(id);
        if(ok){
            return "Se elimino con exito";
        }else {
            return "No se pudo eliminar el usuario con id: "+id;
        }
    }
}
