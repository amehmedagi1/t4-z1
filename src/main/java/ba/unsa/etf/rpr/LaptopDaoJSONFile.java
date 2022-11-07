package ba.unsa.etf.rpr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.*;
import java.util.ArrayList;

public class LaptopDaoJSONFile implements LaptopDao {
    private File file;
    private ArrayList<Laptop> laptopi;

    public LaptopDaoJSONFile() {
        laptopi = new ArrayList<>();
        file = new File("zadatak4.json");
    }

    @Override
    public void dodajLaptopUListu(Laptop laptop) {
        laptopi.add(laptop);
    }

    @Override
    public void dodajLaptopUFile(Laptop laptop) throws IOException {
        laptopi.add(laptop);
        ObjectMapper jm = new JsonMapper();
        String izlaz = jm.writeValueAsString(laptopi);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(izlaz.getBytes());
        fos.close();
    }

    @Override
    public Laptop getLaptop(String procesor) {
        for (Laptop l : laptopi) {
            if (l.getProcesor().equals(procesor)) return l;
        }
        throw new NeodgovarajuciProcesorException("Laptop sa datim procesorom ne postoji");
    }

    @Override
    public void napuniListu(ArrayList<Laptop> laptopi) {
        this.laptopi = laptopi;
    }

    @Override
    public ArrayList vratiPodatkeIzDatoteke() throws IOException {
        ArrayList lista = new ArrayList();
        try {
            lista = (new ObjectMapper()).readValue(file, new TypeReference<ArrayList>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
