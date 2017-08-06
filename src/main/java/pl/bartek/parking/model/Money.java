package pl.bartek.parking.model;

public class Money {

    private final int value;
    private final Currency currency;

    private Money(int value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public static Money fromPlnGrosze(int value){
        return new Money(value, Currency.PLN);
    }

    public static Money fromPlnZlote(int value){
        return fromPlnGrosze(value * 100);
    }

    public Money add(Money other){
        if(this.currency != other.currency){
            throw new MoneyOperationException("Can't add money of different currencies!");
        }
        return new Money(this.value + other.value, this.currency);
    }

    public Money multiply(double times){
        if(times < 0){
            throw new MoneyOperationException("Can't multiply money by negative value!");
        }
        return new Money((int) (this.value * times), this.currency);
    }

    @Override
    public String toString() {
        int whole = value/100;
        int pennies = value %100;
        return "" + whole + "." + (pennies < 10 ? "0" : "") + pennies + " " + currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (value != money.value) return false;
        return currency == money.currency;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    // in the future there might be other currencies
    enum Currency {
        PLN
    }

    class MoneyOperationException extends RuntimeException{
        MoneyOperationException(String message) {
            super(message);
        }
    }
}
