package hr.fer.unifier.backend.util.specification;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.user.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class AdvertSpecification {
    private AdvertSpecification(){}

    public static Specification<Advert> inCity(String city){
        return (root, query, criteriaBuilder) -> {
            Join<User, Advert> userRequestJoin = root.join("user");
            return criteriaBuilder.equal(userRequestJoin.get("volunteerCenter"), city);
        };
    }

    public static Specification<Advert> hasHelpType(String helpType){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("helpType"), helpType);
    }

    public static Specification<Advert> hasCategory(String category){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }
}
