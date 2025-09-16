package coupon.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Year;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Short> {

    @Override
    public Short convertToDatabaseColumn(Year attribute) {
        return (short) attribute.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Short dbValue) {
        return Year.of(dbValue);
    }
}
