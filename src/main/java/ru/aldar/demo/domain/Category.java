package ru.aldar.demo.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName = "category")
public class Category {
  @Id
  private Long id;
  private Category parentId;
  private String name;
  private List<Product> products;

  public Category(Long id, Category parentId, String name, List<Product> products) {
    this.id = id;
    this.parentId = parentId;
    this.name = name;
    this.products = products;
  }
}
