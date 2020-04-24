CREATE TABLE IF NOT EXISTS notes (
    id INT PRIMARY KEY,
    entry VARCHAR(255),
    category VARCHAR(255),
    creationdatetime TIMESTAMP
)