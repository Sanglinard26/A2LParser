/*
 * Creation : 20 févr. 2019
 */
package a2l;

import static constante.SecondaryKeywords.AXIS_PTS_REF;
import static constante.SecondaryKeywords.BYTE_ORDER;
import static constante.SecondaryKeywords.CURVE_AXIS_REF;
import static constante.SecondaryKeywords.DEPOSIT;
import static constante.SecondaryKeywords.FIX_AXIS_PAR;
import static constante.SecondaryKeywords.FIX_AXIS_PAR_DIST;
import static constante.SecondaryKeywords.FIX_AXIS_PAR_LIST;
import static constante.SecondaryKeywords.FORMAT;

import java.nio.ByteOrder;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constante.SecondaryKeywords;

public final class AxisDescr implements A2lObjectBuilder {

    private Attribute attribute;
    @SuppressWarnings("unused")
    private String inputQuantity;
    private String conversion;
    private short maxAxisPoints;
    @SuppressWarnings("unused")
    private float lowerLimit;
    @SuppressWarnings("unused")
    private float upperLimit;

    private CompuMethod compuMethod;
    private RecordLayout recordLayout;
    private AdjustableObject axisPts;
    private AdjustableObject curveAxis;

    private Map<SecondaryKeywords, Object> optionalsParameters;

    public AxisDescr(List<String> parameters, int beginLine, int endLine) {

        initOptionalsParameters();

        build(parameters, beginLine, endLine);
    }

    private final void initOptionalsParameters() {
        optionalsParameters = new EnumMap<SecondaryKeywords, Object>(SecondaryKeywords.class);
        optionalsParameters.put(AXIS_PTS_REF, null);
        optionalsParameters.put(CURVE_AXIS_REF, null);
        optionalsParameters.put(DEPOSIT, null);
        optionalsParameters.put(BYTE_ORDER, null);
        optionalsParameters.put(FIX_AXIS_PAR, null);
        optionalsParameters.put(FIX_AXIS_PAR_DIST, null);
        optionalsParameters.put(FIX_AXIS_PAR_LIST, null);
        optionalsParameters.put(FORMAT, null);
    }

    public final Attribute getAttribute() {
        return attribute;
    }

    public final void setCompuMethod(CompuMethod compuMethod) {
        this.compuMethod = compuMethod;
    }

    public final void setAxisPts(AdjustableObject axisPts) {
        this.axisPts = axisPts;
    }

    public final AdjustableObject getAxisPts() {
        return axisPts;
    }

    public final void setCurveAxis(AdjustableObject adjustableObject) {
        this.curveAxis = adjustableObject;
    }

    public final AdjustableObject getCurveAxis() {
        return curveAxis;
    }

    public final String getConversion() {
        return conversion;
    }

    public final CompuMethod getCompuMethod() {
        return compuMethod;
    }

    public final RecordLayout getRecordLayout() {
        return recordLayout;
    }

    public final short getMaxAxisPoints() {
        return maxAxisPoints;
    }

    public final String getDepositMode() {
        Object oDeposit = optionalsParameters.get(DEPOSIT);
        return oDeposit != null ? oDeposit.toString() : "";
    }

    public final ByteOrder getByteOrder() {
        String sByteOrder = (String) optionalsParameters.get(BYTE_ORDER);
        if (sByteOrder != null) {
            if ("MSB_LAST".equals(sByteOrder) || "BIG_ENDIAN".equals(sByteOrder)) {
                return ByteOrder.LITTLE_ENDIAN;
            }
            return ByteOrder.BIG_ENDIAN;
        }
        return null;
    }

    public final String getAxisRef(Attribute type) {
        Object object = null;
        switch (type) {
        case COM_AXIS:
            object = optionalsParameters.get(AXIS_PTS_REF);
            break;
        case RES_AXIS:
            object = optionalsParameters.get(AXIS_PTS_REF);
            break;
        case CURVE_AXIS:
            object = optionalsParameters.get(CURVE_AXIS_REF);
            break;
        default:
            break;
        }
        return object != null ? object.toString() : "";
    }

