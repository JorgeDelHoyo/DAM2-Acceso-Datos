package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.checkerframework.checker.units.qual.A;
import org.example.model.Autor;
import org.example.model.Editorial;
import org.example.model.Libro;
import org.example.service.BibliotecaService;
import org.example.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BibliotecaServiceTest {

    @Mock private EntityManager emMock;
    @Mock private EntityTransaction txMock;
    private MockedStatic<JPAUtil> mockedJpaUtil;

    private BibliotecaService service;

    @BeforeEach
    public void setUp(){
        mockedJpaUtil = mockStatic(JPAUtil.class);
        mockedJpaUtil.when(JPAUtil::getEntityManager).thenReturn(emMock);
        lenient().when(emMock.getTransaction()).thenReturn(txMock);

        service = new BibliotecaService();
    }

    @AfterEach
    public void tearDown(){
        mockedJpaUtil.close();
    }

// ==================================================================================================
// ==================================================================================================
// ==================================================================================================
    @Test
    public void testAddAutorALibro_Exito(){
        Autor autorMock = new Autor("Pepe","España");
        Libro libroMock = new Libro("Libro1","123",10.0);

        when(emMock.find(Autor.class,1L)).thenReturn(autorMock);
        when(emMock.find(Libro.class,2L)).thenReturn(libroMock);

        service.addAutorALibro(1L,2L);

        verify(txMock).begin();
        verify(emMock).merge(libroMock);
        verify(txMock).commit();
        verify(emMock).close();
    }

    @Test
    public void testAddAutorALibro_Fracaso(){
        when(emMock.find(Autor.class,1L)).thenReturn(null);
        when(emMock.find(Libro.class,2L)).thenReturn(null);

        service.addAutorALibro(1L,2L);
        verify(txMock).begin();
        verify(emMock,never()).merge(any());
        verify(txMock).commit();
    }

    @Test
    public void testAddAutorALibro_Exception(){
        doThrow(new RuntimeException("Error fatal DB")).when(txMock).begin();

        // IF dentro del catch
        when(txMock.isActive()).thenReturn(true);

        // Al explotar el tx begin, saltará al catch directo
        service.addAutorALibro(1L,2L);

        verify(txMock).rollback();
        verify(emMock).close();
    }

// ==================================================================================================
// ==================================================================================================
// ==================================================================================================

    @Test
    public void testAddLibroConAutores_Exito(){

        Editorial editorialMock = new Editorial();
        Autor autorMock = new Autor();

        when(emMock.find(Editorial.class,1L)).thenReturn(editorialMock);
        when(emMock.find(Autor.class,5L)).thenReturn(autorMock);

        service.addLibroConAutores(1L,"Titulo","123",12.2, List.of(5L));

        verify(txMock).begin();
        verify(emMock).merge(editorialMock);
        verify(txMock).commit();
        verify(emMock).close();
    }

    @Test
    public void testAddLibroConAutores_FracasoEditorial(){
        when(emMock.find(Editorial.class,1L)).thenReturn(null);

        service.addLibroConAutores(1L,"Titulo","123",12.0,List.of(2L));

        verify(txMock).begin();
        verify(emMock,never()).merge(any());
        verify(txMock).commit();
        verify(emMock).close();
    }

    @Test
    public void testAddLibroConAutores_FracasoAutor(){
        Editorial editorialMock = new Editorial("a","b");

        when(emMock.find(Editorial.class,1L)).thenReturn(editorialMock);
        when(emMock.find(Autor.class,5L)).thenReturn(null);

        service.addLibroConAutores(1L,"1","23",2.2,List.of(5L));

        verify(txMock).begin();
        verify(emMock).merge(editorialMock);
        verify(txMock).commit();
        verify(emMock).close();
    }

    @Test
    public void testAddLibroConAutores_Exception(){
        doThrow(new RuntimeException("Error try")).when(txMock).begin();

        when(txMock.isActive()).thenReturn(true);

        service.addLibroConAutores(1L,"123","3",2.3,List.of(2L));

        verify(txMock).begin();
        verify(txMock).rollback();
        verify(emMock).close();

    }

// ==================================================================================================
// ==================================================================================================
// ==================================================================================================

    @Test
    public void testListarAutoresLibro(){
        Libro libroMock = new Libro("a","b",3.3);

        Autor autorFalso = new Autor("Cervantes", "España");
        libroMock.addAutor(autorFalso);

        when(emMock.find(Libro.class,1L)).thenReturn(libroMock);

        service.listarAutoresLibro(1L);

        verify(emMock).close();
    }

    @Test
    public void testListarAutoresLibro_LibroNull(){
        when(emMock.find(Libro.class,1L)).thenReturn(null);
        service.listarAutoresLibro(1L);

        verify(emMock).close();
    }

// ==================================================================================================
// ==================================================================================================
// =================================================================================================

    @Test
    public void testListarLibrosAutor(){
        Autor autorMock = new Autor();
        autorMock.setNombre("A");
        autorMock.setNacionalidad("B");
        //==========================================================//
        Editorial e = new Editorial();
        Libro l = new Libro();

        l.setEditorial(e);
        e.addLibro(l);
        e.removeLibro(l);
        e.setNombre("A");
        e.getPais();
        e.setPais("A");
        e.getLibros();
        e.setLibros(List.of(l));
        //==========================================================//
        Libro libroFalso = new Libro();
        libroFalso.setId(1L);
        libroFalso.setTitulo("A");
        libroFalso.setIsbn("b");
        libroFalso.setEditorial(e);
        libroFalso.setPrecio(2.2);
        libroFalso.setAutores(List.of(autorMock));
        //==========================================================//
        assertEquals(1L,libroFalso.getId());
        assertEquals("A",libroFalso.getTitulo());
        assertEquals("b",libroFalso.getIsbn());
        assertEquals(2.2,libroFalso.getPrecio());
        assertEquals(e,libroFalso.getEditorial());
        //==========================================================//

        autorMock.setLibros(List.of(libroFalso));

        when(emMock.find(Autor.class,1L)).thenReturn(autorMock);

        service.listarLibrosAutor(1L);



        verify(emMock).find(Autor.class,1L);
        verify(emMock).close();
    }

    @Test
    public void testListarLibrosPorAutor_AutorNull(){
        when(emMock.find(Autor.class,1L)).thenReturn(null);

        service.listarLibrosAutor(1L);

        verify(emMock).close();
    }

// ==================================================================================================
// ==================================================================================================
// ==================================================================================================

    @Test
    public void testCrearAutor(){

        service.crearAutor("A","B");

        verify(txMock).begin();
        verify(emMock).persist(any(Autor.class));
        verify(txMock).commit();
        verify(emMock).close();
    }

    @Test
    public void testCrearAutor_Exception(){
        doThrow(new RuntimeException("Error")).when(txMock).begin();
        when(txMock.isActive()).thenReturn(true);

        service.crearAutor("A","B");

        verify(txMock).begin();
        verify(txMock).rollback();
        verify(emMock).close();
    }

    @Test
    public void testCrearAutor_ExceptionNotActive(){
        doThrow(new RuntimeException("Error")).when(txMock).begin();
        when(txMock.isActive()).thenReturn(false);

        service.crearAutor("A","B");

        verify(txMock).begin();
        verify(emMock).close();
    }

// ==================================================================================================
// ==================================================================================================
// ==================================================================================================

    @Test
    public void testCrearEditorial(){

        service.crearEditorial("a","a");

        verify(txMock).begin();
        verify(emMock).persist(any(Editorial.class));
        verify(txMock).commit();
        verify(emMock).close();
    }

    @Test
    public void testCrearEditorial_Exception(){
        doThrow(new RuntimeException("Error")).when(txMock).begin();
        when(txMock.isActive()).thenReturn(true);

        service.crearEditorial("a","a");

        verify(txMock).begin();
        verify(txMock).rollback();
        verify(emMock).close();
    }

    @Test
    public void testCrearEditorial_ExceptionNotActive(){
        doThrow(new RuntimeException("Error")).when(txMock).begin();
        when(txMock.isActive()).thenReturn(false);

        service.crearEditorial("A","B");

        verify(txMock).begin();
        verify(emMock).close();
    }
}
