package coupon.repository;

@FunctionalInterface
public interface RepositoryMethod<T> {

    T execute();
}
