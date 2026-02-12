package org;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.model.Tarea;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Tarea t1 = new Tarea("Fregar los platos");
        Tarea t2 = new Tarea("Pasear al perro");
        Tarea t3 = new Tarea("Estudiar Acceso a Datos");

        entityManager.persist(t1);
        entityManager.persist(t2);
        entityManager.persist(t3);

        System.out.println("Tarea 1: "+t1);
        System.out.println("Tarea 2: "+t2);
        System.out.println("Tarea 3: "+t3);

        transaction.commit();
        entityManager.close();

    }
}