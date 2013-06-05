package nl.ead.webservice.core;

import nl.ead.webservice.entity.Article;
import nl.ead.webservice.entity.Interest;

import java.util.ArrayList;

/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */
public class InterestParser {

    public InterestParser() {

    }

    public ArrayList<Interest> processInterests(ArrayList<String> input) {
        ArrayList<Interest> interestList = new ArrayList<Interest>();
        for (String name : input) {
            Interest its = new Interest(name);
            interestList.add(its);
        }
        return interestList;
    }
}
