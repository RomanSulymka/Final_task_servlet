package com.travel_agency.entity;

import com.travel_agency.exceptions.TravelAgencyDataWrongException;

import java.util.Objects;

public class User extends Entity {

    private String name;
    private String surname;
    private double discount;
    private double money;
    private String email;
    private String login;
    private String password;
    private RoleType role;

    public User() {
    }

    public User(int id, String name, String surname, double discount, double money,
                String email, String login, String password, RoleType role) {

        super(id);
        this.name = name;
        this.surname = surname;
        this.discount = discount;
        this.money = money;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TravelAgencyDataWrongException {
        if (name != null && !name.equals("")) {
            this.name = name;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect name value.");
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws TravelAgencyDataWrongException {
        if (surname != null && !surname.equals("")) {
            this.surname = surname;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect surname value.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws TravelAgencyDataWrongException {
        if (email != null && !email.equals("")) {
            this.email = email;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect email value.");
        }
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) throws TravelAgencyDataWrongException {
        if (discount >= 0 && discount <= 100) {
            this.discount = discount;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect discount value, it will be from 0 to 100%.");
        }
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) throws TravelAgencyDataWrongException {
        if (money >= 0) {
            this.money = money;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect money value.");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws TravelAgencyDataWrongException {
        if (login != null && !login.equals("")) {
            this.login = login;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect login value.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws TravelAgencyDataWrongException {
        if (password != null && !password.equals("")) {
            this.password = password;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect password value.");
        }
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) throws TravelAgencyDataWrongException {
        if (role != null) {
            this.role = role;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect role value.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Double.compare(user.discount, discount) == 0 &&
                Double.compare(user.money, money) == 0 &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                email.equals(user.email) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, email, discount, money, login, password, role);
    }

    @Override
    public String toString() {
        return "User{" + super.toString() +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", discount=" + discount +
                ", money=" + money +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}