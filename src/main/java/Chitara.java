public class Chitara extends InstrumentMuzical
{
    private TipChitara tip_chitara;
    public int nr_corzi;


    public Chitara(String producator, double pret, TipChitara tip_chitara, int nr_corzi)
    {
        super(producator, pret);
        this.tip_chitara = tip_chitara;
        this.nr_corzi = nr_corzi;
    }

    public TipChitara getTipChitara()
    {
        return tip_chitara;
    }

    public int getNrCorzi()
    {
        return nr_corzi;
    }

    @Override
    public String toString()
    {
        return super.toString() + ", Tip Chitara: " + tip_chitara + ", Nr. corzi: " + nr_corzi;
    }
}
