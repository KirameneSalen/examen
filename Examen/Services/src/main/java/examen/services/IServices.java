package examen.services;

import examen.model.Configuratie;
import examen.model.Joc;
import examen.model.User;
import examen.persistence.ValidationException;

public interface IServices {
    User login(User user, IObserver client) throws ValidationException;
    Joc incepeJoc(User user) throws ValidationException;
    String ghicestePozitie(Joc joc, int pozitieLinie, int pozitieColoana) throws ValidationException;
    void terminaJoc(Joc joc, IObserver client) throws ValidationException;
    Joc[] clasament() throws ValidationException;
    Joc[] jocuriPierdute(User user) throws ValidationException;
    Configuratie schimbaConfiguratie(int id, int pozitieLinie, int pozitieColoana, String animal, String url) throws ValidationException;
}
