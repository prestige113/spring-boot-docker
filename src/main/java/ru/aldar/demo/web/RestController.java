package ru.aldar.demo.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.aldar.demo.domain.Product;
import ru.aldar.demo.dto.ProductCriteria;
import ru.aldar.demo.service.IInitService;
import ru.aldar.demo.service.ISearchService;

@org.springframework.web.bind.annotation.RestController
public class RestController {


  @Autowired
  ISearchService searchService;

  @Autowired
  IInitService iInitService;


  @GetMapping("/init")
  public HttpStatus startIndex() {
    iInitService.init();
    return HttpStatus.OK;
  }

  @GetMapping("/search")
  public List<Product> searching(@RequestBody ProductCriteria criteria) {
  return searchService.search(criteria);
  }
}
