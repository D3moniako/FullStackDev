package com.souhail.weapp.AlphaShopWebService.tests.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.souhail.weapp.AlphaShopWebService.Application;
import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import com.souhail.weapp.AlphaShopWebService.repository.ArticoliRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest()
@ContextConfiguration(classes = Application.class)
public class ArticoliRepositoryTest {
    @Autowired
    public ArticoliRepository articoliRepository;

    @Test
    public void TestfindByDescrizioneLike()
    // avendo specificato in questo TEST il nome DEL METODO ALLORA DOVREMO CREARE UN METODO CON LO STESSO NOME!!!
    {
        List<Articoli> items = articoliRepository.SelByDescrizioneLike("ACQUA ULIVETO%");
        // questa è una query di selezione in cui selezioniamo tutti gli elementi specificati nella query
        // uso sel quando sto creando un metodo che non utilizza direttamente la sintassi di spring data jpa
        // ma usa o query nativa o altro
        assertEquals(2, items.size()); // numero di elementi ottenuto dalla query è pari a due
    }

    @Test  // IMPORTANTISSSSIMO
    public void TestfindByDescrizioneLikePage() {
        List<Articoli> items = articoliRepository.findByDescrizioneLike("ACQUA%", (Pageable) PageRequest.of(0, 10));
        // pagerequesto vuol dire che stiamo sfruttando il paging e quidi l'interfaccia che ci permette di sfruttare il paging dei nostri record
        // nota sto cercando tutti gli elementi con ACQUA TRAMITE %, con PageRequest.of cerco quegli elementi dal primo al decimo


        // uso findiby quando utilizzo LA SPRING DATA JPA
        assertEquals(10, items.size());// assert riquest mi dice che devo ottenre dal pagin 10 elementi
    }


    @Test
    public void TestfindByCodArt() throws Exception {
        assertThat(articoliRepository.findByCodArt("002000301"))// posso selezionare articolo in  base alla chiave primaria
                .extracting(Articoli::getDescrizione)// una volta ottenuto articolo estraggo la descrizione è imposto che sia uguala a quella stringa
                .isEqualTo("ACQUA ULIVETO 15 LT");

    }

    @Test
    public void TestfindByEan() throws Exception {
        assertThat(articoliRepository.SelByEan("8008490000021"))// posso selezionare articolo in  base alla chiave primaria
                .extracting(Articoli::getDescrizione)// una volta ottenuto articolo estraggo la descrizione è imposto che sia uguala a quella stringa
                .isEqualTo("ACQUA ULIVETO 15 LT");

    }


}
