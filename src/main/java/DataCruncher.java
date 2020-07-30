import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Set;
import java.nio.file.Paths;
import java.util.Map;

public class DataCruncher
{

    // do not modify this method - just use it to get all the Transactions that are in scope for the exercise
    public List<Transaction> readAllTransactions() throws Exception {
        return Files.readAllLines(Paths.get("src/main/resources/payments.csv"), StandardCharsets.UTF_8)
                .stream()
                .skip(1)
                .map(line -> {
                    var commaSeparatedLine = List.of(line
                            .replaceAll("'", "")
                            .split(",")
                    );
                    var ageString = commaSeparatedLine.get(2);
                    var ageInt = "U".equals(ageString) ? 0 : Integer.parseInt(ageString);
                    return new Transaction(commaSeparatedLine.get(1),
                            ageInt,
                            commaSeparatedLine.get(3),
                            commaSeparatedLine.get(4),
                            commaSeparatedLine.get(5),
                            commaSeparatedLine.get(6),
                            commaSeparatedLine.get(7),
                            Double.parseDouble(commaSeparatedLine.get(8)),
                            "1".equals(commaSeparatedLine.get(9)));
                })
                .collect(Collectors.toList());
    }

    // example
    public List<Transaction> readAllTransactionsAge0() throws Exception {
        return readAllTransactions().stream()
                .filter(transaction -> transaction.getAge() == 0)
                .collect(Collectors.toList());
    }
    
    // task 1 - pass
    public Set<String> getUniqueMerchantIds() throws Exception {
        return readAllTransactions().stream()
                .map(Transaction::getMerchantId)
                .collect(Collectors.toSet());
    }//Get all the unique merchant IDs in the file and return in a SET. Run the test provided to validate.

    // task 2 -pass
    public long getTotalNumberOfFraudulentTransactions() throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud).count();
    }//Return the total number of transactions marked as fraudulent. Run the test provided to validate.

    // task 3
    public long getTotalNumberOfTransactions(boolean isFraud) throws Exception {
        return readAllTransactions().stream().filter(transaction -> transaction.isFraud() == isFraud).count();
    }//Expanding on Task 2 two, allow the caller of the method to pass in a flag (true/false) to get total number of transactions which either are fraudulent or not fraudulent.

    // task 4
    public Set<Transaction> getFraudulentTransactionsForMerchantId(String merchantId) throws Exception {
        return readAllTransactions().stream()//predicate on object
                .filter(transaction -> transaction.getMerchantId().equals(merchantId) && transaction.isFraud())
                .collect(Collectors.toUnmodifiableSet());
    }//Return all fraudulent transactions for Merchant XXX (pass in variable). Run test provided to validate.

    // task 5
    public Set<Transaction> getTransactionsForMerchantId(String merchantId, boolean isFraud) throws Exception {
        return readAllTransactions().stream()
                .filter(transaction -> transaction.getMerchantId().equals(merchantId))
                .filter(transaction -> transaction.isFraud()==isFraud)
                .collect(Collectors.toUnmodifiableSet());
    }//Return all fraudulent transactions based on variables provided by method caller (merchant ID and fraudulent payment flag). Run test provided to validate.

    // task 6
    public List<Transaction> getAllTransactionsSortedByAmount() throws Exception {
        return readAllTransactions().stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .collect(Collectors.toList());
    }//Return a List of all transactions sorted by amount. Create a test to validate the list.

    // task 7
    public double getFraudPercentageForMen() throws Exception {
        double fraudulentMen = readAllTransactions().stream()
                .filter(transaction -> transaction.getGender().equals("M"))
                .collect(Collectors.toList()).stream()
                .filter(Transaction::isFraud).count();
        double listSize = readAllTransactions().stream().filter(Transaction::isFraud).count();
        return fraudulentMen/listSize;
    }//Return the % of fraudulent transactions carried out by men. Run test provided to validate.

    // task 8    -- sherpa team had an initial problem with this, it used to be checking for set<Transaction> instead of <string>
    //   Became stuck here until it was addressed that it was an error on their end.
    public Set<String> getCustomerIdsWithNumberOfFraudulentTransactions(int numberOfFraudulentTransactions) throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::getCustomerId,Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry ->stringLongEntry.getValue() >= numberOfFraudulentTransactions)
                .map(Map.Entry::getKey).collect(Collectors.toSet());
    }//Produce a Set of Strings of customer IDs who have equals or higher number of fraudulent transactions (based on variable passed into method). Create a test to validate

    // task 9
    public Map<String, Integer> getCustomerIdToNumberOfTransactions() throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::getCustomerId, Collectors.summingInt(value -> 1)));
    } //self note: summarizingInt results in inequality constraints - intSummary statistics and integer
    //Using a map – return the Customer IDs with total number of fraudulent transaction. Create a test to validate

    // task 10
    public Map<String, Double> getMerchantIdToTotalAmountOfFraudulentTransactions() throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud)
                .collect(Collectors.groupingBy(Transaction::getMerchantId,Collectors.summingDouble(value -> 1)));
    }//Using a map – return the Merchant IDs with total amount of the fraudulent transactions. Create a test to validate

    // bonus
    public double getRiskOfFraudFigure(Transaction transaction) throws Exception {
        double customerFraudRisk = readAllTransactions().stream()
                .filter(transaction1 -> transaction1.getCustomerId().equals(transaction.getCustomerId()))
                .filter(Transaction::isFraud).count();
        double countUs = readAllTransactions().stream()
                .filter(transaction1 -> transaction1.getCustomerId().equals(transaction.getCustomerId())).count();
        return customerFraudRisk / countUs;
    }//write a small model which returns the probability (value between 0 and 1) that the passed in transaction might be a fraudulent one.
}
