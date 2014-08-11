package org.apache.pdfbox.cos;

import java.io.IOException;

/**
 * This class represents an abstract number in a PDF document.
 *
 * @author <a href="mailto:ben@benlitchfield.com">Ben Litchfield</a>
 * @version $Revision: 1.10 $
 */
public abstract class COSNumber extends COSBase
{

    /**
     * @deprecated Use the {@link COSInteger#ZERO} constant instead
     */
    public static final COSInteger ZERO = COSInteger.ZERO;

    /**
     * @deprecated Use the {@link COSInteger#ONE} constant instead
     */
    public static final COSInteger ONE = COSInteger.ONE;

    /**
     * This will get the float value of this number.
     *
     * @return The float value of this object.
     */
    public abstract float floatValue();

    /**
     * This will get the double value of this number.
     *
     * @return The double value of this number.
     */
    public abstract double doubleValue();

    /**
     * This will get the integer value of this number.
     *
     * @return The integer value of this number.
     */
    public abstract int intValue();

    /**
     * This will get the long value of this number.
     *
     * @return The long value of this number.
     */
    public abstract long longValue();

    /**
     * This factory method will get the appropriate number object.
     *
     * @param number The string representation of the number.
     *
     * @return A number object, either float or int.
     *
     * @throws IOException If the string is not a number.
     */
    public static COSNumber get( String number ) throws IOException
    {
        if (number.length() == 1) 
        {
            char digit = number.charAt(0);
            if ('0' <= digit && digit <= '9') 
            {
                return COSInteger.get(digit - '0');
            } 
            else if (digit == '-' || digit == '.') 
            {
                // See https://issues.apache.org/jira/browse/PDFBOX-592
                return COSInteger.ZERO;
            } 
            else 
            {
                throw new IOException("Not a number: " + number);
            }
        } 
        else if (number.indexOf('.') == -1 && (number.toLowerCase().indexOf('e') == -1)) 
        {
            try
            {
                return COSInteger.get( Long.parseLong( number ) );
            }
            catch( NumberFormatException e )
            {
                throw new IOException( "Value is not an integer: " + number );
            }
        } 
        else 
        {
            return new COSFloat(number);
        }
    }
}