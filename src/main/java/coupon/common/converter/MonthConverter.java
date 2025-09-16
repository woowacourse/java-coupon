package coupon.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Month;

@Converter(autoApply = true)
public class MonthConverter implements AttributeConverter<Month, Short> {

    @Override
    public Short convertToDatabaseColumn(Month attribute) {
        return (short) attribute.getValue();
    }

    @Override
    public Month convertToEntityAttribute(Short dbValue) {
        return Month.of(dbValue);
    }
}
