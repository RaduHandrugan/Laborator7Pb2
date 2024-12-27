/*
2. Se consideră următoarea ierarhie de clase:
• clasa abstractă InstrumentMuzical cu variabile membre:
o producator
o pret
• clasa derivată Chitara cu variabilele membre:
o TipChitara tip_chitara; (enumerare cu valorile ELECTRICA, ACUSTICA şi
CLASICA)
o nr_corzi
• clasa derivată SetTobe cu variabilele membre:
o TipTobe tip_tobe; (enumerare cu valorile ELECTRONICE şi ACUSTICE)
o nr_tobe
o nr_cinele.
Să se realizeze un program care implementează următoarele cerințe:
1) Creează o colecție de tip Set<InstrumentMuzical> în care utilizând polimorfismul se
introduc 3 chitări şi 3 seturi de tobe.
2) Salvează colecția Set<InstrumentMuzical> în fișierul instrumente.json. Pentru ca în
fișierul json să fie salvat şi tipul obiectelor (care este necesar la citirea datelor din json
şi crearea obiectelor corespunzătoare) se configurează în funcția de scriere obiectul de
tip ObjectMapper în felul următor:
 mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
3) Încarcă datele din fişierul instrumente.json în program, într-o colecție de tip
Set<InstrumentMuzical>, care va fi utilizată pentru rezolvarea cerințelor următoare.
Pentru ca operația de încărcare a datelor din json în program să reușească în contextual
în care se utilizează tipuri polimorfe, este necesar să se completeze deasupra clasei
abstracte adnotația de mai jos, care va ajuta la realizarea legăturii dintre tipurile
abstracte si implementările concrete.
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)

4) Să se afișeze implementarea utilizată pentru interfața Set de către ObjectMapper în
urma citirii
5) Să se verifice dacă colecția Set permite sau nu duplicate, prin încercarea de a adăuga
un instrument care are aceleași caracteristici cu unul deja introdus. În urma verificării
se va afişa un mesaj corespunzător. Să se scrie codul care face ca duplicatele să nu fie
permise în colecţia Set.
6) Să se șteargă instrumentele din Set al căror preț este mai mare de 3000 de RON. Se va
utiliza metoda removeIf().
7) Să se afișeze toate datele chitărilor, utilizând Stream API şi operatorul instanceof
8) Să se afișeze toate datele tobelor, utilizând Stream API şi metoda getClass()
9) Să se afișeze datele chitării care are cele mai multe corzi, utilizând Stream API,
expresii Lambda, referințe la metode şi clasa Optional.
10) Să se afișeze datele tobelor acustice, ordonat după numărul de tobe din set utilizând
Stream API, referințe la metode, expresii Lambda, etc
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
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
        instrumente.add(new Chitara("Gibson", 1800, TipChitara.CLASICA, 10));
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
        Optional<Chitara> chitaraCuCeleMaiMulteCorzi = instrumente.stream()
                .filter(instrument -> instrument instanceof Chitara) // Filtrăm doar chitarele
                .map(instrument -> (Chitara) instrument) // Facem cast la Chitara
                .max(Comparator.comparingInt(Chitara::getNrCorzi)); // Căutăm chitara cu cel mai mare număr de corzi

        chitaraCuCeleMaiMulteCorzi.ifPresent(chitara -> {
            System.out.println("\nChitara cu cele mai multe corzi este: ");
            System.out.println("Producator: " + chitara.getProducator());
            System.out.println("Pret: " + chitara.getPret());
            System.out.println("Tip Chitara: " + chitara.getTipChitara());  // Dacă ai o metodă care returnează tipul chitarei
            System.out.println("Numar Corzi: " + chitara.getNrCorzi());
        });

        //10 ordonare tobe
        System.out.println("\nLista de tobe ordonate: ");
        instrumente.stream().filter(instrument -> instrument instanceof SetTobe && ((SetTobe) instrument).tip_tobe == TipTobe.ACUSTICE).sorted((t1, t2) -> Integer.compare(((SetTobe) t1).nr_tobe, ((SetTobe) t2).nr_tobe)).forEach(System.out::println);
    }
}
