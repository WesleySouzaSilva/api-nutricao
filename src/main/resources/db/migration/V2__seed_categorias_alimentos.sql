-- ============================================================
-- V2: Seed de categorias e alimentos comuns (TACO)
-- ============================================================

-- ------------------------------------------------------------
-- Categorias de alimentos
-- ------------------------------------------------------------
INSERT INTO categoria_alimento (nome) VALUES
('Cereais e derivados'),
('Legumes e verduras'),
('Frutas'),
('Carnes e ovos'),
('Leite e derivados'),
('Leguminosas'),
('Oleos e gorduras'),
('Acucares e doces'),
('Bebidas'),
('Miscelaneas');

-- ------------------------------------------------------------
-- Alimentos (50+ itens com dados nutricionais por 100g)
-- Fonte: Tabela TACO (Tabela Brasileira de Composicao de Alimentos)
-- ------------------------------------------------------------

-- Cereais e derivados (categoria 1)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Arroz branco cozido', '128', '2.5', '0.2', '28.1', '0.4', '1', 1),
('Arroz integral cozido', '124', '2.6', '1.0', '25.8', '2.7', '1', 1),
('Macarrao cozido', '158', '5.8', '1.8', '30.9', '0.0', '1', 1),
('Pao frances', '300', '8.0', '3.1', '58.6', '2.3', '506', 1),
('Pao integral', '253', '9.4', '3.7', '49.9', '6.9', '430', 1),
('Aveia em flocos', '394', '13.9', '8.5', '66.6', '9.1', '5', 1),
('Farinha de mandioca', '361', '1.6', '0.3', '87.9', '6.4', '2', 1),
('Farinha de milho', '352', '7.2', '1.9', '79.1', '5.5', '1', 1),
('Biscoito cream cracker', '434', '8.1', '14.4', '68.7', '2.5', '670', 1);

-- Legumes e verduras (categoria 2)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Alface crespa', '15', '1.3', '0.2', '2.8', '0.0', '3', 2),
('Alface americana', '10', '0.7', '0.1', '2.1', '1.1', '6', 2),
('Brocolis cozido', '28', '3.9', '0.5', '4.4', '3.4', '22', 2),
('Cenoura crua', '34', '1.3', '0.2', '7.7', '3.2', '11', 2),
('Tomate', '15', '1.1', '0.2', '3.1', '1.2', '1', 2),
('Batata doce cozida', '77', '1.4', '0.1', '18.4', '2.2', '12', 2),
('Batata inglesa cozida', '86', '2.0', '0.1', '19.9', '1.8', '4', 2),
('Abobora cozida', '30', '1.0', '0.1', '6.9', '2.5', '1', 2),
('Couve-flor cozida', '25', '1.8', '0.2', '4.4', '2.2', '15', 2),
('Espinafre cozido', '23', '2.9', '0.2', '4.3', '2.2', '70', 2);

-- Frutas (categoria 3)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Banana prata', '98', '1.3', '0.1', '26.0', '2.0', '1', 3),
('Maca', '56', '0.3', '0.1', '15.2', '1.3', '2', 3),
('Laranja pera', '37', '1.0', '0.1', '8.9', '0.8', '1', 3),
('Mamao papaia', '43', '0.5', '0.1', '11.2', '1.0', '3', 3),
('Abacaxi', '48', '0.9', '0.2', '12.3', '1.0', '2', 3),
('Melancia', '33', '0.9', '0.0', '8.1', '0.1', '1', 3),
('Uva comum', '68', '0.7', '0.2', '17.7', '0.9', '2', 3),
('Morango', '30', '0.9', '0.3', '6.8', '1.7', '1', 3);

-- Carnes e ovos (categoria 4)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Frango peito grelhado', '173', '31.0', '4.8', '0.0', '0.0', '63', 4),
('Frango sobrecoxa cozida', '241', '24.5', '15.4', '0.0', '0.0', '85', 4),
('Carne bovina patinho grelhado', '172', '31.9', '4.4', '0.0', '0.0', '58', 4),
('Carne bovina contra file grelhado', '194', '26.7', '9.7', '0.0', '0.0', '55', 4),
('Carne moida refogada', '207', '26.2', '11.7', '0.0', '0.0', '70', 4),
('Peixe tilapia grelhada', '128', '26.2', '2.7', '0.0', '0.0', '52', 4),
('Ovo de galinha cozido', '146', '13.3', '9.5', '0.6', '0.0', '128', 4),
('Ovo de galinha frito', '240', '15.6', '18.6', '1.2', '0.0', '166', 4);

-- Leite e derivados (categoria 5)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Leite integral', '60', '3.1', '3.0', '4.8', '0.0', '45', 5),
('Leite desnatado', '34', '3.4', '0.3', '5.0', '0.0', '44', 5),
('Queijo mussarela', '300', '22.6', '22.9', '1.0', '0.0', '627', 5),
('Queijo minas frescal', '243', '17.4', '20.2', '3.2', '0.0', '250', 5),
('Iogurte natural', '63', '3.6', '3.0', '5.8', '0.0', '50', 5),
('Requeijao cremoso', '235', '9.6', '22.4', '3.4', '0.0', '672', 5);

-- Leguminosas (categoria 6)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Feijao carioca cozido', '76', '4.8', '0.5', '13.6', '8.5', '2', 6),
('Feijao preto cozido', '77', '4.5', '0.5', '14.0', '8.4', '2', 6),
('Lentilha cozida', '93', '6.3', '0.5', '16.3', '7.9', '3', 6),
('Grao de bico cozido', '139', '8.9', '2.6', '27.4', '5.1', '7', 6),
('Soja cozida', '153', '16.6', '8.0', '11.1', '6.0', '2', 6);

-- Oleos e gorduras (categoria 7)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Azeite de oliva', '884', '0.0', '100.0', '0.0', '0.0', '0', 7),
('Manteiga sem sal', '717', '0.9', '81.0', '0.1', '0.0', '11', 7),
('Margarina', '720', '0.5', '80.0', '0.4', '0.0', '200', 7);

-- Acucares e doces (categoria 8)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Acucar refinado', '387', '0.0', '0.0', '99.9', '0.0', '1', 8),
('Mel de abelha', '304', '0.3', '0.0', '76.0', '0.2', '4', 8),
('Chocolate ao leite', '540', '7.4', '30.3', '59.6', '2.2', '77', 8);

-- Bebidas (categoria 9)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Agua de coco', '22', '0.0', '0.1', '5.3', '0.1', '24', 9),
('Suco de laranja natural', '47', '0.7', '0.1', '11.3', '0.2', '1', 9),
('Cafe infusao sem acucar', '2', '0.1', '0.0', '0.3', '0.0', '2', 9);

-- Miscelaneas (categoria 10)
INSERT INTO alimento (nome, kcal, proteina, gordura, carboidrato, fibra_alimentar, sodio, categoria_alimento_id) VALUES
('Castanha de caju torrada', '574', '18.2', '46.4', '29.1', '3.4', '3', 10),
('Amendoim torrado', '587', '25.3', '49.4', '20.3', '8.5', '1', 10),
('Pipoca estourada', '382', '10.9', '4.3', '77.8', '14.5', '7', 10),
('Salgadinho de milho', '515', '5.8', '27.0', '63.0', '1.8', '720', 10);
