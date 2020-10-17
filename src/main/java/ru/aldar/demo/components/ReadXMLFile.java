package ru.aldar.demo.components;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.aldar.demo.domain.Category;
import ru.aldar.demo.domain.Product;
import ru.aldar.demo.repository.CategoryRepository;
import ru.aldar.demo.repository.ProductRespository;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ReadXMLFile {

  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private ProductRespository productRespository;


  HashMap<Long, Category> categoryHashMap = new HashMap<>();
  HashMap<Long, Product> productHashMap = new HashMap<>();

  public ReadXMLFile() {
  }

  public void parse(File fXmlFile) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("category");
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          parseCategory(nNode);
        }
      }
      nList = doc.getElementsByTagName("product");
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          parseProducts(nNode);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      fXmlFile.deleteOnExit();
    }
    productRespository.saveAll(productHashMap.values());
    categoryRepository.saveAll(categoryHashMap.values());
  }

  public void parseCategory(Node node) {
    Element eElement = (Element) node;
    Long id = parseLong(eElement.getAttribute("id"));
    Long parentId = parseLong(eElement.getAttribute("parentId"));
    String name = node.getTextContent();
    Category parent = categoryHashMap.get(parentId);
    Category category = new Category(id, parent, name, new ArrayList<>());
    categoryHashMap.put(id, category);
  }

  private void parseProducts(Node node) {
    Element eElement = (Element) node;
    Long id = parseLong(eElement.getAttribute("id"));
    Product product = new Product();
    product.setId(id);
    NodeList nodeList = node.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      parseProductChild(product, nodeList.item(i));
    }
    //categoryHashMap.computeIfPresent(product.getCategory().getId(), (key, value) ->{
    //  value.getProducts().add(product);
    //  return value;
    //});
    productHashMap.put(id, product);
  }

  private void parseProductChild(Product product, Node node) {
    String value = node.getTextContent();
    if (node.getNodeName().equals("category_id")) {
      product.setCategory(categoryHashMap.get(parseLong(value)));
    }
    if (node.getNodeName().equals("description")) {
      product.setDescription(value);
    }
    if (node.getNodeName().equals("name")) {
      product.setName(value);
    }
    if (node.getNodeName().equals("price")) {
      product.setPrice(parseDouble(value));
    }
    if (node.getNodeName().equals("picture")) {
      product.setPictureUrl(value);
    }
  }

  private Long parseLong(String value) {
    if (!value.isEmpty()) {
      return Long.valueOf(value);
    }
    return 0L;
  }

  private Double parseDouble(String value) {
    if (!value.isEmpty()) {
      return Double.valueOf(value);
    }
    return 0.0;
  }
}