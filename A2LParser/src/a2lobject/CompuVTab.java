/*
 * Creation : 5 janv. 2019
 */
package a2lobject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import constante.ConversionType;

public final class CompuVTab extends ConversionTable {

    private String name;
    @SuppressWarnings("unused")
    private String longIdentifier;
    @SuppressWarnings("unused")
    private ConversionType conversionType;
    @SuppressWarnings("unused")
    private int numberValuePairs;
    private Map<Float, String> valuePairs;
    @SuppressWarnings("unused")
    private String defaultValue; // DEFAULT_VALUE

    public CompuVTab(List<String> parameters) {

        final int nbParams = parameters.size();

        if (nbParams >= 5) {

            this.name = parameters.get(2);
            this.longIdentifier = parameters.get(3);
            this.conversionType = ConversionType.getConversionType(parameters.get(4));
            this.numberValuePairs = Integer.parseInt(parameters.get(5));

            this.valuePairs = new LinkedHashMap<Float, String>();

            int lastIdx = parameters.indexOf("DEFAULT_VALUE");

            final List<String> listValuePairs;

            if (lastIdx > -1) {
                listValuePairs = parameters.subList(6, lastIdx);
            } else {
                listValuePairs = parameters.subList(6, parameters.size());
            }

            for (int i = 0; i < listValuePairs.size(); i++) {
                if (i % 2 == 0) {
                    valuePairs.put(Float.parseFloat(listValuePairs.get(i)), listValuePairs.get(i + 1));
                }
            }
        } else {
            throw new IllegalArgumentException("Nombre de parametres inferieur au nombre requis");
        }

    }

    @Override
    public String toString() {
        return this.name;
    }

    public final Map<Float, String> getValuePairs() {
        return valuePairs;
    }
}
