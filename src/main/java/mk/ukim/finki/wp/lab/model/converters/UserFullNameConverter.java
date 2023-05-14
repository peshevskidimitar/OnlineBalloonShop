package mk.ukim.finki.wp.lab.model.converters;

import mk.ukim.finki.wp.lab.model.UserFullName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserFullNameConverter implements AttributeConverter<UserFullName, String> {

    private static final String SEPARATOR = " ";

    @Override
    public String convertToDatabaseColumn(UserFullName userFullName) {
        if (userFullName == null)
            return null;

        StringBuilder stringBuilder = new StringBuilder();
        if (userFullName.getName() != null && !userFullName.getName().isEmpty())
            stringBuilder.append(userFullName.getName()).append(SEPARATOR);
        if (userFullName.getSurname() != null && !userFullName.getSurname().isEmpty())
            stringBuilder.append(userFullName.getSurname());

        return stringBuilder.toString();
    }

    @Override
    public UserFullName convertToEntityAttribute(String fullName) {
        if (fullName == null || fullName.isEmpty())
            return null;

        String[] parts = fullName.split(SEPARATOR);
        if (parts.length == 0)
            return null;

        UserFullName userFullName = new UserFullName();
        if (parts.length == 1)
            userFullName.setName(parts[0]);
        else if (parts.length == 2) {
            userFullName.setName(parts[0]);
            userFullName.setSurname(parts[1]);
        }

        return userFullName;
    }

}
