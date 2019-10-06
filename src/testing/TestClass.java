package testing;

/**
 * Class used for testing. Contains one int and one string,
 * set on construction. Printable output surrounded by ~.
 * @author Ryan Rich
 *
 */
public class TestClass
{
    public int i;
    public String s;
    TestClass(int i, String s)
    {
        this.i = i;
        this.s = s;
    }
    public String toString()
    {
        return  i + "~" + s ;
    }
}
