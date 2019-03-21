/*
 * Creation : 5 janv. 2019
 */
package a2lobject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import constante.ConversionType;

public final class CompuTab {

    private String name;
    @SuppressWarnings("unused")
	private String longIdentifier;
    @SuppressWarnings("unused")
	private ConversionType conversionType;
    @SuppressWarnings("unused")
	private int numberValuePairs;
    private Map<Float, Float> valuePairs;
    @SuppressWarnings("unused")
	private String defaultValue; // DEFAULT_VALUE
    @SuppressWarnings("unused")
	private float defaultValueNumeric; // DEFAULT_VALUE_NUMERIC

    public CompuTab(List<String> parameters) {

        parameters.remove("/begin"); // Remove /begin
        parameters.remove("COMPU_TAB"); // Remove COMPU_TAB

        if (parameters.size() == 1 || parameters.size() >= 5) {
            for (int n = 0; n < parameters.size(); n++) {
                switch (n) {
                case 0:
                    this.name = parameters.get(n);
                    break;
                case 1:
                    this.longIdentifier = parameters.get(n);
                    break;
                case 2:
                    this.conversionType = ConversionType.getConversionType(parameters.get(n));
                    break;
                case 3:
                    this.numberValuePairs = Integer.parseInt(parameters.get(n));
                    break;
                case 4:
                    this.valuePairs = new LinkedHashMap<Float, Float>();

                    int lastIdx = parameters.indexOf("DEFAULT_VALUE");
                    if (lastIdx < 0) {
                        lastIdx = parameters.indexOf("DEFAULT_VALUE_NUMERIC");
                    }

                    final List<String> listValuePairs;

                    if (lastIdx > -1) {
                        listValuePairs = parameters.subList(n, lastIdx);
                    } else {
                        listValuePairs = parameters.subList(n, parameters.size());
                    }

                    for (int i = 0; i < listValuePairs.size(); i++) {
                        if (i % 2 == 0) {
                            valuePairs.put(Float.parseFloat(listValuePairs.get(i)), Float.parseFloat(listValuePairs.get(i + 1)));
                        }
                    }

                    break;

                default: // Cas de parametres optionels
                	
                	n = parameters.size();
                	
                    break;
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

}
