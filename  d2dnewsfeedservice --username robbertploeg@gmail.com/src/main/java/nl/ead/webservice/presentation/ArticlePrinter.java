package nl.ead.webservice.presentation;
import java.util.ArrayList;
import nl.ead.webservice.entity.*;

/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class ArticlePrinter {

    public ArticlePrinter(){

    }

    /**
     * Give the array of articles that will be printed
     * @param toBePrinted
     */
    public void printArticles(ArrayList<Article> toBePrinted){
        for(Article a : toBePrinted){
            System.out.println("Title: " + a.title + ", Description: " + a.description + ", Link: " + a.link);
        }
    }

}
