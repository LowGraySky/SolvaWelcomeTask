package kz.lowgraysky.solva.welcometask.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import java.util.List;

@Getter
@ToString
public class ErrorResponseEntityPojo implements BasePojo {

    @JsonProperty(value = "status")
    private String status = "error";

    @JsonProperty(value = "details")
    private String details;

    public ErrorResponseEntityPojo(String message){
        this.details = message;
    }

    public ErrorResponseEntityPojo(List<String> messages){
        this.details = messages.toString();
    }

}
