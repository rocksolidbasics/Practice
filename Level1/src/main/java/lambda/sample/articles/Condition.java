package lambda.sample.articles;

/**
 *
 * @author mmueller
 */
public interface Condition<T> {
  boolean test(T t);
}