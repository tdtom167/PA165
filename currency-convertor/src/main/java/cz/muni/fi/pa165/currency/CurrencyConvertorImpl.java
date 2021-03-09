package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;




/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace("Convert called with params {}{}{}", sourceCurrency, targetCurrency, sourceAmount);
        if (sourceAmount == null) {
            throw new IllegalArgumentException("sourceAmount can not be null");
        }

        if (targetCurrency == null) {
            throw new IllegalArgumentException("targetCurrency can not be null");
        }

        if (sourceCurrency == null) {
            throw new IllegalArgumentException("sourceCurrency can not be null");
        }

        try {
            BigDecimal exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (exchangeRate == null) {
                logger.warn("Missing exchange rate currencies: source{} target{}", sourceCurrency, targetCurrency);
                throw new UnknownExchangeRateException("ExchangeRate can no be null");
            }
            return exchangeRate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
        } catch (ExternalServiceFailureException exception) {
            logger.error("Could not fetch exchange rate: source{} target{}", sourceCurrency, targetCurrency);
            throw new UnknownExchangeRateException("Could not fetch correct exchange rate", exception);
        }
    }

}
