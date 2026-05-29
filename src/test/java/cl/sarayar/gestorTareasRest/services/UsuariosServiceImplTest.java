package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.repositories.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuariosServiceImplTest {

    @Mock
    private UsuariosRepository usuariosRepository;

    @InjectMocks
    private UsuariosServiceImpl usuariosService;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario("1", "Test User", "test@test.com", "password", 1);
    }

    @Test
    void save_ShouldReturnSavedUsuario() {
        when(usuariosRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        Usuario savedUser = usuariosService.save(usuarioMock);

        assertNotNull(savedUser);
        assertEquals("1", savedUser.getId());
        verify(usuariosRepository, times(1)).save(usuarioMock);
    }

    @Test
    void getAll_ShouldReturnUsuarioList() {
        when(usuariosRepository.findAll()).thenReturn(Arrays.asList(usuarioMock));

        List<Usuario> result = usuariosService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getNombre());
        verify(usuariosRepository, times(1)).findAll();
    }

    @Test
    void findByCorreo_WhenExists_ShouldReturnUsuario() {
        when(usuariosRepository.findByCorreo("test@test.com")).thenReturn(Optional.of(usuarioMock));

        Usuario result = usuariosService.findByCorreo("test@test.com");

        assertNotNull(result);
        assertEquals("test@test.com", result.getCorreo());
        verify(usuariosRepository, times(1)).findByCorreo("test@test.com");
    }

    @Test
    void findByCorreo_WhenDoesNotExist_ShouldReturnNull() {
        when(usuariosRepository.findByCorreo("notfound@test.com")).thenReturn(Optional.empty());

        Usuario result = usuariosService.findByCorreo("notfound@test.com");

        assertNull(result);
        verify(usuariosRepository, times(1)).findByCorreo("notfound@test.com");
    }

    @Test
    void findById_WhenExists_ShouldReturnUsuario() {
        when(usuariosRepository.findById("1")).thenReturn(Optional.of(usuarioMock));

        Usuario result = usuariosService.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(usuariosRepository, times(1)).findById("1");
    }

    @Test
    void findById_WhenDoesNotExist_ShouldReturnNull() {
        when(usuariosRepository.findById("2")).thenReturn(Optional.empty());

        Usuario result = usuariosService.findById("2");

        assertNull(result);
        verify(usuariosRepository, times(1)).findById("2");
    }

    @Test
    void loadUserByUsername_WhenUserExists_ShouldReturnUserDetails() {
        when(usuariosRepository.findByCorreo("test@test.com")).thenReturn(Optional.of(usuarioMock));

        UserDetails userDetails = usuariosService.loadUserByUsername("test@test.com");

        assertNotNull(userDetails);
        assertEquals("test@test.com", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_WhenUserDoesNotExist_ShouldReturnNull() {
        when(usuariosRepository.findByCorreo("notfound@test.com")).thenReturn(Optional.empty());

        UserDetails userDetails = usuariosService.loadUserByUsername("notfound@test.com");

        assertNull(userDetails);
    }

    @Test
    void existsByCorreo_ShouldReturnTrueIfUserExists() {
        when(usuariosRepository.existsByCorreo("test@test.com")).thenReturn(true);

        Boolean exists = usuariosService.existsByCorreo("test@test.com");

        assertTrue(exists);
        verify(usuariosRepository, times(1)).existsByCorreo("test@test.com");
    }
}
