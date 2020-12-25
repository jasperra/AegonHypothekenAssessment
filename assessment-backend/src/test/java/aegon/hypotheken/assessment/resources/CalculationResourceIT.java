package aegon.hypotheken.assessment.resources;

import aegon.hypotheken.assessment.AssessmentApplication;
import aegon.hypotheken.assessment.dto.CalculationRequest;
import aegon.hypotheken.assessment.dto.OperationRequest;
import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.model.Operator;
import aegon.hypotheken.assessment.repository.Calculationrepository;
import com.google.gson.Gson;
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

import java.util.Arrays;
import java.util.Collections;

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

    private final Gson gson = new Gson();

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
        MvcResult mvcResult = mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(304, Operator.ADD, 939)))
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
        MvcResult mvcResult = mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(304, Operator.SUBTRACT, 939)))
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
        MvcResult mvcResult = mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(304, Operator.MULTIPLY, 939)))
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
        MvcResult mvcResult = mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(120, Operator.DIVIDE, 3)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        double answer = Double.parseDouble(result);

        assertEquals(40, answer, 0);
    }

    @Test
    public void calculateMultipleAtOnce() throws Exception {
        String content = gson.toJson(CalculationRequest.builder()
                .startingNumber(500)
                .operations(Arrays.asList(OperationRequest.builder()
                                .operator(Operator.ADD)
                                .value(265)
                                .build(),
                        OperationRequest.builder()
                                .operator(Operator.MULTIPLY)
                                .value(5)
                                .build()))
                .build());

        MvcResult mvcResult = mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        double answer = Double.parseDouble(result);

        assertEquals(3825.0, answer, 0);
    }


    @Test
    public void operatorListCantBeNull() throws Exception {
        String content = gson.toJson(CalculationRequest.builder()
                .startingNumber(500)
                .build());

        mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void operatorListCantBeEmpty() throws Exception {
        String content = gson.toJson(CalculationRequest.builder()
                .startingNumber(500)
                .operations(Collections.emptyList())
                .build());

        mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void operatorsAreRequired() throws Exception {
        String content = gson.toJson(CalculationRequest.builder()
                .startingNumber(500)
                .operations(Collections.singletonList(OperationRequest.builder()
                        .value(265)
                        .build()))
                .build());

        mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void divideByZero() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/calculations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest(120, Operator.DIVIDE, 0)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals("\"Infinity\"", result);
    }

    private String createRequest(int start, Operator operator, int number) {
        return gson.toJson(CalculationRequest.builder()
                .startingNumber(start)
                .operations(Collections.singletonList(OperationRequest.builder()
                        .operator(operator)
                        .value(number)
                        .build()))
                .build());
    }
}
