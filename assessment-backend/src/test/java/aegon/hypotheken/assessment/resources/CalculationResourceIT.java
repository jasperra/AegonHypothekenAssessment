package aegon.hypotheken.assessment.resources;

import aegon.hypotheken.assessment.AssessmentApplication;
import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.repository.Calculationrepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AssessmentApplication.class)
public class CalculationResourceIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Calculationrepository calculationrepository;

    @Before
    public void init() {
        calculationrepository.deleteAll();
    }

    @Test
    public void getAllCalculations() throws Exception {
        calculationrepository.save(Calculation.builder().calculation("1 + 1 = 2").build());

        mvc.perform(get("/calculations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].calculation", is("1 + 1 = 2")));
    }

    @Test
    public void add() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/calculations/add/304/939")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        double answer = Double.parseDouble(result);

        assertEquals(1243, answer, 0);
    }

    @Test
    public void subtract() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/calculations/subtract/304/939")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        double answer = Double.parseDouble(result);

        assertEquals(-635, answer, 0);
    }

    @Test
    public void multiply() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/calculations/multiply/304/939")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        double answer = Double.parseDouble(result);

        assertEquals(285456.0, answer, 0);
    }

    @Test
    public void divide() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/calculations/divide/120/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        double answer = Double.parseDouble(result);

        assertEquals(40, answer, 0);
    }

    @Test
    public void divideByZero() throws Exception {
        mvc.perform(post("/calculations/divide/120/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
