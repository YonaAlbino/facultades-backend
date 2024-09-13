package com.example.facultades.generics;

import java.util.List;
import java.util.Optional;

public interface IgenericService <E extends BaseEntity, ID extends Number>{
    public List<E> getAll();
    public E save(E entidad);
    public Optional<E> findById(ID id);
    public void delete(ID id);
    public E update(E entidad);
}
