package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CurrencyConvertorImplTest {

    private static Currency CZK = Currency.getInstance("CZK");
    private static Currency EUR = Currency.getInstance("EUR");

    @Mock
    private ExchangeRateTable exchangeRateTable;
    private CurrencyConvertor currencyConvertor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenReturn(new BigDecimal("0.55"));

        assertEquals(new BigDecimal("3.04"), currencyConvertor.convert(EUR, CZK, new BigDecimal("5.53")));
        assertEquals(new BigDecimal("5.53"), currencyConvertor.convert(EUR, CZK, new BigDecimal("10.050")));
        assertEquals(new BigDecimal("-0.55"), currencyConvertor.convert(EUR, CZK, new BigDecimal("-1.00")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        exception.expect(IllegalArgumentException.class);
        currencyConvertor.convert(null, EUR, BigDecimal.TEN);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        exception.expect(IllegalArgumentException.class);
        currencyConvertor.convert(EUR, null, BigDecimal.TEN);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        exception.expect(IllegalArgumentException.class);
        currencyConvertor.convert(CZK, EUR, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenReturn(null);
        exception.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenThrow(UnknownExchangeRateException.class);
        exception.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, BigDecimal.TEN);
    }

}
