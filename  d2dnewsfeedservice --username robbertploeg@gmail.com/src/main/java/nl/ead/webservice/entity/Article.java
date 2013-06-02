package nl.ead.webservice.entity;

/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class Article {
    public String title;
    public String description;
    public String link;

    /**
     * Implementation of the Article class.
     * @param title
     * @param description
     * @param link
     */
    public Article(String title, String description, String link){
        this.title = title;
        this.description = description;
        this.link = link;
    }
}
