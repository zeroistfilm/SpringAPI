package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class jpaUserRepository implements UserRepository{

    //엔티티 매니저가 모든 데이터를 관리한다.
    private final EntityManager em;

    public jpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long uid) {
        User user = em.find(User.class, uid);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

//    @Override
//    public Optional<User> findForPages(Integer page) {
//        return Optional.empty();
//    }
//
//    @Override
//    public User updateName(User user, String newname) {
//
////        //findbyname
////        List<User> result = em.createQuery("select u from User as u where u.name = :name", User.class)
////                .setParameter("name", newname)
////                .getResultList();
////
////        return result.stream().findAny();
//
//        return null;
//    }

    @Override
    public List<User> findAll() {
        //select u -> 객체 자체를 반환.
        return em.createQuery("select u from User as u", User.class)
                .getResultList();
    }
}
