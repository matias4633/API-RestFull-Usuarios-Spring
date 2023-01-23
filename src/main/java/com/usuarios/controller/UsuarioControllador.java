package com.usuarios.controller;

import com.usuarios.model.UsuarioModel;
import com.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllador {
    @Autowired
    UsuarioService usuarioService;

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
