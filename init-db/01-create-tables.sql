-- Initial setup for budget app database

-- Create categories table
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500),
    type VARCHAR(20) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))
);

-- Insert default categories
INSERT INTO categories (name, description, type) VALUES 
('Pensja', 'Wynagrodzenie ze stosunku pracy', 'INCOME'),
('Freelance', 'Dodatkowe zlecenia', 'INCOME'),
('Inwestycje', 'Zyski z inwestycji', 'INCOME'),
('Inne przychody', 'Pozostałe źródła dochodów', 'INCOME'),

('Jedzenie', 'Zakupy spożywcze i restauracje', 'EXPENSE'),
('Transport', 'Komunikacja publiczna, paliwo, naprawy', 'EXPENSE'),
('Mieszkanie', 'Czynsz, rachunki, utrzymanie domu', 'EXPENSE'),
('Rozrywka', 'Kino, gry, hobby', 'EXPENSE'),
('Zdrowie', 'Lekarze, leki, ubezpieczenie', 'EXPENSE'),
('Ubrania', 'Odzież i obuwie', 'EXPENSE'),
('Edukacja', 'Kursy, książki, szkolenia', 'EXPENSE'),
('Inne wydatki', 'Pozostałe wydatki', 'EXPENSE')
ON CONFLICT (name) DO NOTHING;
