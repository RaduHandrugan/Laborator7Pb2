public class SetTobe extends InstrumentMuzical
{
    public TipTobe tip_tobe;
    public int nr_tobe;
    private int nr_cinele;


    //get set
    public SetTobe(String producator, double pret, TipTobe tip_tobe, int nr_tobe, int nr_cinele)
    {
        super(producator, pret);
        this.tip_tobe = tip_tobe;
        this.nr_tobe = nr_tobe;
        this.nr_cinele = nr_cinele;
    }

    @Override
    public String toString()
    {
        return super.toString() + ", Tip tobe: " + tip_tobe + ", Nr. tobe: " + nr_tobe + ", Nr. cinele: " + nr_cinele;
    }
}
