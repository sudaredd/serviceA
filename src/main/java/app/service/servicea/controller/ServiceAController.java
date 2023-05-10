package app.service.servicea.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/a")
@Slf4j
public class ServiceAController {

  @Autowired
  private RestTemplate restTemplate;

  public static final String URL = "http://localhost:8081";

  @GetMapping("/getById/{id}")
  @CircuitBreaker(name = "serviceA", fallbackMethod = "serviceAFallback")
  public String serviceA(@PathVariable("id") Long id) {
    log.info("incoming variable {}", id);
    String str= null;
    str.length();
    int x = 1/0;
    return restTemplate.getForObject(URL + "/b", String.class);
  }

  public String serviceAFallback(Long id, Exception ex) {
    log.info("incoming variable in fallback{}", id);
    log.info("exception type {} =>  and Nullpointer=> {}", ex.getMessage(), ex instanceof NullPointerException);
    return "this is service B fallback";
  }
}
