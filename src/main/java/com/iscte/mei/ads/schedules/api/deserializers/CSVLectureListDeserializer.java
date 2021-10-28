package com.iscte.mei.ads.schedules.api.deserializers;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.iscte.mei.ads.schedules.api.models.WriteLecture;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CSVLectureListDeserializer extends Deserializer<Iterable<WriteLecture>> {

    private final ObjectReader reader = buildReader();

    @Override
    Iterable<WriteLecture> deserialize(String content) throws IOException {
        String decodedContent = decodeFromBase64(content);

        MappingIterator<WriteLecture> mi = reader.readValues(decodedContent);

        Iterable<WriteLecture> result = mi.readAll();

        if (!hasLectures(result)) throw new IllegalArgumentException();

        return result;
    }

    private ObjectReader buildReader() {
        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = CsvSchema
                .emptySchema()
                .withHeader();

        return mapper
                .readerFor(WriteLecture.class)
                .with(schema);
    }

    private boolean hasLectures(Iterable<WriteLecture> result) {
        return result.iterator().hasNext();
    }

}
