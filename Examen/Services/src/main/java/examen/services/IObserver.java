package examen.services;

import examen.model.Joc;
import examen.persistence.ValidationException;

public interface IObserver {
    void jocTerminat(Joc[] jocuri) throws ValidationException;
}
