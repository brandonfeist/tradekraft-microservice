package com.tradekraftcollective.microservice.specification;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@Slf4j
public class ReleaseSpecification implements Specification<Release> {

    private SearchCriteria criteria;

    public ReleaseSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Release> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                log.info("Build like [{}] [{}]", criteria.getKey(), criteria.getValue());
                return builder.like(
                        builder.lower(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if(criteria.getOperation().equalsIgnoreCase("in")) {
            if(root.get(criteria.getKey()).getJavaType() == String.class) {
                String[] joinValues = ((String) criteria.getValue()).split(",");

                query.distinct(true);

                Join join = root.join(criteria.getKey());
                return builder.like(
                        builder.lower(join.get(joinValues[0])), "%" + joinValues[1].toLowerCase() + "%");
            } else {
                String[] joinValues = ((String) ((SearchCriteria) criteria.getValue()).getValue()).split(",");

                query.distinct(true);

                Join join1 = root.join(criteria.getKey());
                Join join2 = join1.join(((SearchCriteria) criteria.getValue()).getKey());
                return builder.like(
                        builder.lower(join2.get(joinValues[0])), "%" + joinValues[1].toLowerCase() + "%");
            }
        }

        return null;
    }
}
