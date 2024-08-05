package coupon.common.converter;

import java.time.Month;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
