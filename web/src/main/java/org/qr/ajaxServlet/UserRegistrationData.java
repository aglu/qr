package org.qr.ajaxServlet;

import java.util.Map;

import org.qr.ajaxServlet.process.RegistrationUser;


import lombok.Data;

@Data
public class UserRegistrationData {

    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String docNumber;

    public void writeFromData(Map<String, Object> request) throws ApiException {
        String phone = String.valueOf(request.get("phone"));
        if (phone.equals("null") || phone.length() != 10 || phone.startsWith("9")) {
            throw new ApiException("Номер телефона не указан или указан неверно");
        }
        String firstName = String.valueOf(request.get("firstName"));
        if (firstName.equals("null") || firstName.length() < 2) {
            throw new ApiException("Не заполнено имя пользователя");
        }
        String lastName = String.valueOf(request.get("lastName"));
        if (lastName.equals("null") || lastName.length() < 2) {
            throw new ApiException("Не заполнена фамилия пользователя");
        }
        String middleName = String.valueOf(request.get("middleName"));
        if (middleName.equals("null")) {
            middleName = "";
        }
        String docNumber = String.valueOf(request.get("docNumber"));
        if (docNumber == null || docNumber.length() < 2) {
            throw new ApiException("Не заполнена информация о документе");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
        this.docNumber = docNumber;
    }
}
