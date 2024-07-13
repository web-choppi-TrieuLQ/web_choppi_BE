package tlq.com.tlq_sale_management.service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();
    boolean create(T t);
    boolean update(T t);
    T findById(Long id);
    boolean delete(T t);
}
