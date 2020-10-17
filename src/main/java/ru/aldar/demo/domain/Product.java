package ru.aldar.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName = "product")
public class Product {
  @Id
  private Long id;
  private String name;
  private String description;
  private Double price;
  private Category category;
  private String pictureUrl;

}
