import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MainApp
{
    private static final String FILE_PATH = "D:\\Apps\\IntelliJ\\Laborator7Pb2\\instrumente.json";   //path pt fisierul instrumente

    public static void main(String[] args)
    {
        ObjectMapper mapper = new ObjectMapper();
        Set<InstrumentMuzical> instrumente = new HashSet<>();

        //1. adaugare de instrumente in colectie
        instrumente.add(new Chitara("Yamaha", 1200, TipChitara.ELECTRICA, 6));
        instrumente.add(new Chitara("Fender", 1500, TipChitara.ACUSTICA, 6));
        instrumente.add(new Chitara("Gibson", 1800, TipChitara.CLASICA, 6));
        instrumente.add(new SetTobe("Pearl", 3500, TipTobe.ACUSTICE, 5, 2));
        instrumente.add(new SetTobe("Roland", 2500, TipTobe.ELECTRONICE, 5, 3));
        instrumente.add(new SetTobe("Tama", 2800, TipTobe.ACUSTICE, 6, 2));

        // 2 salvare colectie in fisier
        try
        {
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
            //mapper.writeValue(new File(), instrumente);  //da eroare
            mapper.writeValue(new File(FILE_PATH), instrumente);
        } catch (IOException e)
        {
            System.out.println("Eroare la scrierea in fisier!");
        }

        //3. citire din fisier
        Set<InstrumentMuzical> cititeInstrumente = new HashSet<>();
        try
        {
            cititeInstrumente = mapper.readValue(new File(FILE_PATH), Set.class);
        } catch (IOException e)
        {
            System.out.println("Eroare la citirea din fiser! ");
        }

        //4. afisarea instrumentelor muzicale
        cititeInstrumente.forEach(System.out::println);

        //5. verific daca exista duplicate in fisier
        Chitara chitaraNoua = new Chitara("Yamaha", 1200, TipChitara.ELECTRICA, 6); //aleg un produs deja existent in fisier
        if (!instrumente.add(chitaraNoua))
        {
            System.out.println("\nInstrumentul este deja adaugat");
        } else
        {
            System.out.println("\nInstrumentul a fost adaugat in fisier");
        }


        //6. sterg instrumente cu pret > 3000lei
        instrumente.removeIf(instrument -> instrument.pret > 3000);
        System.out.println("\nLista de instrumente dupa eliminarea celor ce depaseau 3000lei: ");
        instrumente.forEach(System.out::println);

         //7. afisare chitari
        System.out.println("\nLista de chitari: ");
        instrumente.stream().filter(instrument -> instrument instanceof Chitara).forEach(System.out::println);


           // 8.afisare tobe
        System.out.println("\nLista de tobe: ");
        instrumente.stream().filter(instrument -> instrument.getClass().equals(SetTobe.class)).forEach(System.out::println);

        //9. date chitara cu cel mai mare nr de corzi
        Optional<Chitara> chitaraMaxCorzi = instrumente.stream().filter(instrument -> instrument instanceof Chitara).map(instrument -> (Chitara) instrument).max((c1, c2) -> Integer.compare(c1.nr_corzi, c2.nr_corzi));
        chitaraMaxCorzi.ifPresent(System.out::println);

        //10 ordonare tobe
        System.out.println("\nLista de tobe ordonate: ");
        instrumente.stream().filter(instrument -> instrument instanceof SetTobe && ((SetTobe) instrument).tip_tobe == TipTobe.ACUSTICE).sorted((t1, t2) -> Integer.compare(((SetTobe) t1).nr_tobe, ((SetTobe) t2).nr_tobe)).forEach(System.out::println);
    }
}
