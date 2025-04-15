package deveducate.library.services.mappers;

import deveducate.library.dtos.BookDto;
import deveducate.library.models.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "bookId", ignore = true)
    @Mapping(target = "bookName", source = "bookName")
    BookEntity toBookEntity(BookDto dto);
}
