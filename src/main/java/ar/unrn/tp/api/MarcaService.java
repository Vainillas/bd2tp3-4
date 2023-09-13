package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Marca;

public interface MarcaService {
    void crearMarca(String marca);
    Marca encontrarMarca(String marca);
}
