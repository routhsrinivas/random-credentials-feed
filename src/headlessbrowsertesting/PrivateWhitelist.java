/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgprivate.whitelist;

import com.google.common.net.InternetDomainName;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author srinivas
 */
public class PrivateWhitelist {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException {
        // TODO code application logic here
        try {
            String filepath = "C:\\Users\\srinivas\\Documents\\NetBeansProjects\\HeadLessBrowserTesting\\src\\headlessbrowsertesting\\PrivateWhitelist.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            boolean whitelist_status=false;
            String address_URL = "http://www.tupaki.com/movienews/article/GV-Prakash-Kumar-Bruce-Lee-Movie-Teaser/138513";
            //**** to get root node access********
            Node root = doc.getFirstChild();
            NodeList list = doc.getElementsByTagName("domain");
            String primaryDomain = getPrimaryDomain(address_URL);
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                //******* check for the address domain with whitelist databse of domains
                if (primaryDomain.equals(node.getTextContent())) {
                    whitelist_status=true;
                   break;
                }
            }
            if(!whitelist_status){
                 //********** to get main child of entire xml file**********
                    Node website = doc.getElementsByTagName("website_list").item(0);

                    //******* creates first entry of website info**********
                    Element website_info = doc.createElement("website_info");
                    //****** append the node to main parent*****
                    website.appendChild(website_info);
                    // Add all the childes to website info (4 childs here) ******
                    // ********** URL child************************
                    Element URL = doc.createElement("URL");
                    URL.appendChild(doc.createTextNode(address_URL));
                    website_info.appendChild(URL);
                    //*******domain child********
                    Element domain = doc.createElement("domain");

                    domain.appendChild(doc.createTextNode(primaryDomain));
                    website_info.appendChild(domain);
                    //*********mapped domain**********
                    Element mapped_domain = doc.createElement("mapped_domain");
                    mapped_domain.appendChild(doc.createTextNode("http://www.fb.com"));
                    website_info.appendChild(mapped_domain);
                    //***************ip address**********
                    InetAddress address = InetAddress.getByName(new URL(address_URL).getHost());
                    String ip = address.getHostAddress();
                    Element ip_address = doc.createElement("ip_address");
                    ip_address.appendChild(doc.createTextNode(ip));
                    website_info.appendChild(ip_address);
                    //****** make all changes and write to XML file************
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(filepath));
                    transformer.transform(source, result);
                    System.out.println("Done");
            }

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }

    }

    public static String getPrimaryDomain(String URL) throws URISyntaxException {

        String host = new URI(URL).getHost();
        InternetDomainName domainName = InternetDomainName.from(host);
        return domainName.topPrivateDomain().toString();
    }

}
