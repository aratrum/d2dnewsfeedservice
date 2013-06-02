//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.30 at 11:26:13 AM CEST 
//


package nl.ead.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CalculateOperation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CalculateOperation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NCName">
 *     &lt;enumeration value="add"/>
 *     &lt;enumeration value="subtract"/>
 *     &lt;enumeration value="multiply"/>
 *     &lt;enumeration value="divide"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CalculateOperation")
@XmlEnum
public enum CalculateOperation {

    @XmlEnumValue("add")
    ADD("add"),
    @XmlEnumValue("subtract")
    SUBTRACT("subtract"),
    @XmlEnumValue("multiply")
    MULTIPLY("multiply"),
    @XmlEnumValue("divide")
    DIVIDE("divide");
    private final String value;

    CalculateOperation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CalculateOperation fromValue(String v) {
        for (CalculateOperation c: CalculateOperation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}