package ru.aldar.demo.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.aldar.demo.domain.Category;

@Repository
public interface CategoryRepository extends ElasticsearchRepository<Category, Long> {

}
