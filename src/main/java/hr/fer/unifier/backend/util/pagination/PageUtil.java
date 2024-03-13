package hr.fer.unifier.backend.util.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PageUtil {
    public static <T,Z> UnifierPage<T> map(Page<Z> objects, Function<Z,T> mapper){
        List<T> list = objects.stream().map(mapper).toList();
        return new UnifierPage<>(list, objects.isFirst(), objects.isLast());
    }

    public static <T> UnifierPage<T> toPage(List<T> data, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        List<T> content;
        if (data.size() < pageNumber * pageSize){
            content = new ArrayList<>();
        }else{
            content = data.subList(pageNumber * pageSize, Math.min((pageNumber + 1) * pageSize, data.size()));
        }

        return new UnifierPage<>(new PageImpl<>(content,pageable,data.size()));
    }
}