    public final Map<SecondaryKeywords, Object> getOptionalsParameters() {
        return optionalsParameters;
    }

    public final byte getNbDecimal() {
        Object objectDisplayFormat = optionalsParameters.get(FORMAT);
        String displayFormat;

        if (!compuMethod.isVerbal()) {
            if (objectDisplayFormat == null) {
                displayFormat = compuMethod.getFormat();
            } else {
                displayFormat = objectDisplayFormat.toString();
            }

            return (byte) Integer.parseInt(displayFormat.substring(displayFormat.indexOf(".") + 1, displayFormat.length()));
        }
        return 0;
    }

    public enum Attribute {
        CURVE_AXIS, COM_AXIS, FIX_AXIS, RES_AXIS, STD_AXIS, UNKNOWN;

        public static Attribute getAttribute(String name) {
            switch (name) {
            case "CURVE_AXIS":
                return CURVE_AXIS;
            case "COM_AXIS":
                return COM_AXIS;
            case "FIX_AXIS":
                return FIX_AXIS;
            case "RES_AXIS":
                return RES_AXIS;
            case "STD_AXIS":
                return STD_AXIS;
            default:
                return UNKNOWN;
            }
        }
    }

    @Override
    public void build(List<String> parameters, int beginLine, int endLine) throws IllegalArgumentException {

        final int nbParams = parameters.size();

        if (nbParams >= 6) {

            this.attribute = Attribute.getAttribute(parameters.get(0));
            this.inputQuantity = parameters.get(1);
            this.conversion = parameters.get(2);
            this.maxAxisPoints = (short) Integer.parseInt(parameters.get(3));
            this.lowerLimit = Float.parseFloat(parameters.get(4));
            this.upperLimit = Float.parseFloat(parameters.get(5));

            int n = 6;

            Set<SecondaryKeywords> keys = optionalsParameters.keySet();
            for (int nPar = n; nPar < nbParams; nPar++) {
                if (keys.contains(SecondaryKeywords.getSecondaryKeyWords(parameters.get(nPar)))) {
                    switch (parameters.get(nPar)) {
                    case "AXIS_PTS_REF":
                        optionalsParameters.put(AXIS_PTS_REF, parameters.get(nPar + 1));
                        nPar += 1;
                        break;
                    case "BYTE_ORDER":
                        optionalsParameters.put(BYTE_ORDER, parameters.get(nPar + 1));
                        nPar += 1;
                        break;
                    case "CURVE_AXIS_REF":
                        optionalsParameters.put(CURVE_AXIS_REF, parameters.get(nPar + 1));
                        nPar += 1;
                        break;
                    case "DEPOSIT":
                        optionalsParameters.put(DEPOSIT, parameters.get(nPar + 1));
                        nPar += 1;
                        break;
                    case "FIX_AXIS_PAR":
                        n = nPar + 1;
                        optionalsParameters.put(FIX_AXIS_PAR, new FixAxisPar(parameters.subList(n, n + 3)));
                        nPar += 3;
                        break;
                    case "FIX_AXIS_PAR_DIST":
                        n = nPar + 1;
                        optionalsParameters.put(FIX_AXIS_PAR_DIST, new FixAxisParDist(parameters.subList(n, n + 3)));
                        nPar += 3;
                        break;
                    case "FIX_AXIS_PAR_LIST":
                        n = nPar + 1;
                        do {
                        } while (!parameters.get(++nPar).equals("FIX_AXIS_PAR_LIST"));
                        optionalsParameters.put(FIX_AXIS_PAR_LIST, new FixAxisParList(parameters.subList(n, nPar - 1)));
                        n = nPar + 1;
                        break;
                    case "FORMAT":
                        optionalsParameters.put(FORMAT, parameters.get(nPar + 1));
                        nPar += 1;
                        break;
                    default:
                        break;
                    }
                }
            }

        } else {
            throw new IllegalArgumentException("Nombre de parametres inferieur au nombre requis");
        }

    }
}