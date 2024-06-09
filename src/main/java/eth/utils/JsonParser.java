package eth.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    public String parseEthPriceJSON(String ethPriceJSON) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(ethPriceJSON);
            String price = jsonNode.get("c").asText();
            return price;
        } catch (Exception e) {
            return "Error occured  : " + e;
        }
    }


}
