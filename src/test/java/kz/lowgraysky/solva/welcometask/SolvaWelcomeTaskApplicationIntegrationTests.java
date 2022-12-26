package kz.lowgraysky.solva.welcometask;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SolvaWelcomeTaskApplicationIntegrationTests {

    private final Long USER_BANK_ACCOUNT_ADDRESS = 1234567890L;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnNewLimitJson() throws Exception {
        String request = "{ \"amount\": 1000, \"category\": \"service\", \"address\": " + USER_BANK_ACCOUNT_ADDRESS +" }";

        String response = " {" +
                "    amount: 1000," +
                "    category: \"SERVICE\"," +
                "    limit_currency_shortname: \"USD\"," +
                "    address: " + USER_BANK_ACCOUNT_ADDRESS + ", " +
                "    month: \"DECEMBER\" " +
                "} ";

        JSONAssert.assertEquals(
                this.mockMvc
                    .perform(post("/api/transaction/limit")
                                    .content(request)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                response,
                JSONCompareMode.LENIENT);
    }

    @Test
    public void shouldReturnTransactionInformation() throws Exception {
        String request = "{ " +
                "     \"account_from\" : " + USER_BANK_ACCOUNT_ADDRESS + ", " +
                "     \"account_to\" : 9999999999, " +
                "     \"currency_shortname\" : \"USD\", " +
                "     \"sum\" : 500 , " +
                "     \"expense_category\" : \"service\", " +
                "     \"datetime\" : \"2022-12-30 00:00:00+06\" " +
                "}";

        String response = "{ " +
                "     account_from : " + USER_BANK_ACCOUNT_ADDRESS + ", " +
                "     account_to : 9999999999, " +
                "     currency_shortname : \"USD\", " +
                "     sum : 500 , " +
                "     expense_category : \"service\", " +
                "}";

        JSONAssert.assertEquals(
                this.mockMvc
                        .perform(post("/transaction")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                response,
                JSONCompareMode.LENIENT
        );
    }

    @Test
    public void apiTransactionLimitExceedShouldReturnJson() throws Exception {
        String response = "[" +
                "{" +
                "   \"amount\":0.00," +
                "   \"category\":\"SERVICE\"," +
                "   \"limit_currency_shortname\":\"USD\"," +
                "   \"address\":1234567890," +
                "   \"month\":\"DECEMBER\"" +
                "}]";

        JSONAssert.assertEquals(
                this.mockMvc
                        .perform(get("/api/transaction/limit_exceed/" + USER_BANK_ACCOUNT_ADDRESS))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                response,
                JSONCompareMode.LENIENT
        );
    }

    @Test
    public void apiTransactionLimitsReturnJson() throws Exception {
        String response = "[" +
                "{" +
                "   \"account_from\":1234567890," +
                "   \"account_to\":9999999999," +
                "   \"currency_shortname\":\"USD\"," +
                "   \"sum\":500.00," +
                "   \"expense_category\":\"SERVICE\"," +
                "   \"limit_sum\":0.00," +
                "   \"limit_datetime\":\"2022-12-26 10:22:23+06\"," +
                "   \"limit_currency_shortname\":\"USD\"" +
                "}]";

        JSONAssert.assertEquals(
                this.mockMvc
                        .perform(get("/api/transaction/limits/" + USER_BANK_ACCOUNT_ADDRESS))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                response,
                JSONCompareMode.LENIENT
        );
    }

}
