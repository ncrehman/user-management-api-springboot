SELECT * FROM etheotel.users;

CREATE TABLE etheotel.roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    rolesName VARCHAR(255) NOT NULL,        
    otherRolesName VARCHAR(255) NOT NULL,         
    rolesLevel int                    
);
CREATE TABLE etheotel.status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, 
    statusName VARCHAR(255) NOT NULL,        
    usedFor VARCHAR(255) NOT NULL,         
    createdDate TIMESTAMP                    
);

CREATE TABLE etheotel.users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for each user
    full_name VARCHAR(255) NOT NULL,      -- Full name of the user
    email_address VARCHAR(255) UNIQUE NOT NULL, -- Email address (unique)
    profile_image VARCHAR(255),           -- Path/URL to the profile image
    mobile_number VARCHAR(15) UNIQUE NOT NULL,            -- Mobile number
    password VARCHAR(255) NOT NULL,       -- Hashed password
    is_deleted BOOLEAN DEFAULT FALSE,     -- Flag for soft deletion
    roles_id BIGINT,                      -- Foreign key to roles table
    status_id BIGINT,                     -- Foreign key to status table
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date of creation
    CONSTRAINT fk_users_roles FOREIGN KEY (roles_id) REFERENCES roles (id) ON DELETE SET NULL,
    CONSTRAINT fk_users_status FOREIGN KEY (status_id) REFERENCES status (id) ON DELETE SET NULL
);