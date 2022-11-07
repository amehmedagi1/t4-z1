package ba.unsa.etf.rpr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LaptopDaoXMLFile implements LaptopDao{
    private File file;
    private ArrayList<Laptop> laptopi;

    public LaptopDaoXMLFile() {
        laptopi = new ArrayList<>();
        file = new File("zadatak4.xml");
    }
    @Override
    public void dodajLaptopUListu(Laptop laptop) {
        laptopi.add(laptop);

    }

    @Override
    public void dodajLaptopUFile(Laptop laptop) throws IOException {
        laptopi.add(laptop);
        ObjectMapper xmlmapper = new XmlMapper();
        String izlaz = xmlmapper.writeValueAsString(laptopi);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(izlaz.getBytes());
        fos.close();

    }

    @Override
    public Laptop getLaptop(String procesor) {
        for(Laptop l : laptopi){
            if(l.getProcesor().equals(procesor)) return l;
        }
        throw new NeodgovarajuciProcesorException("Laptop sa datim procesorom ne postoji");
    }

    @Override
    public void napuniListu(ArrayList<Laptop> laptopi) {
        this.laptopi = laptopi;
    }

    @Override
    public ArrayList vratiPodatkeIzDatoteke() {
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
