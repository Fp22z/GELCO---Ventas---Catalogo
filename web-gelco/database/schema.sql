-- SCRIPT PARA BD (ESQUEMA DE GELCO)

-- =========================================
-- USUARIOS
-- =========================================
CREATE TABLE dbo.usuarios (
    id INT IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    password_hash NVARCHAR(255) NOT NULL,
    rol NVARCHAR(50) NOT NULL,
    estado BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2 NULL,

    CONSTRAINT PK_usuarios PRIMARY KEY (id),
    CONSTRAINT UQ_usuarios_email UNIQUE (email)
);

-- =========================================
-- CONSULTORAS
-- =========================================
CREATE TABLE dbo.consultoras (
    id INT IDENTITY(1,1),
    usuario_id INT NOT NULL,
    dni NVARCHAR(20) NOT NULL,
    telefono NVARCHAR(20),
    direccion NVARCHAR(150),
    nivel NVARCHAR(50),
    ventas_totales DECIMAL(18,2) NOT NULL DEFAULT 0 CHECK (ventas_totales >= 0),
    updated_at DATETIME2 NULL,

    CONSTRAINT PK_consultoras PRIMARY KEY (id),
    CONSTRAINT UQ_consultoras_usuario UNIQUE (usuario_id),
    CONSTRAINT FK_consultoras_usuario FOREIGN KEY (usuario_id)
        REFERENCES dbo.usuarios(id)
        ON DELETE CASCADE
);

-- =========================================
-- CLIENTES
-- =========================================
CREATE TABLE dbo.clientes (
    id INT IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    telefono NVARCHAR(20),
    direccion NVARCHAR(150),
    preferencias NVARCHAR(MAX),

    CONSTRAINT PK_clientes PRIMARY KEY (id)
);

-- =========================================
-- CATEGORIAS
-- =========================================
CREATE TABLE dbo.categorias (
    id INT IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,

    CONSTRAINT PK_categorias PRIMARY KEY (id)
);

