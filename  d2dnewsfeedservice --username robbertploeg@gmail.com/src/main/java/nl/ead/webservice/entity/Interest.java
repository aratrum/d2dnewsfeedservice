package nl.ead.webservice.entity;

/**
 * User: Robbert
 * Date: 29-5-13
 * Time: 11:25
 */

public class Interest {
    String name;

    /**
     * Implementation of the Interest class.
     * @param name
     */
    public Interest(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
