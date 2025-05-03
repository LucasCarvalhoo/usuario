package com.carvalho.usuario.controller;

import com.carvalho.usuario.business.UsuarioService;
import com.carvalho.usuario.business.dto.EnderecoDTO;
import com.carvalho.usuario.business.dto.TelefoneDTO;
import com.carvalho.usuario.business.dto.UsuarioDTO;
import com.carvalho.usuario.infrastructure.entity.AuthResponse;
import com.carvalho.usuario.infrastructure.entity.Usuario;
import com.carvalho.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponse("Bearer " + token));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadoUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                          @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(usuarioDTO, token));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaDadoTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                            @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaDadosTelefone(telefoneDTO, id));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaDadoEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                            @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaDadosEndereco(enderecoDTO, id));
    }
}
