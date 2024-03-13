package hr.fer.unifier.backend.util.pagination;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class UnifierPage<T> {
    private final List<T> content;
    private final boolean first;
    private final boolean last;

    public UnifierPage(Page<T> page){
        this.content = page.getContent();
        this.first = page.isFirst();
        this.last = page.isLast();
    }

    public UnifierPage(List<T> content, boolean first, boolean last){
        this.content = content;
        this.first = first;
        this.last = last;
    }
}
