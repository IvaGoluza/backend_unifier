package hr.fer.unifier.backend.util.specification;

import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.db.user.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class RequestSpecification {
    private RequestSpecification(){}

    public static Specification<Request> inCity(String city){
        return (root, query, criteriaBuilder) -> {
            Join<User, Request> userRequestJoin = root.join("user");
            return criteriaBuilder.equal(userRequestJoin.get("volunteerCenter"), city);
        };
    }

    public static Specification<Request> hasHelpType(String helpType){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("helpType"), helpType);
    }

    public static Specification<Request> hasCategory(String category){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }
}
