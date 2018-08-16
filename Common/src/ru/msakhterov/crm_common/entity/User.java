package ru.msakhterov.crm_common.entity;

public class User implements Entity {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final Long birthday;
    private final String phoneNumber;
    private final String email;
    private final Integer organization;
    private final Integer accessLevel;
    private final String login;
    private final String password;

    public User(Builder builder) {
        this.id = builder.id;
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

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getBirthday() {
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

        private Integer id;
        private String firstName;
        private String lastName;
        private Long birthday;
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

        public Builder setId(Integer id) {
            this.id = id;
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

        public Builder setBirthday(Long birthday) {
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

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
