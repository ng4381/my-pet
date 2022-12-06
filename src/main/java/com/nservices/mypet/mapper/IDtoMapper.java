package com.nservices.mypet.mapper;

public interface IDtoMapper<O, E> {
    E ObjectToEntity(O object);
    O EntityToObject(E entity);
}
