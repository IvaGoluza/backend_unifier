package hr.fer.unifier.backend.util.pagination;

import org.springframework.data.domain.Page;

import java.util.function.Function;

public class PageUtil {
    public static <T,Z> Page<T> map(Page<Z> objects, Function<Z,T> mapper){
        return objects.map(mapper);
    }
}
