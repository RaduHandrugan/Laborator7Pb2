import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class InstrumentMuzical
{
    protected String producator;
    protected double pret;

    //getter & setter
    public InstrumentMuzical(String producator, double pret)
    {
        this.producator = producator;
        this.pret = pret;
    }

    @Override
    public String toString()
    {
        return "Producator: " + producator + ", Pret: " + pret;
    }
}
