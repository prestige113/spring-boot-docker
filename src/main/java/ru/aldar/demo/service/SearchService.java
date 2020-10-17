package ru.aldar.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aldar.demo.domain.Product;
import ru.aldar.demo.dto.ProductCriteria;
import ru.aldar.demo.repository.CategoryRepository;
import ru.aldar.demo.repository.ProductRespository;

@Service
public class SearchService implements ISearchService {
  @Autowired
  ProductRespository productRespository;
  @Autowired
  CategoryRepository categoryRepository;
  @Override
  public List<Product> search(ProductCriteria criteria) {
    if (criteria.getId() == null) {
      Product product = new Product();
      product.setName(criteria.getName());
      product.setDescription(criteria.getName());
      List<String> name = Arrays.asList(criteria.getName().split(" "));
      List<Product> products = new ArrayList<>();
      productRespository.findAllByNameInOrDescriptionIn(name, name).forEach(p->{
        products.add(p);
      });
      return products;
    } else {
      List<Product> products = new ArrayList<>();
      productRespository.findAllByCategoryId(criteria.getId()).forEach(p->{
        products.add(p);
      });
      return products;
    }
  }
}
