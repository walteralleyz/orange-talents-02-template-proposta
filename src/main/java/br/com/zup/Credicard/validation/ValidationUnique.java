package br.com.zup.Credicard.validation;

import br.com.zup.Credicard.annotation.Unique;
import br.com.zup.Credicard.exception.MultipleFieldsException;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;

public class ValidationUnique implements ConstraintValidator<Unique, Object> {
    private String field;
    private Class<?> domain;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(Unique params) {
        field = params.fieldName();
        domain = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = em.createQuery(String.format(
            "select c from %s c where c.%s = :value", domain.getName(), field
        ), domain);

        query.setParameter("value", value);

        try {
            query.getSingleResult();

            throw new MultipleFieldsException(Collections.singletonMap(field, field + " multiple not accepted!"));

        } catch (NoResultException e) {
            return true;
        }
    }
}
