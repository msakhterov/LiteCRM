package ru.msakhterov.crm_common.entity;

import java.util.Date;
import java.util.UUID;

public class User implements Entity {
    private final UUID uuid;
    private final String firstName;
    private final String lastName;
    private final Date birthday;
    private final String phoneNumber;
    private final String email;
    private final Integer organization;
    private final Integer accessLevel;
    private final String login;
    private final String password;

    public User(Builder builder) {
        this.uuid = builder.uuid;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthday = builder.birthday;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.organization = builder.organization;
        this.accessLevel = builder.accessLevel;
        this.login = builder.login;
        this.password = builder.password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Integer getOrganization() {
        return organization;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {

        private UUID uuid;
        private String firstName;
        private String lastName;
        private Date birthday;
        private String phoneNumber;
        private String email;
        private Integer organization;
        private Integer accessLevel;
        private String login;
        private String password;

        public Builder() {

        }

        public Builder(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setUuid() {
            this.uuid = UUID.randomUUID();
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setOrganization(Integer organization) {
            this.organization = organization;
            return this;
        }

        public Builder setAccessLevel(Integer accessLevel) {
            this.accessLevel = accessLevel;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
