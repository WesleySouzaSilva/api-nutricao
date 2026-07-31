package br.com.nutricao.security;

import br.com.nutricao.domain.Usuario;
import br.com.nutricao.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado: " + email));

        return new UsuarioDetails(usuario);
    }

    public UserDetails loadUserByTokenId(String tokenId) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado para o token informado"));

        return new UsuarioDetails(usuario);
    }
}
