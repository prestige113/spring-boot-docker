package ru.aldar.demo.repository;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.aldar.demo.domain.Product;
@Repository
public interface ProductRespository extends ElasticsearchRepository<Product, Long> {

  Iterable<Product> findAllByCategoryId(Long id);

  Iterable<Product> findAllByNameInOrDescriptionIn(List<String> name, List<String> description);
}
