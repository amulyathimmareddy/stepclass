package week4.assignment;

/**
 * Base payment class representing standard cash transactions.
 */
class Payment {
    public double pay(double amount) {
        System.out.printf("Paid (cash): Rs %.1f%n", amount);
        return amount;
    }
}

/**
 * Derived payment class representing card transactions with a 2% surcharge.
 */
class CardPayment extends Payment {
    private static final double PROCESSING_FEE_PERCENT = 0.02;

    public double payWithProcessingFee(double amount) {
        double fee = amount * PROCESSING_FEE_PERCENT;
        double totalCharged = amount + fee;
        System.out.printf("Charged (card, incl. fee): Rs %.1f%n", totalCharged);
        return totalCharged;
    }
}

/**
 * Problem A5: instanceof Inside a Loop — Canteen Closing-Time Payment Dispatch
 * Demonstrates polymorphic type checking via 'instanceof', safe downcasting, and accumulation of running totals.
 */
public class CanteenPaymentProcessor {

    /**
     * Dispatches payment using dynamic type checking with instanceof.
     *
     * @param payment payment strategy reference
     * @param amount  base transaction amount
     * @return actual amount charged (including fees if applicable)
     */
    public static double processTransaction(Payment payment, double amount) {
        if (payment == null) {
            return 0.0;
        }

        if (payment instanceof CardPayment) {
            CardPayment card = (CardPayment) payment;
            return card.payWithProcessingFee(amount);
        } else {
            return payment.pay(amount);
        }
    }

    /**
     * Replays batch transactions at closing time and accumulates total funds collected.
     *
     * @param payments array of Payment instances
     * @param amounts  parallel array of transaction amounts
     * @return cumulative funds collected
     */
    public static double processClosingBatch(Payment[] payments, double[] amounts) {
        if (payments == null || amounts == null) {
            return 0.0;
        }

        double totalCollected = 0.0;
        int count = Math.min(payments.length, amounts.length);
        for (int i = 0; i < count; i++) {
            totalCollected += processTransaction(payments[i], amounts[i]);
        }
        System.out.printf("Total Collected: Rs %.1f%n", totalCollected);
        return totalCollected;
    }

    public static void main(String[] args) {
        try {
            // Setup batch payments: {Card, Cash, Card, Cash, Card}
            Payment[] payments = new Payment[] {
                new CardPayment(),
                new Payment(),
                new CardPayment(),
                new Payment(),
                new CardPayment()
            };

            double[] amounts = {100.0, 50.0, 200.0, 75.0, 120.0};

            processClosingBatch(payments, amounts);
        } catch (Exception e) {
            System.err.println("Error processing canteen closing payments: " + e.getMessage());
        }
    }
}
