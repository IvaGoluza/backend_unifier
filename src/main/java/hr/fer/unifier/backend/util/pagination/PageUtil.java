package hr.fer.unifier.backend.util.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

public class PageUtil {
    public static <T,Z> Page<T> map(Page<Z> objects, Function<Z,T> mapper){
        return objects.map(mapper);
    }

    public static <T> Page<T> toPage(List<T> acceptedDeals, Pageable pageable) {
        return new PageImpl<>(acceptedDeals,pageable,acceptedDeals.size());
    }
}
