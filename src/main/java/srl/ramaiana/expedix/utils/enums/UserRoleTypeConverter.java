package srl.ramaiana.expedix.utils.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import srl.ramaiana.expedix.model.entity.enums.RolesEnum;

@Converter(autoApply = true)
public class UserRoleTypeConverter implements AttributeConverter<RolesEnum, String> {

    @Override
    public String convertToDatabaseColumn(RolesEnum attribute) {

        return attribute != null ? attribute.name() : null;
    }

    @Override
    public RolesEnum convertToEntityAttribute(String dbData) {
        return dbData != null ? RolesEnum.valueOf(dbData) : null;
    }
}
