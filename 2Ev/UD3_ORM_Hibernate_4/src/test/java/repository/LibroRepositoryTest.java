package repository;

import jakarta.persistence.EntityManager;
import org.example.model.Libro;
import org.example.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibroRepositoryTest {

    @Mock private EntityManager emMock;

    @InjectMocks private LibroRepository repository;

    private Libro libro;

    @BeforeEach
    public void setUp(){
        libro = new Libro();
        libro.setId(1L);
    }

    @Test
    public void testFindByID(){
        when(emMock.find(Libro.class,1L)).thenReturn(libro);
        Libro result = repository.findById(emMock,1L);

        assertEquals(result, libro);
        verify(emMock).find(Libro.class,1L);
    }

    @Test
    public void testPersist(){
        repository.persist(emMock,libro);
        verify(emMock).persist(libro);
    }

    @Test
    public void testMerge(){
        when(emMock.merge(libro)).thenReturn(libro);
        Libro result = repository.merge(emMock,libro);

        assertEquals(result,libro);
        verify(emMock).merge(libro);
    }

    @Test
    public void testRemove(){
        repository.remove(emMock,libro);
        verify(emMock).remove(libro);
    }
}
