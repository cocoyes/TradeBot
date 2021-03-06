package trading;

import java.time.LocalDateTime;

public class Trade {

    private double high; //Set the highest price
    private final double trailingSL; //It's in percentages, but using double for comfort.
    private final LocalDateTime entryTime = LocalDateTime.now();
    private final double entryPrice; //Starting price of a trade (when logic decides to buy)
    //private double fillPrice; //The actual price after the completion of a fill
    private final Currency currency; //What cryptocurrency is used.
    private final double amount; //How much are you buying or selling. I.E 6 bitcoins or smth.
    private double closePrice;
    private LocalDateTime closeTime;
    private final String explanation;

    public Trade(Currency currency, double entryPrice, double amount, double trailingSL, String explanation) {
        this.currency = currency;
        this.trailingSL = trailingSL;
        this.entryPrice = entryPrice;
        this.high = entryPrice;
        this.amount = amount;
        this.explanation = explanation;
        closePrice = -1;
    }

    //Getters and setters
    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    /*
    public void setFillPrice(double fillPrice) {
        this.fillPrice = fillPrice;
    }

     */

    public Currency getCurrency() { //for getting the currency to calculate what the price is now.
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public double getHigh() {
        return high;
    }

    public double getLoss() {
        return (getClosePrice() - high) / high;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public String getExplanation() {
        return explanation;
    }

    public boolean isClosed() {
        return closePrice != -1;
    }

    /*
    public double getFillPrice() {
        return fillPrice;
    }

     */


    //Allows user to get the profit percentages on one specific trade.
    public double getProfit() {
        if (closePrice == -1) {
            return (currency.getPrice() - entryPrice) / entryPrice;
        } else {
            return (closePrice - entryPrice) / entryPrice;
        }
    }

    //Checks if there is a new highest price for the trade or if the trade has dropped below the stoploss.
    public void update(double newPrice) {
        if (newPrice > high)
            high = newPrice;
        else if (newPrice < high * (1 - trailingSL)) {
            closePrice = newPrice;
            closeTime = LocalDateTime.now();
            BuySell.close(this);
        }
    }

    @Override
    public String toString() {
        return
                currency.getCoin() + " " + amount
                        + " opened " + Formatter.formatDate(entryTime) + " at " + entryPrice
                        + (isClosed() ? ", closed " + Formatter.formatDate(closeTime) + " at " + closePrice : ", current price " + currency.getPrice())
                        + ", high of " + high + ", profit " + Formatter.formatPercent(getProfit())
                        + (isClosed() ? "\n\t" + explanation : "");
    }
}
