package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.repositories.TareasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TareasServiceImplTest {

    @Mock
    private TareasRepository tareasRepository;

    @InjectMocks
    private TareasServiceImpl tareasService;

    private Tarea tareaMock;

    @BeforeEach
    void setUp() {
        tareaMock = new Tarea();
        tareaMock.setId("1");
        tareaMock.setDescripcion("Test description");
        tareaMock.setVigente(true);
    }

    @Test
    void findAll_ShouldReturnTareaList() {
        when(tareasRepository.findAll()).thenReturn(Arrays.asList(tareaMock));
        
        List<Tarea> result = tareasService.findAll();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test description", result.get(0).getDescripcion());
        verify(tareasRepository, times(1)).findAll();
    }

    @Test
    void save_ShouldReturnSavedTarea() {
        when(tareasRepository.save(any(Tarea.class))).thenReturn(tareaMock);
        
        Tarea savedTarea = tareasService.save(tareaMock);
        
        assertNotNull(savedTarea);
        assertEquals("1", savedTarea.getId());
        verify(tareasRepository, times(1)).save(tareaMock);
    }

    @Test
    void remove_WhenIdExists_ShouldReturnTrue() {
        doNothing().when(tareasRepository).deleteById("1");
        
        boolean result = tareasService.remove("1");
        
        assertTrue(result);
        verify(tareasRepository, times(1)).deleteById("1");
    }

    @Test
    void remove_WhenIdNull_ShouldReturnFalse() {
        doThrow(new IllegalArgumentException()).when(tareasRepository).deleteById(null);
        
        boolean result = tareasService.remove(null);
        
        assertFalse(result);
        verify(tareasRepository, times(1)).deleteById(null);
    }

    @Test
    void findById_WhenIdExists_ShouldReturnTarea() {
        when(tareasRepository.findById("1")).thenReturn(Optional.of(tareaMock));
        
        Tarea result = tareasService.findById("1");
        
        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(tareasRepository, times(1)).findById("1");
    }

    @Test
    void findById_WhenIdDoesNotExist_ShouldReturnNull() {
        when(tareasRepository.findById("2")).thenReturn(Optional.empty());
        
        Tarea result = tareasService.findById("2");
        
        assertNull(result);
        verify(tareasRepository, times(1)).findById("2");
    }
}
