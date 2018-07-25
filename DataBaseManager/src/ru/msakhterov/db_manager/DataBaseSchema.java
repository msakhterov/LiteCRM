package ru.msakhterov.db_manager;

public class DataBaseSchema {
    public static final class DataType {
        public static final String PK_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
        public static final String INT_TYPE = " INTEGER";
        public static final String TEXT_TYPE = " TEXT";
        public static final String DATE_TYPE = " LONG";
    }

    public static final class UsersTable {
        public static final String NAME = "users";

        public static final class Colons {
            public static final String ID = "id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String BIRTHDAY = "birthday";
            public static final String PHONE_NUMBER = "phone_number";
            public static final String EMAIL = "email";
            public static final String ORGANIZATION_ID = "organization_id";
            public static final String ACCESS_LEVEL = "access_level";
            public static final String LOGIN = "login";
            public static final String PASSWORD = "password";
        }
    }

    public static final class RepresentativesTable {
        public static final String NAME = "representatives";

        public static final class Colons {
            public static final String ID = "id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String BIRTHDAY = "birthday";
            public static final String PHONE_NUMBER = "phone_number";
            public static final String EMAIL = "email";
            public static final String ORGANIZATION_ID = "organization_id";
        }
    }

    public static final class OrganizationsTable {
        public static final String NAME = "organizations";

        public static final class Colons {
            public static final String ID = "id";
            public static final String LEGAL_FORM = "legal_form";
            public static final String FULL_NAME = "full_name";
            public static final String SHORT_NAME = "short_name";
            public static final String TIN = "tin";
            public static final String ADDRESS = "address";
            public static final String TYPE = "type";
        }
    }
}
