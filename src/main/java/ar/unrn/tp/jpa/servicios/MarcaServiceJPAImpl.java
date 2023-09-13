package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.modelo.Marca;

import javax.persistence.EntityManagerFactory;

public class MarcaServiceJPAImpl extends ServiceJPAImpl implements MarcaService {
    public MarcaServiceJPAImpl(EntityManagerFactory entityManager) {
        super(entityManager);
    }

    @Override
    public void crearMarca(String marca) {
        inTransactionExecute((em) -> {
            Marca m = new Marca(marca);
            em.persist(m);
        });
    }

    @Override
    public Marca encontrarMarca(String marca) {
        Marca[] m = new Marca[1];
        inTransactionExecute((em) -> {
            m[0] = em.find(Marca.class, marca);
        });
        return m[0];
    }
}
