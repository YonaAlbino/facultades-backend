package com.example.facultades.util;

import com.example.facultades.generics.BaseEntity;

public interface InotificarPropietario <E extends BaseEntity> {
    Long retornarPorpietario(E entidad);
}
