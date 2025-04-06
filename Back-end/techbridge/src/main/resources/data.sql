INSERT INTO endereco VALUES
(1, 'Rua das Trilhas', '100', 'Apto 101', 'Centro', 'São Paulo', 'SP', '01001000'),
(2, 'Av. Aventuras', '200', '', 'Jardins', 'Campinas', 'SP', '13010100'),
(3, 'Rua do Horizonte', '123', '', 'Leste', 'Rio de Janeiro', 'RJ', '20040002'),
(4, 'Estrada da Natureza', '500', '', 'Verde Vale', 'Belo Horizonte', 'MG', '30123000'),
(5, 'Alameda das Águas', '45', 'Casa 2', 'Praia Azul', 'Florianópolis', 'SC', '88010001'),
(6, 'Rua do Pico', '80', '', 'Montanha', 'Curitiba', 'PR', '80010000'),
(7, 'Rua dos Ventos', '99', 'Cobertura', 'Centro', 'Fortaleza', 'CE', '60000000'),
(8, 'Av. do Sol', '70', 'Bloco B', 'Jardim Solar', 'Natal', 'RN', '59010010'),
(9, 'Travessa da Aventura', '10', '', 'Trilheiros', 'Porto Alegre', 'RS', '90010100'),
(10, 'Rua do Rapel', '300', '', 'Alturas', 'Campo Grande', 'MS', '79010000');

INSERT INTO tipo_usuario VALUES
(1, 'Aventureiro'),
(2, 'Guia'),
(3, 'Administrador');

INSERT INTO nivel_aventureiro VALUES
(1, 0, 'Iniciante'),
(2, 100, 'Intermediário'),
(3, 200, 'Avançado');

INSERT INTO usuario VALUES
(1, 'João Aventureiro', 'joao@aventura.com', 'senha123', 1, 1),
(2, 'Maria Guia', 'maria@aventura.com', 'senha456', 2, NULL),
(3, 'Carlos Admin', 'carlos@aventura.com', 'admin123', 3, NULL),
(4, 'Ana Trilheira', 'ana@trilhas.com', 'ana456', 1, 2),
(5, 'Bruno Guia', 'bruno@aventura.com', 'bruno456', 2, NULL),
(6, 'Fernanda Admin', 'fernanda@admin.com', 'admin456', 3, NULL),
(7, 'Diego Aventureiro', 'diego@trilhas.com', 'diego123', 1, 3),
(8, 'Larissa Guia', 'larissa@aventura.com', 'larissa123', 2, NULL),
(9, 'Igor Admin', 'igor@admin.com', 'admin789', 3, NULL),
(10, 'Marina Aventureira', 'marina@aventura.com', 'marina123', 1, 2);

INSERT INTO convidado VALUES
(1, 'Guilherme', 'galnos.oliveira.13@gmail.com', 1, 1),
(2, 'Maria', 'maria.teste@gmail.com', 1, 1),
(3, 'Rodrigo', 'rodrigo.teste@gmail.com', 1, 4),
(4, 'Eduardo', 'eduardo.teste@gmail.com', 1, 4);

INSERT INTO informacoes_pessoais VALUES
(1, '2000-05-20', '11999998888', '12345678901', '123456789', '11988887777', 'Português', 'O aventureiro relatou sedentarismo, mas está animado para atividades leves.', 1, 1, 1),
(2, '1995-08-10', '11999887766', '98765432100', '987654321', '11977778888', 'Inglês', NULL, 4, 2, 1),
(3, '1990-03-15', '21988887777', '45678912300', '456789123', '21999996666', 'Espanhol', NULL, 7, 3, 1),
(4, '1988-07-25', '31999998888', '32165498700', '321654987', '31988889999', 'Francês', NULL, 10, 4, 1);

INSERT INTO agenda_responsavel VALUES
(1, '2025-04-10 14:00:00', 2),
(2, '2025-04-12 09:00:00', 3),
(3, '2025-04-15 08:00:00', 5),
(4, '2025-04-18 15:30:00', 8),
(5, '2025-04-20 10:00:00', 2),
(6, '2025-04-22 13:00:00', 5),
(7, '2025-04-25 07:30:00', 8),
(8, '2025-04-28 16:00:00', 2),
(9, '2025-05-01 11:00:00', 5),
(10, '2025-05-03 09:30:00', 2);

INSERT INTO agendamento_anamnese VALUES
(1, 1, 1);

