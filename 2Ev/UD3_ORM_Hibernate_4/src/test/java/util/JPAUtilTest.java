package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JPAUtilTest {

    @Mock private EntityManagerFactory emfMock;
    @Mock private EntityManager emMock;

    private MockedStatic<Persistence> mockedPersistence;

    @BeforeEach
    public void setUp() throws Exception {
        // TRUCO MÁGICO: Limpiamos la variable estática "emf" antes de cada test para que empiecen de cero
        Field field = JPAUtil.class.getDeclaredField("emf");
        field.setAccessible(true);
        field.set(null, null);

        mockedPersistence = mockStatic(Persistence.class);

        mockedPersistence.when(() -> Persistence.createEntityManagerFactory("entrega_orm_hibernate_4")).thenReturn(emfMock);

        lenient().when(emfMock.createEntityManager()).thenReturn(emMock);
    }

    @AfterEach
    public void tearDown(){
        mockedPersistence.close();
    }

    @Test
    public void testGetEntityManager_primeraVez(){
        EntityManager result = JPAUtil.getEntityManager();

        assertNotNull(result);

        mockedPersistence.verify(() -> Persistence.createEntityManagerFactory("entrega_orm_hibernate_4"),times(1));
    }

    @Test
    public void testGetEntityManager_segundaVez(){
        JPAUtil.getEntityManager();

        JPAUtil.getEntityManager();

        mockedPersistence.verify(() -> Persistence.createEntityManagerFactory("entrega_orm_hibernate_4"),times(1));
    }

    @Test
    public void testShutDown_Abierto(){
        JPAUtil.getEntityManager();

        when(emfMock.isOpen()).thenReturn(true);

        JPAUtil.shutdown();

        verify(emfMock,times(1)).close();
    }

    @Test
    public void testShutDown_Cerrado(){
        JPAUtil.shutdown();

        JPAUtil.getEntityManager();

        when(emfMock.isOpen()).thenReturn(false);
        JPAUtil.shutdown();

        verify(emfMock,never()).close();
    }
}
