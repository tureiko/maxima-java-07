package org.example.repository;

import java.util.List;

public interface BaseRepository<T, I> {
    // CRUD
    boolean create(T element) ;       // create() ,save()
    T read(I id);                     // read(), finById()   get()        // может вернуть null (можно обернуть в Optional)
    int update(I id, T element);      // update(), save()
    void delete(I id);                // delete(), remove()

    // Search
    List<T> findAll();                // search(), get ... select   // может вернуть null (можно обернуть в Optional)

}
