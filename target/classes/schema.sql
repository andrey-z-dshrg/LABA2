-- Очистка таблиц (если нужно)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS leases;
DROP TABLE IF EXISTS properties;
DROP TABLE IF EXISTS tenants;
DROP TABLE IF EXISTS landlords;

-- Создание таблицы landlords
CREATE TABLE landlords (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255)
);

-- Создание таблицы properties
CREATE TABLE properties (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(500) NOT NULL,
    type VARCHAR(100),
    price DECIMAL(10,2),
    area DOUBLE,
    description TEXT,
    landlord_id BIGINT,
    FOREIGN KEY (landlord_id) REFERENCES landlords(id)
);

-- Создание таблицы tenants
CREATE TABLE tenants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255)
);

-- Создание таблицы leases
CREATE TABLE leases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    property_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    monthly_rent DECIMAL(10,2),
    status VARCHAR(50),
    FOREIGN KEY (property_id) REFERENCES properties(id),
    FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);

-- Создание таблицы payments
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lease_id BIGINT NOT NULL,
    amount DECIMAL(10,2),
    payment_date DATE,
    due_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (lease_id) REFERENCES leases(id)
);