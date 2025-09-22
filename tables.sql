CREATE TABLE IF NOT EXISTS advisors (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    advisor_id INTEGER NOT NULL,
    CONSTRAINT fk_advisor FOREIGN KEY (advisor_id) REFERENCES advisors(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contracts (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    client_id INTEGER NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS incidents (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    date TIMESTAMP NOT NULL,
    cost NUMERIC(10, 2) NOT NULL,
    description TEXT,
    contract_id INTEGER NOT NULL,
    CONSTRAINT fk_contract FOREIGN KEY (contract_id) REFERENCES contracts(id) ON DELETE CASCADE
);
