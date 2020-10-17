package ru.aldar.demo.service;

import java.util.List;
import ru.aldar.demo.domain.Product;
import ru.aldar.demo.dto.ProductCriteria;

public interface ISearchService {
  List<Product> search(ProductCriteria productCriteria);
}
