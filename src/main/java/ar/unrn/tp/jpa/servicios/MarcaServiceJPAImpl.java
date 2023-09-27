package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.exceptions.BusinessException;
import jakarta.persistence.EntityManagerFactory;


public class MarcaServiceJPAImpl extends ServiceJPAImpl implements MarcaService {
    public MarcaServiceJPAImpl(EntityManagerFactory entityManager) {
        super(entityManager);
    }

    @Override
    public void crearMarca(String marca) {
        inTransactionExecute((em) -> {
            Marca m = em.find(Marca.class, marca);
            if (m != null)
                throw new BusinessException("La marca " + marca + " ya existe");
            else {
                m = new Marca(marca);
                em.persist(m);
            }
        });
    }

    @Override
    public Marca encontrarMarca(String marca) {
        Marca[] m = new Marca[1];
        inTransactionExecute((em) -> {
            m[0] = em.find(Marca.class, marca);
            if (m[0] == null)
                throw new BusinessException("No existe la marca " + marca);
        });
        return m[0];
    }
}
