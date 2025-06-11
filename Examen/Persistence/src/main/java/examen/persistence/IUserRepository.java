package examen.persistence;

import examen.model.User;

public interface IUserRepository extends IRepository<Integer, User> {
    User findByUsername(String username);
}
