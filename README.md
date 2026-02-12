API Handler - Reto 1 (Maestría en Arquitectura de TI)
Este componente actúa como el punto de entrada único (Entry Point) del sistema de emparejamiento de órdenes. Se encarga de recibir peticiones REST tanto de compradores como de vendedores, asignarles metadatos de tiempo para medición de latencia y prepararlas para el flujo asíncrono.

🚀 Arquitectura y Tecnologías
Lenguaje: Java 17.

Framework: Spring Boot 3.2.2.

Empaquetamiento: Docker (Multi-stage build).

Observabilidad: Implementación de Timestamps personalizados en el DTO para medir saltos entre componentes (ApiHandler -> Gestor Eventos -> Motor).

🛠️ Construcción y Despliegue
Para mantener el servidor limpio, no es necesario instalar Java localmente. Todo el proceso de compilación y ejecución sucede dentro de Docker.

1. Construir la imagen
Ejecuta el siguiente comando en la raíz del proyecto:

Bash
docker build -t apihandler-reto1 .
2. Ejecutar el contenedor
Levanta el servicio exponiendo el puerto 8080:

Bash
docker run -d -p 8080:8080 --name api-handler apihandler-reto1
🚦 Pruebas de Endpoints
El API Handler expone dos endpoints principales según el diagrama de arquitectura:

Orden de Compra (Comprador)
Bash
curl -X POST http://localhost:8080/api/orden-compra \
-H "Content-Type: application/json" \
-d '{"id": "c1", "producto": "AAPL", "cantidad": 10}'
Orden de Venta (Vendedor)
Bash
curl -X POST http://localhost:8080/api/orden-venta \
-H "Content-Type: application/json" \
-d '{"id": "v1", "producto": "AAPL", "cantidad": 10}'
📊 Formato de Salida (Observabilidad)
El componente responde con el objeto de la orden enriquecido con marcas de tiempo en milisegundos:

apihandler_recepcion: Momento exacto en que la petición entró al controlador.

apihandler_salida: Momento previo a la respuesta (o envío al broker en fases futuras).