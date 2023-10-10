package ru.spbu.apcyb.svp.tasks.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is needed exclusively for the implementation of the key method.
 * <br>
 * The search for exchange combinations.
 *
 * @author Evgeny
 */

public class SumExchange {

  public static final Logger logger = Logger.getLogger(SumExchange.class.getName());

  private final long sum;
  private final List<Long> denominations;
  private final List<long[]> combinations;
  private long countOfCombination = 0;

  /**
   * Constructor - creating a new storage object. In it object we store the processed input data. It
   * performs the main exchange method.
   *
   * @param sum          the number to be exchanged
   * @param denomination the nominal value of the received coins
   */

  public SumExchange(long sum, List<Long> denomination) {
    this.sum = sum;
    this.denominations = denomination;
    combinations = new ArrayList<>();
  }

  /**
   * The method of finding all possible exchange options.
   * <br>
   * By recursion at denominations
   * <br>
   * {@link #allCombinationOfExchanges allCombinationOfExchanges} method performs the main role
   */
  public void getAllCombinations() {
    allCombinationOfExchanges(sum, new long[denominations.size()], 0);
    logger.log(Level.INFO, "Count of combination : {0}", countOfCombination);
  }

  public List<long[]> getAllCombinationsWithList() {
    allCombinationOfExchanges(sum, new long[denominations.size()], 0);
    return combinations;
  }

  private void allCombinationOfExchanges(long currentAmount, long[] nominalCount, int indexOfCoin) {

    long numberOfOccurrenceCoins = sum
        / denominations.get(indexOfCoin);

    for (long i = 0; i <= numberOfOccurrenceCoins; i++) {
      if (currentAmount >= 0) {
        nominalCount[indexOfCoin] = i;

        if (currentAmount == 0) {
          countOfCombination++;
          outputWithLogger(nominalCount);
          if (countOfCombination < 1000) {
            combinations.add(nominalCount);
          }
        } else if (indexOfCoin + 1 < denominations.size()) {
          allCombinationOfExchanges(currentAmount, nominalCount.clone(), indexOfCoin + 1);
        }
      }
      currentAmount -= denominations.get(indexOfCoin);
    }
  }

  private void outputWithLogger(long[] nominalsCount) {
    logger.log(Level.INFO, "Combination number : {0}", countOfCombination);
    for (int i = 0; i < nominalsCount.length; i++) {
      int j = 0;
      while (j < nominalsCount[i]) {
        logger.log(Level.INFO, "{0}", denominations.get(i));
        j++;
      }
    }
    logger.info("End of combination");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SumExchange that = (SumExchange) o;
    return sum == that.sum && countOfCombination == that.countOfCombination
        && Objects.equals(denominations, that.denominations) && Objects.equals(
        combinations, that.combinations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sum, denominations, combinations, countOfCombination);


  }
}
