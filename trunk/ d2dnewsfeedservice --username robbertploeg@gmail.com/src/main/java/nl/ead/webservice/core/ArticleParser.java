package nl.ead.webservice.core;

import nl.ead.webservice.entity.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class ArticleParser {

    public ArticleParser(){

    }

    public ArrayList<Article> processArticles(String input, int size) throws JSONException {

        String newString;
        ArrayList<Article> articleList = new ArrayList<Article>();
        JSONArray articleArray = new JSONArray(input);
        for(int i = 0; i < size; i++ )
        {
            newString = articleArray.getString(i);
            newString = escapeString(newString);

            JSONObject object = new JSONObject(newString);
            Article ar = new Article(object.getString("title"), object.getString("text"), object.getString("url"));
            articleList.add(ar);
            object.toString();
        }
        return articleList;
    }

    public static String escapeString(String text)
    {
        text.replaceAll("\"", "\\\"");
        return text;
    }
}

