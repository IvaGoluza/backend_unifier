package hr.fer.unifier.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static hr.fer.unifier.backend.util.file.FileUtil.convertToBase64;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CommonMapper {
    public String toBase64(byte[] image){
        return image != null ? convertToBase64(image) : null;
    }
}
