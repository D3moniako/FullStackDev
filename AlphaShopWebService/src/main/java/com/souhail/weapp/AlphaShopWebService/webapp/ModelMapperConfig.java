package com.souhail.weapp.AlphaShopWebService.webapp;

import com.souhail.weapp.AlphaShopWebService.DTO.ArticoliDTO;
import com.souhail.weapp.AlphaShopWebService.DTO.BarcodeDTO;

import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import com.souhail.weapp.AlphaShopWebService.entity.Barcode;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // indica che è un componente di configurazione
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addMappings(articoliMapping);

        modelMapper.addMappings(new PropertyMap<Barcode, BarcodeDTO>() {
            @Override
            protected void configure() {
                map().setIdTipoArt(source.getIdTipoArt());
            }
        });

        modelMapper.addConverter(articoliConverter);

        return modelMapper;

    }
    /*@Bean // creo un bean per instanziare il mapper
    public ModelMapper modelMapper()// metodo che ritorna il modell mapper stesso
    {

*//* caso precedente
        return new ModelMapper();
*//*
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);// se un campo tra model e dto è nullo allora non verrà mappato
        modelMapper.addMappings(articoliMapping);

        // alternativa a uso di propertyMap con metodo separato
        modelMapper.addMappings(new PropertyMap<Barcode, BarcodeDTO>() {

            @Override
            protected void configure() {
                map().setIdTipoArt(source.getIdTipoArt());// al posto di tipo metto id tipoArt
            }
        });
    modelMapper.addConverter(articoliConverter);
    return  modelMapper;
    }
*/
    // creo new metodo in cui configuro la mappatura

    PropertyMap<Articoli, ArticoliDTO> articoliMapping = new PropertyMap<Articoli, ArticoliDTO>() {
        protected void configure() {
            map().setDataCreazione(source.getDataCreaz());// dalla prop dataCreazione del dto, corrisponde dataCreaz del model cioè origine
            // quindi se voglio modificare i nomi devo poi specificarlo qui!!1
        }
    };

    Converter<String, String> articoliConverter = new Converter<String, String>() {
        @Override
        public String convert(MappingContext<String, String> context) {
            return context.getSource() == null ? "" : context.getSource().trim();
        }
    };

}
