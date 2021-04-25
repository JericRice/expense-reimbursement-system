package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * configuration connection for production
 * */
public class TestingConfig {
    public static String url = "jdbc:h2:C:\\Users\\Jeric\\Desktop";
    public static String username = "sa";
    public static String password = "sa";

    public static void h2InitDao() {
        //I am inserting two pokemon "types" into the database, but my Pokemon model in the model layer
        //	only uses one type. I simplified the model for demo purposes. AND to demo that your model can
        //	differ from the actual database table, in JDBC.

        try(Connection conn=
                    DriverManager.getConnection(url,username, password))
        {
            String sql= "/*Reimbursement type lookup table*/\n" +
                    "CREATE TABLE ERS_REIMBURSEMENT_TYPE(\n" +
                    "\tREIMB_TYPE_ID int NOT NULL,\n" +
                    "\tREIMB_TYPE varchar(10) NOT NULL,\n" +
                    "\tPRIMARY KEY(REIMB_TYPE_ID)\n" +
                    ");\n" +
                    "\n" +
                    "/*Reimbursement status lookup table*/\n" +
                    "CREATE TABLE ERS_REIMBURSEMENT_STATUS(\n" +
                    "\tREIMB_STATUS_ID int NOT NULL,\n" +
                    "\tREIMB_STATUS varchar(10) NOT NULL,\n" +
                    "\tPRIMARY KEY(REIMB_STATUS_ID)\n" +
                    ");\n" +
                    "\n" +
                    "/*user role lookup table*/\n" +
                    "CREATE TABLE ERS_USER_ROLES(\n" +
                    "\tERS_USER_ROLE_ID int NOT NULL,\n" +
                    "\tUSER_ROLE varchar(10) NOT NULL,\n" +
                    "\tPRIMARY KEY(ERS_USER_ROLE_ID)\n" +
                    ");\n" +
                    "\n" +
                    "/*users table*/\n" +
                    "CREATE TABLE ERS_USERS(\n" +
                    "\tERS_USER_ID SERIAL NOT NULL,\n" +
                    "\tERS_USERNAME varchar(50) NOT NULL,\n" +
                    "\tERS_PASSWORD varchar(50) NOT NULL,\n" +
                    "\tUSER_FIRST_NAME varchar(100) NOT NULL,\n" +
                    "\tUSER_LAST_NAME varchar(100) NOT NULL,\n" +
                    "\tUSER_EMAIL varchar(150) NOT NULL,\n" +
                    "\tUSER_ROLE_ID int NOT NULL,\n" +
                    "\tCONSTRAINT USER_ROLES_FK FOREIGN KEY(USER_ROLE_ID) REFERENCES ERS_USER_ROLES(ERS_USER_ROLE_ID),\n" +
                    "\tUNIQUE(ERS_USERNAME, USER_EMAIL),\n" +
                    "\tPRIMARY KEY(ERS_USER_ID)\n" +
                    ");\n" +
                    "\n" +
                    "/*reimbursement data*/\n" +
                    "CREATE TABLE ERS_REIMBURSEMENT (\n" +
                    "\tREIMB_ID SERIAL NOT NULL,\n" +
                    "\tREIMB_AMOUNT DECIMAL NOT NULL,\n" +
                    "\tREIMB_SUBMITTED TIMESTAMP DEFAULT NOW(),\n" +
                    "\tREIMB_RESOLVED TIMESTAMP,\n" +
                    "\tREIMB_DESCRIPTION varchar(250),\n" +
                    "\tREIMB_RECEIPT BYTEA,\n" +
                    "\tREIMB_AUTHOR int,\n" +
                    "\tREIMB_RESOLVER int,\n" +
                    "\tREIMB_STATUS_ID int,\n" +
                    "\tREIMB_TYPE_ID int,\n" +
                    "\tCONSTRAINT ERS_USERS_FK_AUTH FOREIGN KEY(REIMB_AUTHOR) REFERENCES ERS_USERS(ERS_USER_ID),\n" +
                    "\tCONSTRAINT ERS_USERS_FK_RESLVR FOREIGN KEY(REIMB_RESOLVER) REFERENCES ERS_USERS(ERS_USER_ID),\n" +
                    "\tCONSTRAINT ERS_REIMBURSEMENT_STATUS_FK FOREIGN KEY (REIMB_STATUS_ID) REFERENCES ERS_REIMBURSEMENT_STATUS(REIMB_STATUS_ID),\n" +
                    "\tCONSTRAINT ERS_REIMBURSEMENT_TYPE_FK FOREIGN KEY(REIMB_TYPE_ID) REFERENCES ERS_REIMBURSEMENT_TYPE(REIMB_TYPE_ID),\n" +
                    "\tPRIMARY KEY(REIMB_ID)\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "/*hard coded values for the lookup tables*/\n" +
                    "INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (1, 'LODGING');\n" +
                    "INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (2, 'TRAVEL');\n" +
                    "INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (3, 'FOOD');\n" +
                    "INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (4, 'OTHER');\n" +
                    "\n" +
                    "INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) VALUES (1,'PENDING');\n" +
                    "INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) VALUES (2,'APPROVED');\n" +
                    "INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) VALUES (3,'DENIED');\n" +
                    "\n" +
                    "INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (1, 'EMPLOYEE');\n" +
                    "INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (2, 'MANAGER');";

            Statement state = conn.createStatement();
            state.execute(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public static void h2DestroyDao() {
        try(Connection conn=
                    DriverManager.getConnection(url,username, password))
        {
            String sql= "DROP TABLE ers_reimbursement;\n" +
                    "DROP TABLE ers_users;\n" +
                    "DROP TABLE ers_reimbursement_status;\n" +
                    "DROP TABLE ers_reimbursement_type;\n" +
                    "DROP TABLE ers_user_roles;";

            Statement state = conn.createStatement();
            state.execute(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

}
