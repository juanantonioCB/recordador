package com.juanantonio.recordador;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.juanantonio.recordador.model.PersonEntity;
import com.juanantonio.recordador.model.PersonaModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    private PersonaModel personModel;

    @Before
    public void setUp() {
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("DBPersona.db");
        personModel = PersonaModel.get();
    }

    @After
    public void tearDown() {
        personModel.close();
    }

    @Test
    public void testApp() {
        assertEquals(10, personModel.recuperarListado().size());

        PersonEntity p = new PersonEntity();
        p.setName("Juan");
        p.setEmail("juan@juan.com");
        p.setPhone("654987321");
        p.setLocation("Aguilar");
        p.setState(false);
        p.setProvince("Córdoba");
        p.setDate("10/10/2000");

        assertEquals(true, personModel.insertarPersona(p));

        assertEquals(11, personModel.recuperarListado().size());
        assertEquals("Manuel", personModel.recuperarPersona(1).getName());

        PersonEntity pActualizar = personModel.recuperarPersona(1);

        pActualizar.setName("Federico");
        assertEquals(true, personModel.actualizarPersona(pActualizar));
        assertEquals("Federico", personModel.recuperarPersona(1).getName());
        assertNotEquals("Federicoooooo", personModel.recuperarPersona(1).getName());

        assertEquals(true, personModel.eliminarPersona(4));
        assertEquals(false, personModel.eliminarPersona(123432));
        assertEquals(null, personModel.recuperarPersona(4));

        assertEquals(6, personModel.recuperarProvincias().size());
        assertNotEquals(10, personModel.recuperarProvincias().size());

        assertEquals(1, personModel.personasBusqueda("Federico", "", "").size());
        assertEquals(null, personModel.personasBusqueda("TESTTTT", "", ""));


    }
}
