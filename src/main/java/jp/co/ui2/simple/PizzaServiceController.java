package jp.co.ui2.simple;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ui2.shared.OrderConfirmation;
import jp.co.ui2.shared.PizzaOrder;
import jp.co.ui2.shared.Topping;
import lombok.val;

@RestController
@RequestMapping("/simplegwt/pizza-service")
public class PizzaServiceController {
  
  private static List<OrderConfirmation> orders = new LinkedList<>();
  
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<OrderConfirmation> order(@RequestBody String orderJson
      , UriComponentsBuilder uriComponentsBuilder) throws JsonParseException, JsonMappingException, IOException {
    
    ObjectMapper mapper = new ObjectMapper();
    PizzaOrder order = mapper.readValue(orderJson, PizzaOrder.class);
    
    OrderConfirmation confirmation = new OrderConfirmation();
    confirmation.order_id = 123123;
    confirmation.order    = order;
    confirmation.price    = 27.54;
    confirmation.ready_time = System.currentTimeMillis() + 1000 * 60 * 30; // in  30 min.
    
    val headers = new HttpHeaders();
    headers.setLocation(uriComponentsBuilder.path("/pizza-service/{id}").buildAndExpand(orders.size()).toUri());
    return new ResponseEntity<OrderConfirmation>(confirmation, headers, HttpStatus.CREATED);
  }
}
