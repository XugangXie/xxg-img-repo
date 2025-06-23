package springbootmybatisplus.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

public class XPathHelper {

	private final static Logger logger = LoggerFactory.getLogger(XPathHelper.class);
	
	private static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
     
	private static DocumentBuilder builder =  null;
     
	 static {
		 try {
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage(), e);
		}
	 }
	 
	public static String getNodeValue(String sourceXml, String xpath) throws XPathExpressionException, SAXException, IOException {
		if (builder != null) {
			InputSource is = new InputSource(new StringReader(sourceXml));
			Document xmlDocument = builder.parse(is);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpathObj = xpathFactory.newXPath();			
			return xpathObj.evaluate(xpath, xmlDocument);
		}
        return null;
	}
}
