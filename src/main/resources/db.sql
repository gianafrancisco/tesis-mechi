USE [master]
GO
/****** Object:  Database [Restaurante]    Script Date: 03/08/2015 20:23:44 ******/
CREATE DATABASE [Restaurante]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Restaurante', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\Restaurante.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'Restaurante_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\Restaurante_log.ldf' , SIZE = 18240KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [Restaurante] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Restaurante].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Restaurante] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Restaurante] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Restaurante] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Restaurante] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Restaurante] SET ARITHABORT OFF 
GO
ALTER DATABASE [Restaurante] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Restaurante] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [Restaurante] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Restaurante] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Restaurante] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Restaurante] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Restaurante] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Restaurante] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Restaurante] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Restaurante] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Restaurante] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Restaurante] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Restaurante] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Restaurante] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Restaurante] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Restaurante] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Restaurante] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Restaurante] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Restaurante] SET RECOVERY FULL 
GO
ALTER DATABASE [Restaurante] SET  MULTI_USER 
GO
ALTER DATABASE [Restaurante] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Restaurante] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Restaurante] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Restaurante] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [Restaurante]
GO
/****** Object:  Table [dbo].[Asignaciones]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Asignaciones](
	[idAsignacion] [int] IDENTITY(1,1) NOT NULL,
	[turno] [nvarchar](15) NOT NULL,
	[idUsuario] [int] NOT NULL,
	[idMesa] [int] NOT NULL,
 CONSTRAINT [PK_Asignaciones] PRIMARY KEY CLUSTERED 
(
	[idAsignacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Bebidas]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Bebidas](
	[idBebida] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[precio] [money] NOT NULL,
	[idTipoPlato] [int] NOT NULL,
	[descripcion] [varchar](50) NULL,
	[activo] [bit] NOT NULL,
 CONSTRAINT [PK_Bebidas] PRIMARY KEY CLUSTERED 
(
	[idBebida] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[BebidasPedidos]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BebidasPedidos](
	[idBebida] [int] NOT NULL,
	[idPedido] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Mesas]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Mesas](
	[idMesa] [int] IDENTITY(1,1) NOT NULL,
	[numero] [int] NOT NULL,
	[descripcion] [varchar](50) NOT NULL,
	[idUbicacion] [int] NOT NULL,
	[activo] [bit] NOT NULL,
	[reservarOnline] [bit] NOT NULL,
 CONSTRAINT [PK_Mesas] PRIMARY KEY CLUSTERED 
(
	[idMesa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Pedidos]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Pedidos](
	[idPedido] [int] IDENTITY(1,1) NOT NULL,
	[NroReserva] [int] NOT NULL,
	[fecha] [datetime] NOT NULL,
	[idUsuario] [int] NOT NULL,
	[precio] [float] NOT NULL,
	[estado] [nvarchar](20) NOT NULL,
	[idTicket] [int] NULL,
 CONSTRAINT [PK_Table_1] PRIMARY KEY CLUSTERED 
(
	[idPedido] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Platos]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Platos](
	[idPlato] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](50) NOT NULL,
	[idTipoPlato] [int] NOT NULL,
	[precio] [money] NOT NULL,
	[activo] [bit] NOT NULL,
 CONSTRAINT [PK_Platos] PRIMARY KEY CLUSTERED 
(
	[idPlato] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PlatosPedidos]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PlatosPedidos](
	[idPedido] [int] NOT NULL,
	[idPlato] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PromocionBebida]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PromocionBebida](
	[idPromocion] [int] NOT NULL,
	[idBebida] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Promociones]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promociones](
	[idPromocion] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [nvarchar](300) NOT NULL,
	[fechaDesde] [datetime] NOT NULL,
	[fechaHasta] [datetime] NOT NULL,
	[precio] [money] NOT NULL,
	[activo] [bit] NOT NULL,
 CONSTRAINT [PK_Promociones] PRIMARY KEY CLUSTERED 
(
	[idPromocion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PromocionesPedidos]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PromocionesPedidos](
	[idPromocion] [int] NOT NULL,
	[idPedido] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PromocionPlato]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PromocionPlato](
	[idPromocion] [int] NOT NULL,
	[idPlato] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Reservas]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reservas](
	[nroReserva] [int] IDENTITY(1,1) NOT NULL,
	[estado] [nvarchar](50) NOT NULL,
	[fecha] [nvarchar](30) NOT NULL,
	[hora] [time](7) NOT NULL,
	[cantidadPersonas] [int] NOT NULL,
	[idUsuario] [int] NULL,
	[idMesa] [int] NOT NULL,
	[fechaReserva] [datetime] NOT NULL,
 CONSTRAINT [PK_Reservas] PRIMARY KEY CLUSTERED 
(
	[nroReserva] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Tickets]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tickets](
	[idTicket] [int] IDENTITY(1,1) NOT NULL,
	[fecha] [datetime] NOT NULL,
 CONSTRAINT [PK_Tickets] PRIMARY KEY CLUSTERED 
(
	[idTicket] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[TicketsPedidos]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TicketsPedidos](
	[idTicket] [int] NOT NULL,
	[idPedido] [int] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[TiposPlatos]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TiposPlatos](
	[idTipoPlato] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](50) NOT NULL,
	[activo] [bit] NOT NULL,
	[imagen] [varchar](50) NULL,
 CONSTRAINT [PK_TiposPlatos] PRIMARY KEY CLUSTERED 
(
	[idTipoPlato] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Ubicaciones]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Ubicaciones](
	[idUbicacion] [int] IDENTITY(1,1) NOT NULL,
	[descripcion] [varchar](50) NOT NULL,
	[activo] [bit] NOT NULL,
 CONSTRAINT [PK_Ubicaciones] PRIMARY KEY CLUSTERED 
(
	[idUbicacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 03/08/2015 20:23:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Usuarios](
	[idUsuario] [int] IDENTITY(1,1) NOT NULL,
	[usuario] [nvarchar](30) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[rol] [nvarchar](15) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[apellido] [varchar](50) NOT NULL,
	[dni] [int] NOT NULL,
	[telefono] [nvarchar](30) NULL,
	[calle] [varchar](50) NULL,
	[altura] [int] NULL,
	[fechaNacimiento] [varchar](50) NULL,
	[email] [nvarchar](50) NOT NULL,
	[activo] [bit] NOT NULL,
 CONSTRAINT [PK_Usuarios] PRIMARY KEY CLUSTERED 
(
	[idUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[Mesas] ADD  CONSTRAINT [DF_Mesas_reservarOnline]  DEFAULT ((1)) FOR [reservarOnline]
GO
ALTER TABLE [dbo].[Asignaciones]  WITH CHECK ADD  CONSTRAINT [FK_Asignaciones_Mesas] FOREIGN KEY([idMesa])
REFERENCES [dbo].[Mesas] ([idMesa])
GO
ALTER TABLE [dbo].[Asignaciones] CHECK CONSTRAINT [FK_Asignaciones_Mesas]
GO
ALTER TABLE [dbo].[Asignaciones]  WITH CHECK ADD  CONSTRAINT [FK_Asignaciones_Usuarios] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuarios] ([idUsuario])
GO
ALTER TABLE [dbo].[Asignaciones] CHECK CONSTRAINT [FK_Asignaciones_Usuarios]
GO
ALTER TABLE [dbo].[Bebidas]  WITH CHECK ADD  CONSTRAINT [FK_Bebidas_TiposPlatos] FOREIGN KEY([idTipoPlato])
REFERENCES [dbo].[TiposPlatos] ([idTipoPlato])
GO
ALTER TABLE [dbo].[Bebidas] CHECK CONSTRAINT [FK_Bebidas_TiposPlatos]
GO
ALTER TABLE [dbo].[BebidasPedidos]  WITH CHECK ADD  CONSTRAINT [FK_BebidasPedidos_Bebidas] FOREIGN KEY([idBebida])
REFERENCES [dbo].[Bebidas] ([idBebida])
GO
ALTER TABLE [dbo].[BebidasPedidos] CHECK CONSTRAINT [FK_BebidasPedidos_Bebidas]
GO
ALTER TABLE [dbo].[BebidasPedidos]  WITH CHECK ADD  CONSTRAINT [FK_BebidasPedidos_Pedidos] FOREIGN KEY([idPedido])
REFERENCES [dbo].[Pedidos] ([idPedido])
GO
ALTER TABLE [dbo].[BebidasPedidos] CHECK CONSTRAINT [FK_BebidasPedidos_Pedidos]
GO
ALTER TABLE [dbo].[Mesas]  WITH CHECK ADD  CONSTRAINT [FK_Mesas_Ubicaciones] FOREIGN KEY([idUbicacion])
REFERENCES [dbo].[Ubicaciones] ([idUbicacion])
GO
ALTER TABLE [dbo].[Mesas] CHECK CONSTRAINT [FK_Mesas_Ubicaciones]
GO
ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_k0181t87wgybbv2dbyi46avpp] FOREIGN KEY([idTicket])
REFERENCES [dbo].[Tickets] ([idTicket])
GO
ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_k0181t87wgybbv2dbyi46avpp]
GO
ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_Pedidos_Reserva] FOREIGN KEY([NroReserva])
REFERENCES [dbo].[Reservas] ([nroReserva])
GO
ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_Pedidos_Reserva]
GO
ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_Pedidos_Usuarios] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuarios] ([idUsuario])
GO
ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_Pedidos_Usuarios]
GO
ALTER TABLE [dbo].[Platos]  WITH CHECK ADD  CONSTRAINT [FK_Platos_Tipos] FOREIGN KEY([idTipoPlato])
REFERENCES [dbo].[TiposPlatos] ([idTipoPlato])
GO
ALTER TABLE [dbo].[Platos] CHECK CONSTRAINT [FK_Platos_Tipos]
GO
ALTER TABLE [dbo].[PlatosPedidos]  WITH CHECK ADD  CONSTRAINT [FK_PlatosPedidos_Pedidos] FOREIGN KEY([idPedido])
REFERENCES [dbo].[Pedidos] ([idPedido])
GO
ALTER TABLE [dbo].[PlatosPedidos] CHECK CONSTRAINT [FK_PlatosPedidos_Pedidos]
GO
ALTER TABLE [dbo].[PlatosPedidos]  WITH CHECK ADD  CONSTRAINT [FK_PlatosPedidos_Platos] FOREIGN KEY([idPlato])
REFERENCES [dbo].[Platos] ([idPlato])
GO
ALTER TABLE [dbo].[PlatosPedidos] CHECK CONSTRAINT [FK_PlatosPedidos_Platos]
GO
ALTER TABLE [dbo].[PromocionBebida]  WITH CHECK ADD  CONSTRAINT [FK_Bebida_PromocionBebida] FOREIGN KEY([idBebida])
REFERENCES [dbo].[Bebidas] ([idBebida])
GO
ALTER TABLE [dbo].[PromocionBebida] CHECK CONSTRAINT [FK_Bebida_PromocionBebida]
GO
ALTER TABLE [dbo].[PromocionBebida]  WITH CHECK ADD  CONSTRAINT [FK_Promocion_PromocionBebida] FOREIGN KEY([idPromocion])
REFERENCES [dbo].[Promociones] ([idPromocion])
GO
ALTER TABLE [dbo].[PromocionBebida] CHECK CONSTRAINT [FK_Promocion_PromocionBebida]
GO
ALTER TABLE [dbo].[PromocionesPedidos]  WITH CHECK ADD  CONSTRAINT [FK_PromocionesPedidos_Pedidos] FOREIGN KEY([idPedido])
REFERENCES [dbo].[Pedidos] ([idPedido])
GO
ALTER TABLE [dbo].[PromocionesPedidos] CHECK CONSTRAINT [FK_PromocionesPedidos_Pedidos]
GO
ALTER TABLE [dbo].[PromocionesPedidos]  WITH CHECK ADD  CONSTRAINT [FK_PromocionesPedidos_Promociones] FOREIGN KEY([idPromocion])
REFERENCES [dbo].[Promociones] ([idPromocion])
GO
ALTER TABLE [dbo].[PromocionesPedidos] CHECK CONSTRAINT [FK_PromocionesPedidos_Promociones]
GO
ALTER TABLE [dbo].[PromocionPlato]  WITH CHECK ADD  CONSTRAINT [FK_Plato_PromocionPlato] FOREIGN KEY([idPlato])
REFERENCES [dbo].[Platos] ([idPlato])
GO
ALTER TABLE [dbo].[PromocionPlato] CHECK CONSTRAINT [FK_Plato_PromocionPlato]
GO
ALTER TABLE [dbo].[PromocionPlato]  WITH CHECK ADD  CONSTRAINT [FK_Promocion_PromocionPlato] FOREIGN KEY([idPromocion])
REFERENCES [dbo].[Promociones] ([idPromocion])
GO
ALTER TABLE [dbo].[PromocionPlato] CHECK CONSTRAINT [FK_Promocion_PromocionPlato]
GO
ALTER TABLE [dbo].[Reservas]  WITH CHECK ADD  CONSTRAINT [FK_Reservas_Mesas] FOREIGN KEY([idMesa])
REFERENCES [dbo].[Mesas] ([idMesa])
GO
ALTER TABLE [dbo].[Reservas] CHECK CONSTRAINT [FK_Reservas_Mesas]
GO
ALTER TABLE [dbo].[Reservas]  WITH CHECK ADD  CONSTRAINT [FK_Reservas_Usuarios] FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuarios] ([idUsuario])
GO
ALTER TABLE [dbo].[Reservas] CHECK CONSTRAINT [FK_Reservas_Usuarios]
GO
ALTER TABLE [dbo].[TicketsPedidos]  WITH CHECK ADD  CONSTRAINT [FK_TicketsPedidos_Pedidos] FOREIGN KEY([idPedido])
REFERENCES [dbo].[Pedidos] ([idPedido])
GO
ALTER TABLE [dbo].[TicketsPedidos] CHECK CONSTRAINT [FK_TicketsPedidos_Pedidos]
GO
ALTER TABLE [dbo].[TicketsPedidos]  WITH CHECK ADD  CONSTRAINT [FK_TicketsPedidos_Tickets] FOREIGN KEY([idTicket])
REFERENCES [dbo].[Tickets] ([idTicket])
GO
ALTER TABLE [dbo].[TicketsPedidos] CHECK CONSTRAINT [FK_TicketsPedidos_Tickets]
GO
USE [master]
GO
ALTER DATABASE [Restaurante] SET  READ_WRITE 
GO
