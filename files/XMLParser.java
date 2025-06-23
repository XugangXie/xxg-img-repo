package springbootmybatisplus.xml;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xiexugang
 * @Datetime: 2025-06-17 22:55:16
 **/
@Slf4j
public class XMLParser {

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        try {
            // 1. 创建 DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 2. 创建 DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 3. 从 classpath 加载 XML 文件
            ClassPathResource resource = new ClassPathResource("books.xml");
            try (InputStream inputStream = resource.getInputStream()) {
                // 4. 解析 XML 文件为 Document 对象
                Document document = builder.parse(inputStream);
                // 6. 获取根元素
                Element root = document.getDocumentElement();
                // 7. 获取所有 book 节点
                NodeList nodeList = root.getElementsByTagName("book");

                // 8. 遍历 book 节点
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) node;

                        Book book = new Book();
                        book.setId(bookElement.getAttribute("id"));
                        book.setTitle(getElementText(bookElement, "title"));
                        book.setAuthor(getElementText(bookElement, "author"));
                        book.setPrice(Double.parseDouble(getElementText(bookElement, "price")));

                        books.add(book);
                    }
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    // 辅助方法：获取元素的文本内容
    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) {
            return "";
        }
        return nodeList.item(0).getTextContent();
    }

    @Data
    public static class Book {
        private String id;
        private String title;
        private String author;
        private double price;

        // getters and setters
        @Override
        public String toString() {
            return "Book{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

}
