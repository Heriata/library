package deveducate.library.services.mappers;

import deveducate.library.dtos.BookEntryDto;
import deveducate.library.models.BookEntryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookEntryMapper {

    BookEntryEntity toBookEntryEntity(BookEntryDto dto);
}
