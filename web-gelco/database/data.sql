-- PARA LA TABLA categoria

INSERT INTO dbo.categorias (nombre)
VALUES ('Belleza');

-- PARA LA TABLA productos

INSERT INTO dbo.productos (nombre, descripcion, precio, stock, categoria_id, activo)
VALUES 
('Labial Mate', 'Labial de larga duración', 25.50, 100, 1, 1),
('Perfume Floral', 'Fragancia fresca', 80.00, 50, 1, 1),
('Crema Facial', 'Hidratante diaria', 45.00, 70, 1, 1);