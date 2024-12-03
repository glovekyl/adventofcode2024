package core;

import java.util.function.Function;

/**
 * Represents a result of an operation that can either be a success or an error.
 */
public class Result<S, E> {
  private final S success;
  private final E error;

  private Result(S success, E error) {
    this.success = success;
    this.error = error;
  }

  public static <S, E> Result<S, E> success(S success) {
    return new Result<>(success, null);
  }
  
  public static <S, E> Result<S, E> error(E error) {
    return new Result<>(null, error);
  }

  public boolean isSuccess() {
    return success != null;
  }
    
  public boolean isError() {
      return error != null;
  }
      
  public S getSuccess() {
      return success;
  }
      
  public E getError() {
      return error;
  }

  public <T> Result<T, E> mapSuccess(Function<S, T> fn) {
      return Result.success(fn.apply(success));
  }
  
  public <T> Result<S, T> mapError(Function<E, T> fn) {
      return Result.error(fn.apply(error));
  }
}