INSERT INTO tipo_evento VALUES
(1, 'Trilha Ecológica', 50.00),
(2, 'Rapel Extremo', 80.00),
(3, 'Caminhada Noturna', 40.00),
(4, 'Exploração de Caverna', 70.00),
(5, 'Acampamento Selvagem', 90.00),
(6, 'Travessia de Rios', 60.00),
(7, 'Escalada em Rocha', 100.00),
(8, 'Trilha Histórica', 45.00),
(9, 'Expedição Fotográfica', 65.00),
(10, 'Corrida de Aventura', 85.00);

INSERT INTO evento VALUES
(1, 'Trilha da Pedra', 'Trilha até o topo da Pedra Grande', 'Médio', 5.5, 0, 2, 1),
(2, 'Rapel na Serra', 'Descida de rapel com supervisão', 'Difícil', 2.0, 0, 2, 2),
(3, 'Trilha do Pôr do Sol', 'Caminhada ao entardecer', 'Fácil', 3.5, 0, 5, 3),
(4, 'Caverna Secreta', 'Exploração em caverna natural', 'Difícil', 4.2, 0, 5, 4),
(5, 'Acampamento Verde', 'Acampamento em área isolada', 'Médio', 10.0, 0, 8, 5),
(6, 'Trilha do Rio', 'Travessia com obstáculos aquáticos', 'Médio', 6.7, 0, 8, 6),
(7, 'Escalada do Vale', 'Subida em rochas com guia', 'Difícil', 2.5, 0, 2, 7),
(8, 'Trilha Colonial', 'Rota histórica pelas trilhas', 'Fácil', 4.3, 0, 5, 8),
(9, 'Foto Expedição', 'Percurso com foco em paisagens', 'Médio', 5.0, 0, 5, 9),
(10, 'Corrida Aventura', 'Prova de resistência em trilha', 'Difícil', 8.5, 0, 2, 10);

INSERT INTO ativacao_evento VALUES
(1, 1, '08:00:00', '12:00:00', 4.0, 20, '2025-05-10', 1),
(2, 2, '09:30:00', '11:00:00', 1.5, 10, '2025-05-12', 2),
(3, 3, '16:00:00', '18:00:00', 2.0, 15, '2025-05-15', 3),
(4, 4, '13:00:00', '16:00:00', 3.0, 8, '2025-05-18', 4),
(5, 5, '10:00:00', '12:30:00', 2.5, 12, '2025-05-20', 5),
(6, 6, '11:00:00', '14:00:00', 3.0, 18, '2025-05-22', 6),
(7, 7, '08:30:00', '10:00:00', 1.5, 6, '2025-05-25', 7),
(8, 8, '09:00:00', '11:30:00', 2.5, 20, '2025-05-27', 8),
(9, 9, '15:00:00', '17:30:00', 2.5, 16, '2025-05-30', 9),
(10, 10, '07:00:00', '09:00:00', 2.0, 25, '2025-06-01', 10);

INSERT INTO inscricao VALUES
(1, 1, CURRENT_TIMESTAMP, 1),
(2, 4, CURRENT_TIMESTAMP, 1),
(3, 7, CURRENT_TIMESTAMP, 2),
(4, 10, CURRENT_TIMESTAMP, 3),
(5, 1, CURRENT_TIMESTAMP, 4),
(6, 4, CURRENT_TIMESTAMP, 5),
(7, 7, CURRENT_TIMESTAMP, 6),
(8, 10, CURRENT_TIMESTAMP, 7),
(9, 1, CURRENT_TIMESTAMP, 8),
(10, 4, CURRENT_TIMESTAMP, 9);

INSERT INTO comentario VALUES
(1, 'A trilha foi maravilhosa, com vistas incríveis!', CURRENT_TIMESTAMP, 1, 1),
(2, 'A organização do rapel foi excelente. Recomendo muito!', CURRENT_TIMESTAMP, 7, 2),
(3, 'Um entardecer inesquecível na trilha. Quero repetir!', CURRENT_TIMESTAMP, 10, 3),
(4, 'A caverna era maior do que imaginei, aventura top!', CURRENT_TIMESTAMP, 1, 4),
(5, 'Ótima experiência de acampamento, bem equipada.', CURRENT_TIMESTAMP, 4, 5),
(6, 'A travessia do rio foi um desafio divertido!', CURRENT_TIMESTAMP, 7, 6),
(7, 'Nunca tinha escalado antes, foi uma experiência única.', CURRENT_TIMESTAMP, 10, 7),
(8, 'Trilha com muita história e cultura. Adorei.', CURRENT_TIMESTAMP, 1, 8),
(9, 'Fotos lindas garantidas nesse percurso!', CURRENT_TIMESTAMP, 4, 9),
(10, 'Prova desafiadora, muito bem organizada.', CURRENT_TIMESTAMP, 7, 10);