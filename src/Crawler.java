import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

    public static void main(String[] args) {

    String url = "https://fr.wikipedia.org/";
    crawl(1,url, new ArrayList<String>());
    }

    private static void crawl (int level, String url, ArrayList<String> visitedWebPage){
       if (level <=5){
           Document doc = request(url, visitedWebPage);

           if(doc!=null){
               for (Element link : doc.select("a[href]")){
                   String nextLink = link.absUrl("href");
                   if(visitedWebPage.contains(nextLink)==false){
                       crawl(level++, nextLink,visitedWebPage);
                   }
               }
           }
       }
    }

    private static Document request(String url, ArrayList<String> v) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if(con.response().statusCode()==200){
                System.out.println("Link : "+url);
                System.out.println(doc.title());
                v.add(url);
                return doc;
            }
            return null;
        }
        catch (IOException e){
            return null;
        }
    }
}
