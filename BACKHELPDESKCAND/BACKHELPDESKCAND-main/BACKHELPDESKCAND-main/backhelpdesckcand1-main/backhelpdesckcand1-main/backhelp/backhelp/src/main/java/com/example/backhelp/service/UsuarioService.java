package com.example.backhelp.service;

import com.example.backhelp.dto.LoginRequestDTO;
import com.example.backhelp.dto.TokenResponseDTO;
import com.example.backhelp.dto.UsuarioRequestDTO;
import com.example.backhelp.dto.UsuarioResponseDTO;
import com.example.backhelp.model.UsuarioModel;
import com.example.backhelp.repository.UsuarioRepository;
import com.example.backhelp.security.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (dto.email() == null || dto.email().isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail é obrigatório."
            );
        }

        if (!isEmailCorporativo(dto.email())) {
            throw new IllegalArgumentException(
                    "O e-mail deve pertencer ao domínio @helpdeskcand.com."
            );
        }

        if (dto.senha() == null || dto.senha().isBlank()) {
            throw new IllegalArgumentException(
                    "A senha é obrigatória."
            );
        }

        if (dto.perfil() == null) {
            throw new IllegalArgumentException(
                    "O perfil do usuário é obrigatório."
            );
        }

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException(
                    "E-mail já cadastrado."
            );
        }

        UsuarioModel usuario = new UsuarioModel();

        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setSetor(dto.setor());
        usuario.setCargo(dto.cargo());
        usuario.setPerfil(dto.perfil());

        usuario.setEmailConfirmado(true);

        UsuarioModel salvo = usuarioRepository.save(usuario);

        return toDTO(salvo);
    }

    @Transactional
    public UsuarioResponseDTO editarUsuario(
            Long id,
            UsuarioRequestDTO dto
    ) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário não encontrado."
                        )
                );

        if (dto.email() != null && !dto.email().isBlank()) {

            if (!isEmailCorporativo(dto.email())) {
                throw new IllegalArgumentException(
                        "O e-mail deve pertencer ao domínio @helpdeskcand.com."
                );
            }

            if (!usuario.getEmail().equals(dto.email())
                    && usuarioRepository.existsByEmail(dto.email())) {
                throw new IllegalArgumentException(
                        "E-mail já cadastrado."
                );
            }

            usuario.setEmail(dto.email());
        }

        if (dto.setor() != null) {
            usuario.setSetor(dto.setor());
        }

        if (dto.cargo() != null) {
            usuario.setCargo(dto.cargo());
        }

        if (dto.perfil() != null) {
            usuario.setPerfil(dto.perfil());
        }

        UsuarioModel atualizado = usuarioRepository.save(usuario);

        return toDTO(atualizado);
    }

    @Transactional(readOnly = true)
    public TokenResponseDTO login(LoginRequestDTO dto) {

        if (dto.email() == null
                || !isEmailCorporativo(dto.email())) {
            throw new BadCredentialsException(
                    "E-mail ou senha inválidos."
            );
        }

        UsuarioModel usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "E-mail ou senha inválidos."
                        )
                );

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new BadCredentialsException(
                    "E-mail ou senha inválidos."
            );
        }

        String token = tokenProvider.gerarToken(
                usuario.getEmail(),
                usuario.getPerfil().name()
        );

        return new TokenResponseDTO(
                token,
                "Bearer",
                toDTO(usuario)
        );
    }

    @Transactional
    public UsuarioResponseDTO confirmarEmail(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário não encontrado."
                        )
                );

        usuario.setEmailConfirmado(true);

        return toDTO(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private boolean isEmailCorporativo(String email) {
        return email.matches(
                "^[a-zA-Z0-9._%+-]+@helpdeskcand\\.com$"
        );
    }

    private UsuarioResponseDTO toDTO(UsuarioModel model) {
        return new UsuarioResponseDTO(
                model.getId(),
                model.getEmail(),
                model.getSetor(),
                model.getCargo(),
                model.getPerfil(),
                model.isEmailConfirmado()
        );
    }
}