package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class Main {
    public static void main(String[] args) {
//        springXmlContext();
//        springAnnotationContext();
        springJavaConfigContext();
    }



    private static void springXmlContext() {

        ApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("applicationContext.xml");

        CurrencyConvertorImpl currencyConvertor
                = applicationContext.getBean(CurrencyConvertorImpl.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.TEN));

    }

    private static void springAnnotationContext() {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");

        CurrencyConvertor currencyConvertor
                = applicationContext.getBean(CurrencyConvertorImpl.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.TEN));

    }

    private static void springJavaConfigContext() {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(SpringJavaConfig.class);

        CurrencyConvertor currencyConvertor
                = applicationContext.getBean("currencyConvertor", CurrencyConvertor.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.TEN));

    }
}
