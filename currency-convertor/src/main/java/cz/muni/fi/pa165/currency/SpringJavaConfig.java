package cz.muni.fi.pa165.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.inject.Inject;

@Configuration
@ComponentScan("cz.muni.fi.pa165.currency")
@EnableAspectJAutoProxy
public class SpringJavaConfig {

    @Inject
    private ExchangeRateTable exchangeRateTable;

    @Bean
    public CurrencyConvertorImpl currencyConvertor() {
        System.err.println("Creating currencyConvertor");
        return new CurrencyConvertorImpl(exchangeRateTable);
    }

}