-- =========================================
-- PRODUCTOS
-- =========================================
CREATE TABLE dbo.productos (
    id INT IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(MAX),
    precio DECIMAL(18,2) NOT NULL CHECK (precio >= 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    categoria_id INT NOT NULL,
    activo BIT NOT NULL DEFAULT 1,
    updated_at DATETIME2 NULL,

    CONSTRAINT PK_productos PRIMARY KEY (id),
    CONSTRAINT FK_productos_categoria FOREIGN KEY (categoria_id)
        REFERENCES dbo.categorias(id)
);

-- =========================================
-- PEDIDOS
-- =========================================
CREATE TABLE dbo.pedidos (
    id INT IDENTITY(1,1),
    cliente_id INT NOT NULL,
    consultora_id INT NOT NULL,
    fecha DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    estado NVARCHAR(50) NOT NULL,
    total DECIMAL(18,2) NOT NULL CHECK (total >= 0),
    updated_at DATETIME2 NULL,

    CONSTRAINT PK_pedidos PRIMARY KEY (id),
    CONSTRAINT FK_pedidos_cliente FOREIGN KEY (cliente_id)
        REFERENCES dbo.clientes(id),
    CONSTRAINT FK_pedidos_consultora FOREIGN KEY (consultora_id)
        REFERENCES dbo.consultoras(id)
);

-- =========================================
-- DETALLE PEDIDO
-- =========================================
CREATE TABLE dbo.detalle_pedido (
    id INT IDENTITY(1,1),
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(18,2) NOT NULL CHECK (precio_unitario >= 0),

    CONSTRAINT PK_detalle PRIMARY KEY (id),
    CONSTRAINT FK_detalle_pedido FOREIGN KEY (pedido_id)
        REFERENCES dbo.pedidos(id) ON DELETE CASCADE,
    CONSTRAINT FK_detalle_producto FOREIGN KEY (producto_id)
        REFERENCES dbo.productos(id)
);

-- =========================================
-- ORDENES DE COMPRA
-- =========================================
CREATE TABLE dbo.ordenes_compra (
    id INT IDENTITY(1,1),
    pedido_id INT NOT NULL,
    fecha DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    total DECIMAL(18,2) NOT NULL CHECK (total >= 0),

    CONSTRAINT PK_ordenes PRIMARY KEY (id),
    CONSTRAINT FK_orden_pedido FOREIGN KEY (pedido_id)
        REFERENCES dbo.pedidos(id)
);

-- =========================================
-- CAPACITACIONES
-- =========================================
CREATE TABLE dbo.capacitaciones (
    id INT IDENTITY(1,1),
    titulo NVARCHAR(150) NOT NULL,
    descripcion NVARCHAR(MAX),
    fecha DATETIME2,

    CONSTRAINT PK_capacitaciones PRIMARY KEY (id)
);

-- =========================================
-- CAPACITACION CONSULTORA
-- =========================================
CREATE TABLE dbo.capacitacion_consultora (
    id INT IDENTITY(1,1),
    consultora_id INT NOT NULL,
    capacitacion_id INT NOT NULL,
    completado BIT NOT NULL,
    puntaje DECIMAL(5,2) CHECK (puntaje BETWEEN 0 AND 100),

    CONSTRAINT PK_cap_cons PRIMARY KEY (id),
    CONSTRAINT FK_cap_consultora FOREIGN KEY (consultora_id)
        REFERENCES dbo.consultoras(id),
    CONSTRAINT FK_cap_capacitacion FOREIGN KEY (capacitacion_id)
        REFERENCES dbo.capacitaciones(id)
);

-- =========================================
-- INVENTARIO MOVIMIENTOS
-- =========================================
CREATE TABLE dbo.inventario_movimientos (
    id INT IDENTITY(1,1),
    producto_id INT NOT NULL,
    tipo NVARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    fecha DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT PK_inventario PRIMARY KEY (id),
    CONSTRAINT FK_inv_producto FOREIGN KEY (producto_id)
        REFERENCES dbo.productos(id)
);

-- =========================================
-- DEVOLUCIONES
-- =========================================
CREATE TABLE dbo.devoluciones (
    id INT IDENTITY(1,1),
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    estado NVARCHAR(50) NOT NULL,
    motivo NVARCHAR(MAX),
    fecha DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT PK_devoluciones PRIMARY KEY (id),
    CONSTRAINT FK_dev_pedido FOREIGN KEY (pedido_id)
        REFERENCES dbo.pedidos(id),
    CONSTRAINT FK_dev_producto FOREIGN KEY (producto_id)
        REFERENCES dbo.productos(id)
);

-- =========================================
-- VEHICULOS
-- =========================================
CREATE TABLE dbo.vehiculos (
    id INT IDENTITY(1,1),
    placa NVARCHAR(20) NOT NULL,
    tipo NVARCHAR(50),
    estado NVARCHAR(50),

    CONSTRAINT PK_vehiculos PRIMARY KEY (id),
    CONSTRAINT UQ_vehiculo_placa UNIQUE (placa)
);

-- =========================================
-- CHOFERES
-- =========================================
CREATE TABLE dbo.choferes (
    id INT IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    licencia NVARCHAR(50) NOT NULL,

    CONSTRAINT PK_choferes PRIMARY KEY (id)
);

-- =========================================
-- ZONAS
-- =========================================
CREATE TABLE dbo.zonas (
    id INT IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,

    CONSTRAINT PK_zonas PRIMARY KEY (id)
);

-- =========================================
-- RUTAS
-- =========================================
CREATE TABLE dbo.rutas (
    id INT IDENTITY(1,1),
    vehiculo_id INT NOT NULL,
    chofer_id INT NOT NULL,
    zona_id INT NOT NULL,
    fecha DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT PK_rutas PRIMARY KEY (id),
    CONSTRAINT FK_ruta_vehiculo FOREIGN KEY (vehiculo_id)
        REFERENCES dbo.vehiculos(id),
    CONSTRAINT FK_ruta_chofer FOREIGN KEY (chofer_id)
        REFERENCES dbo.choferes(id),
    CONSTRAINT FK_ruta_zona FOREIGN KEY (zona_id)
        REFERENCES dbo.zonas(id)
);

-- =========================================
-- RUTA PEDIDOS
-- =========================================
CREATE TABLE dbo.ruta_pedidos (
    id INT IDENTITY(1,1),
    ruta_id INT NOT NULL,
    pedido_id INT NOT NULL,

    CONSTRAINT PK_ruta_pedidos PRIMARY KEY (id),
    CONSTRAINT FK_rp_ruta FOREIGN KEY (ruta_id)
        REFERENCES dbo.rutas(id),
    CONSTRAINT FK_rp_pedido FOREIGN KEY (pedido_id)
        REFERENCES dbo.pedidos(id)
);

-- =========================================
-- FACTURAS
-- =========================================
CREATE TABLE dbo.facturas (
    id INT IDENTITY(1,1),
    pedido_id INT NOT NULL,
    total DECIMAL(18,2) NOT NULL CHECK (total >= 0),
    fecha DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    estado NVARCHAR(50),

    CONSTRAINT PK_facturas PRIMARY KEY (id),
    CONSTRAINT FK_factura_pedido FOREIGN KEY (pedido_id)
        REFERENCES dbo.pedidos(id)
);

-- =========================================
-- VENTAS CONSULTORA
-- =========================================
CREATE TABLE dbo.ventas_consultora (
    id INT IDENTITY(1,1),
    consultora_id INT NOT NULL,
    mes INT NOT NULL CHECK (mes BETWEEN 1 AND 12),
    anio INT NOT NULL CHECK (anio >= 2000),
    total_ventas DECIMAL(18,2) NOT NULL CHECK (total_ventas >= 0),

    CONSTRAINT PK_ventas PRIMARY KEY (id),
    CONSTRAINT FK_ventas_consultora FOREIGN KEY (consultora_id)
        REFERENCES dbo.consultoras(id),
    CONSTRAINT UQ_ventas_mes_anio UNIQUE (consultora_id, mes, anio)
);

-- =========================================
-- CATEGORIAS CONSULTORA
-- =========================================
CREATE TABLE dbo.categorias_consultora (
    id INT IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    rango_min DECIMAL(18,2),
    rango_max DECIMAL(18,2),

    CONSTRAINT PK_cat_cons PRIMARY KEY (id)
);

-- =========================================
-- INDICES ULTRA OPTIMIZADOS
-- =========================================

-- Pedidos
CREATE INDEX IDX_pedidos_cliente ON dbo.pedidos(cliente_id);
CREATE INDEX IDX_pedidos_consultora ON dbo.pedidos(consultora_id);
CREATE INDEX IDX_pedidos_estado_fecha ON dbo.pedidos(estado, fecha);

-- Productos
CREATE INDEX IDX_productos_categoria ON dbo.productos(categoria_id);

-- Detalle pedido (MEJORADO)
CREATE INDEX IDX_detalle_pedido_compuesto 
ON dbo.detalle_pedido(pedido_id, producto_id);

-- Inventario (MEJORADO)
CREATE INDEX IDX_inv_producto_fecha 
ON dbo.inventario_movimientos(producto_id, fecha);

-- Facturación
CREATE INDEX IDX_facturas_estado_fecha 
ON dbo.facturas(estado, fecha);

-- Rutas
CREATE INDEX IDX_ruta_pedidos ON dbo.ruta_pedidos(ruta_id);